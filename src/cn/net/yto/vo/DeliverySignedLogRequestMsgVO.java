package cn.net.yto.vo;

public class DeliverySignedLogRequestMsgVO extends BaseRequestMsgVO {
	/**
	 * 收派员工号
	 */
	private String empCode;
	
	/**
	 * 面单号
	 */
	private String waybillNo;
	
	/**
	 * 签收时间
	 */
	private String signedTime;
	
	/**
	 * 签收数据  (图片)的二进制数据经过base64编码后的字符串 
	 */
	private String pictureData;
	
	/**
	 * 签收状态标记  "1"- 签收成功，签收失败输入失败原因的编码 
	 */
	private String signedState;
	
	/**
	 * 满意度 - 很满意，满意，不满意
	 */
	private String satisfaction;
	
	/**
	 * 异常签收描述
	 */
	private String expSignedDescription;
	
	/**
	 * 现金金额
	 */
	private float cashAmount;
	
	/**
	 * 刷卡金额
	 */
	private float cardAmount;
	
	public String getEmpCode() {
		return empCode;
	}
	
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public String getSignedTime() {
		return signedTime;
	}
	
	public void setSignedTime(String signedTime) {
		this.signedTime = signedTime;
	}
	
	public String getPictureData() {
		return pictureData;
	}
	
	public void setPictureData(String pictureData) {
		this.pictureData = pictureData;
	}
	
	public String getSignedState() {
		return signedState;
	}
	
	public void setSignedState(String signedState) {
		this.signedState = signedState;
	}
	
	public String getSatisfaction() {
		return satisfaction;
	}
	
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	
	public String getExpSignedDescription() {
		return expSignedDescription;
	}
	
	public void setExpSignedDescription(String expSignedDescription) {
		this.expSignedDescription= expSignedDescription;
	}
	
	public float getCashAmount() {
		return cashAmount;
	}
	
	public void setCashAmount(float cashAmount) {
		this.cashAmount = cashAmount;
	}
	
	public float getCardAmount() {
		return cardAmount;
	}
	
	public void setCardAmount(float cardAmount) {
		this.cardAmount = cardAmount;
	}

}
