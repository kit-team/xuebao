﻿package cn.net.yto.vo;

import cn.net.yto.dao.DbConst.UserTable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = UserTable.TABLE_NAME)
public class UserVO {
	/**
	 * 工号
	 */
	@DatabaseField(canBeNull = false, id = true)
	private String username;
	/**
	 * 密码
	 */
	@DatabaseField(canBeNull = false)
	private String password;
	/**
	 * 员工号
	 */
	@DatabaseField
	private String empCode;
	/**
	 * 员工真实名
	 */
	@DatabaseField
	private String realName;
	/**
	 * 员工所在机构编码
	 */
	@DatabaseField
	private String orgId;
	/**
	 * 员工所在机构名称
	 */
	@DatabaseField
	private String orgName;
	/**
	 * 当前时间
	 */
	@DatabaseField
	private String nowDate;
	/**
	 * 帐号过期前还剩天数
	 */
	@DatabaseField
	private int pastDueDay;
	/**
	 * 当前用户PDA权限列表
	 */
	@DatabaseField
	private long right;
	/**
	 * 1：当前是短信模式； 0：当前是流模式
	 */
	@DatabaseField
	private boolean isSMSMode;
	/**
	 * 此用户上次登录PDA设备号
	 */
	@DatabaseField
	private String lastPDANo;
	/**
	 * PDA订单下载周期
	 */
	@DatabaseField
	private long getOrderPeriod;
	/**
	 * PDA上传取件、派件、签收等业务数据周期
	 */
	@DatabaseField
	private long submitBizPeriod;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public int getPastDueDay() {
		return pastDueDay;
	}

	public void setPastDueDay(int pastDueDay) {
		this.pastDueDay = pastDueDay;
	}

	public long getRight() {
		return right;
	}

	public void setRight(long right) {
		this.right = right;
	}

	public boolean getIsSMSMode() {
		return isSMSMode;
	}

	public void setIsSMSMode(boolean isSMSMode) {
		this.isSMSMode = isSMSMode;
	}

	public String getLastPDANo() {
		return lastPDANo;
	}

	public void setLastPDANo(String lastPDANo) {
		this.lastPDANo = lastPDANo;
	}

	public long getGetOrderPeriod() {
		return getOrderPeriod;
	}

	public void setGetOrderPeriod(long getOrderPeriod) {
		this.getOrderPeriod = getOrderPeriod;
	}

	public long getSubmitBizPeriod() {
		return submitBizPeriod;
	}

	public void setSubmitBizPeriod(long submitBizPeriod) {
		this.submitBizPeriod = submitBizPeriod;
	}
}
