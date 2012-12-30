package cn.net.yto.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.BaseResponseMsgVO;

public class ZltdHttpClient {
	public static final String TAG = "HttpClient";
	/**
	 * TYPE_ERROR_404 HTTP����δ�ҵ�ҳ��
	 */
	public static final int TYPE_ERROR_404 = 404;
	/**
	 * TYPE_ERROR_NETWORK_DEACTIVE ��ǰ�豸���粻����
	 */
	public static final int TYPE_ERROR_NETWORK_DEACTIVE = 2000;

	/**
	 * TYPE_ERROR_NETWORK_DEACTIVE ��ǰ�豸���粻����
	 */
	public static final int TYPE_ERROR_EXCEPTION = 3000;

	/**
	 * TYPE_ERROR_OHTER �������
	 */
	public static final int TYPE_ERROR_OHTER = 4000;
	/**
	 * TYPE_SUCCESS HTTP����ɹ�
	 */
	public static final int TYPE_SUCCESS = 200;

	private HttpAsyncTask asyncTask;
	private UrlType mUrlType;
	private Object mParam;
	private Listener mListener;
	private Class<? extends BaseResponseMsgVO> mResponseCls;
	/**
	 * Ĭ�ϵ�DefaultHttpClient��ÿ��ʹ����ͬclient�����㱣��cookie
	 */
	private static DefaultHttpClient sHttpClient;

	static {
		BasicHttpParams httpParameters = new BasicHttpParams();
		// Set the default socket timeout (SO_TIMEOUT)
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);

		sHttpClient = new DefaultHttpClient(httpParameters);
	}

	public ZltdHttpClient() {

	}

	public ZltdHttpClient(UrlType urlType, Object param) {
		this.mUrlType = urlType;
		this.mParam = param;
	}

	public ZltdHttpClient(UrlType urlType, Object param, Listener listener,
			Class<? extends BaseResponseMsgVO> responseClzz) {
		this.mUrlType = urlType;
		this.mParam = param;
		setListener(listener, responseClzz);
	}

	public void setUrlType(UrlType mUrlType) {
		this.mUrlType = mUrlType;
	}

	public void setParam(Object param) {
		this.mParam = param;
	}

	@SuppressWarnings("unchecked")
	public void setListener(Listener listener,
			Class<? extends BaseResponseMsgVO> responseClzz) {
		this.mListener = listener;
		mResponseCls = responseClzz;
	}

	public boolean submit(Context context) {
		if (!CommonUtils.hasActiveNetwork(context)) {
			LogUtils.i(TAG, "no active network");
			return false;
		}
		asyncTask = new HttpAsyncTask();
		asyncTask.execute();
		return true;
	}

	public interface Listener {
		public void onPreSubmit();

		public void onPostSubmit(Object response, Integer responseType);
	}

	class HttpAsyncTask extends AsyncTask<String, Integer, String> {
		private HttpPost mHttpPost;
		private HttpResponse mHttpResponse;
		private int mNetworkResultType;

		@Override
		protected String doInBackground(String... params) {
			String strResult = "";
			int statusCode = 0;

			do {
				try {
					LogUtils.i(TAG, "url = " + UrlManager.getUrl(mUrlType));
					mHttpPost = new HttpPost(UrlManager.getUrl(mUrlType));
					mHttpPost.setHeader("Content-type", "application/json");

					if (mParam != null) {
						String json = null;
						json = CommonUtils.toJson(mParam);
						LogUtils.i(TAG, "json = " + json);
						if (!CommonUtils.isEmpty(json)) {
							 StringEntity se = new StringEntity(json);
							 mHttpPost.setEntity(se);
//							List<NameValuePair> list = new ArrayList<NameValuePair>(); 
//							list.add(new BasicNameValuePair("json", CommonUtils
//									.toJson(mParam)));//UrlManager.getUrl(mUrlType)
//							mHttpPost.setEntity(new UrlEncodedFormEntity(list));
//							LogUtils.d(TAG, new UrlEncodedFormEntity(list).toString());
						}
					}

					mHttpResponse = sHttpClient.execute(mHttpPost);

					if (mHttpResponse != null) {
						statusCode = mHttpResponse.getStatusLine()
								.getStatusCode();
						// ��״̬��Ϊ200 ok
						if (statusCode == 200) {
							HttpEntity entity = mHttpResponse.getEntity();
							strResult = EntityUtils.toString(entity);
							mNetworkResultType = TYPE_SUCCESS;
						} else {
							strResult = "";
							if (statusCode == 404) {
								mNetworkResultType = TYPE_ERROR_404;
							} else {
								mNetworkResultType = TYPE_ERROR_OHTER;
							}
						}
					} else {
						strResult = "";
						mNetworkResultType = TYPE_ERROR_OHTER;
					}
					return strResult;
				} catch (Exception e) {
					mNetworkResultType = TYPE_ERROR_EXCEPTION;
					LogUtils.e(TAG, e);
				} finally {
					LogUtils.i(TAG, "response = " + strResult + " statusCode = " + statusCode);
				}
			} while (false);

			return strResult;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mListener != null) {
				if (!CommonUtils.isEmpty(result)
						&& mNetworkResultType == TYPE_SUCCESS) {
					Object o = null;

					try {
						o = CommonUtils.fromJson(result, mResponseCls);
					} catch (Exception e) {
						e.printStackTrace();
					}

					mListener.onPostSubmit(o, mNetworkResultType);
				} else {
					mListener.onPostSubmit(null, mNetworkResultType);
				}
			}
		}

		@Override
		protected void onPreExecute() {
			if (mListener != null) {
				mListener.onPreSubmit();
			}
		}
	}
}
