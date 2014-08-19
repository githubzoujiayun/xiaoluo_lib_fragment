package com.xiaoluo.net.callback;

import com.google.gson.Gson;
import com.xiaoluo.utilities.IOUtilities;
import com.xiaoluo.utilities.JsonParser;
import com.xiaoluo.utilities.TextUtil;

public abstract class JsonCallback<T> extends AbstractCallback<T> {
	public Gson gson = new Gson();

	@Override
	protected T bindData(String content) {
		// 如果路径path存在或设置, 就直接从文件获取数据
		if (TextUtil.isValidate(path)) {
			content = IOUtilities.readFromFile(path);
		}
		
		if(mReturnClass != null){
			return JsonParser.deserializeByJson(content, mReturnClass);
		}else if(mReturnType != null){
			return JsonParser.deserializeByJson(content, mReturnType);
		}
		
		return null;
	}	
}
