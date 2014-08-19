package com.xiaoluo.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import com.xiaoluo.net.itf.ICallback;
import com.xiaoluo.net.itf.IProgressListener;

public class Request {
	public enum RequestMethod{
		GET,POST,DELETE,PUT
	}

	/**
	 * Http请求参数的类型,包括表单,string, byte等
	 */
	protected HttpEntity entity;
	public RequestMethod method;
	protected String url;
	protected String postContent;
	protected Map<String, String> headers;
	private static final String ENCODING = "UTF-8";
	private RequestTask mTask;
	
	/**
	 * 设置回调接口,该接口中的onSuccess和onFilure方法需要在体现在UI线程当中
	 */
	protected ICallback callback;
	protected IProgressListener mProgressListener;
	
	/**
	 * 构造方法
	 * @param url
	 * @param method
	 */
	public Request(String url, RequestMethod method) {
		this.url = url;
		this.method = method;
	}
	
	/**
	 * 获取Http请求参数的类型
	 */
	public HttpEntity getEntity() {
		return entity;
	}
	
	/**
	 * 设置Http请求参数的类型为表单
	 * @param forms
	 */
	public void setEntity(ArrayList<NameValuePair> forms) {
		try {
			entity = new UrlEncodedFormEntity(forms, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置Http请求参数的类型为String
	 * @param postContent
	 */
	public void setEntity(String postContent) {
		try {
			entity = new StringEntity(postContent, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置Http请求参数的类型为byte
	 * @param bytes
	 */
	public void setEntity(byte[] bytes) {
		entity = new ByteArrayEntity(bytes);
	}
	
	/**
	 * 设置回调方法,在UI线程中定义需要请求 返回的 方法
	 * @param callback
	 */
	@SuppressWarnings("rawtypes")
	public void setCallback(ICallback callback){
		this.callback = callback;
	}
	
	/**
	 * UI线程中,执行该方法,开启一个AsyncTask,注意AsyncTask每次使用必须重新new
	 */
	public void execute(){
		mTask = new RequestTask(this);
		mTask.execute();
	}

	public void setProgressListener(IProgressListener mProgressListener) {
		this.mProgressListener = mProgressListener;
	}

	public void cancel() {
//		if(mTask != null) {
//			mTask.cancel(true);
//		}
		
		if(callback != null) {
			callback.cancel();
		}
	}
}
