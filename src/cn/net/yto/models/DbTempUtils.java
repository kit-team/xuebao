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
        ToastUtils.getInstance(context).showToast("保存到数据库");
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
