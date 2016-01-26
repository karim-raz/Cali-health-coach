package com.andy.adapter;

/**
 * Created by Justin on 2/2/14.
 */
public class CardItemData {
	private int m_text1;
	private String m_text2;
	private String m_text3;
	String val1;
	String maxVal1;

	public CardItemData(int text1, String text2, String text3, String val1,
			String maxVal1) {

		m_text1 = text1;
		m_text2 = text2;
		m_text3 = text3;
		this.val1 = val1;
		this.maxVal1 = maxVal1;

	}

	public String getVal1() {
		return val1;
	}

	public String getMaxVal1() {
		return maxVal1;
	}

	public int getText1() {
		return m_text1;
	}

	public String getText2() {
		return m_text2;
	}

	public String getText3() {
		return m_text3;
	}
}
