package cn.net.yto.vo.message;

public class ReplaceReceiveRequestMsgVO extends BaseRequestMsgVO {
	//旧单号
	private String oldBillNo;
	//新单号
	private String newBillNo;

	public String getOldBillNo() {
		return oldBillNo;
	}

	public void setOldBillNo(String oldBillNo) {
		this.oldBillNo = oldBillNo;
	}

	public String getNewBillNo() {
		return newBillNo;
	}

	public void setNewBillNo(String newBillNo) {
		this.newBillNo = newBillNo;
	}
}
