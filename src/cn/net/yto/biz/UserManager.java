package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.UserVO;
import cn.net.yto.vo.message.AppUpgradeRequestMsgVO;
import cn.net.yto.vo.message.AppUpgradeResponseMsgVO;
import cn.net.yto.vo.message.LoginRequestMsgVO;
import cn.net.yto.vo.message.LoginResponseMsgVO;
import cn.net.yto.vo.message.WSSignedLogReturnVO;

import com.j256.ormlite.dao.Dao;

public class UserManager {
	public static final int ROLE_ID_OrdMsg = 1; // 揽件通知
	public static final int ROLE_ID_OrdRec = 2; // 有订单收件
	public static final int ROLE_ID_NooRec = 3; // 无订单收件
	public static final int ROLE_ID_DelRec = 4; // 收件作废
	public static final int ROLE_ID_RepRec = 5; // 收件换单
	public static final int ROLE_ID_OrdPus = 6; // 订单催办
	public static final int ROLE_ID_OrdCan = 7; // 订单取消
	public static final int ROLE_ID_DeliSca = 8; // 派件收入扫描
	public static final int ROLE_ID_SigLog = 9; // 客户签收
	public static final int ROLE_ID_DeliPus = 10; // 派件催办
	public static final int ROLE_ID_Notes = 11; // 便签记录
	public static final int ROLE_ID_LocPDA = 12; // 锁屏
	public static final int ROLE_ID_Logout = 13; // 注销
	public static final int ROLE_ID_Photo = 14; // 照相
	public static final int ROLE_ID_Volumn = 15; // 音量调节
	public static final int ROLE_ID_Calcul = 16; // 计算器
	public static final int ROLE_ID_UplLog = 17; // 日志上传
	public static final int ROLE_ID_Restar = 18; // 重新启动
	public static final int ROLE_ID_UplDat = 19; // 离线数据上传
	public static final int ROLE_ID_SysDow = 20; // 系统更新
	public static final int ROLE_ID_SerHel = 21; // 服务手册
	public static final int ROLE_ID_DeliAre = 22; // 派送范围
	public static final int ROLE_ID_Contra = 23; // 违禁品
	public static final int ROLE_ID_BlaLis = 24; // 客户黑名单列表
	public static final int ROLE_ID_FeeQue = 25; // 标准运价查询
	public static final int ROLE_ID_CusInf = 26; // 客户验证
	public static final int ROLE_ID_ExpTra = 27; // 快件跟踪
	public static final int ROLE_ID_Notice = 28; // 公告信息
	public static final int ROLE_ID_RecStat = 29; // 收派件统计

	// -1失败
	public static final Integer LOGIN_RESULT_FAIL = -1;
	// 1 成功
	public static final Integer LOGIN_RESULT_SUCCESS = 1;
	// -101 设备号不存在
	public static final Integer LOGIN_RESULT_DEVICE_UNEXIST = -101;
	// -102 设备号不可用，已作废 或 设置为停用
	public static final Integer LOGIN_RESULT_OUT_OF_SERVICE = -102;
	private static final String TAG = "UserService";

	private AppContext mAppContext;
	private LoginRequestMsgVO mLoginVO;
	private UserVO mUserVO;
	private Dao<UserVO, Integer> mUserDao;
	private static UserManager sInstance;

