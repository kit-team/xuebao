package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 数据字典
 * 
 * @author HurryJiang
 * 
 */
@DatabaseTable
public class DictionaryVO {
	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private String type;
	@DatabaseField
	private String code;
	@DatabaseField
	private String value;
	@DatabaseField
	private Integer displayOrder;
	@DatabaseField
	private long versionNo;
	@DatabaseField
	private String status;
	@DatabaseField
	private String name;

	public DictionaryVO(){
		
	}
	
	public DictionaryVO(String id, String type, String code, String value,
			Integer displayOrder, long versionNo, String status, String name) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.value = value;
		this.displayOrder = displayOrder;
		this.versionNo = versionNo;
		this.status = status;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
