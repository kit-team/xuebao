package cn.net.yto.vo;

public class ExpressTraceVO {
	/**
	 * 操作名称 
	 */
	private String opName;
	/**
	 * 操作时间
	 */
	private String opTime;
	
	/**
	 * 
	 */
	private String orgCode;
	
	/**
	 * 所在网点 
	 */
	private String orgName;
	
	/**
	 * 
	 */
	private String revName;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	public String getOpName() {
		return opName;
	}
	
	public void setOpName(String opName) {
		this.opName = opName;
	}
	
	public String getOpTime() {
		return opTime;
	}
	
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getRevName() {
		return revName;
	}
	
	public void setRevName(String revName) {
		this.revName = revName;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

}
