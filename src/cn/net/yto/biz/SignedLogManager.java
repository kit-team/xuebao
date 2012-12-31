package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.net.yto.application.AppContext;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.UploadStatus;
import cn.net.yto.vo.message.SubmitSignedLogRequestMsgVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

import com.j256.ormlite.dao.Dao;

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
        
        for(int i = 0; i < 20; i++){
            SignedLogVO vo = new SignedLogVO();
            vo.setWaybillNo("233003" + i);
            ZltdHttpClient c = new ZltdHttpClient(UrlType.SUBMIT_SIGNEDLOG, vo);
            mHttpTaskManager.addTask(c);
        }
        try {
            mSignedLogDao = mDatabaseHelper.getSignedLogDao();
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
    }
    
    public void saveSignedLog(SignedLogVO signedLogVO) {
        try {
            mSignedLogDao.createOrUpdate(signedLogVO);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
    }

    public boolean upload(final SignedLogVO signedLogVO, Context context) {

        Listener listener = new Listener() {
            String wayBillNo = signedLogVO.getWaybillNo();

            @Override
            public void onPreSubmit() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPostSubmit(Object response, Integer responseType) {
                SignedLogVO vo = querySignedLog(wayBillNo);
                if (response != null) {
                    SubmitSignedLogRequestMsgVO responseVo = (SubmitSignedLogRequestMsgVO) response;
                    if (vo != null) {
                        vo.setStatus(UploadStatus.UPLOAD_SUCCESS);
                        saveSignedLog(vo);
                    }
                } else {
                    Log.w(TAG, "upload failed! mWayBillNo = " + wayBillNo);
                }
            }
        };
        ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_SIGNEDLOG,
                signedLogVO, listener, SubmitSignedLogResponseMsgVO.class);
        return client.submit(context);
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

    public List<SignedLogVO> querySubWayBillSignedLog(String wayBillNo) {
        List<SignedLogVO> list = null;
        SignedLogVO vo = new SignedLogVO();
        vo.setWaybillNo(wayBillNo);
        try {
            list = mSignedLogDao.queryForMatchingArgs(vo);
        } catch (SQLException e) {
            LogUtils.e(TAG, e);
        }
        return list;
    }
}
