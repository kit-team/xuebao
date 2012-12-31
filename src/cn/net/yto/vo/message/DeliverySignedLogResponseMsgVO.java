package cn.net.yto.vo.message;


public class DeliverySignedLogResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 返回值；  1  成功；-1 失败；
	 */
	private int retVal;
	
	/**
	 * 成功或失败的原因；
	 */
	private String failMessage;
	
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
}
