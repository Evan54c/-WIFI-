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

public class CalculateDao {// ʵ��RSSIָ��ƥ�䶨λ�㷨����

	// �����ļ���ȡ����
	public ArrayList<CoordinatePoint> GetData(String dataFilePath) {

		// ���ֳ�ʼ��
		String dataLine = null;
		int floor = 0, num1 = 0;
		double x = 0, y = 0, strength = 0;
		ArrayList<CoordinatePoint> FingerprintMatching = new ArrayList<CoordinatePoint>();
		Map<String, Double> mac = new HashMap<String, Double>();
		File dataFile = null;
		InputStreamReader Reader = null;
		BufferedReader dataFileReader = null;
		// �����ļ�

		try {
			// ȷ���ļ�·��
			dataFile = new File(dataFilePath);
			// ����io����buffer���ļ�
			Reader = new InputStreamReader(new FileInputStream(dataFile));
			dataFileReader = new BufferedReader(Reader);

			while ((dataLine = dataFileReader.readLine()) != null || (dataLine = dataFileReader.readLine()) == "") {
				if (dataLine.equals("")) { // �����У�������һ�����ݴ�������
					CoordinatePoint coor = new CoordinatePoint(x, y, num1 - 1, floor, mac);
					FingerprintMatching.add(coor);// ��һ��������������
					continue;
				}

				String[] dataLineArray = dataLine.split(" ");// ��ÿһ�����ݰ��տո�ָ�

				if (dataLineArray[1].charAt(0) == '(') { // �����������
					// �ص��������β�ġ������͡������ַ�
					dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
					// �������x��y����ֵ�Լ�¥������ֵ�ֿ�
					String[] subDataLineArray = dataLineArray[1].split(",");
					// �����ַ����ָ���ȡ����������
					x = Double.valueOf(subDataLineArray[0]);
					y = Double.valueOf(subDataLineArray[1]);
					floor = Integer.parseInt(subDataLineArray[2]);

					mac.clear(); // ����һ��mac��ַͼ�������
				} else if (dataLineArray[1].charAt(0) != '(') { // ������������
					// ��ȡ�ź�ǿ���ַ���
					dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
					// ��ȡ�ź�ǿ����ֵ
					strength = 0.0 - Double.valueOf(dataLineArray[1]);
					// mac��ַ���ź�ǿ����ͼ
					mac.put(dataLineArray[0], strength);
				}
			}
			// ���һ������������
			CoordinatePoint coor = new CoordinatePoint(x, y, num1 - 1, floor, mac);
			FingerprintMatching.add(coor);
			return FingerprintMatching;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return FingerprintMatching;
		} catch (Exception e) {
			e.printStackTrace();
			return FingerprintMatching;
		} finally {// �ļ�����buffer�Ĺر�
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

	// ���������ļ���ȡ����
	public Map<String, Double> GetTestData(String dataFilePath) {

		// ���ֳ�ʼ��
		String dataLine = null;
		double strength = 0;
		Map<String, Double> mac = new HashMap<String, Double>();
		File dataFile = null;
		InputStreamReader Reader = null;
		BufferedReader dataFileReader = null;
		// �����ļ�

		try {
			// ȷ���ļ�·��
			dataFile = new File(dataFilePath);
			// ����io����buffer���ļ�
			Reader = new InputStreamReader(new FileInputStream(dataFile));
			dataFileReader = new BufferedReader(Reader);

			while ((dataLine = dataFileReader.readLine()) != null || (dataLine = dataFileReader.readLine()) == "") {

				if (dataLine.equals(""))
					break;
				String[] dataLineArray = dataLine.split(" ");// ��ÿһ�����ݰ��տո�ָ�

				// ��ȡ�ź�ǿ���ַ���
				dataLineArray[1] = dataLineArray[1].substring(1, dataLineArray[1].length() - 1);
				// ��ȡ�ź�ǿ����ֵ
				strength = 0.0 - Double.valueOf(dataLineArray[1]);
				// mac��ַ���ź�ǿ����ͼ
				mac.put(dataLineArray[0], strength);
			}

			return mac;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return mac;
		} catch (Exception e) {
			e.printStackTrace();
			return mac;
		} finally {// �ļ�����buffer�Ĺر�
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

	public ResultPoint findByMac(Map<String, Double> testmac, ArrayList<CoordinatePoint> FingerprintMatching) //// ʵ��������㺯��
	{
		double totledata = 0, totletest = 0, X = 0, Y = 0;
		ArrayList<Double> XR = new ArrayList<Double>(), YR = new ArrayList<Double>(), perData = new ArrayList<Double>(),
				perTest = new ArrayList<Double>(), Relation = new ArrayList<Double>(), Weight = new ArrayList<Double>();

		// ���õ������Բο�����������б���
		Iterator<CoordinatePoint> it = FingerprintMatching.iterator();
		while (it.hasNext()) {
			CoordinatePoint pointData = it.next();
			X = pointData.getX();
			Y = pointData.getY();
			XR.add(X); // ÿ�����ݵ��X����
			YR.add(Y); // ÿ�����ݵ��Y����
			// �Դ�������mac��ַ��ǿ����Ϣ�ļ�ֵ�Խ��б����ĵ�����
			Iterator<Map.Entry<String, Double>> test = testmac.entrySet().iterator();
			while (test.hasNext()) { //// �Դ�������mac��ַ��ǿ����Ϣ�ļ�ֵ�Խ��б���
				Map.Entry<String, Double> test0 = test.next();
				String test1 = test0.getKey(); // �������ݵĵ�ÿ��mac��ַ
				double test2 = test0.getValue(); // �������ݵ�ÿ���ź�ǿ��
				Map<String, Double> data = pointData.getMap();// ��֪���ݵ��mac��ַ��ǿ����Ϣͼ

				if (data.containsKey(test1)) // ����֪�����в��Ҵ������ݵ�mac��ַ
				{
					double num = data.get(test1);
					perData.add(num); // ÿ�������ź�ǿ������
					perTest.add(test2); // ���������ź�ǿ������
					totledata = totledata + num;
					totletest = totletest + test2;
				}

			}
			totledata = totledata / perData.size(); // ÿ�������ź�ǿ�Ⱦ�ֵ
			totletest = totletest / perTest.size(); // ʵ�������ź�ǿ�Ⱦ�ֵ

			double IO = 0, IR1 = 0, IR2 = 0;
			for (int i = 0; i < perTest.size(); i++) {
				double io = perTest.get(i) - totletest;
				double ir = perData.get(i) - totledata;
				IO = IO + io * ir; // ���ϵ������
				IR1 = IR1 + io * io; // ���ϵ����ĸ��һ����
				IR2 = IR2 + ir * ir; // ���ϵ����ĸ�ڶ�����
			}

			totledata = 0;
			totletest = 0;
			perData.clear();
			perTest.clear(); // ������ݣ�������һ��ʹ��

			Relation.add(IO / Math.sqrt(IR1 * IR2)); // ���ϵ������

		}

		double sum = 0;
		for (int k = 0; k < Relation.size(); k++) {
			sum = sum + Relation.get(k) * Relation.get(k);
		}

		for (int i = 0; i < Relation.size(); i++) // �����ϵ�������������Ȩ��
		{
			Weight.add(Relation.get(i) * Relation.get(i) / sum);
		}

		double Xo = 0, Yo = 0;
		for (int j = 0; j < Weight.size(); j++) // ��Ȩ�ؼ�������
		{
			Xo = Xo + Weight.get(j) * XR.get(j);
			Yo = Yo + Weight.get(j) * YR.get(j);

		}

		ResultPoint p = new ResultPoint(Xo, Yo);
		return p;
	}

}
