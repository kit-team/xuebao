package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;
import cn.net.yto.vo.message.UpdateSignedLogResponseMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

/**
 * 
 * 签单
 */
public class SignedLogManager {
    private static final String TAG = "SignedManager";
    private Context mContext;
    private AppContext mAppContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<SignedLogVO, String> mSignedLogDao = null;
    private HttpTaskManager mHttpTaskManager;
    private List<SignedLogVO> mSignedLogList;

    public SignedLogManager(Context context) {
        this.mContext = context;
        this.mAppContext = AppContext.getAppContext();
        this.mDatabaseHelper = mAppContext.getDatabaseHelper();
        this.mHttpTaskManager = HttpTaskManager.getInstance();
        
//        for(int i = 0; i < 20; i++){
//            SignedLogVO vo = new SignedLogVO();
//            vo.setWaybillNo("233003" + i);
//            ZltdHttpClient c = new ZltdHttpClient(UrlType.SUBMIT_SIGNEDLOG, vo);
//            mHttpTaskManager.addTask(c);
//        }
        try {
            mSignedLogDao = mDatabaseHelper.getSignedLogDao();
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
    }
    
    public boolean saveSignedLog(SignedLogVO signedLogVO) {
        try {
        	SignedLogVO existedLogVO = querySignedLog(signedLogVO.getWaybillNo());
        	boolean needUpdate = false;
        	if (existedLogVO != null && existedLogVO.getUploadStatus() == SignedLogVO.UPLOAD_STAUTS_SUCCESS) {
        		needUpdate = true;
			}
            CreateOrUpdateStatus status = mSignedLogDao.createOrUpdate(signedLogVO);
            LogUtils.e(TAG, "status.isCreated() = "+status.isCreated());
            LogUtils.e(TAG, "status.isUpdated() = "+status.isUpdated());
            if (status.isUpdated() && needUpdate) {
            	signedLogVO.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_NEED_UPDATE);
            	status = mSignedLogDao.createOrUpdate(signedLogVO);
			}
            return status.isCreated() || status.isUpdated();
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return false;
    }

    public int removeSignedLog(SignedLogVO signedLogVO) {
        int result = 0; 
        try {
            result = mSignedLogDao.delete(signedLogVO);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return result;
    }
    
    public int removeSignedLog(List<SignedLogVO> signedLogs) {
        int result = 0;
        try {
            result = mSignedLogDao.delete(signedLogs);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return result;
    }

    public boolean submitSignedLog(final SignedLogVO signedLogVO, Context context) {

        Listener listener = new Listener() {
            String wayBillNo = signedLogVO.getWaybillNo();

            @Override
            public void onPreSubmit() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPostSubmit(Object response, Integer responseType) {
                SignedLogVO vo = querySignedLog(wayBillNo);
            	SubmitSignedLogResponseMsgVO responseVo = (SubmitSignedLogResponseMsgVO) response;
                if (response != null) {
                    if (responseVo.getRetVal() == SubmitSignedLogResponseMsgVO.RESPONSE_SUCCESS ||
                    		responseVo.getRetVal() == SubmitSignedLogResponseMsgVO.RESPONSE_FAILURE) {
                    	vo.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_SUCCESS);
                        saveSignedLog(vo);
                        ToastUtils.showOperationToast(Operation.UPLOAD, true);
                    } else if(responseVo.getRetVal() == -103){
                        Log.w(TAG, "repeat upload! mWayBillNo = " + wayBillNo);
                        if (vo != null) {
                            vo.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_RESENDFAILED);
                            saveSignedLog(vo);
                        } 
                    }else {
                        Log.w(TAG, "upload failed! mWayBillNo = " + wayBillNo);
                        if (vo != null) {
                            vo.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_FAILED);
                            saveSignedLog(vo);
                        } 
                        ToastUtils.showOperationToast(Operation.UPLOAD, false);                    	
                    }
                } else {
                    Log.w(TAG, "upload failed! mWayBillNo = " + wayBillNo);
                    if (vo != null) {
                        vo.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_FAILED);
                        saveSignedLog(vo);
                    } 
                    ToastUtils.showOperationToast(Operation.UPLOAD, false);
                }
            }
        };
        ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_SIGNEDLOG,
                signedLogVO.toVO(), listener, SubmitSignedLogResponseMsgVO.class);
        try {
        return client.submit(context);
		} catch (NetworkUnavailableException e) {
			LogUtils.e(TAG, e);
			return false;
		}
    }

    public boolean updateSignedLog(final SignedLogVO signedLogVO, Context context) {

        Listener listener = new Listener() {
            String wayBillNo = signedLogVO.getWaybillNo();

            @Override
            public void onPreSubmit() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPostSubmit(Object response, Integer responseType) {
                if (response != null) {
                	UpdateSignedLogResponseMsgVO responseVo = (UpdateSignedLogResponseMsgVO) response;
                    if (signedLogVO != null && responseVo.getRetVal() == SubmitSignedLogResponseMsgVO.RESPONSE_SUCCESS) {
                    	signedLogVO.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_SUCCESS);
                        saveSignedLog(signedLogVO);
                        ToastUtils.showOperationToast(Operation.MODIFY, true);
                    } else {
                        Log.w(TAG, "update failed! mWayBillNo = " + wayBillNo);
                        signedLogVO.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_UPDATE_FAILED);
                        saveSignedLog(signedLogVO);
                        ToastUtils.showOperationToast(Operation.MODIFY, false);                    	
                    }
                } else {
                    Log.w(TAG, "update failed! mWayBillNo = " + wayBillNo);
                    signedLogVO.setUploadStatus(SignedLogVO.UPLOAD_STAUTS_UPDATE_FAILED);
                    saveSignedLog(signedLogVO);
                    ToastUtils.showOperationToast(Operation.MODIFY, false);
                }
            }
        };
        ZltdHttpClient client = new ZltdHttpClient(UrlType.UPDATE_SIGNEDLOG,
                signedLogVO.toUpdateVO(), listener, UpdateSignedLogResponseMsgVO.class);
        try {
        return client.submit(context);
		} catch (NetworkUnavailableException e) {
			LogUtils.e(TAG, e);
			return false;
		}
    }

    
    public SignedLogVO querySignedLog(String wayBillNo) {
        SignedLogVO signed = null;
        try {
            signed = mSignedLogDao.queryForId(wayBillNo);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return signed;
    }

    public List<SignedLogVO> queryByWaybillno(String wayBillNo) {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().where()
                    .like(SignedLogVO.WAYBILLNO_FIELD_NAME, "%"+wayBillNo+"%").query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;
    }
    
    public List<SignedLogVO> queryByUploadSataus(String status) {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().where()
                    .eq(SignedLogVO.UPLOADSTATUS_FIELD_NAME, status).query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;
    }
    
//    public List<SignedLogVO> queryByDate(String dateFrom, String dateTo) {
//        List<SignedLogVO> list = null;
//        try {
//			list = mSignedLogDao
//					.queryBuilder()
//					.where()
//					.between(SignedLogVO.SIGNED_TIME_FIELD_NAME, dateFrom, dateTo).query();
//        } catch (SQLException e) {
//            list = new ArrayList<SignedLogVO>();
//            LogUtils.e(TAG, e);
//        }
//        return list;
//    }
    
    public List<SignedLogVO> queryAllSignedLog() {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;
    }
    
    public List<SignedLogVO> querySignedLogByTime(Date fromTime, Date toTime) {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().where().le(SignedLogVO.SIGNED_TIME_FIELD_NAME, toTime).and()
            		.ge(SignedLogVO.SIGNED_TIME_FIELD_NAME, fromTime).query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;    	
    }

    public List<SignedLogVO> queryNeedUploadSignedLog() {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().
            		where().
            		eq(SignedLogVO.UPLOADSTATUS_FIELD_NAME, SignedLogVO.UPLOAD_STAUTS_WAITFORSEND).
            		or().
            		eq(SignedLogVO.UPLOADSTATUS_FIELD_NAME, SignedLogVO.UPLOAD_STAUTS_FAILED).
            		query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;    	
    }
    
    public List<SignedLogVO> queryNeedUpdateSignedLog() {
        List<SignedLogVO> list = null;
        try {
            list = mSignedLogDao.queryBuilder().
            		where().
            		eq(SignedLogVO.UPLOADSTATUS_FIELD_NAME, SignedLogVO.UPLOAD_STAUTS_NEED_UPDATE).
            		query();
        } catch (SQLException e) {
            list = new ArrayList<SignedLogVO>();
            LogUtils.e(TAG, e);
        }
        return list;    	
    }    
}
