package cn.net.yto.vo.message;


/**
 * 城市区号表下载VO
 * 收派员在揽件时根据客户需要寄送的地址，输入目标城市的区号，可以下拉出对应的一级城市和二级城市，以便选择
 * @author HurryJiang
 *
 */
public class DownloadBasicDataByVersionRequestMsgVO extends BaseRequestMsgVO {
	private String moduleName;
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
