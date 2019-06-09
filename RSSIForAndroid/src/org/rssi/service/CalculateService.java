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

	public boolean readData(PathAndNumber panUnknown) {// 将对应编号的数据从数据库中读出，成功为true
		return jd.clobRead(panUnknown);
	}

	public ResultPoint calculate(String testDataFilePath, String dataFilePath) {// 利用读出的采集数据和待测数据计算出坐标点

		Map<String, Double> testmac = ca.GetTestData(testDataFilePath);// 读取待测数据的文件
		ArrayList<CoordinatePoint> FingerprintMatching = ca.GetData(dataFilePath);// 读取已知数据点的文件
		return ca.findByMac(testmac, FingerprintMatching);// 计算结果并返回结果点
	}
}
