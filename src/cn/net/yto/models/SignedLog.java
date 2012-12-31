//package cn.net.yto.models;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
//import cn.net.yto.vo.message.SubmitSignedLogRequestMsgVO;
//
//import android.content.ContentValues;
//
//public class SignedLog {
//	
//	public enum SignedState {  
//	    SIGNED_SUCCESS(1),  
//	    SIGNED_FAILURE(2);
//	    
//	    private int signedState;
//	    SignedState(int state) {
//	    	signedState = state;
//	    }
//
//	    int getSignedState() {
//	        return signedState;
//	    }
//	};
//	
//	public enum UploadStatus {
//		NOT_UPLOAD(0),
//		UPLOAD_SUCCESS(1),
//		UPLOAD_FAILURE(2),
//		UPLOADING(3);
//		
//	    private int uploadStatus;
//		UploadStatus(int status) {
//			uploadStatus = status;
//	    }
//
//	    int getUploadStatus() {
//	        return uploadStatus;
//	    }
//
//	}
//	
//	public enum Satisfaction {
//		SATISFIED(0),
//		VERY_SATISFIED(1),
//		DISSATISFIED(2);
//		
//	    private int satisfaction;
//	    Satisfaction(int satis) {
//	    	satisfaction = satis;
//	    }
//
//	    int getSatisfaction() {
//	        return satisfaction;
//	    }
//
//	}
//	
//    public static UploadStatus GetUploadStatus(int i)
//    {
//        switch(i)
//        {
//            case 0:
//                return UploadStatus.NOT_UPLOAD;
//            case 1:
//                return UploadStatus.UPLOAD_SUCCESS;
//            case 2:
//                return UploadStatus.UPLOAD_FAILURE;
//            case 3:
//                return UploadStatus.UPLOADING;
//            default: 
//                return null;
//        }
//    }
//
//    public static SignedState GetSignedState(int i)
//    {
//        switch(i)
//        {
//            case 1:
//                return SignedState.SIGNED_SUCCESS;
//            case 2:
//                return SignedState.SIGNED_FAILURE;
//            default: 
//                return null;
//        }
//    }
//
//    public static Satisfaction GetSatisfaction(int i)
//    {
//        switch(i)
//        {
//            case 0:
//                return Satisfaction.SATISFIED;
//            case 1:
//                return Satisfaction.VERY_SATISFIED;
//            case 2:
//                return Satisfaction.DISSATISFIED;
//            default: 
//                return null;
//        }
//    }
//    
//    // id
//    private String mSignedLogId;
//    
//    // 是否查看
//    private long mIsScan = 0;
//    
//    // 签收类型
//    private String mSignOffTypeCode;
//    
//    // 是否签收
//    private String mRecieverSignOff;
//
//    // 是否签收
//    private String mPdaNumber;
//    
//	// 收派员工号
//	private String mEmpCode;
//	
//	// 面单号
//	private String mWaybillNo;	
//	
//	// 签收时间
//	private Date mSignedTime = new Date();
//	
//	// 签收数据
//	private byte[] mPictureData = new byte[]{};
//	
//	// 签收状态标记
//	private SignedState mSignedState = SignedState.SIGNED_SUCCESS;
//	
//	// 满意度
//	private Satisfaction mSatisfaction = Satisfaction.SATISFIED;
//
//	// 异常签收描述
//	private String mExpSignedDescription;
//	
//	// 到付金额
//	private long mAmountCollected = 0;
//	
//	// 代收金额
//	private long mAmountAgency = 0;
//	
//	// 上传状态
//	private UploadStatus mUploadStatus = UploadStatus.NOT_UPLOAD;
//	
//	// 收件人
//	private String mRecipient;
//	
//	// 签收状态信息
//	private String mSignedStateInfo;
//	
//	// 收派员姓名
//	private String mEmpName;
//	
//	// 是否签收 
//	private long mIsReceiverSignOff = 0;
//	
//	// 是否有图片 
//	private long mIsPicture = 0;
//	
//	public SignedLog() {
//	}
//
//	public String getEmpCode() {
//		return mEmpCode;
//	}
//
//	public void setEmpCode(String empCode) {
//		this.mEmpCode = empCode;
//	}
//
//	public String getWaybillNo() {
//		return mWaybillNo;
//	}
//
//	public void setWaybillNo(String waybillNo) {
//		this.mWaybillNo = waybillNo;
//	}
//
//	public Date getSignedTime() {
//		return mSignedTime;
//	}
//
//	public void setSignedTime(Date signedTime) {
//		this.mSignedTime = signedTime;
//	}
//
//	public byte[] getPictureData() {
//		return mPictureData;
//	}
//
//	public void setPictureData(byte[] pictureData) {
//		this.mPictureData = pictureData;
//	}
//
//	public SignedState getSignedState() {
//		return mSignedState;
//	}
//
//	public void setSignedState(SignedState signedState) {
//		this.mSignedState = signedState;
//	}
//
//	public Satisfaction getSatisfaction() {
//		return mSatisfaction;
//	}
//
//	public void setSatisfaction(Satisfaction satisfaction) {
//		this.mSatisfaction = satisfaction;
//	}
//
//	public String getExpSignedDescription() {
//		return mExpSignedDescription;
//	}
//
//	public void setExpSignedDescription(String expSignedDescription) {
//		this.mExpSignedDescription = expSignedDescription;
//	}
//
//	public UploadStatus getStatus() {
//		return mUploadStatus;
//	}
//
//	public void setStatus(UploadStatus status) {
//		this.mUploadStatus = status;
//	}
//
//	public String getRecipient() {
//		return mRecipient;
//	}
//
//	public void setRecipient(String recipient) {
//		this.mRecipient = recipient;
//	}
//	
//    public String getSignedLogId() {
//		return mSignedLogId;
//	}
//
//	public void setSignedLogId(String signedLogId) {
//		this.mSignedLogId = signedLogId;
//	}
//
//	public long isScan() {
//		return mIsScan;
//	}
//
//	public void setIsScan(long isScan) {
//		this.mIsScan = isScan;
//	}
//
//	public String getSignOffTypeCode() {
//		return mSignOffTypeCode;
//	}
//
//	public void setSignOffTypeCode(String signOffTypeCode) {
//		this.mSignOffTypeCode = signOffTypeCode;
//	}
//
//	public String getRecieverSignOff() {
//		return mRecieverSignOff;
//	}
//
//	public void setRecieverSignOff(String recieverSignOff) {
//		this.mRecieverSignOff = recieverSignOff;
//	}
//
//	public String getPdaNumber() {
//		return mPdaNumber;
//	}
//
//	public void setPdaNumber(String pdaNumber) {
//		this.mPdaNumber = pdaNumber;
//	}
//
//	public long getAmountCollected() {
//		return mAmountCollected;
//	}
//
//	public void setAmountCollected(long amountCollected) {
//		this.mAmountCollected = amountCollected;
//	}
//
//	public long getAmountAgency() {
//		return mAmountAgency;
//	}
//
//	public void setAmountAgency(long amountAgency) {
//		this.mAmountAgency = amountAgency;
//	}
//
//	public String getSignedStateInfo() {
//		return mSignedStateInfo;
//	}
//
//	public void setSignedStateInfo(String signedStateInfo) {
//		this.mSignedStateInfo = signedStateInfo;
//	}
//
//	public String getEmpName() {
//		return mEmpName;
//	}
//
//	public void setEmpName(String empName) {
//		this.mEmpName = empName;
//	}
//
//	public long isReceiverSignOff() {
//		return mIsReceiverSignOff;
//	}
//
//	public void setIsReceiverSignOff(long isReceiverSignOff) {
//		this.mIsReceiverSignOff = isReceiverSignOff;
//	}
//
//	public long isPicture() {
//		return mIsPicture;
//	}
//
//	public void setIsPicture(long isPicture) {
//		this.mIsPicture = isPicture;
//	}
//
//	public ContentValues toContentValues() {
//        ContentValues value = new ContentValues();
//        value.put(YtoDBHelper.C_PICTURE_DATA, getPictureData());
//        value.put(YtoDBHelper.C_AMOUNT_AGENCY, getAmountAgency());
//        value.put(YtoDBHelper.C_AMOUNT_COLLECTED, getAmountCollected());
//        value.put(YtoDBHelper.C_SIGNED_STATE, getSignedState().getSignedState());
//        value.put(YtoDBHelper.C_EMPCODE, getEmpCode());
//        value.put(YtoDBHelper.C_WAYBILLNO, getWaybillNo());
//        value.put(YtoDBHelper.C_EXPSIGNED_DESCRIPTION, getExpSignedDescription());
//        value.put(YtoDBHelper.C_STATUS, getStatus().getUploadStatus());
//        value.put(YtoDBHelper.C_STATISFACION, getSatisfaction().getSatisfaction());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        value.put(YtoDBHelper.C_SIGNED_TIME, dateFormat.format(getSignedTime()));
//        value.put(YtoDBHelper.C_RECIPIENT, getRecipient());
//        value.put(YtoDBHelper.C_EMP_NAME, getEmpName());
//        value.put(YtoDBHelper.C_IS_PICTURE, isPicture());
//        value.put(YtoDBHelper.C_IS_RECEIVER_SIGNOFF, isReceiverSignOff());
//        value.put(YtoDBHelper.C_IS_SCAN, isScan());
//        value.put(YtoDBHelper.C_PAD_NUMBER, getPdaNumber());
//        value.put(YtoDBHelper.C_SIGNEDSTATE_INFO, getSignedStateInfo());
//        value.put(YtoDBHelper.C_SIGNOFF_TYPE_CODE, getSignOffTypeCode());
//        value.put(YtoDBHelper.C_SINGEDLOG_ID, getSignedLogId());
//        value.put(YtoDBHelper.C_RECEIVER_SIGNOFF, getRecieverSignOff());
//        
//        return value;
//    }
//    
//    // TODO remove the code
//    public static SignedLog getSignedLogForTest() {
//        SignedLog log = new SignedLog();
//        log.setWaybillNo("1234567890");
//        log.setRecipient("Jackson Hello大海");
//        return log;
//    }
//
//    public SubmitSignedLogRequestMsgVO toVO() {
//        SubmitSignedLogRequestMsgVO submitSignedLogRequest = new SubmitSignedLogRequestMsgVO();
//        submitSignedLogRequest.setId(setRand());
//        submitSignedLogRequest.setSignOffTypeCode(mSignOffTypeCode);
//        submitSignedLogRequest.setRecieverSignOff(mRecieverSignOff);
//        submitSignedLogRequest.setAmountCollected(String.valueOf(mAmountCollected));
//        submitSignedLogRequest.setAmountAgency(String.valueOf(mAmountAgency));
//        submitSignedLogRequest.setWaybillNo(mWaybillNo);
//        submitSignedLogRequest.setSignedState(String.valueOf(mSignedState));
//        submitSignedLogRequest.setSignedStateInfo(mSignedStateInfo);
//        // FIXME
//        submitSignedLogRequest.setEmpCode("88888888");
//        submitSignedLogRequest.setEmpName("手持终端收派培训");
//        
//        submitSignedLogRequest.setSignedTime(String.valueOf(mSignedTime));
//        submitSignedLogRequest.setSatisfaction(String.valueOf(mSatisfaction));
//        submitSignedLogRequest.setIsReceiverSignOff(String.valueOf(mIsReceiverSignOff));
//        submitSignedLogRequest.setIsPicture(String.valueOf(mIsPicture));
//
//        return submitSignedLogRequest;
//    }
//
//    public static String setRand() {
//        String rad = "0123456789";
//        StringBuffer result = new StringBuffer();
//        Random rand = new Random();
//        int length = 32;
//        for (int i = 0; i < length; i++) {
//            int randNum = rand.nextInt(10);
//            result.append(rad.substring(randNum, randNum + 1));
//        }
//        return result.toString();
//    } 
//}
