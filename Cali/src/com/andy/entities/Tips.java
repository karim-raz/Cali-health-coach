package com.andy.entities;

public class Tips {

	String Title1;
	String Title2;
	String Title3;
	String Desc1;
	String Desc2;
	String Desc3;
	public byte[] getPhotot() {
		return photot;
	}

	public void setPhotot(byte[] photot) {
		this.photot = photot;
	}

	byte[] photot;
	public Tips(String Title1, String Desc1, String Title2, String Desc2, String Title3, String Desc3,byte[] photot) {

		this.Title1 = Title1;
		this.Desc1 = Desc1;
		this.Title2 = Title2;
		this.Desc2 = Desc2;
		this.Title3 = Title3;
		this.Desc3= Desc3;
		this.photot =photot;
	}

	public String getTitle3() {
		return Title3;
	}

	public void setTitle3(String title3) {
		Title3 = title3;
	}

	public String getDesc3() {
		return Desc3;
	}

	public void setDesc3(String desc3) {
		Desc3 = desc3;
	}

	public String getTitle1() {
		return Title1;
	}

	public void setTitle1(String title1) {
		Title1 = title1;
	}

	public String getTitle2() {
		return Title2;
	}

	public void setTitle2(String title2) {
		Title2 = title2;
	}

	public String getDesc1() {
		return Desc1;
	}

	public void setDesc1(String desc1) {
		Desc1 = desc1;
	}

	public String getDesc2() {
		return Desc2;
	}

	public void setDesc2(String desc2) {
		Desc2 = desc2;
	}

}
