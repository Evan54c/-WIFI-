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

public class JDBCClobDao {// 实现对数据库的大文件的增删改查功能的类

	private static final String URL = "jdbc:mysql://localhost:3306/data?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PWD = "12345";

	// 通过 jdbc把大文本数据存储到数据库中
	public void clobWrite(PathAndNumber pan) {// 通过javaBean的PathAndNumber pan来传递文件位置和编号信息
		PreparedStatement pstmt = null;
		Connection connection = null;
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("com.mysql.cj.jdbc.Driver");// 增加具体的驱动类
			System.out.println("成功加载MySQL驱动程序");
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("成功与数据库建立链接");
			// c.发送sql语句执行sql命令（增删改）
			String sql = "INSERT INTO data  VALUES (?,?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pan.getNo());
			// 要上传的是数据位置
			File file = new File(pan.getPath());
			InputStream in = new FileInputStream(file);
			Reader reader = new InputStreamReader(in, "utf-8");
			pstmt.setCharacterStream(2, reader, file.length());
			int count = pstmt.executeUpdate();// 返回值表示增删改几条记录
			if (count > 0) {
				System.out.println("操作成功！");
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

	// 读出数据库中的大文件数据
	public boolean clobRead(PathAndNumber panUnknown) {
		int id = panUnknown.getNo();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("com.mysql.cj.jdbc.Driver");// 增加具体的驱动类
			System.out.println("成功加载MySQL驱动程序");
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("成功与数据库建立链接");
			// c.发送sql语句执行sql命令（查）
			String sql = "select data from data where id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();// 返回值表示增删改几条记录
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

	// 查询数据库中的大文件数据
	public boolean clobQuery(int id) {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("com.mysql.cj.jdbc.Driver");// 增加具体的驱动类
			System.out.println("成功加载MySQL驱动程序");
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			System.out.println("成功与数据库建立链接");
			// c.发送sql语句执行sql命令（查）
			String sql = "select data from data where id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();// 返回值表示增删改几条记录
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
