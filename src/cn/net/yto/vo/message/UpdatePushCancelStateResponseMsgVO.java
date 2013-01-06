package cn.net.yto.vo.message;

public class UpdatePushCancelStateResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 返回值
	 */
	private int retVal;

	/**
	 * 成功或失败的原因
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
