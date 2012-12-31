
package cn.net.yto.engine;

import java.util.Vector;

import android.content.Context;
import cn.net.yto.models.SignedLog;
import cn.net.yto.models.SignedLog.UploadStatus;
import cn.net.yto.models.YtoDBHelper;
import cn.net.yto.net.UserService;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SubmitSignedLogResponseMsgVO;

public class WSSignedLogTask extends BaseTask {
    private Context mContext;

    WSSignedLogTask(Context context) {
        mContext = context;
        setTaskName(WSSignedLogTask.class.getName());
    }

    @Override
    public Object run() {
        LogUtils.logD("WSSignedLogTask ...");
        final Object object = new Object();
        final YtoDBHelper dbHelper = new YtoDBHelper(mContext.getApplicationContext());
        Vector<SignedLog> logs = dbHelper.getUploadSignedLog();
        for (SignedLog log : logs) {
            final SignedLog log2 = log;
            UserService.submitSignedLog(log2, new Listener() {
                @Override
                public void onPreSubmit() {
                	log2.setStatus(UploadStatus.UPLOADING);
                	dbHelper.updateStatusOfSignedLog(log2);
                }

                @Override
                public void onPostSubmit(Object response, Integer responseType) {
                    LogUtils.logD("response " + response + " responseType " + responseType);
                    if (response != null && response instanceof SubmitSignedLogResponseMsgVO) {
                        int errorCode = ((SubmitSignedLogResponseMsgVO)response).getRetVal();
                        if (errorCode == 1) { 
	                        log2.setStatus(UploadStatus.UPLOAD_SUCCESS);
                        } else {
                        	log2.setStatus(UploadStatus.UPLOAD_FAILURE);
                        }
                        dbHelper.updateStatusOfSignedLog(log2);
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
