
package cn.net.yto.engine;

import java.util.List;

import android.content.Context;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.net.UserService;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.message.SubmitSignedLogResponseMsgVO;

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
//        final Object object = new Object();
//        List<SignedLogVO> logs = new SignedLogManager(mContext.getApplicationContext())
//                .querySubWayBillSignedLog("*");
//        for (SignedLogVO log : logs) {
//            final SignedLogVO log2 = log;
//            UserService.submitSignedLog(log2, new Listener() {
//                @Override
//                public void onPreSubmit() {
//                }
//
//                @Override
//                public void onPostSubmit(Object response, Integer responseType) {
//                    LogUtils.logD("response " + response + " responseType " + responseType);
//                    if (response != null && response instanceof SubmitSignedLogResponseMsgVO) {
//                        int status = ((SubmitSignedLogResponseMsgVO)response).getRetVal();
//                        log2.setStatus(SignedLogVO.GetUploadStatus(status));
//                        LogUtils.logD("Signed log status " + status);
//                    }
//                    synchronized (object) {
//                        object.notifyAll();
//                    }
//                };
//            });
//            synchronized (object) {
//                try {
//                    object.wait();
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
        return null;
    }

}
