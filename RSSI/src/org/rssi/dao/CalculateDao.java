package org.rssi.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.rssi.entity.CoordinatePoint;
import org.rssi.entity.ResultPoint;

public class CalculateDao {// 实现RSSI指纹匹配定位算法的类

	// 数据文件读取函数
	public ArrayList<CoordinatePoint> GetData(String dataFilePath) {

		// 各种初始化
		String dataLine = null;
		int floor = 0, num1 = 0;
		double x = 0, y = 0, strength = 0;
		ArrayList<CoordinatePoint> FingerprintMatching = new ArrayList<CoordinatePoint>();
		Map<String, Double> mac = new HashMap<String, Double>();
		File dataFile = null;
		InputStreamReader Reader = null;
		BufferedReader dataFileReader = null;
		// 数据文件

		try {
			// 确定文件路径
			dataFile = new File(dataFilePath);
			// 利用io流和buffer读文件
			Reader = new InputStreamReader(new FileInputStream(dataFile));
			dataFileReader = new BufferedReader(Reader);

			while ((dataLine = dataFileReader.readLine()) != null || (dataLine = dataFileReader.readLine()) == "") {
				if (dataLine.equals("")) { // 遇空行，将其上一组数据存入向量
					CoordinatePoint coor = new CoordinatePoint(x, y, num1 - 1, floor, mac);
					FingerprintMatching.add(coor);// 上一组数据入类向量
					continue;
				}

				String[] dataLineArray = dataLine.split(" ");// 将每一行数据按空空格分割

				if (dataLineArray[1].charAt(0) == '(') { // 数据组标题行
					// 截掉坐标点首尾的“（”和“）”字符
					dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
					// 将坐标点x，y坐标值以及楼层坐标值分开
					String[] subDataLineArray = dataLineArray[1].split(",");
					// 利用字符串分割提取出具体数字
					x = Double.valueOf(subDataLineArray[0]);
					y = Double.valueOf(subDataLineArray[1]);
					floor = Integer.parseInt(subDataLineArray[2]);

					mac.clear(); // 对上一组mac地址图进行清空
				} else if (dataLineArray[1].charAt(0) != '(') { // 数据组数据行
					// 提取信号强度字符串
					dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
					// 提取信号强度数值
					strength = 0.0 - Double.valueOf(dataLineArray[1]);
					// mac地址及信号强度入图
					mac.put(dataLineArray[0], strength);
				}
			}
			// 最后一组数据入向量
			CoordinatePoint coor = new CoordinatePoint(x, y, num1 - 1, floor, mac);
			FingerprintMatching.add(coor);
			return FingerprintMatching;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return FingerprintMatching;
		} catch (Exception e) {
			e.printStackTrace();
			return FingerprintMatching;
		} finally {// 文件流和buffer的关闭
			if (dataFileReader != null) {
				try {
					dataFileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (Reader != null) {
				try {
					Reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	// 测试数据文件读取函数
	public Map<String, Double> GetTestData(String dataFilePath) {

		// 各种初始化
		String dataLine = null;
		double strength = 0;
		Map<String, Double> mac = new HashMap<String, Double>();
		File dataFile = null;
		InputStreamReader Reader = null;
		BufferedReader dataFileReader = null;
		// 数据文件

		try {
			// 确定文件路径
			dataFile = new File(dataFilePath);
			// 利用io流和buffer读文件
			Reader = new InputStreamReader(new FileInputStream(dataFile));
			dataFileReader = new BufferedReader(Reader);

			while ((dataLine = dataFileReader.readLine()) != null || (dataLine = dataFileReader.readLine()) == "") {

				if (dataLine.equals(""))
					break;
				String[] dataLineArray = dataLine.split(" ");// 将每一行数据按空空格分割

				// 提取信号强度字符串
				dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
				// 提取信号强度数值
				strength = 0.0 - Double.valueOf(dataLineArray[1]);
				// mac地址及信号强度入图
				mac.put(dataLineArray[0], strength);
			}

			return mac;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return mac;
		} catch (Exception e) {
			e.printStackTrace();
			return mac;
		} finally {// 文件流和buffer的关闭
			if (dataFileReader != null) {
				try {
					dataFileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (Reader != null) {
				try {
					Reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public ResultPoint findByMac(Map<String, Double> testmac, ArrayList<CoordinatePoint> FingerprintMatching) //// 实测坐标计算函数
	{
		double totledata = 0, totletest = 0, X = 0, Y = 0;
		ArrayList<Double> XR = new ArrayList<Double>(), YR = new ArrayList<Double>(), perData = new ArrayList<Double>(),
				perTest = new ArrayList<Double>(), Relation = new ArrayList<Double>(), Weight = new ArrayList<Double>();

		// 利用迭代器对参考数据数组进行遍历
		Iterator<CoordinatePoint> it = FingerprintMatching.iterator();
		while (it.hasNext()) {
			CoordinatePoint pointData = it.next();
			X = pointData.getX();
			Y = pointData.getY();
			XR.add(X); // 每个数据点的X坐标
			YR.add(Y); // 每个数据点的Y坐标
			// 对待测数据mac地址及强度信息的键值对进行遍历的迭代器
			Iterator<Map.Entry<String, Double>> test = testmac.entrySet().iterator();
			while (test.hasNext()) { //// 对待测数据mac地址及强度信息的键值对进行遍历
				Map.Entry<String, Double> test0 = test.next();
				String test1 = test0.getKey(); // 待测数据的的每个mac地址
				double test2 = test0.getValue(); // 待测数据的每个信号强度
				Map<String, Double> data = pointData.getMap();// 已知数据点的mac地址及强度信息图

				if (data.containsKey(test1)) // 在已知数据中查找待测数据的mac地址
				{
					double num = data.get(test1);
					perData.add(num); // 每个数据信号强度向量
					perTest.add(test2); // 待测数据信号强度向量
					totledata = totledata + num;
					totletest = totletest + test2;
				}

			}
			totledata = totledata / perData.size(); // 每个数据信号强度均值
			totletest = totletest / perTest.size(); // 实测数据信号强度均值

			double IO = 0, IR1 = 0, IR2 = 0;
			for (int i = 0; i < perTest.size(); i++) {
				double io = perTest.get(i) - totletest;
				double ir = perData.get(i) - totledata;
				IO = IO + io * ir; // 相关系数分子
				IR1 = IR1 + io * io; // 相关系数分母第一部分
				IR2 = IR2 + ir * ir; // 相关系数分母第二部分
			}

			totledata = 0;
			totletest = 0;
			perData.clear();
			perTest.clear(); // 清空数据，便于下一次使用

			Relation.add(IO / Math.sqrt(IR1 * IR2)); // 相关系数向量

		}

		double sum = 0;
		for (int k = 0; k < Relation.size(); k++) {
			sum = sum + Relation.get(k) * Relation.get(k);
		}

		for (int i = 0; i < Relation.size(); i++) // 由相关系数计算各组数据权重
		{
			Weight.add(Relation.get(i) * Relation.get(i) / sum);
		}

		double Xo = 0, Yo = 0;
		for (int j = 0; j < Weight.size(); j++) // 由权重计算坐标
		{
			Xo = Xo + Weight.get(j) * XR.get(j);
			Yo = Yo + Weight.get(j) * YR.get(j);

		}

		ResultPoint p = new ResultPoint(Xo, Yo);
		return p;
	}

}
