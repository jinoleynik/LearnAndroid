package com.network;

import java.io.IOException;
import java.io.Serializable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public abstract class BaseNetworkMethod<R> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mUrl;
	protected R mData;
	private DefaultHttpClient mClient;

	public BaseNetworkMethod(String url) {
		mUrl = url;
		mClient = new DefaultHttpClient();
	}

	public void execute() {

		try {
			final HttpGet request = new HttpGet(mUrl);
			HttpResponse response = mClient.execute(request);
			final HttpEntity entity = response.getEntity();
			parseResult(EntityUtils.toString(entity, "utf-8"));
	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public abstract R parseResult(String data);

	public R getResult() {
		return mData;
	}
}
