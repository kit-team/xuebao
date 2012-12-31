package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OrderChannelVO {
	@DatabaseField
	private String informType;
	@DatabaseField(id = true)
	private String orderChannelCode;
	@DatabaseField
	private String orderChannelName;
	@DatabaseField
	private boolean status;
	@DatabaseField
	private long versionNo;

	public OrderChannelVO() {
		super();
	}

	public OrderChannelVO(String informType, String orderChannelCode,
			boolean status, long versionNo) {
		super();
		this.informType = informType;
		this.orderChannelCode = orderChannelCode;
		this.status = status;
		this.versionNo = versionNo;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public String getOrderChannelCode() {
		return orderChannelCode;
	}

	public void setOrderChannelCode(String orderChannelCode) {
		this.orderChannelCode = orderChannelCode;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String getOrderChannelName() {
		return orderChannelName;
	}

	public void setOrderChannelName(String orderChannelName) {
		this.orderChannelName = orderChannelName;
	}

}
