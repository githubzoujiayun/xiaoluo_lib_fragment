package com.xiaoluo.utilities;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月7日 - 上午10:51:48
 */
public class TextUtil {
	public static boolean isValidate(String content){
		return content != null && !"".equals(content.trim());
	}
}
