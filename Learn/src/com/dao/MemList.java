package com.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<MemData> mList;

	public MemList(){
		mList = new ArrayList<MemData>();
	}
	public List<MemData> getList() {
		return mList;
	}

	public void setList(List<MemData> mList) {
		this.mList = mList;
	}

}
