package org.rssi.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		System.out.println("进入服务器了");
		String uploadName = request.getParameter("name");
		// 定义文件路径：指定上传位置(服务器路径)
		String path = request.getSession().getServletContext().getRealPath("upload");
		path = path + "\\" + uploadName + ".txt";
		// 将数据上传路径和数据编号存入javaBean的PathAndNumber pan中
		PathAndNumber pan = new PathAndNumber();
		pan.setName(uploadName);
		pan.setPath(path);

		InputStream is = request.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		String result = "";
		try {
			result = saveFile(dis, path);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传失败！！");
			result = "uploaderror";
		}

		request.getSession().invalidate();
		response.setContentType("text/html;charset=UTF-8");
		ObjectOutputStream dos = new ObjectOutputStream(response.getOutputStream());
		dos.writeObject(result);
		dos.flush();
		dos.close();
		dis.close();
		is.close();

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
			System.out.println("上传成功啦！！！");
		} else {

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 保存文件
	 * 
	 * @param dis
	 * @return
	 */
	private String saveFile(DataInputStream dis, String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fps = null;
		try {
			fps = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;

		try {
			while ((length = dis.read(buffer)) != -1) {
				fps.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fps.flush();
			fps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
