package cn.net.yto.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class OrderVO implements Parcelable{
	// 收派员编号
	@DatabaseField
	private String empCode;
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
	// 时效要求
	@DatabaseField
	private String orderTypeCode;
	// 收件地址
	@DatabaseField
	private String recipientAddress;
	// 收件人
	@DatabaseField
	private String recipientName;
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
	//修改成附加状态
	@DatabaseField
	private String additionState;
	@DatabaseField
	private String cancelRemark;
	
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
		            return o;   
		        }
		        
		        @Override  
		        public OrderVO[] newArray(int size) {   
		            return new OrderVO[size];   
		        }   
		    };

}
