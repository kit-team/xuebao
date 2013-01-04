package cn.net.yto.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 异常揽件
 * 
 * @author HurryJiang
 * 
 */
@DatabaseTable
public class RecvexpVO {
	@DatabaseField
	private String failureCode;// R09,
	@DatabaseField
	private String failureReason;// 上门后用户不接受价格,
	@DatabaseField
	private String failureType;// 2,
	@DatabaseField(id = true)
	private String id;// 132,
	@DatabaseField
	private String status;// VALID,
	@DatabaseField
	private Long versionNo;// 1

	public RecvexpVO(){
		
	}
	
	public RecvexpVO(String failureCode, String failureReason,
			String failureType, String id, String status, Long versionNo) {
		super();
		this.failureCode = failureCode;
		this.failureReason = failureReason;
		this.failureType = failureType;
		this.id = id;
		this.status = status;
		this.versionNo = versionNo;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

}
