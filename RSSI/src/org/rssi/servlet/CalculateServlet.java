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
		// �����������Ӧ�ı����ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// �����ļ�·����ָ���ϴ�λ��(������·��)
		String path = "";
		String path0 = "";
		int dno = -1;
		// ��¼id���ļ�·������
		PathAndNumber panUnknown = new PathAndNumber();
		// �ϴ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {// �ж�ǰ̨form�Ƿ���mutipart����
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			// ͨ��parseRequest����form�е����������ֶΣ���������items������
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();

					// �ж�ǰ���ֶ�����ͨform���ֶΣ������ļ��ֶ�
					if (item.isFormField()) {
						if (itemName.equals("no")) {// �ж�����ֵ
							dno = Integer.parseInt(item.getString("utf-8"));
							panUnknown.setNo(dno);
						} else {
							System.out.println("�����ֶ�...");
						}
					} else {// �ļ��ֶΣ��ļ��ϴ�
						// �ļ���,getFieldName�ǻ�ȡ��ͨ���ֶε�nameֵ
						// getNameΪ��ȡ�ļ���
						String fileName = item.getName();
						// �ļ��ϴ�λ��
						path = request.getSession().getServletContext().getRealPath("upload");
						path0 = path;
						panUnknown.setPath(path0);
						// ��ȡ�ļ����ݲ��ϴ�
						// ָ���ļ�·�����ļ���
						File file = new File(path, fileName);
						path = path + "\\" + fileName;
						// �ϴ�
						item.write(file);
						System.out.println(fileName + "�ϴ��ɹ�������");
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// ���������
		CalculateService cs = new CalculateService();
		if (cs.readData(panUnknown)) {// ��ȡָ�ƿ��ļ��ɹ�
			// ָ�ƿ��ļ�·��
			String dataFilePath = path0 + "\\data" + panUnknown.getNo() + ".txt";
			// ������ļ�·��
			String testDataFilePath = path;
			// ���������
			ResultPoint resultPoint = cs.calculate(testDataFilePath, dataFilePath);

			// ɾ����������в�������ʱ�ļ�
			try {
				File fileTemporary1 = new File(dataFilePath);
				if (fileTemporary1.exists() && fileTemporary1.isFile()) {
					// fileTemporary1.delete();
					System.out.println("ָ�ƿ���ʱ�ļ�ɾ���ɹ�������");
				}
				File fileTemporary2 = new File(testDataFilePath);
				if (fileTemporary2.exists() && fileTemporary2.isFile()) {
					// fileTemporary2.delete();
					System.out.println("����������ʱ�ļ�ɾ���ɹ�������");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ����õ�����������session���ڴ���
			HttpSession session = request.getSession();
			session.setAttribute("result", resultPoint);
			// ��ת�����������ҳ��
			request.getRequestDispatcher("result.jsp").forward(request, response);
			return;
		} else {// ָ�ƿ��ļ���ȡʧ��
			// ��ת����ʧ�ܽ���
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
