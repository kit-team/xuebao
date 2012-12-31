package cn.net.yto.common;

public interface Constants {
	public static final int DEBUG_LEVEL_RELEASE = 0;//0：release 版本；
	public static final int DEBUG_LEVEL_DEBUG_LOW = 1;//1 ：带log信息的版本；
	public static final int DEBUG_LEVEL_DEBUG_HIGH = 2;//2：带测试菜单的版本
	public static final int DEBUG_LEVEL_DEFAULT = DEBUG_LEVEL_DEBUG_HIGH;
	
	public static final String SUPER_USER_PASSWORD = "111111";
	public static final String EXIT_PASSWORD = "0123456789";
	
	public static final int MSG_DOWNLOAD_PLAN_TASK_FINISH = 1;
	public static final int MSG_DOWNLOAD_PLAN_TASK_FAILED = 2;
	public static final int MSG_NO_PLAN_TASK = 3;
	public static final int MSG_SCAN_SUCCESS = 4;
	public static final int MSG_ALREADY_SCAN = 5;
	public static final int MSG_ESC_SUCCESS = 6;
	public static final int MSG_ESC_FAILED = 7;
	public static final int MSG_UPLOADED_SUCCESS = 8;
	public static final int MSG_UPLOADED_FAILED = 9;
	public static final int MSG_INIT_START = 10;
	public static final int MSG_INIT_STOP = 11;
	public static final int MSG_UPLOAD_START = 12;
	public static final int MSG_UPLOAD_STOP = 13;
	public static final int MSG_STATE_CHANGED = 14;
	public static final int MSG_START_PROGRESSDIALOG = 100;
	public static final int MSG_STOP_PROGRESSDIALOG = 101;
	
	public String KEY_NETWORK_TYPE = "network_type";
	public String KEY_USERNAME = "username";
	
	public String KEY_PDA_SERVER_URL = "pda_server_url";
	
	public String KEY_DEBUG_SWITCH = "debug_switch";
	
	public String DATETIME_PATTERN_SIMPLE = "MM-dd HH:mm";
	public String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public String TIME_PATTERN = "HHmmss";
	
	public String STATE_NOT_UPLOADED = "not_uploaded";
	public String STATE_UPLOADED_SUCCESS = "uploaded_success";
	public String STATE_UPLOADED_FAIL = "uploaded_fail";
	
	public int SOUND_TYPE_SUCCESS = 0;
	public int SOUND_TYPE_WARNING = 1;
	public int SOUND_TYPE_QUERY = 2;
	
	public static String ACTION_RECEIVER_DOWNLOAD_ORDER = "cn.net.yto.ACTION_RECEIVER_DOWNLOAD_ORDER";
	public static String ACTION_RECEIVER_BACKGROUND_NET = "cn.net.yto.ACTION_RECEIVER_BACKGROUND_NET";

}
