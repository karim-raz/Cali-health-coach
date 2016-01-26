package com.andy.entities;

public class Nutrition {

	String name_N;

	String category_N;
	String Id_N;

	public Nutrition(String Id_N, String name_N, String category_N) {
		this.Id_N = Id_N;
		this.name_N = name_N;
		this.category_N = category_N;

	}

	public String getName_N() {
		return name_N;
	}

	public void setName_N(String name_N) {
		this.name_N = name_N;
	}

	public String getCategory_N() {
		return category_N;
	}

	public void setCategory_N(String category_N) {
		this.category_N = category_N;
	}

	public String getId_N() {
		return Id_N;
	}

	public void setId_N(String id_N) {
		Id_N = id_N;
	}
}
