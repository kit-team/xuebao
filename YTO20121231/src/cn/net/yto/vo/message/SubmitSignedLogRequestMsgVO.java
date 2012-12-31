package cn.net.yto.vo.message;

public class SubmitSignedLogRequestMsgVO extends BaseRequestMsgVO {
	//��Ҫ���ϵ��˵���
	private String ID;
	private String empCode;
	private String waybillNo;
	private String signedTime="2011-03-26 06:06:06";
	private String pictureData;
	private String signedState;
	private String satisfaction;
	private String expSignedDescription;
	private Integer cashAmount;
	private Integer cardAmount;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
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
		this.expSignedDescription = expSignedDescription;
	}
	public Integer getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(Integer cashAmount) {
		this.cashAmount = cashAmount;
	}
	public Integer getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}

}
