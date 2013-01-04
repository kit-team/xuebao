package cn.net.yto.vo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.net.yto.vo.message.SubmitSignedLogRequestMsgVO;
import cn.net.yto.vo.message.UpdateSignedLogRequestMsgVO;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * 签单
 * 
 * @author Administrator
 * 
 */
public class SignedLogVO {
    public static final String WAYBILLNO_FIELD_NAME = "waybillNo"; 
    public static final String UPLOADSTATUS_FIELD_NAME = "uploadStatus"; 
    public static final String SIGNED_TIME_FIELD_NAME = "signedTime";
    public static final String SIGNOFF_TYPE_SELF	  = "SELF";
    
    public static final String UPLOAD_STAUTS_SUCCESS  		= "Success";
    public static final String UPLOAD_STAUTS_WAITFORSEND  	= "WaitForSend";
    public static final String UPLOAD_STAUTS_FAILED  		= "Failed";
    public static final String UPLOAD_STAUTS_RESENDING  	= "ReSending";
    public static final String UPLOAD_STAUTS_RESENDFAILED  	= "ReSendFailed";
    
    // FIXME use the upload status file indicate the update status
    public static final String UPLOAD_STAUTS_NEED_UPDATE  	= "NeedUpdate";
    public static final String UPLOAD_STAUTS_UPDATE_FAILED  = "UpdateFailed";
    
    
    public static final Map<String, String> SIGNOFFMAP;
    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("门卫", "GATEKEEPER");
        map.put("邮件收发章", "POST");
        map.put("他人代收", "BYOTHER");
        map.put("本人签收", "SELF");
        map.put("已签收", "SELF");
        SIGNOFFMAP = Collections.unmodifiableMap(map);
    }
    
    public static final Map<String, String> UPLOADSTATUSMAP;
    static {
        Map<String, String> uploadMap = new HashMap<String, String>();
        uploadMap.put(UPLOAD_STAUTS_SUCCESS, "成功");
        uploadMap.put(UPLOAD_STAUTS_WAITFORSEND, "发送中");
        uploadMap.put(UPLOAD_STAUTS_FAILED, "发送失败");
        uploadMap.put(UPLOAD_STAUTS_RESENDING, "重复发送中");
        uploadMap.put(UPLOAD_STAUTS_RESENDFAILED, "重复发送失败");
        UPLOADSTATUSMAP = Collections.unmodifiableMap(uploadMap);
    }
    
    
    // 派件 id
	@DatabaseField
    private String signedLogId;
    // 是否查看
    @DatabaseField
    private long isScan = 0;

    // 签收类型
    @DatabaseField
    private String signOffTypeCode = "";

    // 是否签收
    @DatabaseField
    private String recieverSignOff = "";

    // 是否签收
    @DatabaseField
    private String pdaNumber = "";

    // 收派员工号
    @DatabaseField
    private String empCode = "";

    // 面单号
    @DatabaseField(columnName = WAYBILLNO_FIELD_NAME, id = true, unique = true)
    private String waybillNo = "";

    // 签收时间
    @DatabaseField(columnName = SIGNED_TIME_FIELD_NAME)
    private Date signedTime = new Date();

    // 签收数据
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] pictureData = new byte[]{};

    // 签收状态标记
    @DatabaseField
    private String signedState = "1";

    // 满意度
    @DatabaseField
    private Satisfaction satisfaction = Satisfaction.SATISFIED;

    // 异常签收描述
    @DatabaseField
    private String expSignedDescription = "";

    // 到付金额
    @DatabaseField
    private long amountCollected = 0;

    // 代收金额
    @DatabaseField
    private long amountAgency = 0;

    // 上传状态
    @DatabaseField(columnName = UPLOADSTATUS_FIELD_NAME)
    private String uploadStatus = UPLOAD_STAUTS_WAITFORSEND;

    // 收件人
    @DatabaseField
    private String recipient = "";

    // 签收状态信息
    @DatabaseField
    private String signedStateInfo = "";

    // 收派员姓名
    @DatabaseField
    private String empName = "";

    // 是否签收
    @DatabaseField
    private long isReceiverSignOff = 0;

    // 是否有图片
    @DatabaseField
    private long mIsPicture = 0;

    public SignedLogVO() {
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    public String getSignedState() {
        return signedState;
    }

    public void setSignedState(String signedState) {
        this.signedState = signedState;
    }

    public Satisfaction getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Satisfaction satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getExpSignedDescription() {
        return expSignedDescription;
    }

    public void setExpSignedDescription(String expSignedDescription) {
        this.expSignedDescription = expSignedDescription;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String status) {
        this.uploadStatus = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSignedLogId() {
        return signedLogId;
    }

    public void setSignedLogId(String signedLogId) {
        this.signedLogId = signedLogId;
    }

    public long isScan() {
        return isScan;
    }

    public void setIsScan(long isScan) {
        this.isScan = isScan;
    }

    public String getSignOffTypeCode() {
        return signOffTypeCode;
    }

    public void setSignOffTypeCode(String signOffTypeCode) {
        this.signOffTypeCode = signOffTypeCode;
    }

    public String getRecieverSignOff() {
        return recieverSignOff;
    }

    public void setRecieverSignOff(String recieverSignOff) {
        this.recieverSignOff = recieverSignOff;
    }

    public String getPdaNumber() {
        return pdaNumber;
    }

    public void setPdaNumber(String pdaNumber) {
        this.pdaNumber = pdaNumber;
    }

    public long getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(long amountCollected) {
        this.amountCollected = amountCollected;
    }

    public long getAmountAgency() {
        return amountAgency;
    }

    public void setAmountAgency(long amountAgency) {
        this.amountAgency = amountAgency;
    }

    public String getSignedStateInfo() {
        return signedStateInfo;
    }

    public void setSignedStateInfo(String signedStateInfo) {
        this.signedStateInfo = signedStateInfo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public long isReceiverSignOff() {
        return isReceiverSignOff;
    }

    public void setIsReceiverSignOff(long isReceiverSignOff) {
        this.isReceiverSignOff = isReceiverSignOff;
    }

    public long isPicture() {
        return mIsPicture;
    }

    public void setIsPicture(long isPicture) {
        this.mIsPicture = isPicture;
    }

    public static SubmitSignedLogRequestMsgVO getFakeRequestMsgVO() {
    	final SubmitSignedLogRequestMsgVO requestMsgVO = new SubmitSignedLogRequestMsgVO();
    	requestMsgVO.setAmountAgency("");
    	requestMsgVO.setAmountCollected("");
    	requestMsgVO.setEmpCode("00003523");
    	requestMsgVO.setEmpName("手持终端收派培训");
    	requestMsgVO.setExpSignedDescription("");
    	requestMsgVO.setStatus("发送中");
    	requestMsgVO.setId("b2cd08d9-5eae-4c83-996f-f6b51c6accfb");
    	requestMsgVO.setIsPicture("0");
    	requestMsgVO.setIsReceiverSignOff("0");
    	requestMsgVO.setWaybillNo("2473718025");
    	requestMsgVO.setPdaNumber("63101128211487");
    	requestMsgVO.setPictureData("U3lzdGVtLkJ5dGVbXQ\u003d\u003d");
    	requestMsgVO.setRecieverSignOff("已签收");
    	requestMsgVO.setSatisfaction("不满意");
    	requestMsgVO.setSignOffTypeCode("SELF");
    	requestMsgVO.setSignedState("1");
    	requestMsgVO.setSignedStateInfo("正常签收");
    	requestMsgVO.setSignedTime("2012-12-29 21:30:25");
    	requestMsgVO.setUploadStatus("WaitForSend");
    	requestMsgVO.setScan(0);
    	return requestMsgVO;
    }
    
    public SubmitSignedLogRequestMsgVO toVO() {
        SubmitSignedLogRequestMsgVO submitSignedLogRequest = new SubmitSignedLogRequestMsgVO();
        submitSignedLogRequest.setId(setRand());
        submitSignedLogRequest.setSignOffTypeCode(signOffTypeCode);
        submitSignedLogRequest.setRecieverSignOff(recieverSignOff);
        submitSignedLogRequest.setAmountCollected(String.valueOf(amountCollected));
        submitSignedLogRequest.setAmountAgency(String.valueOf(amountAgency));
        submitSignedLogRequest.setWaybillNo(waybillNo);
        submitSignedLogRequest.setSignedState(String.valueOf(signedState));
        submitSignedLogRequest.setSignedStateInfo(signedStateInfo);        
        submitSignedLogRequest.setEmpCode(empCode);
        submitSignedLogRequest.setEmpName(empName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        submitSignedLogRequest.setSignedTime( dateFormat.format(signedTime));       
        submitSignedLogRequest.setSatisfaction(SignedLogVO.GetSatisfaction(satisfaction));
        submitSignedLogRequest.setIsReceiverSignOff(String.valueOf(isReceiverSignOff));
        submitSignedLogRequest.setIsPicture(String.valueOf(mIsPicture));
        submitSignedLogRequest.setScan(1);
        submitSignedLogRequest.setExpSignedDescription(expSignedDescription);
        submitSignedLogRequest.setPdaNumber(pdaNumber);
       // submitSignedLogRequest.setStatus("");
        // FIXME, must use the base64 encoding the picture 
        // now we just use ""
        submitSignedLogRequest.setPictureData("");
        submitSignedLogRequest.setUploadStatus(uploadStatus);
        submitSignedLogRequest.setStatus(UPLOADSTATUSMAP.get(uploadStatus));
        
        return submitSignedLogRequest;
    }

    public UpdateSignedLogRequestMsgVO toUpdateVO() {
    	UpdateSignedLogRequestMsgVO updateSignedLogRequest = new UpdateSignedLogRequestMsgVO();
    	updateSignedLogRequest.setSignOffTypeCode(signOffTypeCode);
    	updateSignedLogRequest.setWaybillNo(waybillNo);
    	updateSignedLogRequest.setSatisfaction(SignedLogVO.GetSatisfaction(satisfaction));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        updateSignedLogRequest.setSignedTime( dateFormat.format(signedTime));       
    	updateSignedLogRequest.setRecieverSignOff(recieverSignOff);
    	updateSignedLogRequest.setIsReceiverSignOff(String.valueOf(isReceiverSignOff));
    	updateSignedLogRequest.setIsPicture(String.valueOf(mIsPicture));
        // FIXME, must use the base64 encoding the picture 
        // now we just use ""
        updateSignedLogRequest.setPictureData("");
        
        return updateSignedLogRequest;
    }
    
    public static String setRand() {
        String rad = "0123456789";
        StringBuffer result = new StringBuffer();
        Random rand = new Random();
        int length = 32;
        for (int i = 0; i < length; i++) {
            int randNum = rand.nextInt(10);
            result.append(rad.substring(randNum, randNum + 1));
        }
        return result.toString();
    }

    public enum Satisfaction {
        SATISFIED(0), VERY_SATISFIED(1), DISSATISFIED(2);

        private int satisfaction;

        Satisfaction(int satis) {
            satisfaction = satis;
        }

        int getSatisfaction() {
            return satisfaction;
        }

    }

    public static String GetSatisfaction(Satisfaction statis) {
        switch (statis) {
        case SATISFIED:
            return "满意";
        case VERY_SATISFIED:
            return "很满意";
        case DISSATISFIED:
            return "不满意";
        default:
            return null;
        }
    }
}
