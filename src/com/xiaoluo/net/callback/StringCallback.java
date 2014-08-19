package com.xiaoluo.net.callback;

import com.xiaoluo.utilities.IOUtilities;
import com.xiaoluo.utilities.TextUtil;

public abstract class StringCallback<T> extends AbstractCallback<T> {

	@SuppressWarnings("unchecked")
	@Override
	protected T bindData(String content) {
		// 如果路径存在，则重新将数据从文件中读取出来
		if(TextUtil.isValidate(path)){
			return (T) IOUtilities.readFromFile(path);
		}
		
		return (T) content;
	}
}
