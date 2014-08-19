package com.xiaoluo.net.callback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.xiaoluo.exceptions.AppException;
import com.xiaoluo.exceptions.AppException.EnumException;
import com.xiaoluo.net.itf.ICallback;
import com.xiaoluo.net.itf.IProgressListener;
import com.xiaoluo.utilities.TextUtil;

import android.net.ParseException;

public abstract class AbstractCallback<T> implements ICallback<T> {
	/**
	 * 文件存放的路径
	 */
	protected String path;
	/**
	 * Json反序列化对应的类
	 */
	protected Class<T> mReturnClass;
	/**
	 * Json反序列化类型
	 */
	protected Type mReturnType;
	
	private static final int IO_BUFFER_SIZE = 4 * 1024;
	
	protected boolean isCancelled = false;
	
	@Override
	public void checkIfCanceled() throws AppException {
		if (isCancelled) {
			throw new AppException(EnumException.CancelException, "request has been cancel");
		}
	}
	
	@Override
	public T handler(HttpResponse response, IProgressListener mProgressListener) throws AppException{
		checkIfCanceled();
		int statusCode = -1;
		InputStream in = null;
		try {
			HttpEntity entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode(); 
			// file, json, images, xml, string ......
			switch (statusCode) {
			case HttpStatus.SC_OK:
				if(TextUtil.isValidate(path)){
					// 将服务器返回的数据写入到文件当中
					FileOutputStream fos = new FileOutputStream(path);
					
					if(entity.getContentEncoding() != null){
						String encoding = entity.getContentEncoding().getValue();
						
						if(encoding != null && "gzip".equalsIgnoreCase(encoding)){
							in = new GZIPInputStream(entity.getContent());
						} else if(encoding != null && "deflate".equalsIgnoreCase(encoding)){
							in = new DeflaterInputStream(entity.getContent());
						}
					} else {
						in = entity.getContent();// 注意：entity.getContent()只能用一次,不然出现异常
					}
					
					byte[] b = new byte[IO_BUFFER_SIZE];
					int read;
					
					long curPos = 0;
					long length = entity.getContentLength();
					if((read = in.read(b)) != -1){
						// TODO update progress
						if(mProgressListener != null){
							checkIfCanceled();
							curPos += read;
							System.err.println("curPos = "+ curPos +", read = "+ read);
							System.err.println("curPos = "+ curPos +", length = "+ length);
							mProgressListener.onProgressUpdate((int) curPos / 1, (int) length / 1);
						}
						
						fos.write(b, 0, read);
					}
					
					fos.flush();
					fos.close();
					in.close();
					
					//写入文件之后，再从文件当中将数据读取出来，直接返回对象
					return bindData(path);
				} else {
					//1. 需要返回的是对象，而不是数据流，所以需要去解析服务器返回的数据对应StringCallback 中的return content;
					//2. 调用binData
					// System.err.println(" EntityUtils.toString(entity) ==> "+ EntityUtils.toString(entity) +", \\\\ entity ==>"+ entity);
					// 注意：EntityUtils.toString(entity)只能用一次,不然出现java.lang.IllegalStateException: Content has been consumed异常
					String _response = EntityUtils.toString(entity);
					return bindData(_response);
				}
	
			default:
				return null;
			}
		} catch (ParseException e) {
			throw new AppException(EnumException.ParseException, e.getMessage());
		}catch (IOException e) {
			throw new AppException(EnumException.IOException, e.getMessage());
		}
	}
	
	/**
	 * 数据放入到不同的Callback中处理
	 * @param content 
	 * @throws AppException 
	 */
	protected T bindData(String content) throws AppException {
		// 在StringCallback、JsonCallback、PathCallback、XmlCallback等方法中实现了该方法
		checkIfCanceled();
		return null;
	}
	
	/**
	 * 如果要存入到文件,则设置文件路径
	 * @param path 
	 */
	public AbstractCallback<T> setPath(String path) {
		this.path = path;
		return this;
	}
	
	/**
	 * 如果...,则设置...
	 * @param clz 
	 */
	public AbstractCallback<T> setReturnClass(Class<T> clz){
		this.mReturnClass = clz;
		return this;
	}
	
	/**
	 * 如果...,则设置...
	 * @param type 
	 */
	public AbstractCallback<T> setReturnType(Type type){
		this.mReturnType = type;
		return this;
	}
	
	@Override
	public void cancel() {
		isCancelled = true;
	}
}
