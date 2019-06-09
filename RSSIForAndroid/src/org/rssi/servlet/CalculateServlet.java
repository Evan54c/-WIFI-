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
import org.rssi.entity.ResultPoint;
import org.rssi.service.CalculateService;

public class CalculateServlet extends HttpServlet {

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
		String path0 = request.getSession().getServletContext().getRealPath("upload");
		String path = path0 + "\\unknown.txt";
		// 将数据上传路径和数据编号存入javaBean的PathAndNumber pan中
		PathAndNumber panUnknown = new PathAndNumber();
		panUnknown.setName(uploadName);
		panUnknown.setPath(path0);

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

		// 计算服务类
		CalculateService cs = new CalculateService();
		if (cs.readData(panUnknown)) {// 读取指纹库文件成功
			// 指纹库文件路径
			String dataFilePath = path0 + "\\data" + panUnknown.getName() + ".txt";
			// 待测点文件路径
			String testDataFilePath = path;
			// 计算坐标点
			ResultPoint resultPoint = cs.calculate(testDataFilePath, dataFilePath);
			// 响应输出流
			String resultout = "result," + resultPoint.getX() + "," + resultPoint.getY();
			System.out.println("计算结果为：" + resultout);
			request.getSession().invalidate();
			response.setContentType("text/html;charset=UTF-8");
			ObjectOutputStream dos = new ObjectOutputStream(response.getOutputStream());
			dos.writeObject(resultout);
			dos.flush();
			dos.close();
			dis.close();
			is.close();
			// 删除计算过程中产生的临时文件
			try {
				File fileTemporary1 = new File(dataFilePath);
				if (fileTemporary1.exists() && fileTemporary1.isFile()) {
					fileTemporary1.delete();
					System.out.println("指纹库临时文件删除成功！！！");
				}
				File fileTemporary2 = new File(testDataFilePath);
				if (fileTemporary2.exists() && fileTemporary2.isFile()) {
					fileTemporary2.delete();
					System.out.println("待测数据临时文件删除成功！！！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 跳转到输出计算结果页面
		} else {// 指纹库文件读取失败
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
