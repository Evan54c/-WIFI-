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
		// �����������Ӧ�����ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("�����������");
		String uploadName = request.getParameter("name");
		// �����ļ�·����ָ���ϴ�λ��(������·��)
		String path0 = request.getSession().getServletContext().getRealPath("upload");
		String path = path0 + "\\unknown.txt";
		// �������ϴ�·�������ݱ�Ŵ���javaBean��PathAndNumber pan��
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
			System.out.println("�ϴ�ʧ�ܣ���");
			result = "uploaderror";
		}

		// ���������
		CalculateService cs = new CalculateService();
		if (cs.readData(panUnknown)) {// ��ȡָ�ƿ��ļ��ɹ�
			// ָ�ƿ��ļ�·��
			String dataFilePath = path0 + "\\data" + panUnknown.getName() + ".txt";
			// ������ļ�·��
			String testDataFilePath = path;
			// ���������
			ResultPoint resultPoint = cs.calculate(testDataFilePath, dataFilePath);
			// ��Ӧ�����
			String resultout = "result," + resultPoint.getX() + "," + resultPoint.getY();
			System.out.println("������Ϊ��" + resultout);
			request.getSession().invalidate();
			response.setContentType("text/html;charset=UTF-8");
			ObjectOutputStream dos = new ObjectOutputStream(response.getOutputStream());
			dos.writeObject(resultout);
			dos.flush();
			dos.close();
			dis.close();
			is.close();
			// ɾ����������в�������ʱ�ļ�
			try {
				File fileTemporary1 = new File(dataFilePath);
				if (fileTemporary1.exists() && fileTemporary1.isFile()) {
					fileTemporary1.delete();
					System.out.println("ָ�ƿ���ʱ�ļ�ɾ���ɹ�������");
				}
				File fileTemporary2 = new File(testDataFilePath);
				if (fileTemporary2.exists() && fileTemporary2.isFile()) {
					fileTemporary2.delete();
					System.out.println("����������ʱ�ļ�ɾ���ɹ�������");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ��ת�����������ҳ��
		} else {// ָ�ƿ��ļ���ȡʧ��
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
