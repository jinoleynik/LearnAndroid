package com.dao;

import java.io.Serializable;

public class MemData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mText;
	private String mHeader;
	private String mDate;
	public MemData(String text, String header, String date){
		mText = text;
		mHeader = header;
		mDate = date;
	}
	public String getText() {
		return mText;
	}
	public void setText(String mText) {
		this.mText = mText;
	}
	public String getHeader() {
		return mHeader;
	}
	public void setHeader(String mHeader) {
		this.mHeader = mHeader;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String mDate) {
		this.mDate = mDate;
	}

}
