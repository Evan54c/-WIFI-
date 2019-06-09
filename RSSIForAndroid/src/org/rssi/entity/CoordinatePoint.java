package org.rssi.entity;

import java.util.HashMap;
import java.util.Map;

public class CoordinatePoint {// 存放每个数据点,及其各mac地址强度的类
	private double x;
	private double y; // 每个数据采集点的坐标
	private int number;
	private int floor;
	Map<String, Double> map = new HashMap<String, Double>();// String和Double分别对应测量数据点的的mac地址和强度信息

	public CoordinatePoint() {
		super();
	}

	public CoordinatePoint(double x, double y, int number, int floor, Map<String, Double> map) {
		super();
		this.x = x;
		this.y = y;
		this.number = number;
		this.floor = floor;
		this.map = map;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Map<String, Double> getMap() {
		return map;
	}

	public void setMap(Map<String, Double> map) {
		this.map = map;
	}
}
