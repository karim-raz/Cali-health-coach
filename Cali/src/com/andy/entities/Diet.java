package com.andy.entities;

public class Diet {

	String defaul;
	String max;

	public Diet(String max, String defaul) {
		this.defaul = defaul;
		this.max = max;

	}

	public String getDefaul() {
		return defaul;
	}

	public void setDefaul(String defaul) {
		this.defaul = defaul;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

}
