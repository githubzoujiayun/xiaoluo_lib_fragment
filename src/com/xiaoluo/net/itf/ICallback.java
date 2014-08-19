package com.xiaoluo.net.itf;

import org.apache.http.HttpResponse;

import com.xiaoluo.exceptions.AppException;

public interface ICallback<T> {
	void onFailure(Exception result);
	void onSuccess(T result);
	
	/**
	 * 将从服务器得到的HttpResponse进行解析，解析完成以后，返回给UI线程
	 * @param response 
	 * @param mProgressListener 
	 * @throws AppException 
	 */
	T handler(HttpResponse response, IProgressListener mProgressListener) throws AppException;
	
	/**
	 * 检测是否关闭数据请求
	 * @throws AppException 
	 */
	void checkIfCanceled() throws AppException;
	
	/**
	 * 关闭数据请求
	 */
	void cancel();
	
	/**
	 * 预处理
	 * @param object
	 * @return
	 */
	T onPreHandler(T t);
}
