package cn.net.yto.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.xmlpull.v1.XmlSerializer;

import cn.net.yto.vo.SecureVO;

import android.content.Context;
import android.content.Intent;
import android.util.Xml;

public class SecureUtils {
	public static void setDefaultSecureSetting(Context context){
		SecureVO secureVO = new SecureVO();
		secureVO.setAdbPullEnable(true);
		secureVO.setAdbPushEnable(true);
		secureVO.setAppInstallEnable(true);
		secureVO.setCallInEnable(false);
		secureVO.setCallOutEnable(false);
		secureVO.setPhoneEnable(false);
		secureVO.setSmsEnable(false);
		secureVO.setSmsReceiveEnable(false);
		secureVO.setSmsSendEnable(false);
		secureVO.setNetSecureEnable(false);
		Intent wIntent = new Intent();
		wIntent.setAction("android.intent.action.CHANGE_SETTING");
		wIntent.putExtra("android.intent.extra.CHANGE_SETTING_CONTENT", getXmlByteArray(secureVO));
		context.sendBroadcast(wIntent);
	}
	
	public static void openAppInstall(Context context){
		SecureVO secureVO = new SecureVO();
		secureVO.setAdbPullEnable(true);
		secureVO.setAdbPushEnable(true);
		secureVO.setAppInstallEnable(true);
		secureVO.setCallInEnable(false);
		secureVO.setCallOutEnable(false);
		secureVO.setPhoneEnable(false);
		secureVO.setSmsEnable(false);
		secureVO.setSmsReceiveEnable(false);
		secureVO.setSmsSendEnable(false);
		secureVO.setNetSecureEnable(false);
		Intent wIntent = new Intent();
		wIntent.setAction("android.intent.action.CHANGE_SETTING");
		wIntent.putExtra("android.intent.extra.CHANGE_SETTING_CONTENT", getXmlByteArray(secureVO));
		context.sendBroadcast(wIntent);
	}
	
	public static void closeSecureSetting(Context context){
		SecureVO secureVO = new SecureVO();
		secureVO.setAdbPullEnable(true);
		secureVO.setAdbPushEnable(true);
		secureVO.setAppInstallEnable(true);
		secureVO.setCallInEnable(true);
		secureVO.setCallOutEnable(true);
		secureVO.setPhoneEnable(true);
		secureVO.setSmsEnable(true);
		secureVO.setSmsReceiveEnable(true);
		secureVO.setSmsSendEnable(true);
		secureVO.setNetSecureEnable(false);
		Intent wIntent = new Intent();
		wIntent.setAction("android.intent.action.CHANGE_SETTING");
		wIntent.putExtra("android.intent.extra.CHANGE_SETTING_CONTENT", getXmlByteArray(secureVO));
		context.sendBroadcast(wIntent);
	}
	
	private static byte[] getXmlByteArray(SecureVO secureVO) {
		OutputStreamWriter writer = null;
		try {
			// 将数据写入XML文件，并保存到SD卡中
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				writer = new OutputStreamWriter(baos);

				XmlSerializer serializer = Xml.newSerializer();// 序列化
				serializer.setOutput(writer);// 输出流

				// 开始文档
				serializer.startDocument("UTF-8", true);
				serializer.startTag(null, "map");

				// 电话功能
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_phone_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isPhoneEnable());
				serializer.endTag(null, "boolean");

				// 电话呼入
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_callin_key");
				serializer.attribute(null, "value",
						"" + secureVO.isCallInEnable());
				serializer.endTag(null, "boolean");

				// 电话呼出
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_callout_key");
				serializer.attribute(null, "value",
						"" + secureVO.isCallOutEnable());
				serializer.endTag(null, "boolean");

				// 短信功能
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_sms_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isSmsEnable());
				serializer.endTag(null, "boolean");

				// 短信接收
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_smsin_key");
				serializer.attribute(null, "value",
						"" + secureVO.isSmsReceiveEnable());
				serializer.endTag(null, "boolean");

				// 短信发送
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_smsout_key");
				serializer.attribute(null, "value",
						"" + secureVO.isSmsSendEnable());
				serializer.endTag(null, "boolean");

				// 电话白名单
				serializer.startTag(null, "boolean");
				serializer
						.attribute(null, "name", "pref_phonewhitelist_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isCallWhiteListEnable());
				serializer.endTag(null, "boolean");

				// 电话白名单号码
				serializer.startTag(null, "string");
				serializer.attribute(null, "name",
						"pref_phonewhitelist_set_key");
				serializer.text(secureVO.getCallWhiteListNumbers());
				serializer.endTag(null, "string");

				// 短信白名单
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_smswhitelist_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isSmsWhiteListEnable());
				serializer.endTag(null, "boolean");

				// 短信白名单号码
				serializer.startTag(null, "string");
				serializer.attribute(null, "name", "pref_smswhitelist_set_key");
				serializer.text(secureVO.getSmsWhiteListNumbers());
				serializer.endTag(null, "string");

				// 短信直显
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_smsshow_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isSmsShowEnable());
				serializer.endTag(null, "boolean");

				// 短信直显号码
				serializer.startTag(null, "string");
				serializer.attribute(null, "name", "pref_smsshow_set_key");
				serializer.text(secureVO.getSmsShowNumbers());
				serializer.endTag(null, "string");

				// adb push
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_adbpush_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isAdbPushEnable());
				serializer.endTag(null, "boolean");

				// adb pull
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_adbpull_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isAdbPullEnable());
				serializer.endTag(null, "boolean");

				// 应用程序安装
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_securityapp_on_key");
				serializer.attribute(null, "value",
						"" + secureVO.isAppInstallEnable());
				serializer.endTag(null, "boolean");

				// 网络安全
				serializer.startTag(null, "boolean");
				serializer.attribute(null, "name", "pref_netsecurity_key");
				serializer.attribute(null, "value",
						"" + secureVO.isNetSecureEnable());
				serializer.endTag(null, "boolean");

				// 静止网址
				serializer.startTag(null, "string");
				serializer.attribute(null, "name", "pref_netsecurity_set_key");
				serializer.text(secureVO.getPermitUrls());
				serializer.endTag(null, "string");

				serializer.endTag(null, "map");
				serializer.endDocument();
				writer.flush(); 
				return baos.toByteArray();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
