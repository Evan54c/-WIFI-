package org.rssi.service;

import org.rssi.dao.JDBCClobDao;
import org.rssi.entity.PathAndNumber;

public class UploadService {
	JDBCClobDao jd = new JDBCClobDao();

	public boolean addData(PathAndNumber pan) {
		if (!jd.clobQuery(pan.getName())) {// 数据库不存在该编号文件时加入数据库
			jd.clobWrite(pan);
			return true;
		} else {// 数据库中已存在改是数据编号
			System.out.println("此编号文件已存在！！！");
			return false;
		}
	}

}
