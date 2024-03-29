package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 黑名单
 * 
 * @author HurryJiang
 * 
 */
@DatabaseTable
public class BlackListVO {
	@DatabaseField
	private String address;
	@DatabaseField
	private String cause;
	@DatabaseField
	private String customerType;
	@DatabaseField
	private String customerUnit;
	@DatabaseField
	private String status;
	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String orgID;
	@DatabaseField
	private String phone;
	@DatabaseField
	private String remark;
	@DatabaseField
	private String serviceLevel;
	@DatabaseField
	private Long versionNo;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerUnit() {
		return customerUnit;
	}

	public void setCustomerUnit(String customerUnit) {
		this.customerUnit = customerUnit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public BlackListVO() {

	}

	public BlackListVO(String address, String cause, String customerType,
			String customerUnit, String status, String id, String name,
			String orgID, String phone, String remark, String serviceLevel,
			long versionNo) {
		super();
		this.address = address;
		this.cause = cause;
		this.customerType = customerType;
		this.customerUnit = customerUnit;
		this.status = status;
		this.id = id;
		this.name = name;
		this.orgID = orgID;
		this.phone = phone;
		this.remark = remark;
		this.serviceLevel = serviceLevel;
		this.versionNo = versionNo;
	}

}
