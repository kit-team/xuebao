package cn.net.yto.ui;

import java.text.SimpleDateFormat;

import android.text.TextUtils;

import cn.net.yto.models.SignedLog;

public class SignListAdapterItem {
    private SignedLog mSignedLog = null;
    private String mSignTime = null;

    public boolean mSelected = false;

    public SignListAdapterItem(SignedLog signedLog) {
        mSignedLog = signedLog;
        mSelected = false;
    }

    public SignedLog getSignedLog() {
        return mSignedLog;
    }

    public String getWaybillNo() {
        return mSignedLog.getWaybillNo();
    }

    public String getSignType() {
        if (TextUtils.isEmpty(mSignedLog.getSignOffTypeCode())) {
            return "(异常)"+mSignedLog.getSignedStateInfo();
        }
        return mSignedLog.getSignOffTypeCode();
    }

    public String getRecipient() {
        return mSignedLog.getRecipient();
    }

    public String getSignTime() {
        if (mSignTime == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            mSignTime = dateFormat.format(mSignedLog.getSignedTime());
        }
        return mSignTime;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
