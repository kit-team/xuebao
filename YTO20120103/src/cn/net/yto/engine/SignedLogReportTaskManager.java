
package cn.net.yto.engine;

import java.util.List;
import android.content.Context;
import cn.net.yto.engine.BaseTask.TaskCallback;
import cn.net.yto.utils.LogUtils;

/**
 * It's a task download manager, not a engine. So it just add download task to
 * engine, and then sleep. Sleep time is define in <b>SLEEP_TIME_MS</b>. Please
 * modify this class carefully.
 */
public class SignedLogReportTaskManager {
    private static int sID;

    private static boolean sRun;

    private static Thread sWorkThread;

    private final static int SLEEP_TIME_MS = 10000; // 10s

    private boolean mIsTaskCompleted = true;

    private static SignedLogReportTaskManager sCycleDownloadTaskManagerInstance = new SignedLogReportTaskManager();

    private static Context mContext;

    private SignedLogReportTaskManager() {
    }

    public static SignedLogReportTaskManager getInstance(Context context) {
        LogUtils.logD("CycleDownloadTaskManager.getInstance");
        mContext = context.getApplicationContext();
        return sCycleDownloadTaskManagerInstance;
    }

    public void run() {
        if (sRun) {
            LogUtils.logE("CycleDownloadTaskManager has been running, cancel it");
            stop();
        }
        sRun = true;
        LogUtils.logD("CycleDownloadTaskManager.run, ID" + sID);
        sWorkThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    // TODO: worried about that, if tasks is pending, then more
                    // and more task will be added into.
                    if (mIsTaskCompleted) {
                        mIsTaskCompleted = false;
                        addTask();
                    }
                    try {
                        Thread.sleep(SLEEP_TIME_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        LogUtils.logW("CycleDownloadTaskManager has been canceled, ID" + sID);
                        return;
                    }
                }
            }
        }, "CycleDownloadTaskManager" + sID++);
        sWorkThread.start();
    }

    private void addTask() {
        WSSignedLogTask task = new WSSignedLogTask(mContext);
        task.setCallback(new TaskCallback() {
            @Override
            public void onCallBack(Object result) {
                mIsTaskCompleted = true;
            }
        });
        GeneralTaskEngine.getInstance().appendTask(task);
    }

    public void stop() {
        if (sWorkThread != null) {
            sWorkThread.interrupt();
        }
    }
}
