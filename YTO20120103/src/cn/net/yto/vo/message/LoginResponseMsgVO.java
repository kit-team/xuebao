package cn.net.yto.vo.message;

import java.util.ArrayList;

public class LoginResponseMsgVO extends BaseResponseMsgVO {
	private String empCode;
	private String failMessage;
	private String getOrderPeriod;
	private String isSMSMode;
	private String isSimpleReceive;
	private String isSimpleReceives;
	private String isSimpleSign;
	private String isSimpleSigns;
	private String lastPDANo;
	private String needScanBeforeSignedLog;
	private String nowDate;
	private String orgCode;
	private String orgName;
	private int pastDueDay;
	private String realName;
	private int retVal;
	private ArrayList<String> rights;
	private String rsParaters;
	private int submitBizPeriod;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public String getGetOrderPeriod() {
		return getOrderPeriod;
	}

	public void setGetOrderPeriod(String getOrderPeriod) {
		this.getOrderPeriod = getOrderPeriod;
	}

	public String getIsSMSMode() {
		return isSMSMode;
	}

	public void setIsSMSMode(String isSMSMode) {
		this.isSMSMode = isSMSMode;
	}

	public String getIsSimpleReceive() {
		return isSimpleReceive;
	}

	public void setIsSimpleReceive(String isSimpleReceive) {
		this.isSimpleReceive = isSimpleReceive;
	}

	public String getIsSimpleReceives() {
		return isSimpleReceives;
	}

	public void setIsSimpleReceives(String isSimpleReceives) {
		this.isSimpleReceives = isSimpleReceives;
	}

	public String getIsSimpleSign() {
		return isSimpleSign;
	}

	public void setIsSimpleSign(String isSimpleSign) {
		this.isSimpleSign = isSimpleSign;
	}

	public String getIsSimpleSigns() {
		return isSimpleSigns;
	}

	public void setIsSimpleSigns(String isSimpleSigns) {
		this.isSimpleSigns = isSimpleSigns;
	}

	public String getLastPDANo() {
		return lastPDANo;
	}

	public void setLastPDANo(String lastPDANo) {
		this.lastPDANo = lastPDANo;
	}

	public String getNeedScanBeforeSignedLog() {
		return needScanBeforeSignedLog;
	}

	public void setNeedScanBeforeSignedLog(String needScanBeforeSignedLog) {
		this.needScanBeforeSignedLog = needScanBeforeSignedLog;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getPastDueDay() {
		return pastDueDay;
	}

	public void setPastDueDay(int pastDueDay) {
		this.pastDueDay = pastDueDay;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getRetVal() {
		return retVal;
	}

	public void setRetVal(int retVal) {
		this.retVal = retVal;
	}

	public ArrayList<String> getRights() {
		return rights;
	}

	public void setRights(ArrayList<String> rights) {
		this.rights = rights;
	}

	public String getRsParaters() {
		return rsParaters;
	}

	public void setRsParaters(String rsParaters) {
		this.rsParaters = rsParaters;
	}

	public int getSubmitBizPeriod() {
		return submitBizPeriod;
	}

	public void setSubmitBizPeriod(int submitBizPeriod) {
		this.submitBizPeriod = submitBizPeriod;
	}

}
