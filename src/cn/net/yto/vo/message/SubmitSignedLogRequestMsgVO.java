package cn.net.yto.vo.message;

public class SubmitSignedLogRequestMsgVO extends BaseRequestMsgVO {
      /**
       * 32位随机数，唯一的ID，mac 地址
       */
	  private String id;
	  /**
	   * 无意义，填0
	   */
	  private String isScan;
	  /**
	   * 签收类型
	   */
	  private String signOffTypeCode;
	  /**
	   * 
	   */
	  private String recieverSignOff;
	  /**
	   * 到付金额
	   */
	  private String amountCollected;
	  /**
	   * 代收金额
	   */
	  private String amountAgency;
	  /**
	   * 
	   */
	  private String uploadStatus;
	  /**
	   * 
	   */
	  private String pdaNumber;
	  /**
	   * 
	   */
	  private String getStatus;
	  /**
	   * 用户签名图片
	   */
	  private String pictureData;
	  /**
	   * 异常签收原因
	   */
	  private String expSignedDescription;
	  /**
	   * 运单号
	   */
	  private String waybillNo;
	  /**
	   * 正常签收，填1
	   */
	  private String signedState;
	  /**
	   * signedState = 1， 正常签收； 非1， 异常签收
	   */
	  private String signedStateInfo;
	  /**
	   * 
	   */
	  private String empCode;
	  /**
	   * 
	   */
	  private String empName;
	  /**
	   * 签收时间
	   */
	  private String signedTime;
	  /**
	   * 满意度
	   */
	  private String satisfaction;
	  /**
	   * “签收人必填”如果打勾，1，否则是0. 对于0的可以补录签收人。
	   */
	  private String isReceiverSignOff;
	  /**
	   * 是否有pictureData
	   */
	  private String isPicture;
	  
	  public String getId() {
		  return id;
	  }
	  public void setId(String id) {
		  this.id = id;
	  }
	  
	  public String getScan() {
		  return isScan;
	  }
	  
	  public void setScan(String isScan) {
		  this.isScan = isScan;
	  }
	  
	  public String getSignOffTypeCode() {
		  return signOffTypeCode;
	  }
	  
	  public void setSignOffTypeCode(String signOffTypeCode) {
		  this.signOffTypeCode = signOffTypeCode;
	  }
	  
	  public String getRecieverSignOff() {
		  return recieverSignOff;
	  }
	  
	  public void setRecieverSignOff(String recieverSignOff) {
		  this.recieverSignOff = recieverSignOff;
	  }
	  
	  public String getAmountCollected() {
		  return amountCollected;
	  }
	  
	  public void setAmountCollected(String amountCollected) {
		  this.amountCollected = amountCollected;
	  }
	  
	  public String getAmountAgency() {
		  return amountAgency;
	  }
	  
	  public void setAmountAgency(String amountAgency) {
		  this.amountAgency = amountAgency;
	  }
	  
	  public String getUploadStatu() {
		  return uploadStatus;
	  }
	  
	  public void setUploadStatu(String uploadStatus) {
		  this.uploadStatus = uploadStatus;
	  }
	  
	  public String getPdaNumber() {
		  return pdaNumber;
	  }
	  
	  public void setPdaNumber(String pdaNumber) {
		  this.pdaNumber = pdaNumber;
	  }
	  
	  
	  
	  public String getStatus() {
		  return getStatus;
	  }
	  
	  public void setStatus(String status) {
		  this.getStatus = status;
	  }
	  
	  public String getPictureData() {
		  return pictureData;
	  }
	  
	  public void setPictureData(String pictureData) {
		  this.pictureData = pictureData;
	  }
	  
	  public String getExpSignedDescription() {
		  return expSignedDescription;
	  }
	  
	  public void setExpSignedDescription(String expSignedDescription) {
		  this.expSignedDescription= expSignedDescription;
	  }
	  
	  public String getWaybillNo() {
		  return waybillNo;
	  }
	  
	  public void setWaybillNo(String waybillNo) {
		  this.waybillNo = waybillNo;
	  }
	  
	  public String getSignedState() {
		  return signedState;
	  }
	  
	  public void setSignedState(String signedState) {
		  this.signedState = signedState;
	  }
	  
	  public String getSignedStateInfo() {
		  return signedStateInfo;
	  }
	  
	  public void setSignedStateInfo(String signedStateInfo) {
		  this.signedStateInfo = signedStateInfo;
	  }
	  
	  public String getEmpCode() {
		  return empCode;
	  }
	  
	  public void setEmpCode(String empCode) {
		  this.empCode = empCode;
	  }
	  
	  public String getEmpName() {
		  return empName;
	  }
	  
	  public void setEmpName(String empName) {
		  this.empName = empName;
	  }
	  
	  public String getSignedTime() {
		  return signedTime;
	  }
	  
	  public void setSignedTime(String signedTime) {
		  this.signedTime = signedTime;
	  }
	  
	  public String getSatisfaction() {
		  return satisfaction;
	  }
	  
	  public void setSatisfaction(String satisfaction) {
		  this.satisfaction = satisfaction;
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
