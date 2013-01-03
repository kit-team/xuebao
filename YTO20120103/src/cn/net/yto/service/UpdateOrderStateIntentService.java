package cn.net.yto.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.common.utils.ALongRunningNonStickyBroadcastService;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;
import cn.net.yto.vo.message.ModifyOrderStatusRequestMsgVO;

public class UpdateOrderStateIntentService extends
		ALongRunningNonStickyBroadcastService {
	private static final int RESULT_SUCCESS = 1;
	private List<String> backupLink = new ArrayList<String>();
	
	public UpdateOrderStateIntentService() {
		super(UpdateOrderStateIntentService.class.getName());
	}

	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) {
		List<OrderVO> list = AppContext.getAppContext().getOrderService()
				.queryUnUploadOrderVO();
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				final OrderVO vo = list.get(i);
				if(backupLink.contains(vo.getOrderNo())) continue;
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
							if(responseVO.getRetVal() == RESULT_SUCCESS){
								Integer state = Integer.parseInt(vo.getAdditionState());
								if(OrderManager.STATE_FETCHED.equals(state)){
									vo.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED_UPLOADED));
								} else if(OrderManager.STATE_FETCHED_EXCEPTION.equals(state)){
									vo.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED_UPLOADED_EXCEPTION));
								}
								AppContext.getAppContext().getOrderService().updateOrder(vo);
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
		}
	}

}
