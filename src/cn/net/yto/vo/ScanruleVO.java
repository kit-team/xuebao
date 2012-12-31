package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 扫描规则
 * 
 * @author HurryJiang
 * 
 */
@DatabaseTable
public class ScanruleVO {
	@DatabaseField
	private String code;
	@DatabaseField
	private String englishName;
	@DatabaseField(id=true)
	private String id;
	@DatabaseField
	private String matCode;
	@DatabaseField
	private String name;
	@DatabaseField
	private String postfix;
	@DatabaseField
	private String prefix;
	@DatabaseField
	private Integer scanLength;
	@DatabaseField
	private String status;
	@DatabaseField
	private long versionNo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getScanLength() {
		return scanLength;
	}

	public void setScanLength(Integer scanLength) {
		this.scanLength = scanLength;
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
	
	public ScanruleVO(){
		
	}

	public ScanruleVO(String code, String englishName, String id,
			String matCode, String name, String postfix, String prefix,
			Integer scanLength, String status, long versionNo) {
		super();
		this.code = code;
		this.englishName = englishName;
		this.id = id;
		this.matCode = matCode;
		this.name = name;
		this.postfix = postfix;
		this.prefix = prefix;
		this.scanLength = scanLength;
		this.status = status;
		this.versionNo = versionNo;
	}
}
