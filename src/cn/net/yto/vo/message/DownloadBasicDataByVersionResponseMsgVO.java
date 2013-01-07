package cn.net.yto.vo.message;

public class DownloadBasicDataByVersionResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 返回值
	 */
	private int retVal;
	/**
	 * 成功或失败的原因
	 */
	private String failMessage;
	/**
	 * 是否存在下一页
	 */
	private int havaNextPage;
	/**
	 * 同步的模块名
	 */
	private String tableName;
	
	public int getRetVal() {
		return retVal;
	}
	
	public void setRetVal(int retVal) {
		this.retVal = retVal;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
	public int getHavaNextPage() {
		return havaNextPage;
	}
	
	public void setHavaNextPage(int havaNextPage) {
		this.havaNextPage = havaNextPage;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
