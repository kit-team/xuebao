package cn.net.yto;

import cn.net.yto.engine.SignedLogReportTaskManager;
import android.app.Application;

public class MainApp extends Application {

	private static MainApp sAppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
		sAppContext = this;
        SignedLogReportTaskManager.getInstance(this).run();
    }

	public static MainApp getAppContext() {
		return sAppContext;
	}

	@Override
	public void onTerminate() {
		sAppContext = null;
		super.onTerminate();
	}
}