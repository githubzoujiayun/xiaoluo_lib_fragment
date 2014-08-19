package com.xiaoluo.net;

import org.apache.http.HttpResponse;

import com.xiaoluo.net.itf.IProgressListener;

import android.os.AsyncTask;

public class RequestTask extends AsyncTask<Object, Integer, Object> {
	protected Request request;
	
	public RequestTask(Request request) {
		this.request = request;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object object = null;
		try {
			HttpResponse response = HttpClientUtil.execute(request);
			if(request.mProgressListener != null) {
				//response 解析代码放到对应的类中，对应handle中的bindData方法
				object = request.callback.handler(response, new IProgressListener() {
					
					@Override
					public void onProgressUpdate(int curPos, int contentLength) {
						publishProgress(curPos, contentLength);
					}
				});
			} else {
				object = request.callback.handler(response, null);
			}
			return request.callback.onPreHandler(object);
		} catch (Exception e) {
			return e;
		}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		
		if(result instanceof Exception){
			request.callback.onFailure((Exception)result);
		} else {
			request.callback.onSuccess(result);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		System.err.println("values[0] = "+ values[0] +", values[1] = "+ values[1]);
//		if(request.mProgressListener != null) {
			request.mProgressListener.onProgressUpdate(values[0], values[1]);
//		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if(request.callback != null) {
			request.callback.cancel();
		}
	}
}
