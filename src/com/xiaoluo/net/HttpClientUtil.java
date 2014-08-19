package com.xiaoluo.net;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.xiaoluo.exceptions.AppException;
import com.xiaoluo.exceptions.AppException.EnumException;

public class HttpClientUtil {
	
	/**
	 * 执行HTTP方法,Request 设置请求类型
	 * @param request
	 * @return
	 * @throws AppException
	 */
	public static HttpResponse execute(Request request) throws AppException{
		switch (request.method) {
		case GET:
			return get(request);
		case POST:
			return post(request);
		default:
			throw new AppException(EnumException.NormalException, "the method "+ request.method.name() +" don't support");
		}
	}
	
	/**
	 * 请求GET
	 * @param request
	 * @return
	 * @throws AppException
	 */
	private static HttpResponse get(Request request) throws AppException{
		try {
			if(request.callback != null) {
				request.callback.checkIfCanceled();
			}
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(request.url);
			addHeader(get, request.headers);
			HttpResponse response;
				response = client.execute(get);
			// 返回的结果放到上一层进行处理
			return response;
		} catch (ClientProtocolException e) {
			throw new AppException(EnumException.ClientProtocolException, e.getMessage());
		} catch (IOException e) {
			throw new AppException(EnumException.IOException, e.getMessage());
		}
	}
	
	/**
	 * 请求POST
	 * @param request
	 * @return
	 * @throws AppException
	 */
	private static HttpResponse post(Request request) throws AppException{
		try {
			if(request.callback != null) {
				request.callback.checkIfCanceled();
			}
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(request.url);
			addHeader(post, request.headers);
			// post的请求参数在 Request 中定义,如果为空,则没有定义
			if (request.entity == null) {
				throw new IllegalStateException("you forget to set postContent to the httpost");
			} else {
				post.setEntity(request.entity);
				//post.setEntity(new StringEntity(request.postContent));//已在Request类中处理
			}
	
			HttpResponse response;
				response = client.execute(post);
			// 返回的结果放到上一层进行处理
			return response;
		} catch (ClientProtocolException e) {
			throw new AppException(EnumException.ClientProtocolException, e.getMessage());
		} catch (IOException e) {
			throw new AppException(EnumException.IOException, e.getMessage());
		}
	}
	
	/**
	 * 请求头
	 * @param request
	 * @param headers
	 */
	private static void addHeader(HttpUriRequest request, Map<String, String> headers){
		if(headers != null && headers.size() > 0){
			for (Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
	}
}
