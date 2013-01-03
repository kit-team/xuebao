package cn.net.yto.receiver;

import android.app.IntentService;
import cn.net.yto.common.utils.ALongRunningReceiver;
import cn.net.yto.service.DownloadOrderIntentService;

public class DownloadOrderReceiver extends ALongRunningReceiver {

	@Override
	public Class<? extends IntentService> getLRSClass() {
		return DownloadOrderIntentService.class;
	}

}
