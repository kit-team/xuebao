
package cn.net.yto.engine;

import cn.net.yto.utils.LogUtils;

/**
 * Base task which define methods to run in Engines.
 * All tasks should be its sub-class.
 * Please modify this class carefully.
 *
 */
public abstract class BaseTask {
    private boolean mIsCanceled;

    private String mTaskName;

    private TaskCallback mCallback;

    public static interface TaskCallback {
        public void onCallBack(Object result);
    }

    public abstract Object run();

    public void setTaskName(String name) {
        mTaskName = name;
    }

    public void setCallback(TaskCallback callback) {
        LogUtils.logD("setCallback");
        mCallback = callback;
    }

    public void onCallback(Object result) {
        LogUtils.logD("onCallback");
        TaskCallback callback = mCallback;
        if (callback != null) {
            callback.onCallBack(result);
        }
    }

    public boolean isCanceled() {
        LogUtils.logD("isCanceled");
        return mIsCanceled;
    }

    public void markAsCanceled() {
        LogUtils.logD("cancelTask");
        mIsCanceled = true;
        mCallback = null;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public String toString() {
        return "TaskName " + mTaskName + " mCallback" + mCallback + " mIsCanceled " + mIsCanceled;
    }
}
