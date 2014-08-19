package com.xiaoluo.utilities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

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
	
	public static boolean isValidate(ArrayList<NameValuePair> content){  
        return content != null && content.size() > 0;  
    }
}
