package cn.net.yto.biz;

import java.sql.SQLException;
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
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.message.CancelReceiveRequestMsgVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;
import cn.net.yto.vo.message.LoginResponseMsgVO;
import cn.net.yto.vo.message.ReplaceReceiveRequestMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ReceiveManager {
	private static final String TAG = "ReceiveManager";
	private Context mContext;
	private AppContext mAppContext;
	private DatabaseHelper mDatabaseHelper;
	private Dao<ReceiveVO, String> mReceiveDao = null;
	private HttpTaskManager mHttpTaskManager;
	private List<ReceiveVO> mReceiveList;
	private ReceiveVO mCurNomalReceiveVO;
	private static ReceiveManager sInstance;
	private UserManager mUserManager;

	private ReceiveManager(Context context) {
		this.mContext = context;
		this.mAppContext = AppContext.getAppContext();
		this.mDatabaseHelper = mAppContext.getDatabaseHelper();
		this.mHttpTaskManager = HttpTaskManager.getInstance();
		this.mUserManager = UserManager.getInstance();
		for(int i = 0; i < 20; i++){
			ReceiveVO vo = new ReceiveVO();
			vo.setWaybillNo("233003" + i);
			ZltdHttpClient c = new ZltdHttpClient(UrlType.SUBMIT_RECEIVE, vo);
			mHttpTaskManager.addTask(c);
		}
		try {
			this.mReceiveDao = mDatabaseHelper.getReceiveDao();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}
	
	public static ReceiveManager getInstance(){
		if(sInstance == null){
			sInstance = new ReceiveManager(AppContext.getAppContext().getDefaultContext());
		}
		return sInstance;
	}
	
	public void init(){
		QueryBuilder<ReceiveVO, String> builder = mReceiveDao.queryBuilder();
		try {
			builder.where().ne("getStatus", ReceiveVO.SEND_STATUS_UPLOADED);
			List<ReceiveVO> list = mReceiveDao.query(builder.prepare());
			if(list != null){
				for(ReceiveVO vo : list){
					backgroundUpload(vo);
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
	}

	public void saveReceive(ReceiveVO receiveVO) {
		try {
			if(receiveVO != null){
				receiveVO.setEmpCode(mUserManager.getUserVO().getEmpCode());
				receiveVO.setEmpName(mUserManager.getUserVO().getRealName());
				receiveVO.setSourceOrgCode(mUserManager.getUserVO().getOrgId());
			}
			mReceiveDao.createOrUpdate(receiveVO);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}
	
	public void removeReceive(ReceiveVO receiveVO) {
		try {
			mReceiveDao.delete(receiveVO);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}
	
	public void backgroundUpload(final ReceiveVO receiveVO){
		Listener listener = new Listener() {
			String mWayBillNo = receiveVO.getWaybillNo();

			@Override
			public void onPreSubmit() {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if(vo != null){
					vo.setGetStatus(ReceiveVO.SEND_STATUS_UPLOADING);
					saveReceive(vo);
				}
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (response != null) {
					CommonResponseMsgVO responseVo = (CommonResponseMsgVO) response;
					if (vo != null) {
						vo.setGetStatus(ReceiveVO.SEND_STATUS_UPLOADED);
						saveReceive(vo);
					}
				} else if(responseType != ZltdHttpClient.TYPE_SUCCESS && vo != null){
					backgroundUpload(vo);
				} else {
					Log.w(TAG, "upload failed! mWayBillNo = " + mWayBillNo);
				}
			}
		};
		ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_RECEIVE,
				receiveVO, listener, CommonResponseMsgVO.class);
		HttpTaskManager.getInstance().addTask(client);
	}

	public boolean upload(final ReceiveVO receiveVO, Context context) throws NetworkUnavailableException {

		Listener listener = new Listener() {
			String mWayBillNo = receiveVO.getWaybillNo();
			@Override
			public void onPreSubmit() {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if(vo != null){
					vo.setGetStatus(ReceiveVO.SEND_STATUS_UPLOADING);
					saveReceive(vo);
				}
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (response != null) {
					CommonResponseMsgVO responseVo = (CommonResponseMsgVO) response;
					if (vo != null) {
						vo.setGetStatus(ReceiveVO.SEND_STATUS_UPLOADED);
						saveReceive(vo);
					}
				} else {
					Log.w(TAG, "upload failed! mWayBillNo = " + mWayBillNo);
				}
			}
		};
		ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_RECEIVE,
				receiveVO, listener, CommonResponseMsgVO.class);
		return client.submit(context);
	}
	
	public boolean CancelReceive(Context context, Listener listener, String waybillNo)
			 throws NetworkUnavailableException{
		CancelReceiveRequestMsgVO msgVO = new CancelReceiveRequestMsgVO();
		msgVO.setWayBillNo(waybillNo);
		ZltdHttpClient client = new ZltdHttpClient(UrlType.CANCEL_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		return client.submit(context);
	}
	
	public boolean ReplaceReceive(Context context, Listener listener, String oldWaybillNo, String newWaybillNo)
			 throws NetworkUnavailableException{
		ReplaceReceiveRequestMsgVO msgVO = new ReplaceReceiveRequestMsgVO();
		msgVO.setOldBillNo(oldWaybillNo);
		msgVO.setNewBillNo(newWaybillNo);
		ZltdHttpClient client = new ZltdHttpClient(UrlType.REPLACE_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		return client.submit(context);
	}

	public ReceiveVO queryReceive(String wayBillNo) {
		ReceiveVO receive = null;
		try {
			receive = mReceiveDao.queryForId(wayBillNo);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return receive;
	}

	public List<ReceiveVO> querySubWayBillReceive(String wayBillNo) {
		List<ReceiveVO> list = null;
		ReceiveVO vo = new ReceiveVO();
		vo.setParentWaybillNo(wayBillNo);
		try {
			list = mReceiveDao.queryForMatchingArgs(vo);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return list;
	}
	
	public List<ReceiveVO> queryReceive(String beginTime, String endTime, String wayBillNo) {
		List<ReceiveVO> list = null;
		try {
			QueryBuilder<ReceiveVO, String> builder = mReceiveDao.queryBuilder();
			builder.where().ge("salesmanTime", beginTime).and()
				.le("salesmanTime", endTime);
			if(wayBillNo!=null && wayBillNo.length() > 5){
				builder.where().eq("waybillNo", wayBillNo);
			}
			list = mReceiveDao.query(builder.prepare());
//			list = mReceiveDao.queryForAll();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return list;
	}
	
	public ReceiveVO getCurNomalReceiveVO(){
		if(mCurNomalReceiveVO == null){
			mCurNomalReceiveVO = new ReceiveVO();
		}
		return mCurNomalReceiveVO;
	}
	
	public void setCurNomalReceiveVO(ReceiveVO vo){
		mCurNomalReceiveVO = vo;
	}
	
	public int deleteReceive(String wayBillNo) {
		int result = 0;
		try {
			result = mReceiveDao.deleteById(wayBillNo);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return result;
	}
	
	public void clearCurNomalReceiveVO(){
		mCurNomalReceiveVO = null;
	}
}
