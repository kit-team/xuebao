﻿package cn.net.yto.biz;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.Constants;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.UrlManager;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.BlackListVO;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.DictionaryVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.FreqVO;
import cn.net.yto.vo.InsteadPayCustomerVO;
import cn.net.yto.vo.NoticeVO;
import cn.net.yto.vo.OrderChannelVO;
import cn.net.yto.vo.RecvexpVO;
import cn.net.yto.vo.ScanruleVO;
import cn.net.yto.vo.ScopeVO;
import cn.net.yto.vo.message.BaseResponseMsgVO;
import cn.net.yto.vo.message.DownloadBasicDataByVersionRequestMsgVO;
import cn.net.yto.vo.message.DownloadBlackListResponseMsgVO;
import cn.net.yto.vo.message.DownloadCityResponseMsgVO;
import cn.net.yto.vo.message.DownloadDictionaryResponseMsgVO;
import cn.net.yto.vo.message.DownloadEffectiveTypeResponseMsgVO;
import cn.net.yto.vo.message.DownloadFreqResponseMsgVO;
import cn.net.yto.vo.message.DownloadInsteadPayCustomerResponseMsgVO;
import cn.net.yto.vo.message.DownloadNoticeResponseMsgVO;
import cn.net.yto.vo.message.DownloadOrderChannelResponseMsgVO;
import cn.net.yto.vo.message.DownloadRecvexpResponseMsgVO;
import cn.net.yto.vo.message.DownloadScanRuleResponseMsgVO;
import cn.net.yto.vo.message.DownloadScopeResponseMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class BasicDataManager {
	private static final String MODULE_CITY = "CITY";
	private static final String MODULE_SCANRULE = "SCANRULE";
	private static final String MODULE_EFFECTIVETYPE = "EffectiveType";
	private static final String MODULE_ORDER_CHANNEL = "OrderChannel";
	private static final String MODULE_DICT = "DICT";
	private static final String MODULE_SCOPE = "SCOPE";
	private static final String MODULE_BLACKLIST = "BLACKLIST";
	private static final String MODULE_RECVEXP = "RECVEXP";
	private static final String MODULE_NOTICE = "NOTICE";
	private static final String MODULE_FREQ = "FREQ";
	private static final String MODULE_INSTEAD_PAY_CUSTOMER = "InsteadPayCustomer";
	private static final String MODULE_BIGPEN_MANAGER = "BigpenManager";
	private static final String TAG = "BasicDataManager";
	private static BasicDataManager sInstance;

	private Context mContext;
	private static AppContext mAppContext = AppContext.getAppContext();
	private DatabaseHelper mDatabaseHelper;
	private Dao<CityVO, String> mCityDao = null;
	private Dao<ScanruleVO, Integer> mScanruleDao = null;
	private Dao<EffectiveTypeVO, String> mEffectiveTypeDao = null;
	private Dao<OrderChannelVO, Integer> mOrderChannelDao = null;
	private Dao<BlackListVO, Integer> mBlackListDao = null;
	private Dao<DictionaryVO, Integer> mDictionaryDao = null;
	private Dao<FreqVO, Integer> mFreqDao = null;
	private Dao<InsteadPayCustomerVO, Integer> mInsteadPayCustomerDao = null;
	private Dao<NoticeVO, Integer> mNoticeDao = null;
	private Dao<RecvexpVO, String> mRecvexpDao = null;
	private Dao<ScopeVO, Integer> mScopeDao = null;

	private List<ScanruleVO> mScanruleList = new ArrayList<ScanruleVO>();
	private List<EffectiveTypeVO> mEffectiveTypeList = new ArrayList<EffectiveTypeVO>();
	private List<OrderChannelVO> mOrderChannelList = new ArrayList<OrderChannelVO>();
	private List<BlackListVO> mBlackListList = new ArrayList<BlackListVO>();
	private List<DictionaryVO> mDictionaryList = new ArrayList<DictionaryVO>();
	private List<FreqVO> mFreqList = new ArrayList<FreqVO>();
	// private List<InsteadPayCustomerVO> mInsteadPayCustomerList = new
	// ArrayList<InsteadPayCustomerVO>();
	private List<NoticeVO> mNoticeList = new ArrayList<NoticeVO>();
	private List<RecvexpVO> mRecvexpList = new ArrayList<RecvexpVO>();
	private List<ScopeVO> mScopeList = new ArrayList<ScopeVO>();

	private BasicDataManager(Context context) {
		mContext = context;
		mDatabaseHelper = mAppContext.getDatabaseHelper();
		try {
			mCityDao = mDatabaseHelper.getCityDao();
			mScanruleDao = mDatabaseHelper.getScanruleDao();
			mEffectiveTypeDao = mDatabaseHelper.getEffectiveTypeDao();
			mOrderChannelDao = mDatabaseHelper.getOrderChannelDao();
			mBlackListDao = mDatabaseHelper.getBlackListDao();
			mDictionaryDao = mDatabaseHelper.getDictionaryDao();
			mFreqDao = mDatabaseHelper.getFreqDao();
			mInsteadPayCustomerDao = mDatabaseHelper.getInsteadPayCustomerDao();
			mNoticeDao = mDatabaseHelper.getNoticeDao();
			mRecvexpDao = mDatabaseHelper.getRecvexpDao();
			mScopeDao = mDatabaseHelper.getScopeDao();

			mScanruleList = queryScanruleList();
			mEffectiveTypeList = queryEffectiveTypeList();
			mOrderChannelList = mOrderChannelDao.queryForAll();
			mBlackListList = mBlackListDao.queryForAll();
			mDictionaryList = mDictionaryDao.queryForAll();
			mFreqList = mFreqDao.queryForAll();
			mNoticeList = mNoticeDao.queryForAll();
			mRecvexpList = queryRecvexpList();
			mScopeList = mScopeDao.queryForAll();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}

	public static BasicDataManager getInstance() {
		if (sInstance == null) {
			sInstance = new BasicDataManager(mAppContext.getDefaultContext());
		}
		return sInstance;
	}

	private ZltdHttpClient getCityHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_CITY);
		vo.setVersion(getCityMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadCity((DownloadCityResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadCityResponseMsgVO.class);
		return client;
	}

	private long getCityMaxVersion() {
		long maxVersoin = -1l;
		List<CityVO> cities = queryCityList();
		if (cities != null) {
			for (CityVO vo : cities) {
				if (vo.getVersionNo() > maxVersoin) {
					maxVersoin = vo.getVersionNo();
				}
			}
		}
		return maxVersoin;
	}

	private void saveDownloadCity(DownloadCityResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (CityVO vo : response.getSyncRecords()) {
				try {
					mCityDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
		queryCityList();
	}

	private long getScanruleMaxVersion() {
		long maxVersoin = -1l;
		for (ScanruleVO vo : mScanruleList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private ZltdHttpClient getScanRuleHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_SCANRULE);
		vo.setVersion(getScanruleMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadScanrule((DownloadScanRuleResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadScanRuleResponseMsgVO.class);

		return client;
	}

	private void saveDownloadScanrule(DownloadScanRuleResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (ScanruleVO vo : response.getSyncRecords()) {
				try {
					mScanruleDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
		mScanruleList = queryScanruleList();
		BarcodeManager.getInstance().reInit();
	}

	private ZltdHttpClient getEffectiveTypeHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_EFFECTIVETYPE);
		vo.setVersion(getEffectiveTypeMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadEffectiveType((DownloadEffectiveTypeResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadEffectiveTypeResponseMsgVO.class);
		return client;
	}

	private long getEffectiveTypeMaxVersion() {
		long maxVersoin = -1l;
		for (EffectiveTypeVO vo : mEffectiveTypeList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadEffectiveType(
			DownloadEffectiveTypeResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (EffectiveTypeVO vo : response.getSyncRecords()) {
				try {
					mEffectiveTypeDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
		queryEffectiveTypeList();
	}

	private ZltdHttpClient getOrderChannel() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_ORDER_CHANNEL);
		vo.setVersion(getOrderChannelMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadOrderChannel((DownloadOrderChannelResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadOrderChannelResponseMsgVO.class);
		return client;
	}

	private long getOrderChannelMaxVersion() {
		long maxVersoin = -1l;
		for (OrderChannelVO vo : mOrderChannelList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadOrderChannel(
			DownloadOrderChannelResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (OrderChannelVO vo : response.getSyncRecords()) {
				try {
					mOrderChannelDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	private ZltdHttpClient getBlackListHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_BLACKLIST);
		vo.setVersion(getBlackListMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadBlackList((DownloadBlackListResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadBlackListResponseMsgVO.class);
		return client;
	}

	private long getBlackListMaxVersion() {
		long maxVersoin = -1l;
		for (BlackListVO vo : mBlackListList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadBlackList(DownloadBlackListResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (BlackListVO vo : response.getSyncRecords()) {
				try {
					mBlackListDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	private ZltdHttpClient getDictionaryHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_DICT);
		vo.setVersion(getDictionaryMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadDictionary((DownloadDictionaryResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadDictionaryResponseMsgVO.class);
		return client;
	}

	private long getDictionaryMaxVersion() {
		long maxVersoin = -1l;
		for (DictionaryVO vo : mDictionaryList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadDictionary(DownloadDictionaryResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (DictionaryVO vo : response.getSyncRecords()) {
				try {
					mDictionaryDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	private ZltdHttpClient getFreqHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_FREQ);
		vo.setVersion(getFreqMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadFreq((DownloadFreqResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadFreqResponseMsgVO.class);
		return client;
	}

	private long getFreqMaxVersion() {
		long maxVersoin = -1l;
		for (FreqVO vo : mFreqList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadFreq(DownloadFreqResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (FreqVO vo : response.getSyncRecords()) {
				try {
					mFreqDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	private ZltdHttpClient getInsteadPayCustomerHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_INSTEAD_PAY_CUSTOMER);
		vo.setVersion(getInsteadPayCustomerMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadInsteadPayCustomer((DownloadInsteadPayCustomerResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadInsteadPayCustomerResponseMsgVO.class);
		return client;
	}

	private long getInsteadPayCustomerMaxVersion() {
		long maxVersoin = -1l;
		List<InsteadPayCustomerVO> list = queryInsteadPayCustomerList();
		if (list != null) {
			for (InsteadPayCustomerVO vo : list) {
				if (vo.getVersionNo() > maxVersoin) {
					maxVersoin = vo.getVersionNo();
				}
			}
			list.clear();
		}
		return maxVersoin;
	}

	private void saveDownloadInsteadPayCustomer(
			DownloadInsteadPayCustomerResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (InsteadPayCustomerVO vo : response.getSyncRecords()) {
				try {
					mInsteadPayCustomerDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	public List<InsteadPayCustomerVO> queryInsteadPayCustomerList() {
		List<InsteadPayCustomerVO> list = null;
		try {
			list = mInsteadPayCustomerDao.queryForEq("status", "VALID");
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return list;
	}

	private ZltdHttpClient getNoticeHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_NOTICE);
		vo.setVersion(getNoticeMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadNotice((DownloadNoticeResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadNoticeResponseMsgVO.class);
		return client;
	}

	private long getNoticeMaxVersion() {
		long maxVersoin = -1l;
		for (NoticeVO vo : mNoticeList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadNotice(DownloadNoticeResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (NoticeVO vo : response.getSyncRecords()) {
				try {
					mNoticeDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	private ZltdHttpClient getRecvexpHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_RECVEXP);
		vo.setVersion(getRecvexpMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadRecvexp((DownloadRecvexpResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadRecvexpResponseMsgVO.class);
		return client;
	}

	private long getRecvexpMaxVersion() {
		long maxVersoin = -1l;
		for (RecvexpVO vo : mRecvexpList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadRecvexp(DownloadRecvexpResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (RecvexpVO vo : response.getSyncRecords()) {
				try {
					mRecvexpDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
		queryRecvexpList();
	}

	private ZltdHttpClient getScopeHttpClient() {
		DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
		vo.setModuleName(MODULE_SCOPE);
		vo.setVersion(getScopeMaxVersion());

		ZltdHttpClient.Listener l = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					saveDownloadScope((DownloadScopeResponseMsgVO) response);
				}
			}
		};

		ZltdHttpClient client = new ZltdHttpClient(UrlType.BASIC_DATA, vo, l,
				DownloadScopeResponseMsgVO.class);
		return client;
	}

	private long getScopeMaxVersion() {
		long maxVersoin = -1l;
		for (ScopeVO vo : mScopeList) {
			if (vo.getVersionNo() > maxVersoin) {
				maxVersoin = vo.getVersionNo();
			}
		}
		return maxVersoin;
	}

	private void saveDownloadScope(DownloadScopeResponseMsgVO response) {
		if (response != null && response.getSyncRecords() != null) {
			for (ScopeVO vo : response.getSyncRecords()) {
				try {
					mScopeDao.createOrUpdate(vo);
				} catch (SQLException e) {
					LogUtils.e(TAG, e);
				}
			}
		}
	}

	public List<ScanruleVO> getScanruleList() {
		return mScanruleList;
	}

	private List<ScanruleVO> queryScanruleList() {
		if (mScanruleList != null) {
			mScanruleList.clear();
		}
		try {
			mScanruleList = mScanruleDao.queryForEq("status", "VALID");
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return mScanruleList;
	}

	public int getDefaultEffectiveTypeIndex() {
		for (int i = 0; i < mEffectiveTypeList.size(); i++) {
			EffectiveTypeVO vo = mEffectiveTypeList.get(i);
			if ("C005".equals(vo.getCode())) {
				return i;
			}
		}
		return 0;
	}

	public List<EffectiveTypeVO> getEffectiveTypeList() {
		return mEffectiveTypeList;
	}

	private List<EffectiveTypeVO> queryEffectiveTypeList() {
		if (mEffectiveTypeList != null) {
			mEffectiveTypeList.clear();
		}
		try {
			mEffectiveTypeList = mEffectiveTypeDao
					.queryForEq("status", "VALID");
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return mEffectiveTypeList;
	}

	public EffectiveTypeVO getEffectiveTypeByCode(String code) {
		if (CommonUtils.isEmpty(code)) {
			try {
				return mEffectiveTypeDao.queryForId(code);
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
		return null;
	}

	public List<OrderChannelVO> getOrderChannelList() {
		return mOrderChannelList;
	}

	public List<BlackListVO> getBlackListList() {
		return mBlackListList;
	}

	public List<DictionaryVO> getDictionaryList() {
		return mDictionaryList;
	}

	public List<FreqVO> getFreqList() {
		return mFreqList;
	}

	public List<NoticeVO> getNoticeList() {
		return mNoticeList;
	}

	public List<RecvexpVO> getRecvexpList() {
		return mRecvexpList;
	}

	private List<RecvexpVO> queryRecvexpList() {
		if (mRecvexpList != null) {
			mRecvexpList.clear();
		}
		try {
			RecvexpVO vo = new RecvexpVO();
			vo.setFailureType("2");
			vo.setStatus("VALID");
			mRecvexpList = mRecvexpDao.queryForMatchingArgs(vo);
			// queryForEq("status", "VALID");
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return mRecvexpList;
	}

	public RecvexpVO getRecvexpById(String id) {
		if (CommonUtils.isEmpty(id)) {
			try {
				return mRecvexpDao.queryForId(id);
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
		return null;
	}

	public List<ScopeVO> getScopeList() {
		return mScopeList;
	}

	private List<CityVO> queryCityList() {
		List<CityVO> cities = null;
		try {
			cities = mCityDao.queryForEq("status", "VALID");
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return cities;
	}

	public CityVO getCityById(String id) {
		if (!CommonUtils.isEmpty(id)) {
			try {
				return mCityDao.queryForId(id);
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
		return null;
	}// 5239010

	public CityVO getParentCityById(String parentId) {
		if (!CommonUtils.isEmpty(parentId)) {
			try {
				return mCityDao.queryForId(parentId);
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
		return null;
	}

	public List<CityVO> getChildrenCityList(String parentId) {
		List<CityVO> cities = null;
		if (!CommonUtils.isEmpty(parentId)) {
			try {
				QueryBuilder<CityVO, String> qb = mCityDao.queryBuilder();

				qb.where().eq("status", "VALID").and()
						.eq("parentCityCode", parentId);
				LogUtils.i(TAG,
						qb.toString() + "   " + qb.prepareStatementString());
				cities = mCityDao.query(qb.prepare());
			} catch (SQLException e) {
				LogUtils.e(TAG, e);
			}
		}
		return cities;
	}

	private CitySearch searchCityListByAreaNo(String areaNo) {
		CitySearch citySearch = new CitySearch();

		try {
			QueryBuilder<CityVO, String> qb = mCityDao.queryBuilder();
			qb.where().like("areaNo", "%" + areaNo + "%").and()
					.le("cityLevel", 3);
			List<CityVO> cities = mCityDao.query(qb.prepare());

			if (cities != null) {
				if (cities.size() == 1) {
					citySearch.targetCity = cities.get(0);
					citySearch.cityList = getChildrenCityList(citySearch.targetCity
							.getId());
				} else if (cities.size() > 1) {
					citySearch.targetCity = cities.get(0);
					citySearch.cityList = cities;
				}
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
		return citySearch;
	}

	public CitySearch searchCityList(String key) {
		if (CommonUtils.isEmpty(key)) {
			return null;
		}

		try {
			Integer.parseInt(key);

			CitySearch cs = searchCityListByAreaNo(key);
			if (cs != null && (cs.targetCity != null || cs.cityList != null)) {
				return cs;
			}
		} catch (NumberFormatException e1) {
		}

		CitySearch citySearch = new CitySearch();
		List<CityVO> cities = null;
		String searchKey = "%" + key.toUpperCase() + "%";
		try {
			QueryBuilder<CityVO, String> qb = mCityDao.queryBuilder();
			qb.where().like("areaNo", searchKey).or()
					.like("cityName", searchKey).or()
					.like("cityPinYin", searchKey);

			LogUtils.i(TAG, qb.toString() + "   " + qb.prepareStatementString());

			cities = mCityDao.query(qb.prepare());

			if (cities != null) {
				if (cities.size() == 1) {
					if (cities.get(0).getCityLevel() >= 4) {
						citySearch.targetCity = getParentCityById(cities.get(0)
								.getParentCityCode());
						citySearch.cityList = getChildrenCityList(cities.get(0)
								.getParentCityCode());
					} else {
						citySearch.targetCity = cities.get(0);
						citySearch.cityList = getChildrenCityList(cities.get(0)
								.getId());
						if (citySearch.cityList == null
								|| citySearch.cityList.size() < 1) {
							citySearch.cityList = new ArrayList<CityVO>();
							citySearch.cityList.add(citySearch.targetCity);
						}
					}
				} else if (cities.size() > 1) {
					citySearch.cityList = cities;
					if (citySearch.cityList.get(0).getCityLevel() >= 4) {
						citySearch.targetCity = getParentCityById(citySearch.cityList
								.get(0).getParentCityCode());
					} else {
						citySearch.targetCity = citySearch.cityList.get(0);
					}
				}
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}

		return citySearch;
	}

	public CitySearch searchCityListById(CityVO vo) {
		if (vo == null) {
			return null;
		}
		CitySearch citySearch = new CitySearch();

		if (vo.getCityLevel() >= 4) {
			citySearch.targetCity = getParentCityById(vo.getParentCityCode());
			citySearch.cityList = getChildrenCityList(citySearch.targetCity
					.getId());
		} else {
			citySearch.targetCity = vo;
			citySearch.cityList = getChildrenCityList(vo.getId());
			if (citySearch.cityList == null || citySearch.cityList.size() < 1) {
				citySearch.cityList = new ArrayList<CityVO>();
				citySearch.cityList.add(citySearch.targetCity);
			}
		}
		return citySearch;
	}

	// public void downloadBasicData() throws NetworkUnavailableException {
	// downloadBlackList();
	// downloadCity();
	// downloadDictionary();
	// downloadEffectiveType();
	// downloadFreq();
	// downloadInsteadPayCustomer();
	// downloadNotice();
	// downloadOrderChannel();
	// downloadRecvexp();
	// downloadScanRule();
	// downloadScope();
	// }

	public void backgroundDownload() {

		HttpTaskManager tm = HttpTaskManager.getInstance();
		tm.addTask(getCityHttpClient());
		tm.addTask(getBlackListHttpClient());
		tm.addTask(getDictionaryHttpClient());
		tm.addTask(getEffectiveTypeHttpClient());
		tm.addTask(getFreqHttpClient());
		tm.addTask(getInsteadPayCustomerHttpClient());
		tm.addTask(getNoticeHttpClient());
		tm.addTask(getOrderChannel());
		tm.addTask(getScanRuleHttpClient());
		tm.addTask(getScopeHttpClient());
		tm.addTask(getRecvexpHttpClient());
	}

	public class CitySearch {
		public CityVO targetCity;
		public List<CityVO> cityList;
	}

	/**
	 * If exist basic data this method will update data in background, otherwise
	 * will initial data and you can use InitialDataListener to do some work in
	 * UI thread
	 * 
	 * @param listener
	 * @return true if is initial dasic data else already initial
	 */
	public boolean downloadBasicData(InitialDataListener listener) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(AppContext.getAppContext());

		boolean hadBasicData = sharedPreferences.getBoolean(
				Constants.KEY_HAS_BASIC_DATA, false);

		if (!hadBasicData) {
			initialBasicData(listener);
			return true;
		} else {
			backgroundDownload();
			return false;
		}
	}

	private long getMaxVersion(String moduleName) {
		if (moduleName.equals(MODULE_CITY))
			return getCityMaxVersion();
		if (moduleName.equals(MODULE_DICT))
			return getDictionaryMaxVersion();
		if (moduleName.equals(MODULE_EFFECTIVETYPE))
			return getEffectiveTypeMaxVersion();
		if (moduleName.equals(MODULE_FREQ))
			return getFreqMaxVersion();
		if (moduleName.equals(MODULE_INSTEAD_PAY_CUSTOMER))
			return getInsteadPayCustomerMaxVersion();
		if (moduleName.equals(MODULE_NOTICE))
			return getNoticeMaxVersion();
		if (moduleName.equals(MODULE_ORDER_CHANNEL))
			return getOrderChannelMaxVersion();
		if (moduleName.equals(MODULE_RECVEXP))
			return getRecvexpMaxVersion();
		if (moduleName.equals(MODULE_SCANRULE))
			return getScanruleMaxVersion();
		if (moduleName.equals(MODULE_SCOPE))
			return getScopeMaxVersion();

		return -1;
	}

	private void initialBasicData(InitialDataListener listener) {
		InitialDataAsyncTask task = new InitialDataAsyncTask();
		task.mListener = listener;
		task.execute("");
	}

	private class InitialDataAsyncTask extends
			AsyncTask<String, Integer, Boolean> {

		InitialDataListener mListener;

		@Override
		protected Boolean doInBackground(String... values) {
			DefaultHttpClient httpClient = ZltdHttpClient.getHttpClient();

			HttpPost httpPost = new HttpPost();

			httpPost.setHeader("Content-type", "application/json");
			httpPost.setURI(URI.create(UrlManager.getUrl(UrlType.BASIC_DATA)));

			BaseResponseMsgVO response;

			long maxVersion = getMaxVersion(MODULE_EFFECTIVETYPE);
			maxVersion = getMaxVersion(MODULE_EFFECTIVETYPE);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_FREQ, maxVersion,
						DownloadFreqResponseMsgVO.class)))
					return false;

				saveDownloadFreq((DownloadFreqResponseMsgVO) response);
			}

			publishProgress(10);

			maxVersion = getMaxVersion(MODULE_DICT);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_DICT, maxVersion,
						DownloadDictionaryResponseMsgVO.class)))
					return false;

				saveDownloadDictionary((DownloadDictionaryResponseMsgVO) response);
			}

			publishProgress(20);

			maxVersion = getMaxVersion(MODULE_EFFECTIVETYPE);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_EFFECTIVETYPE, maxVersion,
						DownloadEffectiveTypeResponseMsgVO.class)))
					return false;

				saveDownloadEffectiveType((DownloadEffectiveTypeResponseMsgVO) response);
			}

			publishProgress(30);

			maxVersion = getMaxVersion(MODULE_CITY);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_CITY, maxVersion,
						DownloadCityResponseMsgVO.class)))
					return false;

				saveDownloadCity((DownloadCityResponseMsgVO) response);
			}

			publishProgress(40);

			maxVersion = getMaxVersion(MODULE_INSTEAD_PAY_CUSTOMER);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_INSTEAD_PAY_CUSTOMER, maxVersion,
						DownloadInsteadPayCustomerResponseMsgVO.class)))
					return false;

				saveDownloadInsteadPayCustomer((DownloadInsteadPayCustomerResponseMsgVO) response);
			}

			publishProgress(50);

			maxVersion = getMaxVersion(MODULE_NOTICE);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_NOTICE, maxVersion,
						DownloadNoticeResponseMsgVO.class)))
					return false;

				saveDownloadNotice((DownloadNoticeResponseMsgVO) response);
			}

			publishProgress(60);

			maxVersion = getMaxVersion(MODULE_ORDER_CHANNEL);
			if (-1 != maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_ORDER_CHANNEL, maxVersion,
						DownloadOrderChannelResponseMsgVO.class)))
					return false;

				saveDownloadOrderChannel((DownloadOrderChannelResponseMsgVO) response);
			}

			publishProgress(70);

			maxVersion = getMaxVersion(MODULE_RECVEXP);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_RECVEXP, maxVersion,
						DownloadRecvexpResponseMsgVO.class)))
					return false;

				saveDownloadRecvexp((DownloadRecvexpResponseMsgVO) response);
			}

			publishProgress(80);

			maxVersion = getMaxVersion(MODULE_SCANRULE);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_SCANRULE, maxVersion,
						DownloadScanRuleResponseMsgVO.class)))
					return false;

				saveDownloadScanrule((DownloadScanRuleResponseMsgVO) response);
			}

			publishProgress(90);

			maxVersion = getMaxVersion(MODULE_SCOPE);
			if (-1 == maxVersion) {
				if (null == (response = downloadBasicModule(httpClient,
						httpPost, MODULE_SCOPE, maxVersion,
						DownloadScopeResponseMsgVO.class)))
					return false;

				saveDownloadScope((DownloadScopeResponseMsgVO) response);
			}
			return true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (mListener != null)
				mListener.onPreInitial();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (mListener != null)
				mListener.onFinish(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			if (mListener != null)
				mListener.onProgressUpdate(values[0]);
		}

		private BaseResponseMsgVO downloadBasicModule(
				DefaultHttpClient httpClient, HttpPost httpPost,
				String moduleName, long maxVersion,
				Class<? extends BaseResponseMsgVO> responseCls) {
			try {
				DownloadBasicDataByVersionRequestMsgVO vo = new DownloadBasicDataByVersionRequestMsgVO();
				vo.setModuleName(moduleName);
				vo.setVersion(maxVersion);
				String param = CommonUtils.toJson(vo);

				StringEntity se = new StringEntity(param);

				httpPost.setEntity(se);

				HttpResponse response = httpClient.execute(httpPost);

				if (response != null) {
					int statusCode = response.getStatusLine().getStatusCode();
					// 若状态码为200 ok
					if (statusCode == 200) {
						HttpEntity entity = response.getEntity();
						String strResult = EntityUtils.toString(entity);
						return CommonUtils.fromJson(strResult, responseCls);
					} else {
						return null;
					}
				} else {
					return null;
				}

			} catch (Exception e) {
				LogUtils.e(TAG, e);
				return null;
			}

		}

	}

	public interface InitialDataListener {
		/**
		 * before initial work
		 */
		public void onPreInitial();

		/**
		 * @param result
		 *            False if initial task failed
		 */
		public void onFinish(boolean result);

		public void onProgressUpdate(int progress);

	}
}
