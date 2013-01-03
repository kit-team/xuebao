package cn.net.yto.service;

import android.content.Intent;
import cn.net.yto.common.utils.ALongRunningNonStickyBroadcastService;
import cn.net.yto.net.HttpTaskManager;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.utils.LogUtils;

public class BackgroundNetIntentService extends ALongRunningNonStickyBroadcastService {
	private static final String TAG = "BackgroundNetAlarmIntentService";
	private HttpTaskManager mHttpTaskManager = HttpTaskManager.getInstance();

	public BackgroundNetIntentService() {
		super(BackgroundNetIntentService.class.getName());
	}

	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) {
		ZltdHttpClient client = mHttpTaskManager.getUploadTask();
		while(client != null){
			if(!client.submit(this)){
				break;
			}
			
			while(client.getState() == ZltdHttpClient.STATE_UPLOADING){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					LogUtils.e(TAG, e);
				}
			}
			mHttpTaskManager.removeTask(client);
			client = mHttpTaskManager.getUploadTask();
		}
	}

}
