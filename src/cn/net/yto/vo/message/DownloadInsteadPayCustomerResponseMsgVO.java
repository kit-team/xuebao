package cn.net.yto.vo.message;

import java.util.List;

import cn.net.yto.vo.InsteadPayCustomerVO;

/**
 * 城市区号表下载VO 收派员在揽件时根据客户需要寄送的地址，输入目标城市的区号，可以下拉出对应的一级城市和二级城市，以便选择
 * 
 * @author HurryJiang
 * 
 */
public class DownloadInsteadPayCustomerResponseMsgVO extends BaseResponseMsgVO {
	private List<InsteadPayCustomerVO> syncRecords;
	private int havaNextPage;
	private String tableName;

	public List<InsteadPayCustomerVO> getSyncRecords() {
		return syncRecords;
	}

	public void setSyncRecords(List<InsteadPayCustomerVO> syncRecords) {
		this.syncRecords = syncRecords;
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
