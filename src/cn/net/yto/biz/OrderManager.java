package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.net.yto.application.AppContext;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;
import cn.net.yto.vo.message.DownloadOrderRequestMsgVO;
import cn.net.yto.vo.message.DownloadOrderResponseMsgVO;
import cn.net.yto.vo.message.ModifyOrderStatusRequestMsgVO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class OrderManager {
	private static final String TAG = "OrderManager";
	// -1失败
	public static final Integer DOWNLOAD_ORDER_RESULT_FAIL = -1;
	// 1 成功
	public static final Integer DOWNLOAD_ORDER_RESULT_SUCCESS = 1;
	//订单状态
	public static final int STATE_INIT = 0;//未取（未读，未上传）
	public static final int STATE_FETCHED = 1;//已取(正常,已读,未上传）
	public static final int STATE_VIEWED = 2;//已查看
	//本地的订单状态
	public static final int STATE_INIT_READED = 3;//未取（已读，未上传）
	public static final int STATE_FETCHED_UPLOADED = 4;//已取(正常,已读,已上传）
	public static final int STATE_FETCHED_EXCEPTION = 5;//已取（异常，已读，未上传）
	public static final int STATE_FETCHED_UPLOADED_EXCEPTION = 6;//已取（异常，已读，已上传）
	public static final int STATE_CANCELED = 7;//订单已作废（未读，未上传）
	public static final int STATE_CANCELED_READED = 8;//订单已作废（已读，未上传）
//	public static final int STATE_CANCELED_UPLOADED = 9;//订单已作废（已读，已上传）
	public static final int STATE_ENERGED = 10;//催办订单（未读，未上传）
	public static final int STATE_ENERGED_READED = 11;//催办订单（已读，未上传）
//	public static final int STATE_ENERGED_UPLOADED = 12;//催办订单（已读，已上传）
	private AppContext mAppContext;
	private DownloadOrderRequestMsgVO mOrderRequestMsgVO;
	private ModifyOrderStatusRequestMsgVO mOrderStatusRequestMsgVO;
	private Dao<OrderVO, String> mOrderDao;
	private List<String> backupLink = new ArrayList<String>();
	private static OrderManager instance;
	
	public OrderManager() {
		mAppContext = AppContext.getAppContext();
		mOrderRequestMsgVO = new DownloadOrderRequestMsgVO();
		mOrderStatusRequestMsgVO = new ModifyOrderStatusRequestMsgVO();
		try {
			mOrderDao = mAppContext.getDatabaseHelper().getOrderDao();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
		}
	}
	
	public static OrderManager getInstance(){
		if(instance == null){
			instance = new OrderManager();
		}
		return instance;
	}
	
	public boolean downloadOrder(Context context) throws NetworkUnavailableException {
		ZltdHttpClient client = new ZltdHttpClient(UrlType.DOWNLOAD_ORDER, 
				mOrderRequestMsgVO, mListener, 
				DownloadOrderResponseMsgVO.class);
		return client.submit(context);
	}
	
	public boolean modifyOrderStatus(Context context, Listener mListener) throws NetworkUnavailableException{
		ZltdHttpClient client = new ZltdHttpClient(UrlType.MODIFY_ORDER_STATUS, 
				mOrderStatusRequestMsgVO, mListener, 
				CommonResponseMsgVO.class);
		return client.submit(context);
	}
	
	ZltdHttpClient.Listener mListener = new Listener() {

		@Override
		public void onPostSubmit(Object response, Integer responseType) {
			if (response != null) {
				DownloadOrderResponseMsgVO orderResponse = (DownloadOrderResponseMsgVO) response;
				if (orderResponse.getRetVal() == DOWNLOAD_ORDER_RESULT_SUCCESS) {
					setDownloadOrderResponse(orderResponse);
				}
			} else if (responseType != ZltdHttpClient.TYPE_SUCCESS) {
			} else {
			}
		}

		@Override
		public void onPreSubmit() {
		}
	};
	
	public void updateOrdersState(){
		List<OrderVO> list = queryUnUploadOrderVO();
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				OrderVO vo = list.get(i);
				if(backupLink.contains(vo.getOrderNo())) continue;
				updateOrderState(vo);
			}
		}
	}
	
	public void updateOrderState(final OrderVO vo){
		ModifyOrderStatusRequestMsgVO msgVO = new ModifyOrderStatusRequestMsgVO();
		msgVO.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED));
		msgVO.setOrderChannelCode(vo.getOrderChannelCode());
		msgVO.setOrderNo(vo.getOrderNo());
		Listener l = new Listener(){
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response,
					Integer responseType) {
				if(response != null){
					CommonResponseMsgVO responseVO = (CommonResponseMsgVO) response;
					if(responseVO.getRetVal() == DOWNLOAD_ORDER_RESULT_SUCCESS){
						int state = Integer.parseInt(vo.getAdditionState());
						if(OrderManager.STATE_FETCHED == state){
							vo.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED_UPLOADED));
						} else if(OrderManager.STATE_FETCHED_EXCEPTION == state){
							vo.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED_UPLOADED_EXCEPTION));
						}
						updateOrder(vo);
					}
				}
				backupLink.remove(vo.getOrderNo());
			}
		};
		ZltdHttpClient client = new ZltdHttpClient();
		client.setListener(l, CommonResponseMsgVO.class);
		client.setParam(msgVO);
		client.setUrlType(UrlType.MODIFY_ORDER_STATUS);
		HttpTaskManager.getInstance().addTask(client);
		backupLink.add(vo.getOrderNo());
	}
	
	public DownloadOrderRequestMsgVO getOrderVO(){
		return mOrderRequestMsgVO;
	}
	
	public void setDownloadOrderResponse(DownloadOrderResponseMsgVO downloadOrderResponse){
		if(downloadOrderResponse.getRetVal() == DOWNLOAD_ORDER_RESULT_SUCCESS){
			List<OrderVO> listOrderVO = downloadOrderResponse.getOrders();
			if(listOrderVO == null) return;
			for(int i = 0; i< listOrderVO.size();i++){
				OrderVO vo = listOrderVO.get(i);
				OrderVO local = queryOrderVO(vo);
				if(local == null){
					addOrder(vo);
				} else {
					if(Integer.parseInt(local.getAdditionState()) > STATE_INIT){
					} else {
						addOrder(vo);
					}
				}
			}
		}
	}
	
	private void addOrder(OrderVO orderVO){
		try {
			mOrderDao.createOrUpdate(orderVO);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		}
	}
	
	public void updateOrder(OrderVO vo){
		try {
			mOrderDao.update(vo);
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		}
	}
	
	public List<OrderVO> queryOrderVO(String beginTime, String endTime, String code){
		List<OrderVO> list = null;
		Integer param = Integer.parseInt(code);
		if(param==STATE_INIT){
			list = query(beginTime,endTime,String.valueOf(STATE_INIT));
		} else if(param == STATE_INIT_READED){
			list = query(beginTime,endTime,String.valueOf(STATE_INIT_READED));
		} else if(param == STATE_FETCHED){
			list = queryFetchOrderVO(beginTime, endTime);
		} else if(param == STATE_FETCHED_EXCEPTION){
			list = queryFetchExceptionOrderVO(beginTime, endTime);
		} else if(param == STATE_CANCELED){
			list = queryCancelOrderVO(beginTime, endTime);
		} else if(param == STATE_ENERGED){
			list = queryEngerOrderVO(beginTime, endTime);
		}
		return list;
	}
	
	public List<OrderVO> queryUnUploadOrderVO(){
		String[] params = {String.valueOf(STATE_FETCHED),
				String.valueOf(STATE_FETCHED_EXCEPTION)};
		List<OrderVO> list = null;
		try {
			QueryBuilder<OrderVO, String> builder = mOrderDao.queryBuilder();
			builder.where().in("additionState", params[0], params[1]);
			list = mOrderDao.query(builder.prepare());
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		}
		return list;
	}
	
	private List<OrderVO> queryFetchOrderVO(String beginTime, String endTime){
		String[] params = {String.valueOf(STATE_FETCHED),
				String.valueOf(STATE_FETCHED_UPLOADED)};
		List<OrderVO> list = queryIn(beginTime,endTime, params);
		return list;
	}
	
	private List<OrderVO> queryFetchExceptionOrderVO(String beginTime, String endTime){
		String[] params = {String.valueOf(STATE_FETCHED_EXCEPTION),
				String.valueOf(STATE_FETCHED_UPLOADED_EXCEPTION)};
		List<OrderVO> list = queryIn(beginTime,endTime, params);
		return list;
	}
	
	private List<OrderVO> queryCancelOrderVO(String beginTime, String endTime){
		String[] params = {String.valueOf(STATE_CANCELED),
				String.valueOf(STATE_CANCELED_READED)/*,
				String.valueOf(STATE_CANCELED_UPLOADED)*/};
		List<OrderVO> list = queryIn(beginTime,endTime, params);
		return list;
	}
	
	private List<OrderVO> queryEngerOrderVO(String beginTime, String endTime){
		String[] params = {String.valueOf(STATE_ENERGED),
				String.valueOf(STATE_ENERGED_READED)/*,
				String.valueOf(STATE_ENERGED_UPLOADED)*/};
		List<OrderVO> list = queryIn(beginTime,endTime, params);
		return list;
	}
	
	private List<OrderVO> query(String beginTime, String endTime, String code){
		List<OrderVO> list = null;
		try {
			QueryBuilder<OrderVO, String> builder = mOrderDao.queryBuilder();
			builder.where().ge("orderCreateTime", beginTime).and()
					.le("orderCreateTime", endTime).and().eq("additionState", code);
			list = mOrderDao.query(builder.prepare());
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		}
		return list;
	}
	
	private List<OrderVO> queryIn(String beginTime, String endTime, String[] params){
		List<OrderVO> list = null;
		try {
			QueryBuilder<OrderVO, String> builder = mOrderDao.queryBuilder();
			Where<OrderVO, String> where = builder.where()
					.ge("orderCreateTime", beginTime).and()
					.le("orderCreateTime", endTime).and();
			if(params.length == 1){
				where.in("additionState", params[0]);
			} else if(params.length == 2){
				where.in("additionState", params[0], params[1]);
			}else if(params.length == 3){
				where.in("additionState", params[0], params[1], params[2]);
			}
			
			list = mOrderDao.query(builder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private OrderVO queryOrderVO(OrderVO vo){
		return queryOrderVO(vo.getOrderNo());
	}
	
	private OrderVO queryOrderVO(String orderNo){
		OrderVO local = null;
		try {
			local = mOrderDao.queryForId(orderNo);
		} catch (NumberFormatException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		} catch (SQLException e) {
			LogUtils.e(TAG, e);
			e.printStackTrace();
		}
		return local;
	}
}
