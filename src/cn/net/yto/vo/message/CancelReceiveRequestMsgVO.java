package cn.net.yto.vo.message;

public class CancelReceiveRequestMsgVO extends BaseRequestMsgVO {
	//需要作废的运单号
	private String wayBillNo;

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
}
