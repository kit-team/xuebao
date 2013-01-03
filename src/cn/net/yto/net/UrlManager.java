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
		sUrlTailMap.put(UrlType.SUBMIT_SIGNEDLOG, "m_delivery!SubmitSignedLog.action");		
		sUrlTailMap.put(UrlType.UPDATE_SIGNEDLOG, "m_delivery!updateSignedLog.action");		
		
		sRequestParamNameMap = new HashMap<UrlType, String>();
		sRequestParamNameMap.put(UrlType.LOGIN, "WSLoginParam");
		sRequestParamNameMap.put(UrlType.LOGOUT, "WSParam");
		sRequestParamNameMap.put(UrlType.MODIFY_PSW, "WSModifyUserPasswordParam");
		sRequestParamNameMap.put(UrlType.SUBMIT_SIGNEDLOG, "WSSignedLogParam");		
		sRequestParamNameMap.put(UrlType.UPDATE_SIGNEDLOG, "WSSignedLogParam");		
		
		sResponseParamNameMap = new HashMap<UrlType, String>();
		sResponseParamNameMap.put(UrlType.LOGIN, "WSLoginReturn");
		sResponseParamNameMap.put(UrlType.LOGOUT, "WSLogoutReturn");
		sResponseParamNameMap.put(UrlType.MODIFY_PSW, "WSModifyUserPasswordReturn");
		sResponseParamNameMap.put(UrlType.SUBMIT_SIGNEDLOG, "WSSignedLogReturn");
		sResponseParamNameMap.put(UrlType.SUBMIT_SIGNEDLOG, "WSSignedLogReturn");
		
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
