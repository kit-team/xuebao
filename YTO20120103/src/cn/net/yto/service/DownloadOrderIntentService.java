package cn.net.yto.service;

import android.content.Intent;
import android.os.Looper;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.biz.UserManager;
import cn.net.yto.common.utils.ALongRunningNonStickyBroadcastService;
import cn.net.yto.vo.UserVO;
import cn.net.yto.vo.message.DownloadOrderRequestMsgVO;

public class DownloadOrderIntentService extends ALongRunningNonStickyBroadcastService {

	public DownloadOrderIntentService() {
		super(DownloadOrderIntentService.class.getName());
	}

	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) {
		new Thread(){
			@Override
			public void run() {
				Looper.prepare();
				OrderManager orderService = AppContext.getAppContext().getOrderService();
				DownloadOrderRequestMsgVO requestVO = orderService.getOrderVO();
				UserManager userService = AppContext.getAppContext().getUserService();
				UserVO userVO = userService.getUserVO();
				requestVO.setDeliveryEmpCode(userVO.getEmpCode());
				requestVO.setFetchType(1);
				orderService.downloadOrder(AppContext.getAppContext());
				Looper.loop();
			}
		}.start();
	}

}
