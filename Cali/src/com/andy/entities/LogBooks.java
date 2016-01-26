package com.andy.entities;

public class LogBooks {

	String weight;
	String date;

	public LogBooks(String date,String weight) {
		this.weight = weight;
		this.date = date;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
