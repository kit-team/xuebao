package cn.net.yto.vo.message;


public class UpdateReceiveRequestMsgVO extends BaseRequestMsgVO {
	
	/**
	 * id
	 */
	private String id;
	/**
	 * orderNo
	 */
	private String orderNo;
	/**
	 * sourceOrgCode
	 */
	private String sourceOrgCode;
	/**
	 * waybillNo
	 */
	private String waybillNo;
	/**
	 * cityId
	 */
	private String cityId;
	/**
	 * orderNo
	 */
	private String destAddress;
	/**
	 * orderNo
	 */
	private String weighWeight;
	/**
	 * orderNo
	 */
	private String pkgQty;
	/**
	 * orderNo
	 */
	private String feeAmt;
	/**
	 * orderNo
	 */
	private String practicalType;
	/**
	 * orderNo
	 */
	private String returnWaybillNo;
	/**
	 * orderNo
	 */
	private String parentWaybillNo;
	/**
	 * orderNo
	 */
	private String currentState;
	
	/**
	 * orderNo
	 */
	private String empCode;
	/**
	 * orderNo
	 */
	private String goodsSize;
	/**
	 * orderNo
	 */
	private String causesException;
	/**
	 * orderNo
	 */
	private String orderChannelCode;
	/**
	 * orderNo
	 */
	private String invertedPay;
	/**
	 * orderNo
	 */
	private String insteadPay;
	/**
	 * orderNo
	 */
	private String goodsAmount;
	
	/**
	 * childWaybillNo
	 */
	private String[] childWaybillNo ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceOrgCode() {
		return sourceOrgCode;
	}

	public void setSourceOrgCode(String sourceOrgCode) {
		this.sourceOrgCode = sourceOrgCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public String getWeighWeight() {
		return weighWeight;
	}

	public void setWeighWeight(String weighWeight) {
		this.weighWeight = weighWeight;
	}

	public String getPkgQty() {
		return pkgQty;
	}

	public void setPkgQty(String pkgQty) {
		this.pkgQty = pkgQty;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getPracticalType() {
		return practicalType;
	}

	public void setPracticalType(String practicalType) {
		this.practicalType = practicalType;
	}

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}

	public String getParentWaybillNo() {
		return parentWaybillNo;
	}

	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getCausesException() {
		return causesException;
	}

	public void setCausesException(String causesException) {
		this.causesException = causesException;
	}

	public String getOrderChannelCode() {
		return orderChannelCode;
	}

	public void setOrderChannelCode(String orderChannelCode) {
		this.orderChannelCode = orderChannelCode;
	}

	public String getInvertedPay() {
		return invertedPay;
	}

	public void setInvertedPay(String invertedPay) {
		this.invertedPay = invertedPay;
	}

	public String getInsteadPay() {
		return insteadPay;
	}

	public void setInsteadPay(String insteadPay) {
		this.insteadPay = insteadPay;
	}

	public String getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String[] getChildWaybillNo() {
		return childWaybillNo;
	}

	public void setChildWaybillNo(String[] childWaybillNo) {
		this.childWaybillNo = childWaybillNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

	
}
