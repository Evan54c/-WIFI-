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
	// �����ļ�·����ָ���ϴ�λ��(������·��)

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �����������Ӧ�����ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// �����ļ�·����ָ���ϴ�λ��(������·��)
		String path;
		// �������ϴ�·�������ݱ�Ŵ���javaBean��PathAndNumber pan��
		PathAndNumber pan = new PathAndNumber();
		// �ϴ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {// �ж�ǰ̨form�Ƿ���mutipart����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ͨ��parseRequest����form�е����������ֶΣ���������items������
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					int dno = -1;
					// �ж�ǰ���ֶ�����ͨform���ֶΣ������ļ��ֶ�
					if (item.isFormField()) {
						if (itemName.equals("no")) {// �ж�����ֵ
							dno = Integer.parseInt(item.getString("utf-8"));
							pan.setNo(dno);
						} else {
							System.out.println("�����ֶ�...");
						}
					} else {// �ļ��ֶΣ��ļ��ϴ�
						// �ļ���,getFieldName�ǻ�ȡ��ͨ���ֶε�nameֵ
						// getNameΪ��ȡ�ļ���
						String fileName = item.getName();

						// �ļ��ϴ�λ��
						path = request.getSession().getServletContext().getRealPath("upload");
						// ��ȡ�ļ����ݲ��ϴ�
						// ָ���ļ�·�����ļ���
						File file = new File(path, fileName);

						// �����ϴ��ļ������л����ļ��Ĵ�С
						factory.setSizeThreshold(10485760);
						// �����ϴ��ļ��Ĵ�С��С��100Mb
						upload.setSizeMax(104857600);// ��λΪB

						path = path + "\\" + fileName;
						pan.setPath(path);

						item.write(file); // �ϴ�

						System.out.println(fileName + "�ϴ��ɹ�������");

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
