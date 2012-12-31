package cn.net.yto.vo.message;

public class DeleteSignedLogRequestMsgVO extends BaseRequestMsgVO {
	private String waybillNo;
	private String Id;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}

}
