package com.cookcook.main.http;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RestClient {
	private static final String BASE_URL = "http://dicho2.aws.af.cm/";
//			"http://192.168.1.34:3000/";
//			"http://dicho2.aws.af.cm/";
//			http://192.168.194.1:3000

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		
		client.setTimeout(100000);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void post(Context context, String url, StringEntity entity, String type, JsonHttpResponseHandler responseHandler)
	{
		client.post(context, getAbsoluteUrl(url), entity, type, responseHandler);
	}

	public static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

}
