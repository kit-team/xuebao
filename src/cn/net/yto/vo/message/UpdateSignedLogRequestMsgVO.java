package cn.net.yto.vo.message;
/**
 * m_delivery!updateSignedLog=》
{"signOffTypeCode":"",
"pictureData":"U3lzdGVtLkJ5dGVbXQ==",
"waybillNo":"2473718091",
"satisfaction":"",
"signedTime":"2012-12-31  17:34:39",
"recieverSignOff":"kkk",
"isReceiverSignOff":"1",
"isPicture":"0"}

 result {"failMessage":"补录签收人成功!","id":"402884923bee8ee5013bf0502189003f","retVal":1}

 * @author HurryJiang
 *
 */
public class UpdateSignedLogRequestMsgVO extends BaseRequestMsgVO {
	private String signOffTypeCode;
	private String pictureData;
	private String waybillNo;
	private String satisfaction;
	private String signedTime;
	private String recieverSignOff;
	private String isReceiverSignOff;
	private String isPicture;
	public String getSignOffTypeCode() {
		return signOffTypeCode;
	}
	public void setSignOffTypeCode(String signOffTypeCode) {
		this.signOffTypeCode = signOffTypeCode;
	}
	public String getPictureData() {
		return pictureData;
	}
	public void setPictureData(String pictureData) {
		this.pictureData = pictureData;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getSignedTime() {
		return signedTime;
	}
	public void setSignedTime(String signedTime) {
		this.signedTime = signedTime;
	}
	public String getRecieverSignOff() {
		return recieverSignOff;
	}
	public void setRecieverSignOff(String recieverSignOff) {
		this.recieverSignOff = recieverSignOff;
	}
	public String getIsReceiverSignOff() {
		return isReceiverSignOff;
	}
	public void setIsReceiverSignOff(String isReceiverSignOff) {
		this.isReceiverSignOff = isReceiverSignOff;
	}
	public String getIsPicture() {
		return isPicture;
	}
	public void setIsPicture(String isPicture) {
		this.isPicture = isPicture;
	}
	
}