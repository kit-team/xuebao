package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.ExpressTraceVO;
import cn.net.yto.vo.message.QueryExpressTraceRequestMsgVO;
import cn.net.yto.vo.message.QueryExpressTraceResponseMsgVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

public class ExpressTraceManager {
    private static final String TAG = "ExpressTraceManager";
    private AppContext mAppContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<ExpressTraceVO, String> mExpressTraceDao = null;
    
    private static ExpressTraceManager mInstance;

    private ExpressTraceManager(Context context) {
        this.mAppContext = AppContext.getAppContext();
        this.mDatabaseHelper = mAppContext.getDatabaseHelper();
        try {
        	mExpressTraceDao = mDatabaseHelper.getExpressTraceDao();
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
	}
    
    public static synchronized ExpressTraceManager getInstance(Context context) {
    	if (mInstance == null) {
    		mInstance = new ExpressTraceManager(context);
    	}
    	return mInstance;
    }
    
    public static interface ExpressTraceListener {
    	
    	public void done(List<ExpressTraceVO> traces, String error);
    	
    }
    
    public boolean retrieveExpressTrace(String wayBillNo, final ExpressTraceListener traceListener) {
    	Listener listener = new Listener() {
			@Override
			public void onPreSubmit() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					QueryExpressTraceResponseMsgVO responseVo = (QueryExpressTraceResponseMsgVO) response;
					if (responseVo.getRetVal() == SubmitSignedLogResponseMsgVO.RESPONSE_SUCCESS) {
						for(ExpressTraceVO trace : responseVo.getTrackInfos()) {
							saveExpressTrace(trace);
						}
						Log.i(TAG, "Query express trace success: " + responseVo.getFailMessage());
					} else {
						Log.w(TAG, "Query express trace failed: " + responseVo.getFailMessage());
						if (traceListener != null) {
							traceListener.done(null, "Query express trace failed: response is null.");
						}
					}
				} else {
					if (traceListener != null) {
						traceListener.done(null, "Query express trace failed: response is null.");
					}
				}
			}
		};

		final QueryExpressTraceRequestMsgVO requestMsgVO = new QueryExpressTraceRequestMsgVO();
		requestMsgVO.setWayBillNo(wayBillNo);
		ZltdHttpClient client = new ZltdHttpClient(
				UrlType.QUERY_EXPRESS_TRACE, requestMsgVO, listener,
				QueryExpressTraceResponseMsgVO.class);
		try {
			return client.submit(mAppContext);
		} catch (NetworkUnavailableException e) {
			LogUtils.e(TAG, e);
			return false;
		}
    }
    
    public boolean saveExpressTrace(ExpressTraceVO expressTrace) {
    	try {
    		CreateOrUpdateStatus status = mExpressTraceDao.createOrUpdate(expressTrace);
    		return status.isCreated() || status.isUpdated();
		} catch (Exception e) {
            LogUtils.e(TAG, e);
            return false;
		}
    }
    
    public ExpressTraceVO queryExpressTrace(String wayBillNo) {
    	ExpressTraceVO expressTrace = null;
        try {
        	expressTrace = mExpressTraceDao.queryForId(wayBillNo);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return expressTrace;	
    }
    
    public List<ExpressTraceVO> queryAllExpressTrace() {
        List<ExpressTraceVO> list = null;
        try {
            list = mExpressTraceDao.queryBuilder().query();
        } catch (SQLException e) {
            list = new ArrayList<ExpressTraceVO>();
            LogUtils.e(TAG, e);
        }
        return list; 
    }
}
