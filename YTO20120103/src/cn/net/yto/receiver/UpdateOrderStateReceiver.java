package cn.net.yto.receiver;

import android.app.IntentService;
import cn.net.yto.common.utils.ALongRunningReceiver;
import cn.net.yto.service.UpdateOrderStateIntentService;

public class UpdateOrderStateReceiver extends ALongRunningReceiver {

	@Override
	public Class<? extends IntentService> getLRSClass() {
		return UpdateOrderStateIntentService.class;
	}

}
