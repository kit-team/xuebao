package cn.net.yto.engine;

import java.util.List;

import android.content.Context;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SignedLogVO;

public class WSSignedLogTask extends BaseTask {
    private Context mContext;
    private SignedLogManager mSignedLogMgr = null;

    WSSignedLogTask(Context context) {
        mContext = context;
        mSignedLogMgr = SignedLogManager.getInstance();
        setTaskName(WSSignedLogTask.class.getName());
    }

    @Override
    public Object run() {
        LogUtils.logD("WSSignedLogTask ...");
        final Object object = new Object();
        List<SignedLogVO> logs = mSignedLogMgr.queryNeedUploadSignedLog();
        LogUtils.logD("queryNeedUploadSignedLog : " + logs.size());
        for (SignedLogVO log : logs) {
        	mSignedLogMgr.submitSignedLog(log, mContext);
        }
        logs = mSignedLogMgr.queryNeedUpdateSignedLog();
        LogUtils.logD("queryNeedUpdateSignedLog : " + logs.size());
        for (SignedLogVO log : logs) {
        	mSignedLogMgr.updateSignedLog(log, mContext);
        }
        return null;
    }
}
