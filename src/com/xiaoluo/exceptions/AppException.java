package com.xiaoluo.exceptions;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年7月28日 - 下午2:38:02
 */
public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumException mExceptionType;
	private String mDetailMessage;
	
	public enum EnumException{
		ParseException, IOException, CancelException, NormalException, ClientProtocolException
	}

	public AppException(EnumException exceptionType, String detailMessage) {
		super(detailMessage);
		
		this.mExceptionType = exceptionType;
		this.mDetailMessage = detailMessage;
	}
	
	
}
