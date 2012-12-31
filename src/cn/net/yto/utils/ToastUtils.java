package cn.net.yto.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ToastUtils {
    private final static String TAG = "ToastUtils";

    private static ToastUtils sInstance = null;

    private Context mContext = null;
    private Toast mToast = null;

    public static ToastUtils getInstance() {
        if (sInstance == null) {
            sInstance = new ToastUtils();
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private ToastUtils() {
    }

    public static void showToast(String text) {
        if (sInstance.mContext == null) {
            Log.e(TAG, "context is null, not init ToastUtils");
            return;
        }
        if (sInstance.mToast == null) {
            sInstance.mToast = Toast.makeText(sInstance.mContext, text, Toast.LENGTH_SHORT);
        } else {
            sInstance.mToast.setText(text);
        }
        sInstance.mToast.show();
    }

    public void showToast(int resId) {
        if (sInstance.mContext == null) {
            Log.e(TAG, "context is null, not init ToastUtils");
            return;
        }
        if (sInstance.mToast == null) {
            sInstance.mToast = Toast.makeText(sInstance.mContext, resId, Toast.LENGTH_SHORT);
        } else {
            sInstance.mToast.setText(resId);
        }
        sInstance.mToast.show();
    }

}
