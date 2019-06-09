package org.rssi.service;

import java.util.ArrayList;
import java.util.Map;

import org.rssi.dao.CalculateDao;
import org.rssi.dao.JDBCClobDao;
import org.rssi.entity.CoordinatePoint;
import org.rssi.entity.PathAndNumber;
import org.rssi.entity.ResultPoint;

public class CalculateService {
	JDBCClobDao jd = new JDBCClobDao();
	CalculateDao ca = new CalculateDao();

	public boolean readData(PathAndNumber panUnknown) {// ����Ӧ��ŵ����ݴ����ݿ��ж������ɹ�Ϊtrue
		return jd.clobRead(panUnknown);
	}

	public ResultPoint calculate(String testDataFilePath, String dataFilePath) {// ���ö����Ĳɼ����ݺʹ������ݼ���������

		Map<String, Double> testmac = ca.GetTestData(testDataFilePath);// ��ȡ�������ݵ��ļ�
		ArrayList<CoordinatePoint> FingerprintMatching = ca.GetData(dataFilePath);// ��ȡ��֪���ݵ���ļ�
		return ca.findByMac(testmac, FingerprintMatching);// �����������ؽ����
	}
}
