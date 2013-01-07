package cn.net.yto.vo.message;

public class DownloadBasicDataByVersionRequestMsgVO extends BaseRequestMsgVO {
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * PDA 端记录中最大的version No值 
	 */
	private long version;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
}
