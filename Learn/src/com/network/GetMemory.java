package com.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dao.MemData;
import com.dao.MemList;


public class GetMemory extends BaseNetworkMethod<MemList> {
	private static final long serialVersionUID = 1L;

	public GetMemory(String url) {
		super(url);		
	}

	@Override
	public MemList parseResult(String data) {
		try {
			mData= new MemList();
			JSONObject mdata = new JSONObject(data);
			JSONArray arr = mdata.getJSONArray("memory");
			for(int i = 0; i<arr.length(); i++){
				mData.getList().add(new MemData(arr.getJSONObject(i).getString("text"), arr.getJSONObject(i).getString("header"), arr.getJSONObject(i).getString("data")));
			}
			return mData;
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		return null;
	}

}
