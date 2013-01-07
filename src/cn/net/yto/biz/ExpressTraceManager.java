package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

import cn.net.yto.application.AppContext;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.ExpressTraceVO;

public class ExpressTraceManager {
    private static final String TAG = "ExpressTraceManager";
    private AppContext mAppContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<ExpressTraceVO, String> mExpressTraceDao = null;

    public ExpressTraceManager(Context context) {
        this.mAppContext = AppContext.getAppContext();
        this.mDatabaseHelper = mAppContext.getDatabaseHelper();
        try {
        	mExpressTraceDao = mDatabaseHelper.getExpressTraceDao();
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
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
