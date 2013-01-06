package cn.net.yto.vo.message;


public class DownloadOrderCancelResponseMsgVO extends BaseResponseMsgVO {
	/**
	 * 消息列表
	 */
	private CancelMessage[] cancelMsg;
	
	/**
	 * 收派员工号
	 */
	private String deliveryEmpCode;
	
	/**
	 * 成功或失败的原因
	 */
	private String failMessage;
	
	/**
	 * 是否有下一页
	 */
	private int haveNextPage;
	
	/**
	 * 返回值；  1  成功；-1 失败；
	 */
	private int retVal;
	
	/**
	 * 固定值-- " T_COR_PUSH_CANCEL_MSG "
	 */
	private String tableName;
	
	public CancelMessage[] getCancelMsg() {
		return cancelMsg;
	}
	
	public void setCancelMsg(CancelMessage[] cancelMsg) {
		this.cancelMsg = cancelMsg;
	}
	
	public String getDeliveryEmpCode() {
		return deliveryEmpCode;
	}
	
	public void setDeliveryEmpCode(String deliveryEmpCode) {
		this.deliveryEmpCode = deliveryEmpCode;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
	public int getHaveNextPage() {
		return haveNextPage;
	}
	
	public void setHaveNextPage(int haveNextPage) {
		this.haveNextPage = haveNextPage;
	}
	
	public int getRetVal() {
		return retVal;
	}
	
	public void setRetVal(int retVal) {
		this.retVal = retVal;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
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
	
	class CancelMessage {
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
		 * 订单号（或  派件的运单号）
		 */
		private String orderId;
		
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

}
