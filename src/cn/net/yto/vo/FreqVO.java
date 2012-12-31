package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 频次信息
 * @author HurryJiang
 *
 */
@DatabaseTable
public class FreqVO {
	@DatabaseField
	private String code;//FC21090821,
	@DatabaseField
	private String endTime;//20.28,
	@DatabaseField(id = true)
	private String id;//402882f92ce47abb012ce47e854c0001,
	@DatabaseField
	private String name;//派1,
	@DatabaseField
	private String orgCode;//999999,
	@DatabaseField
	private String orgId;//1001,
	@DatabaseField
	private String startTime;//15;//28,
	@DatabaseField
	private String status;//VALID,
	@DatabaseField
	private String type;//20,
	@DatabaseField
	private Integer value;//1234,
	@DatabaseField
	private Long versionNo;//1297413361858
	
	public FreqVO(){
	}
	
	public FreqVO(String code, String endTime, String id, String name,
			String orgCode, String orgId, String startTime, String status,
			String type, Integer value, Long versionNo) {
		super();
		this.code = code;
		this.endTime = endTime;
		this.id = id;
		this.name = name;
		this.orgCode = orgCode;
		this.orgId = orgId;
		this.startTime = startTime;
		this.status = status;
		this.type = type;
		this.value = value;
		this.versionNo = versionNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}


	

}
