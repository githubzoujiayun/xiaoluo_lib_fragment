package com.xiaoluo.net.itf;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年7月27日 - 下午10:22:12
 */
public interface IProgressListener {
	void onProgressUpdate(int curPos, int contentLength);
}
