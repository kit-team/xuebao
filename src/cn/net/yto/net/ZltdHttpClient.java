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
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.message.BaseResponseMsgVO;
import cn.net.yto.vo.message.SubmitSignedLogRequestMsgVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

public class ZltdHttpClient {
	public static final String TAG = "HttpClient";
	/**
	 * TYPE_ERROR_404 HTTP请求未找到页面
	 */
	public static final int TYPE_ERROR_404 = 404;
	/**
	 * TYPE_ERROR_NETWORK_DEACTIVE 当前设备网络不可用
	 */
	public static final int TYPE_ERROR_NETWORK_DEACTIVE = 2000;

	/**
	 * TYPE_ERROR_NETWORK_DEACTIVE 当前设备网络不可用
	 */
	public static final int TYPE_ERROR_EXCEPTION = 3000;

	/**
	 * TYPE_ERROR_OHTER 其他错误
	 */
	public static final int TYPE_ERROR_OHTER = 4000;
	/**
	 * TYPE_SUCCESS HTTP请求成功
	 */
	public static final int TYPE_SUCCESS = 200;
	/**
	 * 排队等待状态
	 */
	public static final int STATE_WAIT_UPLOAD = 0;
	/**
	 * 正在上传
	 */
	public static final int STATE_UPLOADING = 1;
	/**
	 * 成功上传
	 */
	public static final int STATE_UPLOADED = 2;

	public static final int PRIORITY_LOW = -10;
	public static final int PRIORITY_MID = 0;
	public static final int PRIORITY_HIGH = 10;

	private HttpAsyncTask asyncTask;
	private UrlType mUrlType;
	private Object mParam;
	private Listener mListener;
	private Class<? extends BaseResponseMsgVO> mResponseCls;
	private static Object mMutex = new Object();
	private int mState;
	private int mPriority;
	/**
	 * 默认的DefaultHttpClient，每次使用相同client，方便保持cookie
	 */
	private static DefaultHttpClient sHttpClient;
	private long mId;

	static {
		BasicHttpParams httpParameters = new BasicHttpParams();
		// Set the default socket timeout (SO_TIMEOUT)
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);

		sHttpClient = new DefaultHttpClient(httpParameters);
	}

	public ZltdHttpClient() {
		mState = STATE_WAIT_UPLOAD;
		mPriority = PRIORITY_MID;
		mId = System.currentTimeMillis();
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(int mPriority) {
		this.mPriority = mPriority;
	}

	public int getState() {
		return mState;
	}
	
	public long getId(){
		return mId;
	}

	public ZltdHttpClient(UrlType urlType, Object param) {
		this();
		this.mUrlType = urlType;
		this.mParam = param;
	}

	public ZltdHttpClient(UrlType urlType, Object param, Listener listener,
			Class<? extends BaseResponseMsgVO> responseClzz) {
		this(urlType, param);
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

			synchronized (mMutex) {
				do {
					try {
						LogUtils.i(TAG, "url = " + UrlManager.getUrl(mUrlType));
						mHttpPost = new HttpPost(UrlManager.getUrl(mUrlType));
						mHttpPost.setHeader("Content-type", "application/json");

						if (mParam != null) {
							String json = null;
							if (mUrlType != UrlType.LOGIN) {
								json = CommonUtils.toJson(mParam);
							} else {
								json = "{\"force\":\"1\",\"isUpload\":\"N\",\"password\":\"4mfPzRhGHOk4Bn7KZ8WfQQ==\",\"pdaLocalTime\":\"2012-12-29 16:59:30\",\"pdaNumber\":\"63101127209335\",\"versionNo\":\"1.0.1.22\",\"userName\":\"00003518\",\"uploadStatu\":0}";
							}
							LogUtils.i(TAG, "json = " + json);
							if (!CommonUtils.isEmpty(json)) {
								StringEntity se = new StringEntity(json);
								mHttpPost.setEntity(se);
								// List<NameValuePair> list = new
								// ArrayList<NameValuePair>();
								// list.add(new BasicNameValuePair("json",
								// CommonUtils
								// .toJson(mParam)));//UrlManager.getUrl(mUrlType)
								// mHttpPost.setEntity(new
								// UrlEncodedFormEntity(list));
								// LogUtils.d(TAG, new
								// UrlEncodedFormEntity(list).toString());
							}
						}

						mHttpResponse = sHttpClient.execute(mHttpPost);

						if (mHttpResponse != null) {
							statusCode = mHttpResponse.getStatusLine()
									.getStatusCode();
							// 若状态码为200 ok
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
						LogUtils.i(TAG, "response = " + strResult
								+ " statusCode = " + statusCode);
					}
				} while (false);
			}

			return strResult;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			mState = STATE_UPLOADING;
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
			mState = STATE_UPLOADED;
		}
	}
}
