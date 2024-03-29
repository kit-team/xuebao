package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ScopeVO {
	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private String cityCode;
	@DatabaseField
	private String cityName;
	@DatabaseField
	private String provenceCode;
	@DatabaseField
	private String provenceName;
	@DatabaseField
	private String sendArea;
	@DatabaseField
	private String noSendArea;
	@DatabaseField
	private String productType;
	@DatabaseField
	private String other;
	@DatabaseField
	private long versionNo;
	@DatabaseField
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvenceCode() {
		return provenceCode;
	}

	public void setProvenceCode(String provenceCode) {
		this.provenceCode = provenceCode;
	}

	public String getProvenceName() {
		return provenceName;
	}

	public void setProvenceName(String provenceName) {
		this.provenceName = provenceName;
	}

	public String getSendArea() {
		return sendArea;
	}

	public void setSendArea(String sendArea) {
		this.sendArea = sendArea;
	}

	public String getNoSendArea() {
		return noSendArea;
	}

	public void setNoSendArea(String noSendArea) {
		this.noSendArea = noSendArea;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ScopeVO() {

	}

	public ScopeVO(String id, String cityCode, String cityName,
			String provenceCode, String provenceName, String sendArea,
			String noSendArea, String productType, String other,
			long versionNo, String status) {
		super();
		this.id = id;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.provenceCode = provenceCode;
		this.provenceName = provenceName;
		this.sendArea = sendArea;
		this.noSendArea = noSendArea;
		this.productType = productType;
		this.other = other;
		this.versionNo = versionNo;
		this.status = status;
	}
}
