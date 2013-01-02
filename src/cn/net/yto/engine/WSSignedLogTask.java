package cn.net.yto.engine;

import java.util.List;

import android.content.Context;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.net.UserService;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.UploadStatus;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

public class WSSignedLogTask extends BaseTask {
    private Context mContext;
    private SignedLogManager mSignedLogMgr = null;

    WSSignedLogTask(Context context) {
        mContext = context;
        mSignedLogMgr = new SignedLogManager(mContext.getApplicationContext());
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
