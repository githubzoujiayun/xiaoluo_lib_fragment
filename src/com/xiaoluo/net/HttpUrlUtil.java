package com.xiaoluo.net;

import java.net.HttpURLConnection;

import com.xiaoluo.exceptions.AppException;
import com.xiaoluo.exceptions.AppException.EnumException;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月19日 - 下午9:21:20
 */
public class HttpUrlUtil {
	
	/**
	 * 执行HTTP方法
	 * @param request 设置请求类型
	 * @return
	 * @throws AppException
	 */
	public static HttpURLConnection execute(Request request) throws AppException {
		switch (request.method) {
		case GET:
			return get(request);
		case POST:
			return post(request);
		case DELETE:
			return delete(request);
		case PUT:
			return put(request);
			
		default:
			throw new AppException(EnumException.NormalException, "the method "+ request.method.name() +" don't support");
		}
	}

	/**
	 * 请求GET
	 * @param request
	 * @return
	 */
	private static HttpURLConnection post(Request request) {
		return null;
	}
	
	/**
	 * 请求POST
	 * @param request
	 * @return
	 */
	private static HttpURLConnection get(Request request) {
		return null;
	}
	
	/**
	 * 请求DELETE
	 * @param request
	 * @return
	 */
	private static HttpURLConnection delete(Request request) {
		return null;
	}
	
	/**
	 * 请求PUT
	 * @param request
	 * @return
	 */
	private static HttpURLConnection put(Request request) {
		return null;
	}
}
