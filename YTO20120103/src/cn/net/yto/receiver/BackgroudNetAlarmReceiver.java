package cn.net.yto.receiver;

import android.app.IntentService;
import cn.net.yto.common.utils.ALongRunningReceiver;
import cn.net.yto.service.BackgroundNetIntentService;

public class BackgroudNetAlarmReceiver extends ALongRunningReceiver {

	@Override
	public Class<? extends IntentService> getLRSClass() {
		return BackgroundNetIntentService.class;
	}
}
