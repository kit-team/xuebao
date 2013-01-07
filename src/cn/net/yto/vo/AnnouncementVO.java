package cn.net.yto.vo;

public class AnnouncementVO {
	/**
	 * 公告ID
	 */
	private String id;
	/**
	 * 公告类型 
	 */
	private String noticeType;
	/**
	 * 机构ID
	 */
	private String orgID;
	/**
	 * 公告标题 
	 */
	private String noticeTitle;
	/**
	 * 公告内容
	 */
	private String noticeContent;
	/**
	 * 公告时间
	 */
	private String noticeTime;
	/**
	 * 公告状态
	 */
	private String state;
	/**
	 * 版本号
	 */
	private long versionNo;
	/**
	 * 是否有效状态
	 */
	private String status;
	/**
	 * 
	 */
	private String empName;
	/**
	 * 
	 */
	private String facedOrgCode;
	/**
	 * 
	 */
	private String time;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNoticeType() {
		return noticeType;
	}
	
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	public String getOrgID() {
		return orgID;
	}
	
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
	public String getNoticeTitle() {
		return noticeTitle;
	}
	
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	
	public String getNoticeContent() {
		return noticeContent;
	}
	
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeTime() {
		return noticeTime;
	}
	
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public long getVersionNo() {
		return versionNo;
	}
	
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEmpName() {
		return empName;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getFacedOrgCode() {
		return facedOrgCode;
	}
	
	public void setFacedOrgCode(String facedOrgCode) {
		this.facedOrgCode = facedOrgCode;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
