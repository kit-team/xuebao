package cn.net.yto.service;

import android.content.Intent;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.biz.UserManager;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.common.utils.ALongRunningNonStickyBroadcastService;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.UserVO;
import cn.net.yto.vo.message.DownloadOrderRequestMsgVO;

public class DownloadOrderIntentService extends ALongRunningNonStickyBroadcastService {

	public DownloadOrderIntentService() {
		super(DownloadOrderIntentService.class.getName());
	}

	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) {
		OrderManager orderService = OrderManager.getInstance();
		DownloadOrderRequestMsgVO requestVO = new DownloadOrderRequestMsgVO();
		UserManager userService = UserManager.getInstance();
		UserVO userVO = userService.getUserVO();
		requestVO.setDeliveryEmpCode(userVO.getEmpCode());
		requestVO.setFetchType(1);
		try {
			orderService.downloadOrder(this);
		} catch (NetworkUnavailableException e) {
			LogUtils.e("DownloadOrderIntentService", e);
			e.printStackTrace();
		}
	}

}
