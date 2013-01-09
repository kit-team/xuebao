﻿package cn.net.yto.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class OrderVO implements Parcelable{
	//修改成附加状态
	@DatabaseField
	private String additionState;
	@DatabaseField
	private String cancelRemark;
	// 收派员编号
	@DatabaseField
	private String empCode;
	@DatabaseField
	private String endCanvassTime;
	@DatabaseField
	private String endTime;
	// 货物内容
	@DatabaseField
	private String goodsContent;
	// 重量
	@DatabaseField
	private int goodsTotalWeight;
	@DatabaseField
	private String id;
	// 订单类型
	@DatabaseField
	private String orderChannelCode;
	// 下单时间
	@DatabaseField
	private String orderCreateTime;
	// 订单号
	@DatabaseField(canBeNull = false, id = true)
	private String orderNo;
	@DatabaseField
	private String orderStatus;
	// 时效要求
	@DatabaseField
	private String orderTypeCode;
	@DatabaseField
	private String receiverMobile;
	@DatabaseField
	private String receiverPhone;	
	// 收件地址
	@DatabaseField
	private String recipientAddress;
	@DatabaseField
	private String recipientCityId;
	@DatabaseField
	private String recipientCountyId;
	// 收件人
	@DatabaseField
	private String recipientName;
	@DatabaseField
	private String recipientProvId;
	// 备注（客户特殊要求说明）
	@DatabaseField
	private String remark;
	// 客户地址
	@DatabaseField
	private String senderAddress;
	// 客户编号
	@DatabaseField
	private String senderId;
	// 客户电话
	@DatabaseField
	private String senderMobile;
	// 客户姓名
	@DatabaseField
	private String senderName;
	// 客户电话
	@DatabaseField
	private String senderPhone;
	@DatabaseField
	private String startCanvassTime;
	@DatabaseField
	private String startTime;
	@DatabaseField
	private String time;
	@DatabaseField
	private String waybillNo;
	@DatabaseField
	private String downLoadTime;//本地添加的保存时间
	
	public String getDownLoadTime() {
		return downLoadTime;
	}

	public void setDownLoadTime(String downLoadTime) {
		this.downLoadTime = downLoadTime;
	}
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public int getGoodsTotalWeight() {
		return goodsTotalWeight;
	}

	public void setGoodsTotalWeight(int goodsTotalWeight) {
		this.goodsTotalWeight = goodsTotalWeight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderChannelCode() {
		return orderChannelCode;
	}

	public void setOrderChannelCode(String orderChannelCode) {
		this.orderChannelCode = orderChannelCode;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderMobile() {
		return senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getAdditionState() {
		return additionState;
	}

	public void setAdditionState(String additionState) {
		this.additionState = additionState;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getEndCanvassTime() {
		return endCanvassTime;
	}

	public void setEndCanvassTime(String endCanvassTime) {
		this.endCanvassTime = endCanvassTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getRecipientCityId() {
		return recipientCityId;
	}

	public void setRecipientCityId(String recipientCityId) {
		this.recipientCityId = recipientCityId;
	}

	public String getRecipientCountyId() {
		return recipientCountyId;
	}

	public void setRecipientCountyId(String recipientCountyId) {
		this.recipientCountyId = recipientCountyId;
	}

	public String getRecipientProvId() {
		return recipientProvId;
	}

	public void setRecipientProvId(String recipientProvId) {
		this.recipientProvId = recipientProvId;
	}

	public String getStartCanvassTime() {
		return startCanvassTime;
	}

	public void setStartCanvassTime(String startCanvassTime) {
		this.startCanvassTime = startCanvassTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(empCode);
		dest.writeString(goodsContent);
		dest.writeInt(goodsTotalWeight);
		dest.writeString(id);
		dest.writeString(orderChannelCode);
		dest.writeString(orderCreateTime);
		dest.writeString(orderNo);
		dest.writeString(orderTypeCode);
		dest.writeString(recipientAddress);
		dest.writeString(recipientName);
		dest.writeString(remark);
		dest.writeString(senderAddress);
		dest.writeString(senderId);
		dest.writeString(senderMobile);
		dest.writeString(senderName);
		dest.writeString(senderPhone);
		dest.writeString(additionState);
		dest.writeString(cancelRemark);
		dest.writeString(endCanvassTime);
		dest.writeString(endTime);
		dest.writeString(orderStatus);
		dest.writeString(receiverMobile);
		dest.writeString(receiverPhone);
		dest.writeString(recipientCityId);
		dest.writeString(recipientCountyId);
		dest.writeString(recipientProvId);
		dest.writeString(startCanvassTime);
		dest.writeString(startTime);
		dest.writeString(time);
		dest.writeString(waybillNo);
		dest.writeString(downLoadTime);
	}
	
	public static final Parcelable.Creator<OrderVO> CREATOR = new Parcelable.Creator<OrderVO>() {   
		        @Override  
		        public OrderVO createFromParcel(Parcel source) {
		        	OrderVO o = new OrderVO();
		        	o.setEmpCode(source.readString());
		        	o.setGoodsContent(source.readString());
		        	o.setGoodsTotalWeight(source.readInt());
		        	o.setId(source.readString());
		        	o.setOrderChannelCode(source.readString());
		        	o.setOrderCreateTime(source.readString());
		        	o.setOrderNo(source.readString());
		        	o.setOrderTypeCode(source.readString());
		        	o.setRecipientAddress(source.readString());
		        	o.setRecipientName(source.readString());
		        	o.setRemark(source.readString());
		        	o.setSenderAddress(source.readString());
		        	o.setSenderId(source.readString());
		        	o.setSenderMobile(source.readString());
		        	o.setSenderName(source.readString());
		        	o.setSenderPhone(source.readString());
		        	o.setAdditionState(source.readString());
		        	o.setCancelRemark(source.readString());
		        	o.setEndCanvassTime(source.readString());
		        	o.setEndTime(source.readString());
		        	o.setOrderStatus(source.readString());
		        	o.setReceiverMobile(source.readString());
		        	o.setReceiverPhone(source.readString());
		        	o.setRecipientCityId(source.readString());
		        	o.setRecipientCountyId(source.readString());
		        	o.setRecipientProvId(source.readString());
		        	o.setStartCanvassTime(source.readString());
		        	o.setStartTime(source.readString());
		        	o.setTime(source.readString());
		        	o.setWaybillNo(source.readString());
		        	o.setDownLoadTime(source.readString());
		            return o;
		        }
		        
		        @Override  
		        public OrderVO[] newArray(int size) {
		            return new OrderVO[size];   
		        }   
		    };

}
