
package cn.net.yto.engine;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

import android.content.Context;

import cn.net.yto.R;
import cn.net.yto.models.SignedLog;
import cn.net.yto.models.YtoDBHelper;
import cn.net.yto.net.UserService;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.DeliverySignedLogResponseMsgVO;

public class WSSignedLogTask extends BaseTask {
    private Context mContext;

    WSSignedLogTask(Context context) {
        mContext = context;
        setTaskName(WSSignedLogTask.class.getName());
    }

    @Override
    public Object run() {
        // TODO submit signed log to server later.
        LogUtils.logD("WSSignedLogTask ...");
        final Object object = new Object();
        Vector<SignedLog> logs = new YtoDBHelper(mContext.getApplicationContext())
                .getUploadSignedLog();
        for (SignedLog log : logs) {
            final SignedLog log2 = log;
            UserService.submitSignedLog(log2, new Listener() {
                @Override
                public void onPreSubmit() {
                }

                @Override
                public void onPostSubmit(Object response, Integer responseType) {
                    LogUtils.logD("response " + response + " responseType " + responseType);
                    if (response != null && response instanceof DeliverySignedLogResponseMsgVO) {
                        int status = ((DeliverySignedLogResponseMsgVO)response).getRetVal();
                        log2.setStatus(SignedLog.GetUploadStatus(status));
                        LogUtils.logD("Signed log status " + status);
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
