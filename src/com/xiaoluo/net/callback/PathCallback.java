package com.xiaoluo.net.callback;

import com.xiaoluo.exceptions.AppException;

public abstract class PathCallback<T> extends AbstractCallback<T> {

	@Override
	protected T bindData(String content) throws AppException {
		return super.bindData(content);
	}
}
