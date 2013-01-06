package cn.net.yto.ui.menu;

import android.text.TextUtils;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.SignedLogVO;

public class SignListItem {
    private SignedLogVO mSignedLog = null;
    private String mSignTime = null;

    public boolean mSelected = false;

    public SignListItem(SignedLogVO signedLog) {
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
    
    public String getEmpCode() {
        return mSignedLog.getEmpCode();
    }
    
    public String getEmpName() {
        return mSignedLog.getEmpName();
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
    
    public String getUploadStatus() {
        return mSignedLog.getUploadStatusForUI();
    }

    public String getComment() {
        String comment = mSignedLog.getExpSignedDescription();
        if (TextUtils.isEmpty(comment)){
            return "无备注"; 
        }
        return comment;
    }

}
