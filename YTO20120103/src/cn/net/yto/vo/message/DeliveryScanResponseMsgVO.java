package cn.net.yto.vo.message;


public class DeliveryScanResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回值； 1  成功；-1 失败；
	 */
	private int retVal;
	
	/**
	 * 成功或失败的原因；
	 */
	private String failMessage;
	
	/**
	 * 派件扫描 ID
	 */
	private String scanId;
	
	/**
	 * 到付标志  0：不是到付；1：到付类型 
	 */
	private boolean invertedPay;
	
	/**
	 * 代收标志  0：不是代收；1：代收类型 
	 */
	private boolean insteadPay;

	/**
	 * 运费
	 */
	private float feeAmt; 
	
	/**
	 * 货物金额
	 */
	private float goodsAmount;
	
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
	
	public String getScanId() {
		return scanId;
	}
	
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	
	public boolean isInvertedPay() {
		return invertedPay;
	}
	
	public void setInvertedPay(boolean invertedPay) {
		this.insteadPay = invertedPay;
	}
	
	public boolean isInsteadPay() {
		return insteadPay;
	}
	
	public void setInsteadPay(boolean insteadPay) {
		this.insteadPay = insteadPay;
	}
	
	public float getFeeAmt() {
		return feeAmt;
	}
	
	public void setFeeAmt(float feeAmt) {
		this.feeAmt = feeAmt;
	}
	
	public float getGoodsAmount() {
		return goodsAmount;
	}
	
	public void setGoodsAmount(float goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
}
