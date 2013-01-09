package cn.net.yto.biz;

import java.util.Date;

import android.content.Context;
import android.util.Log;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.ExpressTraceVO;
import cn.net.yto.vo.ReceiveInfoVO;
import cn.net.yto.vo.message.QueryExpressTraceRequestMsgVO;
import cn.net.yto.vo.message.QueryExpressTraceResponseMsgVO;
import cn.net.yto.vo.message.QueryReceiveInfoRequestMsgVO;
import cn.net.yto.vo.message.QueryReceiveInfoResponseMsgVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

public class ReceiveInfoManager {
    private static final String TAG = "ReceiveInfoManager";
    private AppContext mAppContext;
    
    private static ReceiveInfoManager mInstance;

    private ReceiveInfoManager(Context context) {
        this.mAppContext = AppContext.getAppContext();
	}
    
    public static synchronized ReceiveInfoManager getInstance(Context context) {
    	if (mInstance == null) {
    		mInstance = new ReceiveInfoManager(context);
    	}
    	return mInstance;
    }
    
    public boolean queryReceiveInfoOnlineByTime(String beginTime, String endTime) {
    	Listener listener = new Listener() {
			@Override
			public void onPreSubmit() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					QueryReceiveInfoResponseMsgVO responseVo = (QueryReceiveInfoResponseMsgVO) response;
					if (responseVo.getRetVal() == SubmitSignedLogResponseMsgVO.RESPONSE_SUCCESS) {
						Log.i(TAG, "Query receive info online by time success: " + responseVo.getFailMessage());
					} else {
						Log.w(TAG, "Query receive info online by time failed: " + responseVo.getFailMessage());

					}
				} else {
					Log.w(TAG, "Query receive info online by time failed: response is null.");
				}
			}
		};

		final QueryReceiveInfoRequestMsgVO requestMsgVO = new QueryReceiveInfoRequestMsgVO();
		requestMsgVO.setBeginTime(beginTime);
		requestMsgVO.setEndTime(endTime);
		ZltdHttpClient client = new ZltdHttpClient(
				UrlType.QUERY_RECEIVE_INFO, requestMsgVO, listener,
				QueryReceiveInfoResponseMsgVO.class);
		try {
			return client.submit(mAppContext);
		} catch (NetworkUnavailableException e) {
			LogUtils.e(TAG, e);
			return false;
		}
    }
}
