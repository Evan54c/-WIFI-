package org.rssi.entity;

public class PathAndNumber {
	private String path;
	private String name;

	public PathAndNumber() {
		super();

	}

	public PathAndNumber(String path, String name) {
		super();
		this.path = path;
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
