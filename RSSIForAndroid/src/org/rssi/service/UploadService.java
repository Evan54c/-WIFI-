package org.rssi.service;

import org.rssi.dao.JDBCClobDao;
import org.rssi.entity.PathAndNumber;

public class UploadService {
	JDBCClobDao jd = new JDBCClobDao();

	public boolean addData(PathAndNumber pan) {
		if (!jd.clobQuery(pan.getName())) {// ���ݿⲻ���ڸñ���ļ�ʱ�������ݿ�
			jd.clobWrite(pan);
			return true;
		} else {// ���ݿ����Ѵ��ڸ������ݱ��
			System.out.println("�˱���ļ��Ѵ��ڣ�����");
			return false;
		}
	}

}
