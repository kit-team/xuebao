package cn.net.yto.vo.message;


public class LoginRequestMsgVO extends BaseRequestMsgVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 登录用户密码
	 */
	private String password;
	/**
	 * PDA设备号
	 */
	private String pdaNumber;
	/**
	 * 是否强制登录 1：强制登录；0：非强制登录
	 */
	private String force;

	private String versionNo;

	private String pdaLocalTime;

	private String isUpload;

	private int uploadStatu;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPdaNumber() {
		return pdaNumber;
	}

	public void setPdaNumber(String pdaNumber) {
		this.pdaNumber = pdaNumber;
	}

	public String getForce() {
		return force;
	}

	public void setForce(String force) {
		this.force = force;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getPdaLocalTime() {
		return pdaLocalTime;
	}

	public void setPdaLocalTime(String pdaLocalTime) {
		this.pdaLocalTime = pdaLocalTime;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public int getUploadStatu() {
		return uploadStatu;
	}

	public void setUploadStatu(int uploadStatu) {
		this.uploadStatu = uploadStatu;
	}

}
