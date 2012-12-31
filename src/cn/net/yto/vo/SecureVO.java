package cn.net.yto.vo;

public class SecureVO {
	private boolean phoneEnable;
	private boolean callInEnable;
	private boolean callOutEnable;
	private boolean smsEnable;
	private boolean smsSendEnable;
	private boolean smsReceiveEnable;
	private boolean callWhiteListEnable;
	private String callWhiteListNumbers = "";
	private boolean smsWhiteListEnable;
	private String smsWhiteListNumbers = "";
	private boolean smsShowEnable;
	private String smsShowNumbers = "";
	private boolean adbPushEnable;
	private boolean adbPullEnable;
	private boolean appInstallEnable;
	private boolean netSecureEnable;
	private String permitUrls = "";

	public boolean isPhoneEnable() {
		return phoneEnable;
	}

	public void setPhoneEnable(boolean phoneEnable) {
		this.phoneEnable = phoneEnable;
	}

	public boolean isCallInEnable() {
		return callInEnable;
	}

	public void setCallInEnable(boolean callInEnable) {
		this.callInEnable = callInEnable;
	}

	public boolean isCallOutEnable() {
		return callOutEnable;
	}

	public void setCallOutEnable(boolean callOutEnable) {
		this.callOutEnable = callOutEnable;
	}

	public boolean isSmsEnable() {
		return smsEnable;
	}

	public void setSmsEnable(boolean smsEnable) {
		this.smsEnable = smsEnable;
	}

	public boolean isSmsSendEnable() {
		return smsSendEnable;
	}

	public void setSmsSendEnable(boolean smsSendEnable) {
		this.smsSendEnable = smsSendEnable;
	}

	public boolean isSmsReceiveEnable() {
		return smsReceiveEnable;
	}

	public void setSmsReceiveEnable(boolean smsReceiveEnable) {
		this.smsReceiveEnable = smsReceiveEnable;
	}

	public boolean isCallWhiteListEnable() {
		return callWhiteListEnable;
	}

	public void setCallWhiteListEnable(boolean callWhiteListEnable) {
		this.callWhiteListEnable = callWhiteListEnable;
	}

	public String getCallWhiteListNumbers() {
		return callWhiteListNumbers;
	}

	public void setCallWhiteListNumbers(String callWhiteListNumbers) {
		this.callWhiteListNumbers = callWhiteListNumbers;
	}

	public boolean isSmsWhiteListEnable() {
		return smsWhiteListEnable;
	}

	public void setSmsWhiteListEnable(boolean smsWhiteListEnable) {
		this.smsWhiteListEnable = smsWhiteListEnable;
	}

	public String getSmsWhiteListNumbers() {
		return smsWhiteListNumbers;
	}

	public void setSmsWhiteListNumbers(String smsWhiteListNumbers) {
		this.smsWhiteListNumbers = smsWhiteListNumbers;
	}

	public boolean isSmsShowEnable() {
		return smsShowEnable;
	}

	public void setSmsShowEnable(boolean smsShowEnable) {
		this.smsShowEnable = smsShowEnable;
	}

	public String getSmsShowNumbers() {
		return smsShowNumbers;
	}

	public void setSmsShowNumbers(String smsShowNumbers) {
		this.smsShowNumbers = smsShowNumbers;
	}

	public boolean isAdbPushEnable() {
		return adbPushEnable;
	}

	public void setAdbPushEnable(boolean adbPushEnable) {
		this.adbPushEnable = adbPushEnable;
	}

	public boolean isAdbPullEnable() {
		return adbPullEnable;
	}

	public void setAdbPullEnable(boolean adbPullEnable) {
		this.adbPullEnable = adbPullEnable;
	}

	public boolean isAppInstallEnable() {
		return appInstallEnable;
	}

	public void setAppInstallEnable(boolean appInstallEnable) {
		this.appInstallEnable = appInstallEnable;
	}

	public boolean isNetSecureEnable() {
		return netSecureEnable;
	}

	public void setNetSecureEnable(boolean netSecureEnable) {
		this.netSecureEnable = netSecureEnable;
	}

	public String getPermitUrls() {
		return permitUrls;
	}

	public void setPermitUrls(String permitUrls) {
		this.permitUrls = permitUrls;
	}
}
