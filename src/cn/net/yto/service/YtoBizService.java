package cn.net.yto.service;

import android.app.Activity;
import android.content.Intent;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.UserManager;
import cn.net.yto.common.Constants;
import cn.net.yto.common.utils.AlarmManagerUtil;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.UserVO;

public class YtoBizService {
	
	public static void startDownloadOrder() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_DOWNLOAD_ORDER);
		UserVO vo = UserManager.getInstance().getUserVO();
		long interval = 1000 * 60;//默认值
		if(vo != null){
			if(vo.getGetOrderPeriod() > 0){
				try{
					interval = vo.getGetOrderPeriod();
					interval *= 1000;
				}catch(Exception e){
				}
			}
		}
		AlarmManagerUtil.sendRepeatAlarmBroadcast(AppContext.getAppContext(), intent, interval);
	}
	
	public static void cancelDownloadOrder(){
		Intent intent = new Intent(Constants.ACTION_RECEIVER_DOWNLOAD_ORDER);
		AlarmManagerUtil.cancelAlarmBroadcast(AppContext.getAppContext(), intent);
	}
	
	public static void startUploadRepeatAlarm() {
		Intent intent = new Intent(Constants.ACTION_RECEIVER_BACKGROUND_NET);
		UserVO vo = UserManager.getInstance().getUserVO();
		long interval = 1000 * 60;//默认值
		if(vo != null){
			if(vo.getSubmitBizPeriod() > 0){
				try{
					interval = vo.getSubmitBizPeriod();
					interval *= 1000;
				}catch(Exception e){
				}
			}
		}
		AlarmManagerUtil.sendRepeatAlarmBroadcast(AppContext.getAppContext(), intent, interval);
	}
	
	public static void cancelUploadRepeatAlarm(){
		Intent intent = new Intent(Constants.ACTION_RECEIVER_BACKGROUND_NET);
		AlarmManagerUtil.cancelAlarmBroadcast(AppContext.getAppContext(), intent);
	}
	
	public static void synchSystemTime(Activity context){
		UserVO vo = UserManager.getInstance().getUserVO();
		if(vo != null){
			if(vo.getNowDate() != null && vo.getNowDate().length() > 0){
				try{
					long timeInMillis = CommonUtils.parseDateTime(vo.getNowDate(), CommonUtils.FORMAT_TIME);
					CommonUtils.setSystemDateTime(context, timeInMillis);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
