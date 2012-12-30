
package cn.net.yto.engine;

import cn.net.yto.utils.LogUtils;

public class WSSignedLogTask extends BaseTask {

    WSSignedLogTask() {
        setTaskName(WSSignedLogTask.class.getName());
    }

    @Override
    public Object run() {
        // TODO submit signed log to server later.
        LogUtils.logD("WSSignedLogTask ...");
        return null;
    }

}
