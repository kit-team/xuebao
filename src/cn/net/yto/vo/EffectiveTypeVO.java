package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class EffectiveTypeVO {
	@DatabaseField(id = true)
	private String code;
	@DatabaseField
	private String id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String parentEffectiveType;
	/**
	 * 是否有效状态	VALID:有效；INVALID:无效
	 */
	@DatabaseField
	private String status;
	@DatabaseField
	private long versionNo;

	public EffectiveTypeVO() {
		super();
	}

	public EffectiveTypeVO(String code, String id, String name,
			String parentEffectiveType, String status, int versionNo) {
		super();
		this.code = code;
		this.id = id;
		this.name = name;
		this.parentEffectiveType = parentEffectiveType;
		this.status = status;
		this.versionNo = versionNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getParentEffectiveType() {
		return parentEffectiveType;
	}

	public void setParentEffectiveType(String parentEffectiveType) {
		this.parentEffectiveType = parentEffectiveType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	@Override
	public String toString() {
		return "" + name;
	}
}
