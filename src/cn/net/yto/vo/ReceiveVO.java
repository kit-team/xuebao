package cn.net.yto.vo;

import cn.net.yto.common.Constants;
import cn.net.yto.utils.CommonUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ReceiveVO {
	@DatabaseField
	private String id = "";
	/**
	 * 员工号
	 */
	@DatabaseField
	private String empName = "";
	/**
	 * 目的网点对应中心
	 */
	@DatabaseField
	private String desOrgCode = "";
	/**
	 * 收件状态描述 ，和currentState的相互对应关系,为failureReason
	 */
	@DatabaseField
	private String getCurrentState = "";
	/**
	 * 收件人电话
	 */
	@DatabaseField
	private String receiverPhone = "";
	/**
	 * 收件人姓名
	 */
	@DatabaseField
	private String receiverName = "";
	/**
	 * 代收货款    寄件人编号
	 */
	@DatabaseField
	private String contactCode = "";
	/**
	 * 代收货款    寄件人姓名
	 */
	@DatabaseField
	private String contactName = "";
	/**
	 * 返回错误信息
	 */
	@DatabaseField
	private String failMessage = "";
	/**
	 * 子母键标记
	 */
	@DatabaseField
	private String parentchildFlag = "0";
	/**
	 * "Success", "WaitForSend","Failed","ReSending","ReSendFailed"
	 */
	@DatabaseField
	private String uploadStatu = "WaitForSend";
	/**
	 * 设备号
	 */
	@DatabaseField
	private String pdaNumber = "";
	/**
	 * 发送状态,都是赋值为“N”，暂没有发现其它处理情况
	 */
	@DatabaseField
	private final String sendStatus = "N";
	/**
	 * 收件扫描时间
	 */
	@DatabaseField
	private String salesmanTime = CommonUtils.getFormatedDateTime(Constants.DATETIME_PATTERN);;
	/**
	 * 目的网点，暂时没用
	 */
	@DatabaseField
	private String salesmanStation = "";
	/**
	 * 备注
	 */
	@DatabaseField
	private String remarks = "";
	/**
	 * 是否作废 1：定单已作废，不能修改，0：不作废
	 */
	@DatabaseField
	private String isInvalid = "";
	/**
	 * 有订单时订单编号
	 */
	@DatabaseField
	private String orderId = "";
	@DatabaseField
	/**
	 * 正常，作废？？
	 */
	private String isShowInvalid = "";
	/**
	 * "成功","发送中","发送失败","重复发送中","重复发送失败"
	 */
	@DatabaseField
	private String getStatus = "";
	/**
	 * 订单号
	 */
	@DatabaseField
	private String orderNo = "";
	/**
	 * 收件组织机构编码
	 */
	@DatabaseField
	private String sourceOrgCode = "";
	/**
	 * 运单号
	 */
	@DatabaseField(id = true)
	private String waybillNo = "";
	/**
	 * 目的城市编码
	 */
	@DatabaseField
	private String cityId = "";
	/**
	 * 目的地址
	 */
	@DatabaseField
	private String destAddress = "";
	/**
	 * 重量
	 */
	@DatabaseField
	private String weighWeight = "";
	/**
	 * 数量
	 */
	@DatabaseField
	private String pkgQty = "1";
	/**
	 * 运费
	 */
	@DatabaseField
	private String feeAmt = "";
	/**
	 * 时效类型编码
	 */
	@DatabaseField
	private String practicalType = "";
	/**
	 * 签回单运单号
	 */
	@DatabaseField
	private String returnWaybillNo = "";
	/**
	 * 父单号
	 */
	@DatabaseField
	private String parentWaybillNo = "";
	/**
	 * 当前状态-1：正常收件；或者是 异常编码，为failCode
	 */
	@DatabaseField
	private String currentState = "-1";
	/**
	 * 收派员工号
	 */
	@DatabaseField
	private String empCode = "";
	/**
	 * 体积
	 */
	@DatabaseField
	private String goodsSize = "";
	/**
	 * 异常描述
	 */
	@DatabaseField
	private String causesException = "";
	/**
	 * 订单渠道编码
	 */
	@DatabaseField
	private String orderChannelCode = "";
	/**
	 * 是否到付
	 */
	@DatabaseField
	private String invertedPay = "0";// 0：不是到付；1：是到付
	/**
	 * 是否代收
	 */
	@DatabaseField
	private String insteadPay = "0";// 0：不是代收；1：是代收
	/**
	 * 货物金额
	 */
	@DatabaseField
	private String goodsAmount = "";
	/**
	 * 寄件人名称(客户名称)
	 */
	@DatabaseField
	private String customerName = "";
	/**
	 * 寄件地址
	 */
	@DatabaseField
	private String sendAddress = "";
	/**
	 * 寄件人电话
	 */
	@DatabaseField
	private String contactPhone = "";

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesOrgCode() {
		return desOrgCode;
	}

	public void setDesOrgCode(String desOrgCode) {
		this.desOrgCode = desOrgCode;
	}

	public String getGetCurrentState() {
		return getCurrentState;
	}

	public void setGetCurrentState(String getCurrentState) {
		this.getCurrentState = getCurrentState;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getContactCode() {
		return contactCode;
	}

	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public String getParentchildFlag() {
		return parentchildFlag;
	}

	public void setParentchildFlag(String parentchildFlag) {
		this.parentchildFlag = parentchildFlag;
	}

	public String getUploadStatu() {
		return uploadStatu;
	}

	public void setUploadStatu(String uploadStatu) {
		this.uploadStatu = uploadStatu;
	}

	public String getPdaNumber() {
		return pdaNumber;
	}

	public void setPdaNumber(String pdaNumber) {
		this.pdaNumber = pdaNumber;
	}

	public String getSendStatus() {
		return sendStatus;
	}

//	public void setSendStatus(String sendStatus) {
//		this.sendStatus = sendStatus;
//	}

	public String getSalesmanTime() {
		return salesmanTime;
	}

	public void setSalesmanTime(String salesmanTime) {
		this.salesmanTime = salesmanTime;
	}

	public String getSalesmanStation() {
		return salesmanStation;
	}

	public void setSalesmanStation(String salesmanStation) {
		this.salesmanStation = salesmanStation;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(String isInvalid) {
		this.isInvalid = isInvalid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIsShowInvalid() {
		return isShowInvalid;
	}

	public void setIsShowInvalid(String isShowInvalid) {
		this.isShowInvalid = isShowInvalid;
	}

	public String getGetStatus() {
		return getStatus;
	}

	public void setGetStatus(String getStatus) {
		this.getStatus = getStatus;
	}
	
	@Override
	public boolean equals(Object anObject) {
		if(anObject == null){
			return false;
		}
		
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof ReceiveVO) {
			ReceiveVO receive1 = (ReceiveVO) anObject;
			if(!CommonUtils.isEmpty(waybillNo) && waybillNo.equals(receive1.getWaybillNo())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		String s = "" + waybillNo;
		return s.hashCode();
	}
}