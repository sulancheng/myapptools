package com.susu.hh.myapptools.activity.gridviewtuoz;

public class DragView {

	private int id;
	private String name;
	private int resid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResid() {
		return resid;
	}
	public void setResid(int resid) {
		this.resid = resid;
	}

	@Override
	public String toString() {
		return "DragView{" +
				"id=" + id +
				", name='" + name + '\'' +
				", resid=" + resid +
				'}';
	}
}
