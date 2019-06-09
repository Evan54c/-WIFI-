package org.rssi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.rssi.entity.PathAndNumber;
import org.rssi.entity.ResultPoint;
import org.rssi.service.CalculateService;

public class CalculateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的编码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// 定义文件路径：指定上传位置(服务器路径)
		String path = "";
		String path0 = "";
		int dno = -1;
		// 记录id和文件路径的类
		PathAndNumber panUnknown = new PathAndNumber();
		// 上传
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {// 判断前台form是否有mutipart属性
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 通过parseRequest解析form中的所有请求字段，并保存在items集合中
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();

					// 判断前端字段是普通form表单字段，还是文件字段
					if (item.isFormField()) {
						if (itemName.equals("no")) {// 判断属性值
							dno = Integer.parseInt(item.getString("utf-8"));
							panUnknown.setNo(dno);
						} else {
							System.out.println("其他字段...");
						}
					} else {// 文件字段，文件上传
						// 文件名,getFieldName是获取普通表单字段的name值
						// getName为获取文件名
						String fileName = item.getName();
						// 文件上传位置
						path = request.getSession().getServletContext().getRealPath("upload");
						path0 = path;
						panUnknown.setPath(path0);
						// 获取文件内容并上传
						// 指定文件路径和文件名
						File file = new File(path, fileName);
						path = path + "\\" + fileName;
						// 上传
						item.write(file);
						System.out.println(fileName + "上传成功！！！");
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// 计算服务类
		CalculateService cs = new CalculateService();
		if (cs.readData(panUnknown)) {// 读取指纹库文件成功
			// 指纹库文件路径
			String dataFilePath = path0 + "\\data" + panUnknown.getNo() + ".txt";
			// 待测点文件路径
			String testDataFilePath = path;
			// 计算坐标点
			ResultPoint resultPoint = cs.calculate(testDataFilePath, dataFilePath);

			// 删除计算过程中产生的临时文件
			try {
				File fileTemporary1 = new File(dataFilePath);
				if (fileTemporary1.exists() && fileTemporary1.isFile()) {
					// fileTemporary1.delete();
					System.out.println("指纹库临时文件删除成功！！！");
				}
				File fileTemporary2 = new File(testDataFilePath);
				if (fileTemporary2.exists() && fileTemporary2.isFile()) {
					// fileTemporary2.delete();
					System.out.println("待测数据临时文件删除成功！！！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 计算得到的坐标点存入session便于传输
			HttpSession session = request.getSession();
			session.setAttribute("result", resultPoint);
			// 跳转到输出计算结果页面
			request.getRequestDispatcher("result.jsp").forward(request, response);
			return;
		} else {// 指纹库文件读取失败
			// 跳转计算失败界面
			request.getRequestDispatcher("calulateDataNoFind.jsp").forward(request, response);
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
