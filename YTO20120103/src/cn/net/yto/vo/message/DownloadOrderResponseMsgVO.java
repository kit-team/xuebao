﻿package cn.net.yto.vo.message;

import java.util.ArrayList;
import java.util.List;

import cn.net.yto.vo.OrderVO;

public class DownloadOrderResponseMsgVO extends BaseResponseMsgVO {
	// 收派员工号
	private String deliveryEmpCode;
	// 成功或失败的原因；
	private String failMessage;
	// 是否有下一页
	private int haveNextPage;
	// 返回值； 1 成功；-1失败；
	private int retVal;
	// 固定值--"T_COR_ORDER"
	private String tableName;
	/**
	 * 当前用户订单列表
	 */
	private ArrayList<OrderVO> corOrder;

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

	public List<OrderVO> getOrders() {
		return corOrder;
	}

	public void setOrders(ArrayList<OrderVO> orders) {
		this.corOrder = orders;
	}

}
