package cn.net.yto.service;

import android.app.AlarmManager;
import android.content.Intent;
import cn.net.yto.application.AppContext;
import cn.net.yto.common.Constants;
import cn.net.yto.common.utils.AlarmManagerUtil;

public class YtoBizService {
	
	public static void startDownloadOrder() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_DOWNLOAD_ORDER);
		long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
		AlarmManagerUtil.sendRepeatAlarmBroadcast(AppContext.getAppContext(), intent, interval);
	}
	
	public static void cancelDownloadOrder(){
		Intent intent = new Intent(Constants.ACTION_RECEIVER_DOWNLOAD_ORDER);
		AlarmManagerUtil.cancelAlarmBroadcast(AppContext.getAppContext(), intent);
	}
	
	public static void startUploadRepeatAlarm() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_BACKGROUND_NET);
		long interval = 1000 * 60;//AlarmManager.INTERVAL_FIFTEEN_MINUTES;
		AlarmManagerUtil.sendRepeatAlarmBroadcast(AppContext.getAppContext(), intent, interval);
	}
	
	public static void cancelUploadRepeatAlarm(){
		Intent intent = new Intent(Constants.ACTION_RECEIVER_BACKGROUND_NET);
		AlarmManagerUtil.cancelAlarmBroadcast(AppContext.getAppContext(), intent);
	}
	
	public static void updateOrderStateRepeatAlarm() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_UPDATE_ORDER_STATE);
		long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
		AlarmManagerUtil.sendRepeatAlarmBroadcast(AppContext.getAppContext(), intent, interval);
	}
	
	public static void cancelUpdateOrderStateRepeatAlarm() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_UPDATE_ORDER_STATE);
		AlarmManagerUtil.cancelAlarmBroadcast(AppContext.getAppContext(), intent);
	}
}
