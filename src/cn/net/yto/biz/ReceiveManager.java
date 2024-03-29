package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.utils.Md5Util;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.message.CancelReceiveRequestMsgVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;
import cn.net.yto.vo.message.ReplaceReceiveRequestMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ReceiveManager {
	public static final String UPLOAD_STATUS_WAIT_FOR_SEND = "WaitForSend";
	public static final String UPLOAD_STATUS_FAILED = "Failed";
	public static final String UPLOAD_STATUS_SUCCESS = "Success";
	public static final String UPLOAD_STATUS_RESENDING = "ReSending";
	public static final String UPLOAD_STATUS_RESENDFAILED = "ReSendFailed";

	public static String GET_STATUS_WAIT_FOR_SEND;
	public static String GET_STATUS_FAILED;
	public static String GET_STATUS_SUCCESS;
	public static String GET_STATUS_RESENDING;
	public static String GET_STATUS_RESENDFAILED;

	public static int UPLOAD_RETURN_VALUE_FAILED_REPEAT_WAYBILL = -103;

	private static final String TAG = "ReceiveManager";
	/**
	 * 保存失败
	 */
	public static final int SAVE_RESULT_FAILED = -1;
	/**
	 * 已经存在，保存失败
	 */
	public static final int SAVE_RESULT_FAILED_EXIST = -2;
	/**
	 * 保存成功
	 */
	public static final int SAVE_RESULT_SUCCESS = 1;

	/**
	 * 更新失败
	 */
	public static final int UPDATE_RESULT_FAILED = -1;
	/**
	 * 不存在，更新失败
	 */
	public static final int UPDATE_RESULT_FAILED_NOT_EXIST = -2;
	/**
	 * 更新成功
	 */
	public static final int UPDATE_RESULT_SUCCESS = 1;

	/**
	 * 编辑失败
	 */
	public static final int EDIT_RESULT_FAILED = -1;
	/**
	 * 不存在，编辑失败
	 */
	public static final int EDIT_RESULT_FAILED_NOT_EXIST = -2;
	/**
	 * 正在上传，编辑失败
	 */
	public static final int EDIT_RESULT_FAILED_SENDING = -3;
	/**
	 * 已作废，编辑失败
	 */
	public static final int EDIT_RESULT_FAILED_INVALID = -4;
	/**
	 * 编辑成功
	 */
	public static final int EDIT_RESULT_SUCCESS = 1;
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
		for (int i = 0; i < 20; i++) {
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

		GET_STATUS_WAIT_FOR_SEND = mContext.getString(R.string.wait_for_send);
		GET_STATUS_FAILED = mContext.getString(R.string.send_failed);
		GET_STATUS_SUCCESS = mContext.getString(R.string.send_success);
		GET_STATUS_RESENDING = mContext.getString(R.string.resending);
		GET_STATUS_RESENDFAILED = mContext.getString(R.string.resend_failed);
	}

	public static ReceiveManager getInstance() {
		if (sInstance == null) {
			sInstance = new ReceiveManager(AppContext.getAppContext()
					.getDefaultContext());
		}
		return sInstance;
	}

	public void init() {
		QueryBuilder<ReceiveVO, String> builder = mReceiveDao.queryBuilder();
		try {
			builder.where().ne("uploadStatu", UPLOAD_STATUS_SUCCESS);
			List<ReceiveVO> list = mReceiveDao.query(builder.prepare());
			if (list != null) {
				for (ReceiveVO vo : list) {
					backgroundSubmitReceive(vo);
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
	}

	public int saveReceive(ReceiveVO receiveVO) {
		try {
			if (receiveVO != null) {
				ReceiveVO vo = mReceiveDao.queryForId(receiveVO.getWaybillNo());

				if (vo != null) {
					return SAVE_RESULT_FAILED_EXIST;
				}
				receiveVO.setEmpCode(mUserManager.getUserVO().getEmpCode());
				receiveVO.setEmpName(mUserManager.getUserVO().getRealName());
				receiveVO.setSalesmanStation(mUserManager.getUserVO().getOrgId());
				receiveVO.setPdaNumber(mAppContext.getImei());
				receiveVO.setId(getReceiveId());
				mReceiveDao.create(receiveVO);
				return SAVE_RESULT_SUCCESS;
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return SAVE_RESULT_FAILED;
	}
	
	private String getReceiveId(){
		String md = Md5Util.md5("" + System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		sb.append(md.substring(0, 8));
		sb.append("-");
		sb.append(md.substring(8, 12));
		sb.append("-");
		sb.append(md.substring(12, 16));
		sb.append("-");
		sb.append(md.substring(16, 20));
		sb.append("-");
		sb.append(md.substring(20));
		return sb.toString();
	}

	public int updateReceive(ReceiveVO receiveVO) {
		try {
			if (receiveVO != null) {
				ReceiveVO vo = mReceiveDao.queryForId(receiveVO.getWaybillNo());

				if (vo == null) {
					return UPDATE_RESULT_FAILED_NOT_EXIST;
				}
				mReceiveDao.update(receiveVO);
				return UPDATE_RESULT_SUCCESS;
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return UPDATE_RESULT_FAILED;
	}

	public int editReceive(ReceiveVO receiveVO){
		try {
			if(receiveVO != null){
				ReceiveVO vo = mReceiveDao.queryForId(receiveVO.getWaybillNo());
				
				if(vo == null){
					return EDIT_RESULT_FAILED_NOT_EXIST;
				}
				
				//已经作废的收件 不让编辑
				if(!"0".endsWith(vo.getIsInvalid())){
					return EDIT_RESULT_FAILED_INVALID;
				} 
				
				updateReceive(receiveVO);
				if(UPLOAD_STATUS_SUCCESS.equals(vo.getUploadStatu())) {
					backgroundUpdateReceive(receiveVO);
					return EDIT_RESULT_SUCCESS;
				} 
				
				int result = removeUploadByWayBillNo(receiveVO.getWaybillNo());
				
				if(result == HttpTaskManager.REMOVE_SUCCESS){
					if(UPLOAD_STATUS_RESENDING.equals(vo.getUploadStatu())) {
						backgroundUpdateReceive(receiveVO);
					} else {
						backgroundSubmitReceive(receiveVO);
					}
				} else {
					backgroundSubmitReceive(receiveVO);
				}
				return EDIT_RESULT_SUCCESS;
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return EDIT_RESULT_FAILED;
	}

	public void removeReceive(ReceiveVO receiveVO) {
		try {
			mReceiveDao.delete(receiveVO);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}

	public void backgroundSubmitReceive(final ReceiveVO receiveVO) {
		Listener listener = new Listener() {
			String mWayBillNo = receiveVO.getWaybillNo();

			@Override
			public void onPreSubmit() {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (vo != null) {
					vo.setUploadStatu(UPLOAD_STATUS_WAIT_FOR_SEND);
					vo.setGetStatus(GET_STATUS_WAIT_FOR_SEND);
					updateReceive(vo);
				}
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (response != null
						&& responseType == ZltdHttpClient.TYPE_SUCCESS) {
					CommonResponseMsgVO responseVo = (CommonResponseMsgVO) response;
					if (vo != null) {
						if (responseVo.getRetVal() == UPLOAD_RETURN_VALUE_FAILED_REPEAT_WAYBILL) {
							backgroundUpdateReceive(vo);
						} else {
							vo.setUploadStatu(UPLOAD_STATUS_SUCCESS);
							vo.setGetStatus(GET_STATUS_SUCCESS);
							vo.setFailMessage(responseVo.getFailMessage());
							updateReceive(vo);
						}
					}
				} else if (responseType != ZltdHttpClient.TYPE_SUCCESS
						&& vo != null) {
					vo.setUploadStatu(UPLOAD_STATUS_RESENDING);
					vo.setGetStatus(GET_STATUS_RESENDING);
					backgroundSubmitReceive(vo);
				} else {
					vo.setUploadStatu(UPLOAD_STATUS_FAILED);
					vo.setGetStatus(GET_STATUS_FAILED);
					Log.e(TAG, "upload failed! mWayBillNo = " + mWayBillNo);
				}
				updateReceive(vo);
			}
		};
		ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_RECEIVE,
				receiveVO, listener, CommonResponseMsgVO.class);
		client.setId(getHttpClientId(receiveVO.getWaybillNo()));

		HttpTaskManager.getInstance().addTask(client);
	}

	public void backgroundUpdateReceive(final ReceiveVO receiveVO) {
		Listener listener = new Listener() {
			String mWayBillNo = receiveVO.getWaybillNo();

			@Override
			public void onPreSubmit() {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (vo != null) {
					vo.setUploadStatu(UPLOAD_STATUS_WAIT_FOR_SEND);
					vo.setGetStatus(GET_STATUS_WAIT_FOR_SEND);
					updateReceive(vo);
				}
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (response != null
						&& responseType == ZltdHttpClient.TYPE_SUCCESS) {
					CommonResponseMsgVO responseVo = (CommonResponseMsgVO) response;
					if (vo != null) {
						vo.setUploadStatu(UPLOAD_STATUS_SUCCESS);
						vo.setGetStatus(GET_STATUS_SUCCESS);
						vo.setFailMessage(responseVo.getFailMessage());

						updateReceive(vo);
					}
				} else if (responseType != ZltdHttpClient.TYPE_SUCCESS
						&& vo != null) {
					vo.setUploadStatu(UPLOAD_STATUS_RESENDING);
					vo.setGetStatus(GET_STATUS_RESENDING);
					backgroundSubmitReceive(vo);
				} else {
					vo.setUploadStatu(UPLOAD_STATUS_FAILED);
					vo.setGetStatus(GET_STATUS_FAILED);
					Log.e(TAG, "upload failed! mWayBillNo = " + mWayBillNo);
				}
				updateReceive(vo);
			}
		};
		ZltdHttpClient client = new ZltdHttpClient(UrlType.UPDATE_RECEIVE,
				receiveVO, listener, CommonResponseMsgVO.class);
		client.setId(getHttpClientId(receiveVO.getWaybillNo()));

		HttpTaskManager.getInstance().addTask(client);
	}
	
	public boolean cancelReceive(Context context, Listener listener,
			String waybillNo) throws NetworkUnavailableException {
		CancelReceiveRequestMsgVO msgVO = new CancelReceiveRequestMsgVO();
		msgVO.setWayBillNo(waybillNo);
		ZltdHttpClient client = new ZltdHttpClient(UrlType.CANCEL_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		return client.submit(context);
	}
	
	public void cancelReceive(Context context, 
			final ReceiveVO mReceiveVO) {
		Listener listener = new Listener(){
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if(response != null){
					CommonResponseMsgVO comm = (CommonResponseMsgVO) response;
					if(comm.getRetVal()==1){
					} else{
						mReceiveVO.setIsInvalid("0");
						updateReceive(mReceiveVO);
					}
				} else{
					mReceiveVO.setIsInvalid("0");
					updateReceive(mReceiveVO);
				}
			}
		};
		int result = removeUploadByWayBillNo(mReceiveVO.getWaybillNo());
		
		if(result == HttpTaskManager.REMOVE_SUCCESS){
			deleteReceive(mReceiveVO.getWaybillNo());
			return;
		}
		CancelReceiveRequestMsgVO msgVO = new CancelReceiveRequestMsgVO();
		msgVO.setWayBillNo(mReceiveVO.getWaybillNo());
		ZltdHttpClient client = new ZltdHttpClient(UrlType.CANCEL_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		HttpTaskManager.getInstance().addTask(client);
	}
	
	public boolean replaceReceive(Context context, Listener listener,
			String oldWaybillNo, String newWaybillNo)
			throws NetworkUnavailableException {
		ReplaceReceiveRequestMsgVO msgVO = new ReplaceReceiveRequestMsgVO();
		msgVO.setOldBillNo(oldWaybillNo);
		msgVO.setNewBillNo(newWaybillNo);
		ZltdHttpClient client = new ZltdHttpClient(UrlType.REPLACE_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		return client.submit(context);
	}
	
	public void replaceReceive(Context context,
			final ReceiveVO  mReceiveVO, final String oldWaybillNo) {
		Listener listener = new Listener(){
			@Override
			public void onPreSubmit() {
			}
			
			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if(response != null){
					CommonResponseMsgVO comm = (CommonResponseMsgVO) response;
					if(comm.getRetVal()==1){
					} else{
						deleteReceive(mReceiveVO.getWaybillNo());
						mReceiveVO.setWaybillNo(oldWaybillNo);
						saveReceive(mReceiveVO);
					}
				} else {
					deleteReceive(mReceiveVO.getWaybillNo());
					mReceiveVO.setWaybillNo(oldWaybillNo);
					saveReceive(mReceiveVO);
				}
			}
		};
		removeUploadByWayBillNo(mReceiveVO.getWaybillNo());
		ReplaceReceiveRequestMsgVO msgVO = new ReplaceReceiveRequestMsgVO();
		msgVO.setOldBillNo(oldWaybillNo);
		msgVO.setNewBillNo(mReceiveVO.getWaybillNo());
		ZltdHttpClient client = new ZltdHttpClient(UrlType.REPLACE_RECEIVE,
				msgVO, listener, CommonResponseMsgVO.class);
		HttpTaskManager.getInstance().addTask(client);
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

	public List<ReceiveVO> queryReceive(String beginTime, String endTime,
			String wayBillNo) {
		LogUtils.e(TAG, beginTime+" "+endTime);
		List<ReceiveVO> list = null;
		try {
			QueryBuilder<ReceiveVO, String> builder = mReceiveDao
					.queryBuilder();
			if (wayBillNo != null && wayBillNo.length() > 5) {
				builder.where().eq("waybillNo", wayBillNo);
			} else{
				builder.where().ge("salesmanTime", beginTime).and()
				.le("salesmanTime", endTime);
			}
			list = mReceiveDao.query(builder.prepare());
			// list = mReceiveDao.queryForAll();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return list;
	}

	public ReceiveVO getCurNomalReceiveVO() {
		if (mCurNomalReceiveVO == null) {
			mCurNomalReceiveVO = new ReceiveVO();
		}
		return mCurNomalReceiveVO;
	}

	public void setCurNomalReceiveVO(ReceiveVO vo) {
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

	public void clearCurNomalReceiveVO() {
		mCurNomalReceiveVO = null;
	}

	private long getHttpClientId(String wayBillNo) {
		String s = "receiveWayBillNo" + wayBillNo;
		return s.hashCode();
	}

	private int removeUploadByWayBillNo(String wayBillNo) {
		long id = getHttpClientId(wayBillNo);
		return HttpTaskManager.getInstance().removeTaskById(id);
	}
}
