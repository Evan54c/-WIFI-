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
	// �����ļ�·����ָ���ϴ�λ��(������·��)

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �����������Ӧ�����ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("�����������");
		String uploadName = request.getParameter("name");
		// �����ļ�·����ָ���ϴ�λ��(������·��)
		String path = request.getSession().getServletContext().getRealPath("upload");
		path = path + "\\" + uploadName + ".txt";
		// �������ϴ�·�������ݱ�Ŵ���javaBean��PathAndNumber pan��
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
			System.out.println("�ϴ�ʧ�ܣ���");
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

		// ����service���й���
		UploadService uploadService = new UploadService();
		boolean dosuccess = false;
		// �ļ��ϴ������ݿ�
		dosuccess = uploadService.addData(pan);
		// ɾ���ϴ������ݿ�����в�������ʱ�ļ�
		try {
			File fileTemporary = new File(pan.getPath());
			if (fileTemporary.exists() && fileTemporary.isFile()) {
				fileTemporary.delete();
				System.out.println("��ʱ�ļ�ɾ���ɹ�������");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �ж��ļ��ϴ������ݿ�����Ƿ�ɹ���������ҳ����ת
		if (dosuccess) {
			System.out.println("�ϴ��ɹ���������");
		} else {

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * �����ļ�
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
