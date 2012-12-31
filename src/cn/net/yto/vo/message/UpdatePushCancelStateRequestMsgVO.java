package cn.net.yto.vo.message;


public class UpdatePushCancelStateRequestMsgVO extends BaseRequestMsgVO {
	/**
	 * 消息ID
	 */
	private String msgId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
