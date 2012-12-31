package cn.net.yto.vo.message;

public class AppUpgradeRequestMsgVO extends BaseRequestMsgVO {
	// PDA编号
	private String pdaNo;
	// PDA软件版本号
	private String pdaVersion;
	// 面单号
	private String pdaDriveType;
	// PDA组
	private String orgId;

	public String getPdaNo() {
		return pdaNo;
	}

	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

	public String getPdaVersion() {
		return pdaVersion;
	}

	public void setPdaVersion(String pdaVersion) {
		this.pdaVersion = pdaVersion;
	}

	public String getPdaDriveType() {
		return pdaDriveType;
	}

	public void setPdaDriveType(String pdaDriveType) {
		this.pdaDriveType = pdaDriveType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
