package cn.net.yto.vo;

public class CancelMessageVO {
	/**
	 * 消息ID
	 */
	private String msgId;

	/**
	 * 消息类型
	 */
	private int msgType;

	/**
	 * 订单渠道编码
	 */
	private String orderChannelCode;

	/**
	 * 订单号（或 派件的运单号）
	 */
	private String orderId;
	
	public enum MessageType {
		 
		ORDER_PUSH(1), ORDER_CANCEL(2), DELIVERY_PUSH(3);
		
		private int code;

		MessageType(int code) {
			this.code = code;
		}
		
		public static MessageType valueOf(int code) {
			MessageType result = null;
			for(MessageType type : MessageType.values()) {
				if (type.code == code) {
					result = type;
					break;
				}
			}
			return result;
		}
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getOrderChannelCode() {
		return orderChannelCode;
	}

	public void setOrderChannelCode(String orderChannelCode) {
		this.orderChannelCode = orderChannelCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}