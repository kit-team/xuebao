package cn.net.yto.vo;

import java.util.Date;
import java.util.Random;

import cn.net.yto.vo.message.SubmitSignedLogRequestMsgVO;

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
    @DatabaseField(columnName = WAYBILLNO_FIELD_NAME, id = true)
    private String waybillNo = "";

    // 签收时间
    @DatabaseField(columnName = SIGNED_TIME_FIELD_NAME)
    private Date signedTime = new Date();

    // 签收数据
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] pictureData = new byte[]{};

    // 签收状态标记
    @DatabaseField
    private SignedState signedState = SignedState.SIGNED_SUCCESS;

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
    private UploadStatus uploadStatus = UploadStatus.NOT_UPLOAD;

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

    public SignedState getSignedState() {
        return signedState;
    }

    public void setSignedState(SignedState signedState) {
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

    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(UploadStatus status) {
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
        // FIXME
        submitSignedLogRequest.setEmpCode("88888888");
        submitSignedLogRequest.setEmpName("手持终端收派培训");

        submitSignedLogRequest.setSignedTime(String.valueOf(signedTime));
        submitSignedLogRequest.setSatisfaction(String.valueOf(satisfaction));
        submitSignedLogRequest.setIsReceiverSignOff(String.valueOf(isReceiverSignOff));
        submitSignedLogRequest.setIsPicture(String.valueOf(mIsPicture));

        return submitSignedLogRequest;
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

    public enum SignedState {
        SIGNED_SUCCESS(1), SIGNED_FAILURE(2);

        private int signedState;

        SignedState(int state) {
            signedState = state;
        }

        int getSignedState() {
            return signedState;
        }
    };

    public enum UploadStatus {
        NOT_UPLOAD(0), UPLOAD_SUCCESS(1), UPLOAD_FAILURE(2), UPLOADING(3), NEED_UPDATE(4), UPDATE_FAILURE(5);

        private int uploadStatus;

        UploadStatus(int status) {
            uploadStatus = status;
        }

        int getUploadStatus() {
            return uploadStatus;
        }

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

    public static UploadStatus GetUploadStatus(int i) {
        switch (i) {
        case 0:
            return UploadStatus.NOT_UPLOAD;
        case 1:
            return UploadStatus.UPLOAD_SUCCESS;
        case 2:
            return UploadStatus.UPLOAD_FAILURE;
        case 3:
            return UploadStatus.UPLOADING;
        case 4:
            return UploadStatus.NEED_UPDATE;
        case 5:
            return UploadStatus.UPDATE_FAILURE;    
        default:
            return null;
        }
    }

    public static SignedState GetSignedState(int i) {
        switch (i) {
        case 1:
            return SignedState.SIGNED_SUCCESS;
        case 2:
            return SignedState.SIGNED_FAILURE;
        default:
            return null;
        }
    }

    public static Satisfaction GetSatisfaction(int i) {
        switch (i) {
        case 0:
            return Satisfaction.SATISFIED;
        case 1:
            return Satisfaction.VERY_SATISFIED;
        case 2:
            return Satisfaction.DISSATISFIED;
        default:
            return null;
        }
    }
}
