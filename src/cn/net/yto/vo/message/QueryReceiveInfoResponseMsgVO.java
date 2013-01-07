package cn.net.yto.vo.message;

public class QueryReceiveInfoResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 返回值
	 */
	private int retVal;
	/**
	 * 成功或失败的原因
	 */
	private String failMessage;
	/**
	 * 派件扫描数
	 */
	private String countDeliveryScan;
	/**
	 * 正常收件数
	 */
	private String countNormalReceive;
	/**
	 * 正常签收数 
	 */
	private String countNormalSignedLog;
	/**
	 * 接收的订单数
	 */
	private String countOrder;
	/**
	 * 异常收件数
	 */
	private String countUnNormalReceive;
	/**
	 * 异常签收数
	 */
	private String countUnNormalSignedLog;
	
	public int getRetVal() {
		return retVal;
	}
	
	public void setRetVal(int retVal) {
		this.retVal = retVal;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
	public String getCountDeliveryScan() {
		return countDeliveryScan;
	}
	
	public void setCountDeliveryScan(String countDeliveryScan) {
		this.countDeliveryScan = countDeliveryScan;
	}
	
	public String getCountNormalReceive() {
		return countNormalReceive;
	}
	
	public void setCountNormalReceive(String countNormalReceive) {
		this.countNormalReceive = countNormalReceive;
	}
	
	public String getCountNormalSignedLog() {
		return countNormalSignedLog;
	}
	
	public void setCountNormalSignedLog(String countNormalSignedLog) {
		this.countNormalSignedLog = countNormalSignedLog;
	}
	
	public String getCountOrder() {
		return countOrder;
	}
	
	public void setCountOrder(String countOrder) {
		this.countOrder = countOrder;
	}
	
	public String getCountUnNormalReceive() {
		return countUnNormalReceive;
	}
	
	public void setCountUnNormalReceive(String countUnNormalReceive) {
		this.countUnNormalReceive = countUnNormalReceive;
	}
	
	public String getCountUnNormalSignedLog() {
		return countUnNormalSignedLog;
	}
	
	public void setCountUnNormalSignedLog(String countUnNormalSignedLog) {
		this.countUnNormalSignedLog = countUnNormalSignedLog;
	}

}
