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
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;
import cn.net.yto.vo.message.LoginResponseMsgVO;

import com.j256.ormlite.dao.Dao;

public class ReceiveManager {
	private static final String TAG = "ReceiveManager";
	private Context mContext;
	private AppContext mAppContext;
	private DatabaseHelper mDatabaseHelper;
	private Dao<ReceiveVO, String> mReceiveDao = null;
	private HttpTaskManager mHttpTaskManager;
	private List<ReceiveVO> mReceiveList;

	public ReceiveManager(Context context) {
		this.mContext = context;
		this.mAppContext = AppContext.getAppContext();
		this.mDatabaseHelper = mAppContext.getDatabaseHelper();
		this.mHttpTaskManager = HttpTaskManager.getInstance();
		
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

	public void saveReceive(ReceiveVO receiveVO) {
		try {
			mReceiveDao.createOrUpdate(receiveVO);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}

	public boolean upload(final ReceiveVO receiveVO, Context context) {

		Listener listener = new Listener() {
			String mWayBillNo = receiveVO.getWaybillNo();

			@Override
			public void onPreSubmit() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				ReceiveVO vo = queryReceive(mWayBillNo);
				if (response != null) {
					CommonResponseMsgVO responseVo = (CommonResponseMsgVO) response;
					if (vo != null) {
						vo.setSendStatus("已上传");
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
}
