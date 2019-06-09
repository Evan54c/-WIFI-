package org.rssi.entity;

public class PathAndNumber {
	private String path;
	private int no;

	public PathAndNumber() {
		super();

	}

	public PathAndNumber(String path) {
		super();
		this.path = path;
	}

	public PathAndNumber(String path, int no) {
		super();
		this.path = path;
		this.no = no;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
}
