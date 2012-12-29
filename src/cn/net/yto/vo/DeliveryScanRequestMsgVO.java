package cn.net.yto.vo;

public class DeliveryScanRequestMsgVO extends BaseRequestMsgVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 扫描时间
	 */
	private String scanTime;
	
	/**
	 * 收派员工号
	 */
	private String deliveryEmpCode;
	
	/**
	 * 面单号
	 */
	private String wayBillNo;
	
	public String getScanTime() {
		return scanTime;
	}
	
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	
	public String getDeliveryEmpCode() {
		return deliveryEmpCode;
	}
	
	public void setDeliveryEmpCode(String deliveryEmpCode) {
		this.deliveryEmpCode = deliveryEmpCode;
	}
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
}
