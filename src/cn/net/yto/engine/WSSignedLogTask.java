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
        List<SignedLogVO> logs = mSignedLogMgr.queryAllSignedLog();
        for (SignedLogVO log : logs) {
            final SignedLogVO log2 = log;
            UserService.submitSignedLog(log2, new Listener() {
                @Override
                public void onPreSubmit() {
                    log2.setUploadStatus(UploadStatus.UPLOADING);
//                    dbHelper.updateStatusOfSignedLog(log2);
                }

                @Override
                public void onPostSubmit(Object response, Integer responseType) {
                    LogUtils.logD("response " + response + " responseType " + responseType);
                    if (response != null && response instanceof SubmitSignedLogResponseMsgVO) {
                        int errorCode = ((SubmitSignedLogResponseMsgVO) response).getRetVal();
                        if (errorCode == 1) {
                            log2.setUploadStatus(UploadStatus.UPLOAD_SUCCESS);
                        } else {
                            log2.setUploadStatus(UploadStatus.UPLOAD_FAILURE);
                        }
//                        dbHelper.updateStatusOfSignedLog(log2);
                    }
                    synchronized (object) {
                        object.notifyAll();
                    }
                };
            });
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
