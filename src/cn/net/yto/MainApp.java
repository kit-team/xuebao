package cn.net.yto;

import cn.net.yto.engine.SignedLogReportTaskManager;
import cn.net.yto.ui.ToastUtils;
import android.app.Application;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignedLogReportTaskManager.getInstance(this).run();
        ToastUtils.getInstance().init(this);
    }

}
