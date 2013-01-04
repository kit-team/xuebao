package cn.net.yto.ui.menu;

import android.text.TextUtils;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.UploadStatus;

public class SignListAdapterItem {
    private SignedLogVO mSignedLog = null;
    private String mSignTime = null;

    public boolean mSelected = false;

    public SignListAdapterItem(SignedLogVO signedLog) {
        mSignedLog = signedLog;
        mSelected = false;
    }

    public SignedLogVO getSignedLogVO() {
        return mSignedLog;
    }

    public String getWaybillNo() {
        return mSignedLog.getWaybillNo();
    }

    public String getSignType() {
        if (TextUtils.isEmpty(mSignedLog.getSignOffTypeCode())) {
            return mSignedLog.getSignedStateInfo();
        }
        return mSignedLog.getSignOffTypeCode();
    }

    public String getRecipient() {
        return mSignedLog.getRecipient();
    }

    public String getSignTime() {
        if (mSignTime == null) {
            mSignTime = CommonUtils.getFormatedDateTime(CommonUtils.FORMAT_DATE, mSignedLog
                    .getSignedTime().getTime());
        }
        return mSignTime;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
    
    public String getUploadStatus(){
        String status = "";
        if (mSignedLog.getUploadStatus() == UploadStatus.NOT_UPLOAD) {
            status = "未上传";
        } else if (mSignedLog.getUploadStatus() == UploadStatus.UPLOAD_SUCCESS) {
            status = "上传成功";
        } else if (mSignedLog.getUploadStatus() == UploadStatus.UPLOAD_FAILURE) {
            status = "上传失败";
        } else if (mSignedLog.getUploadStatus() == UploadStatus.NEED_UPDATE) {
            status = "数据过期";
        } else if (mSignedLog.getUploadStatus() == UploadStatus.UPDATE_FAILURE) {
            status = "更新失败";
        } else {
            status = "状态错误";
        }
        return status;
    }

    public String getComment() {
        String comment = mSignedLog.getExpSignedDescription();
        if (TextUtils.isEmpty(comment)){
            return "无备注"; 
        }
        return comment;
    }

}
