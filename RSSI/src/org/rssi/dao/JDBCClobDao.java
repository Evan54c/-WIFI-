package org.rssi.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.rssi.entity.PathAndNumber;

public class JDBCClobDao {// ʵ�ֶ����ݿ�Ĵ��ļ�����ɾ�Ĳ鹦�ܵ���

	private static final String URL = "jdbc:mysql://localhost:3306/data?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PWD = "12345";

	// ͨ�� jdbc�Ѵ��ı����ݴ洢�����ݿ���
	public void clobWrite(PathAndNumber pan) {// ͨ��javaBean��PathAndNumber pan�������ļ�λ�úͱ����Ϣ
		PreparedStatement pstmt = null;
		Connection connection = null;
		try {
			// a.�������������ؾ����������
			Class.forName("com.mysql.cj.jdbc.Driver");// ���Ӿ����������
			System.out.println("�ɹ�����MySQL��������");
			// b.�����ݿ⽨������
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("�ɹ������ݿ⽨������");
			// c.����sql���ִ��sql�����ɾ�ģ�
			String sql = "INSERT INTO data  VALUES (?,?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pan.getNo());
			// Ҫ�ϴ���������λ��
			File file = new File(pan.getPath());
			InputStream in = new FileInputStream(file);
			Reader reader = new InputStreamReader(in, "utf-8");
			pstmt.setCharacterStream(2, reader, file.length());
			int count = pstmt.executeUpdate();// ����ֵ��ʾ��ɾ�ļ�����¼
			if (count > 0) {
				System.out.println("�����ɹ���");
			}
			reader.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// �������ݿ��еĴ��ļ�����
	public boolean clobRead(PathAndNumber panUnknown) {
		int id = panUnknown.getNo();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			// a.�������������ؾ����������
			Class.forName("com.mysql.cj.jdbc.Driver");// ���Ӿ����������
			System.out.println("�ɹ�����MySQL��������");
			// b.�����ݿ⽨������
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("�ɹ������ݿ⽨������");
			// c.����sql���ִ��sql����飩
			String sql = "select data from data where id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();// ����ֵ��ʾ��ɾ�ļ�����¼
			if (rs.next()) {
				Reader reader = rs.getCharacterStream("data");
				Writer writer = new FileWriter(panUnknown.getPath() + "\\data" + id + ".txt");
				char[] chs = new char[100];
				int len = -1;
				while ((len = reader.read(chs)) != -1) {
					writer.write(chs, 0, len);

				}
				writer.close();
				reader.close();
				flag = true;
			}
			return flag;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// ��ѯ���ݿ��еĴ��ļ�����
	public boolean clobQuery(int id) {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			// a.�������������ؾ����������
			Class.forName("com.mysql.cj.jdbc.Driver");// ���Ӿ����������
			System.out.println("�ɹ�����MySQL��������");
			// b.�����ݿ⽨������
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("�ɹ������ݿ⽨������");
			// c.����sql���ִ��sql����飩
			String sql = "select data from data where id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();// ����ֵ��ʾ��ɾ�ļ�����¼
			if (rs.next()) {
				flag = true;
			}
			return flag;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
