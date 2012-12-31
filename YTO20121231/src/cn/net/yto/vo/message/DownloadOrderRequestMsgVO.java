package cn.net.yto.vo.message;

public class DownloadOrderRequestMsgVO extends BaseRequestMsgVO {
	private String deliveryEmpCode;
	/**
	 * 类型 = 1：取此员工未取订单列表；
	 * 类型 = 2：取此员工未取和已取未收件的订单列表
	 */
	private int fetchType;

	public String getDeliveryEmpCode() {
		return deliveryEmpCode;
	}

	public void setDeliveryEmpCode(String deliveryEmpCode) {
		this.deliveryEmpCode = deliveryEmpCode;
	}

	public int getFetchType() {
		return fetchType;
	}

	public void setFetchType(int fetchType) {
		this.fetchType = fetchType;
	}
}
