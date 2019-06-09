package org.rssi.entity;

public class ResultPoint {// 存放计算结果的坐标点的javaBean
	private double X;
	private double Y;

	public ResultPoint() {
		super();
	}

	public ResultPoint(double x, double y) {
		super();
		X = x;
		Y = y;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}
}
