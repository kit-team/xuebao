package cn.net.yto.vo.message;

public class QueryFeeResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 返回值
	 */
	private int retVal;
	/**
	 * 成功或失败的原因
	 */
	private String failMessage;
	/**
	 * 超重重量
	 */
	private float overWeightPrice;
	/**
	 * 标准运价
	 */
	private float standPrice;
	/**
	 * 标准重量
	 */
	private float standWeight;
	
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
	
	public float getOverWeightPrice() {
		return overWeightPrice;
	}
	
	public void setOverWeightPrice(float overWeightPrice) {
		this.overWeightPrice = overWeightPrice;
	}
	
	public float getStandPrice() {
		return standPrice;
	}
	
	public void setStandPrice(float standPrice) {
		this.standPrice = standPrice;
	}
	
	public float getStandWeight() {
		return standWeight;
	}
	
	public void setStandWeight(float standWeight) {
		this.standWeight = standWeight;
	}

}
