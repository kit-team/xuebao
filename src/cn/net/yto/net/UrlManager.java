package cn.net.yto.net;

import java.util.HashMap;

public class UrlManager {
	private static HashMap<UrlType, String> sUrlTailMap;
	private static HashMap<UrlType, String> sRequestParamNameMap;
	private static HashMap<UrlType, String> sResponseParamNameMap;
	private static String sUrlPre;
	
	static{
		sUrlTailMap= new HashMap<UrlType, String>();
		
		sUrlTailMap.put(UrlType.LOGIN, "m_login!PDAlogin.action");
		sUrlTailMap.put(UrlType.LOGOUT, "m_login!PDAlogout.action");
		sUrlTailMap.put(UrlType.MODIFY_PSW, "m_login!+.action");
		sUrlTailMap.put(UrlType.VERSION_CHECK, "m_login!SoftwareVerSyn.action");
		sUrlTailMap.put(UrlType.BASIC_DATA, "m_basic_data!DownloadBasicDataByVersion.action");
		sUrlTailMap.put(UrlType.DOWNLOAD_ORDER, "m_receive!DownloadOrder.action");
		sUrlTailMap.put(UrlType.MODIFY_ORDER_STATUS, "m_receive!ModifyOrderStatus.action");
		sUrlTailMap.put(UrlType.SUBMIT_RECEIVE, "m_receive!SubmitReceive.action");
		sUrlTailMap.put(UrlType.UPDATE_RECEIVE, "m_receive!UpdateReceive.action");
		sUrlTailMap.put(UrlType.SUBMIT_SIGNEDLOG, "m_delivery!SubmitSignedLog.action");	
		sUrlTailMap.put(UrlType.UPDATE_SIGNEDLOG, "m_delivery!updateSignedLog.action");		
		sUrlTailMap.put(UrlType.CANCEL_RECEIVE, "m_receive!CancelReceive.action");		
		sUrlTailMap.put(UrlType.REPLACE_RECEIVE, "m_receive!ReplaceReceive.action");		
		sUrlTailMap.put(UrlType.DELETE_SIGNEDLOG, "m_delivery!DeleteSignedLog.action");	
		sUrlTailMap.put(UrlType.DOWNLOAD_ORDER_CANCEL, "m_receive!DownloadOrderCancel.action");
		sUrlTailMap.put(UrlType.UPDATE_ORDER_CANCEL, "m_receive!updatePushCancelState.action");
		
//		sRequestParamNameMap = new HashMap<UrlType, String>();
//		sRequestParamNameMap.put(UrlType.LOGIN, "WSLoginParam");
//		sRequestParamNameMap.put(UrlType.LOGOUT, "WSParam");
//		sRequestParamNameMap.put(UrlType.MODIFY_PSW, "WSModifyUserPasswordParam");
//		
//		sResponseParamNameMap = new HashMap<UrlType, String>();
//		sResponseParamNameMap.put(UrlType.LOGIN, "WSLoginReturn");
//		sResponseParamNameMap.put(UrlType.LOGOUT, "WSLogoutReturn");
//		sResponseParamNameMap.put(UrlType.MODIFY_PSW, "WSModifyUserPasswordReturn");
	}

	public static void setServerUrl(String serverUrl){
		StringBuffer sb = new StringBuffer();
		
		if(!serverUrl.startsWith("http://")){
			sb.append("http://");
		}
		
		sb.append(serverUrl);
		
		if(!serverUrl.endsWith("/")){
			sb.append("/");
		} 
		
		sUrlPre = sb.toString();
	}
	
	public static String getUrl(UrlType type){
		if(sUrlTailMap.containsKey(type)){
			StringBuffer sb = new StringBuffer();
			
			sb.append(sUrlPre);
			sb.append(sUrlTailMap.get(type));
			
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public static String getRequestParamName(UrlType type){
		return sRequestParamNameMap.get(type);
	}
	
	public static String getResponseParamName(UrlType type){
		return sResponseParamNameMap.get(type);
	}
}
