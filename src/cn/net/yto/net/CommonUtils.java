package cn.net.yto.net;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import cn.net.yto.utils.LogUtils;

import com.google.gson.Gson;

public class CommonUtils {
	private static final String TAG = "CommonUtils";
	public static final Pattern sDateTimePattern = Pattern
			.compile("(\\d4)(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
	public static long timeInterval = 0;

	public final static String MD5Encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getPhoneIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		return imei;
	}

	public static String getSoftwareUpdateXml() {
		return "";
	}

	// dateTime yyyyMMddHHmmss
	public static long parseDateTime(String dateTimeString) {
		long dateTime = -1L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			dateTime = sdf.parse(dateTimeString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return dateTime;
	}

	public static String getFormatedDateTime(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(System.currentTimeMillis() + timeInterval));
	}

	public static String getFormatedDateTime(String pattern, long dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(dateTime + timeInterval));
	}

	public static void changeWifiState(Context context, boolean enable) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (enable) {
			if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
				LogUtils.i(TAG, "changeWifiState wifi is already WIFI_STATE_ENABLED");
			} else if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
				LogUtils.i(TAG, "changeWifiState wifi is  WIFI_STATE_ENABLING");
			} else {
				wm.setWifiEnabled(enable);
				LogUtils.i(TAG, "changeWifiState to enable = " + enable);
			}
		} else {
			if (wm.getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
				LogUtils.i(TAG,
						"changeWifiState wifi is already WIFI_STATE_DISABLED");
			} else if (wm.getWifiState() == WifiManager.WIFI_STATE_DISABLING) {
				Log.i(TAG, "changeWifiState wifi is  WIFI_STATE_DISABLING");
			} else {
				wm.setWifiEnabled(enable);
				LogUtils.i(TAG, "changeWifiState to enable = " + enable);
			}
		}
	}

	public static int getWifiState(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		int state = wm.getWifiState();
		LogUtils.i(TAG, "getWifiState state = " + state);
		return state;
	}

	public static boolean hasActiveNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cm.getActiveNetworkInfo();
		
//		if(info == null){
//			return false;
//		} else if(info.getState() != State.CONNECTED
//				&& info.getState() != State.CONNECTING){
//			return false;
//		}
//		return true;
		if(info != null){
			return info.isAvailable();
		}
		return false;
	}

	public static int getMobileDataNetworkState(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDataState();
	}

	public static void changNetworkState(Context context,
			String type, boolean enable) {
		Intent intent = new Intent("android.intent.action.CHANGE_NETWORK_STATE");
		intent.putExtra("type", type);
		intent.putExtra("enable", enable);
		LogUtils.i(TAG, "sendChangeNetworkStateBroadcast type = " + type
				+ " enable = " + enable);
		if ("wifi".equalsIgnoreCase(type)) {
			changeWifiState(context, enable);
			
//			int wifiState = getWifiState(context);
//			if (enable && (wifiState == WifiManager.WIFI_STATE_ENABLED)) {
//				Log.i(TAG,
//						"sendChangeNetworkStateBroadcast wifi is already WIFI_STATE_ENABLED");
//			} else if (enable && (wifiState == WifiManager.WIFI_STATE_ENABLING)) {
//				Log.i(TAG,
//						"sendChangeNetworkStateBroadcast wifi is already WIFI_STATE_ENABLING");
//			} else if (!enable
//					&& (wifiState == WifiManager.WIFI_STATE_DISABLED)) {
//				Log.i(TAG,
//						"sendChangeNetworkStateBroadcast wifi is already WIFI_STATE_DISABLED");
//			} else if (!enable
//					&& (wifiState == WifiManager.WIFI_STATE_DISABLING)) {
//				Log.i(TAG,
//						"sendChangeNetworkStateBroadcast wifi is already WIFI_STATE_DISABLING");
//			} else {
//				context.sendBroadcast(intent);
//			}
		} else if ("mobile".equalsIgnoreCase(type)) {
			int mobileState = getMobileDataNetworkState(context);

			if (enable && (mobileState == TelephonyManager.DATA_CONNECTED)) {
				LogUtils.i(TAG,
						"sendChangeNetworkStateBroadcast data net is already DATA_CONNECTED");
			} else if (enable
					&& (mobileState == TelephonyManager.DATA_CONNECTING)) {
				LogUtils.i(TAG,
						"sendChangeNetworkStateBroadcast data net is already DATA_CONNECTING");
			} else if (!enable
					&& (mobileState == TelephonyManager.DATA_DISCONNECTED)) {
				LogUtils.i(TAG,
						"sendChangeNetworkStateBroadcast data net is already DATA_DISCONNECTED");
			} else if (!enable
					&& (mobileState == TelephonyManager.DATA_SUSPENDED)) {
				LogUtils.i(TAG,
						"sendChangeNetworkStateBroadcast data net is already DATA_SUSPENDED");
			} else {
				context.sendBroadcast(intent);
			}
		}
	}
	
	public static void startNetworkSettingActivity(Context context){
		Intent intent = new Intent(
				"android.settings.WIRELESS_SETTINGS");
		context.startActivity(intent);
	}
	
	public static void startSystemSetting(Context context){
		Intent intent = new Intent(
				"android.settings.SETTINGS");
		context.startActivity(intent);
	}
	
	public static void startWifiSettingActivity(Context context){
		context.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
	}
	
	public static void startMobileDataSettingActivity(Context context){
		Intent mIntent = new Intent("/");
        ComponentName comp = new ComponentName(
                "com.android.phone",
                "com.android.phone.Settings");
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        context.startActivity(mIntent);
	}

	public static void setSystemDateTime(Activity context, long datetime) {
		Intent intent = new Intent("android.intent.action.SET_DATETIME");
		intent.putExtra("datetime", datetime);
		context.sendBroadcast(intent);
		timeInterval = datetime - System.currentTimeMillis();
	}
	
	public static void closeSilently(Cursor c){
		if(c != null){
			c.close();
		}
	}
	
	public static boolean isEmpty(String str) {
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static int length(String str){
		if(str == null){
			return 0;
		} else {
			return str.length();
		}
	}
	
	public static boolean equals(String s, String s1){
		if(s != null && s1 != null){
			return s.equals(s1);
		}
		return false;
	}
	
	public static String readInternalFile(Context context, String fileName) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(
					context.openFileInput(fileName), "GBK"));
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			//Log.e(TAG, e.toString());
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void writeInternalFile(Context context, String fileName, String content, int mode) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, mode);
			fos.write(content.getBytes("GBK"));
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deleteInternalFile(Context context, String fileNamePre){
		context.deleteFile(fileNamePre+ "*");
	}

	public static void closeSilently(Socket mSocket) {
		try {
			if (mSocket != null) {
				mSocket.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static void closeSilently(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static void closeSilently(OutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static void closeSilently(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static void closeSilently(Writer writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static String toJson(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> classOfT){
		Gson gson = new Gson();
		
		return gson.fromJson(json, classOfT);
	}
	
	public static <T> T fromJson(String json, Type typeOfT){
		Gson gson = new Gson();
		
		return gson.fromJson(json, typeOfT);
	}
	
	public static void setEnable(View view, boolean enalbe){
		if(view != null){
			view.setEnabled(enalbe);
			view.setFocusable(enalbe);
			view.setFocusableInTouchMode(enalbe);
		}
	}
}