	private UserManager() {
		mAppContext = AppContext.getAppContext();
		mLoginVO = new LoginRequestMsgVO();
		mUserVO = new UserVO();
		try {
			mUserDao = mAppContext.getDatabaseHelper().getUserDao();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}
	
	public static UserManager getInstance(){
		if(sInstance == null){
			sInstance = new UserManager();
		}
		return sInstance;
	}
	
	public boolean loginOut(Context context, Listener listener) throws NetworkUnavailableException{
		ZltdHttpClient client = new ZltdHttpClient(UrlType.LOGOUT, null,
				listener, null);
		return client.submit(context);
	}
	
	public boolean checkUpdate(Context context, Listener listener) throws NetworkUnavailableException{
		AppUpgradeRequestMsgVO vo = new AppUpgradeRequestMsgVO();
	//	vo.setPdaNo(mAppContext.getPdaType());
		vo.setPdaDriveType("I3");
		
	//  vo.setPdaVersion("" + mAppContext.getVersionCode());
		 vo.setPdaVersion("1.0.1.22");
		
	//	vo.setPdaNo(mAppContext.getImei());
		vo.setPdaNo("63101127209335");
	  //  vo.setOrgId("210045");
		vo.setOrgId("999999");
		
	    
	
		ZltdHttpClient client = new ZltdHttpClient(UrlType.VERSION_CHECK, vo,
				listener, AppUpgradeResponseMsgVO.class);
		return client.submit(context);
	}
	
	public boolean login(Context context, Listener listener) throws NetworkUnavailableException {
		ZltdHttpClient client = new ZltdHttpClient(UrlType.LOGIN, mLoginVO,
				listener, LoginResponseMsgVO.class);
		return client.submit(context);
	}
	
	private void addUser(UserVO u){
		if(u != null){
			try {
				mUserDao.createOrUpdate(u);
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
	}
	
	private UserVO queryUser(String username, String psw){
		UserVO u = new UserVO();
		u.setUsername(username);
		u.setPassword(psw);
		List<UserVO> list = null;
		try {
			list = mUserDao.queryForMatchingArgs(u);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		if(list != null && list.size() > 0){
			return list.get(0);
		} 
		return null;
	}

	public boolean isOfflineEnable() {
		if(queryUser(mLoginVO.getUserName(), mLoginVO.getPassword()) != null){
			return true;
		}
		return false;
	}

	public void offlineLogin() {
		mUserVO = queryUser(mLoginVO.getUserName(), mLoginVO.getPassword());
	}

	public LoginRequestMsgVO getLoginVO() {
		return mLoginVO;
	}
	
	public UserVO getUserVO(){
		return mUserVO;
	}

	public void setLoginResponse(LoginResponseMsgVO loginResponse) {
		mUserVO.setUsername(mLoginVO.getUserName());
		mUserVO.setPassword(mLoginVO.getPassword());

		mUserVO.setEmpCode(loginResponse.getEmpCode());
		mUserVO.setGetOrderPeriod(loginResponse.getGetOrderPeriod());
		
		if("1".equals(loginResponse.getIsSMSMode())){
			mUserVO.setIsSMSMode(true);
		} else {
			mUserVO.setIsSMSMode(false);
		}
		
		mUserVO.setLastPDANo(loginResponse.getLastPDANo());
		mUserVO.setNowDate(loginResponse.getNowDate());
		mUserVO.setOrgId(loginResponse.getOrgCode());
		mUserVO.setOrgName(loginResponse.getOrgName());
		mUserVO.setPastDueDay(loginResponse.getPastDueDay());
		mUserVO.setRealName(loginResponse.getRealName());
		mUserVO.setEmpCode(loginResponse.getEmpCode());
		
		//mUserVO.setSubmitBizPeriod(loginResponse.getSubmitBizPeriod());
		mUserVO.setRight(parseRights(loginResponse.getRights()));
		
		addUser(mUserVO);
	}
	
	public boolean hasRight(int role){
		return (mUserVO.getRight() & (0x1l << role)) == 0;
	}

	private long parseRights(ArrayList<String> rights) {
		long right = 0;

		if (rights == null || rights.size() < 1) {
			return right;
		}

		for (String item : rights) {
			if ("PDA_OrdMsg".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdMsg;
			} else if ("PDA_OrdRec".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdRec;
			}  else if ("PDA_NooRec".equals(item)) {
				right |= 0x1 << ROLE_ID_NooRec;
			} else if ("PDA_DelRec".equals(item)) {
				right |= 0x1 << ROLE_ID_DelRec;
			} else if ("PDA_RepRec".equals(item)) {
				right |= 0x1 << ROLE_ID_RepRec;
			} 

			else if ("PDA_OrdPus".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdPus;
			} else if ("PDA_OrdCan".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdCan;
			} else if ("PDA_DeliSca".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliSca;
			} else if ("PDA_SigLog".equals(item)) {
				right |= 0x1 << ROLE_ID_SigLog;
			} else if ("PDA_DeliPus".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliPus;
			} 
			
			else if ("PDA_Notes".equals(item)) {
				right |= 0x1 << ROLE_ID_Notes;
			} else if ("PDA_LocPDA".equals(item)) {
				right |= 0x1 << ROLE_ID_LocPDA;
			} else if ("PDA_Logout".equals(item)) {
				right |= 0x1 << ROLE_ID_Logout;
			} else if ("PDA_Photo".equals(item)) {
				right |= 0x1 << ROLE_ID_Photo;
			} else if ("PDA_Volumn".equals(item)) {
				right |= 0x1 << ROLE_ID_Volumn;
			} 
			
			else if ("PDA_Calcul".equals(item)) {
				right |= 0x1 << ROLE_ID_Calcul;
			} else if ("PDA_UplLog".equals(item)) {
				right |= 0x1 << ROLE_ID_UplLog;
			} else if ("PDA_Restar".equals(item)) {
				right |= 0x1 << ROLE_ID_Restar;
			} else if ("PDA_UplDat".equals(item)) {
				right |= 0x1 << ROLE_ID_UplDat;
			} else if ("PDA_SysDow".equals(item)) {
				right |= 0x1 << ROLE_ID_SysDow;
			} 

			else if ("PDA_SerHel".equals(item)) {
				right |= 0x1 << ROLE_ID_SerHel;
			} else if ("PDA_DeliAre".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliAre;
			} else if ("PDA_Contra".equals(item)) {
				right |= 0x1 << ROLE_ID_Contra;
			} else if ("PDA_BlaLis".equals(item)) {
				right |= 0x1 << ROLE_ID_BlaLis;
			} else if ("PDA_FeeQue".equals(item)) {
				right |= 0x1 << ROLE_ID_FeeQue;
			}
			
			else if ("PDA_CusInf".equals(item)) {
				right |= 0x1 << ROLE_ID_CusInf;
			} else if ("PDA_ExpTra".equals(item)) {
				right |= 0x1 << ROLE_ID_ExpTra;
			} else if ("PDA_Notice".equals(item)) {
				right |= 0x1 << ROLE_ID_Notice;
			} else if ("PDA_RecStat".equals(item)) {
				right |= 0x1 << ROLE_ID_RecStat;
			}
		}
		return right;
	}
}
