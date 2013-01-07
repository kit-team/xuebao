package cn.net.yto.vo.message;

public class QueryExpressTraceRequestMsgVO extends BaseRequestMsgVO {
	/**
	 * 需要查询运单的运单号
	 */
	private String wayBillNo;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	public void setwayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

}
