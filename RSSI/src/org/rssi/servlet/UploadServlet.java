package org.rssi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.rssi.entity.PathAndNumber;
import org.rssi.service.UploadService;

public class UploadServlet extends HttpServlet {
	// 定义文件路径：指定上传位置(服务器路径)

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应编码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// 定义文件路径：指定上传位置(服务器路径)
		String path;
		// 将数据上传路径和数据编号存入javaBean的PathAndNumber pan中
		PathAndNumber pan = new PathAndNumber();
		// 上传
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {// 判断前台form是否有mutipart属性
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 通过parseRequest解析form中的所有请求字段，并保存在items集合中
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					int dno = -1;
					// 判断前端字段是普通form表单字段，还是文件字段
					if (item.isFormField()) {
						if (itemName.equals("no")) {// 判断属性值
							dno = Integer.parseInt(item.getString("utf-8"));
							pan.setNo(dno);
						} else {
							System.out.println("其他字段...");
						}
					} else {// 文件字段，文件上传
						// 文件名,getFieldName是获取普通表单字段的name值
						// getName为获取文件名
						String fileName = item.getName();

						// 文件上传位置
						path = request.getSession().getServletContext().getRealPath("upload");
						// 获取文件内容并上传
						// 指定文件路径和文件名
						File file = new File(path, fileName);

						// 设置上传文件过程中缓冲文件的大小
						factory.setSizeThreshold(10485760);
						// 控制上传文件的大小，小于100Mb
						upload.setSizeMax(104857600);// 单位为B

						path = path + "\\" + fileName;
						pan.setPath(path);

						item.write(file); // 上传

						System.out.println(fileName + "上传成功！！！");

					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

		}

		// 调用service进行管理
		UploadService uploadService = new UploadService();
		boolean dosuccess = false;
		// 文件上传到数据库
		dosuccess = uploadService.addData(pan);

		// 删除上传到数据库过程中产生的临时文件
		try {
			File fileTemporary = new File(pan.getPath());
			if (fileTemporary.exists() && fileTemporary.isFile()) {
				fileTemporary.delete();
				System.out.println("临时文件删除成功！！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 判断文件上传到数据库操作是否成功，并进行页面跳转
		if (dosuccess) {
			response.sendRedirect("uploadsuccess.jsp");
		} else {
			response.sendRedirect("uploadfail.jsp");
		}
		return;

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
