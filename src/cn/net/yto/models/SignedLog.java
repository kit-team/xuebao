package cn.net.yto.models;

import java.util.Date;

public class SignedLog {
	
	public enum SignedState {  
	    SIGNED_SUCCESS(1),  
	    SIGNED_FAILURE(2);
	    
	    private int signedState;
	    SignedState(int state) {
	    	signedState = state;
	    }

	    int getSignedState() {
	        return signedState;
	    }
	};
	
	public enum UploadStatus {
		NOT_UPLOAD(0),
		UPLOAD_SUCCESS(1),
		UPLOAD_FAILURE(2),
		UPLOADING(3);
		
	    private int uploadStatus;
		UploadStatus(int status) {
			uploadStatus = status;
	    }

	    int getUploadStatus() {
	        return uploadStatus;
	    }

	}
	
	public enum Satisfaction {
		SATISFIED(0),
		VERY_SATISFIED(1),
		DISSATISFIED(2);
		
	    private int satisfaction;
	    Satisfaction(int satis) {
	    	satisfaction = satis;
	    }

	    int getSatisfaction() {
	        return satisfaction;
	    }

	}
	
    public static UploadStatus GetUploadStatus(int i)
    {
        switch(i)
        {
            case 0:
                return UploadStatus.NOT_UPLOAD;
            case 1:
                return UploadStatus.UPLOAD_SUCCESS;
            case 2:
                return UploadStatus.UPLOAD_FAILURE;
            case 3:
                return UploadStatus.UPLOADING;
            default: 
                return null;
        }
    }

    public static SignedState GetSignedState(int i)
    {
        switch(i)
        {
            case 1:
                return SignedState.SIGNED_SUCCESS;
            case 2:
                return SignedState.SIGNED_FAILURE;
            default: 
                return null;
        }
    }

    public static Satisfaction GetSatisfaction(int i)
    {
        switch(i)
        {
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

	// 收派员工号
	private String mEmpCode;
	
	// 面单号
	private String mWaybillNo;
	
	// 签收时间
	private Date mSignedTime;
	
	// 签收数据
	private byte[] mPictureData;
	
	// 签收状态标记
	private SignedState mSignedState;
	
	// 满意度
	private Satisfaction mSatisfaction;
	
	// 异常签收描述
	private String mExpSignedDescription;
	
	// 异常签收描述
	private long mCashAmount;
	
	// 异常签收描述
	private long mCardAmount;
	
	// 上传状态
	private UploadStatus mStatus;

	public SignedLog(String empCode, String waybillNo, Date signedTime, byte[] pictureData, SignedState signedState, Satisfaction satisfaction, 
			String expSignedDescription, long cashAmount, long cardAmount) {
		this.mEmpCode = empCode;
		this.mWaybillNo = waybillNo;
		this.mPictureData = pictureData;
		this.mSignedState = signedState;
		this.mSatisfaction = satisfaction;
		this.mExpSignedDescription = expSignedDescription;
		this.mCashAmount = cashAmount;
		this.mCardAmount = cardAmount;
		this.mSignedTime = signedTime;
		this.mStatus = UploadStatus.NOT_UPLOAD;
	}
	
	
	public SignedLog() {
	}

	public String getEmpCode() {
		return mEmpCode;
	}

	public void setEmpCode(String empCode) {
		this.mEmpCode = empCode;
	}

	public String getWaybillNo() {
		return mWaybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.mWaybillNo = waybillNo;
	}

	public Date getSignedTime() {
		return mSignedTime;
	}

	public void setSignedTime(Date signedTime) {
		this.mSignedTime = signedTime;
	}

	public byte[] getPictureData() {
		return mPictureData;
	}

	public void setPictureData(byte[] pictureData) {
		this.mPictureData = pictureData;
	}

	public SignedState getSignedState() {
		return mSignedState;
	}

	public void setSignedState(SignedState signedState) {
		this.mSignedState = signedState;
	}

	public Satisfaction getSatisfaction() {
		return mSatisfaction;
	}

	public void setSatisfaction(Satisfaction satisfaction) {
		this.mSatisfaction = satisfaction;
	}

	public String getExpSignedDescription() {
		return mExpSignedDescription;
	}

	public void setExpSignedDescription(String expSignedDescription) {
		this.mExpSignedDescription = expSignedDescription;
	}

	public long getCashAmount() {
		return mCashAmount;
	}

	public void setCashAmount(long cashAmount) {
		this.mCashAmount = cashAmount;
	}

	public long getCardAmount() {
		return mCardAmount;
	}

	public void setCardAmount(long cardAmount) {
		this.mCardAmount = cardAmount;
	}

	public UploadStatus getStatus() {
		return mStatus;
	}

	public void setStatus(UploadStatus status) {
		this.mStatus = status;
	}
	
	
}
