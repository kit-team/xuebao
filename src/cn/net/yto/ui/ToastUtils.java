package cn.net.yto.ui;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private Context mContext = null;
    private Toast mToast = null;

    private static ToastUtils sInstance = null;

    public static ToastUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ToastUtils();
            sInstance.mContext = context.getApplicationContext();
        }
        return sInstance;
    }

    private ToastUtils() {
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

}
