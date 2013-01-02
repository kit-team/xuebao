package cn.net.yto.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import cn.net.yto.R;

public class ToastUtils {
    private final static String TAG = "ToastUtils";

    private static ToastUtils sInstance = new ToastUtils();

    private Context mContext = null;
    private Toast mToast = null;

    public static ToastUtils getInstance() {
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

    public  static void showToast(int resId) {
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

    public enum Operation {
        SAVE, MODIFY, DELETE, UPLOAD,
    };

    public static void showOperationToast(Operation opt, boolean result) {
        switch (opt) {
        case SAVE:
            if (result) {
                ToastUtils.showToast(R.string.toast_save_success);
            } else {
                ToastUtils.showToast(R.string.toast_save_failed);
            }
            break;
        case MODIFY:
            if (result) {
                ToastUtils.showToast(R.string.toast_modify_success);
            } else {
                ToastUtils.showToast(R.string.toast_modify_failed);
            }
            break;
        case DELETE:
            if (result) {
                ToastUtils.showToast(R.string.toast_delete_success);
            } else {
                ToastUtils.showToast(R.string.toast_delete_failed);
            }
            break;
        case UPLOAD:
            if (result) {
                ToastUtils.showToast(R.string.toast_upload_success);
            } else {
                ToastUtils.showToast(R.string.toast_upload_failed);
            }
            break;
        default:
            break;
        }
    }

}
