package cn.net.yto.biz;

import java.util.Date;

import android.content.Context;
import cn.net.yto.application.AppContext;
import cn.net.yto.vo.ReceiveInfoVO;

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
    
    public ReceiveInfoVO queryReceiveInfoOnlineByTime(Date fromTime, Date toTime) {
    	// TODO:
    	return null;
    }
}
