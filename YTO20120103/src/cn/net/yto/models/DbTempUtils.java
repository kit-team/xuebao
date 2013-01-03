package cn.net.yto.models;

import java.util.ArrayList;
import java.util.Vector;

import cn.net.yto.ui.SignListAdapterItem;
import cn.net.yto.ui.ToastUtils;
import android.content.Context;

public class DbTempUtils {

    public static void insert(final Context context, final SignedLog signedLog) {
        new Thread() {
            public void run() {
                new YtoDBHelper(context.getApplicationContext()).insertSignLog(signedLog);
            }
        }.start();
        ToastUtils.showToast("保存到数据库");
    }

    public static void delete(final Context context, final SignedLog signedLog) {
        ArrayList<SignedLog> signedLogs = new ArrayList<SignedLog>();
        signedLogs.add(signedLog);
        delete(context, signedLogs);
    }

    public static void delete(final Context context, final ArrayList<SignedLog> signedLogs) {
        new Thread() {
            public void run() {
                for (SignedLog signedLog : signedLogs) {
                    // TODO delete 
//                    new YtoDBHelper(context.getApplicationContext()).delete(signedLog);
                }
            }
        }.start();
        ToastUtils.showToast("TODO ==>正在删除本地数据");
    }

    public static ArrayList<SignListAdapterItem> query(final Context context) {
        Vector<SignedLog> logs = new YtoDBHelper(context.getApplicationContext())
                .getUploadSignedLog();
        ArrayList<SignListAdapterItem> arrLogs = new ArrayList<SignListAdapterItem>();
        for (SignedLog log : logs) {
            arrLogs.add(new SignListAdapterItem(log));
        }
        return arrLogs;
    }

}
