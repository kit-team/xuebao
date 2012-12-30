package cn.net.yto.test;

import java.util.Date;
import java.util.Vector;

import cn.net.yto.models.SignedLog;
import cn.net.yto.models.SignedLog.Satisfaction;
import cn.net.yto.models.SignedLog.SignedState;
import cn.net.yto.models.SignedLog.UploadStatus;
import cn.net.yto.models.YtoDBHelper;
import android.test.AndroidTestCase;

public class YtoDBTest extends AndroidTestCase{
	
	private YtoDBHelper ytoDBHelper = null;
	
    protected void setUp() throws Exception
    {
    	ytoDBHelper = new YtoDBHelper(this.getContext());
        super.setUp();
    }
    
    public void test_get_uploadsignlog() {
    	SignedLog signLog1 = new SignedLog("id123", "123", new Date(), null, SignedState.SIGNED_SUCCESS, Satisfaction.SATISFIED, "", 50, 50);
    	signLog1.setStatus(UploadStatus.NOT_UPLOAD); 
    	signLog1.setRecipient("abc");
    	SignedLog signLog2 = new SignedLog("id124", "124", new Date(), null, SignedState.SIGNED_SUCCESS, Satisfaction.SATISFIED, "", 50, 50);
    	signLog2.setStatus(UploadStatus.UPLOAD_SUCCESS);
    	signLog2.setRecipient("abc");
    	SignedLog signLog3 = new SignedLog("id125", "125", new Date(), null, SignedState.SIGNED_SUCCESS, Satisfaction.SATISFIED, "", 50, 50);
    	signLog3.setStatus(UploadStatus.UPLOAD_FAILURE);
    	signLog3.setRecipient("abc");
    	SignedLog signLog4 = new SignedLog("id126", "126", new Date(), null, SignedState.SIGNED_SUCCESS, Satisfaction.SATISFIED, "", 50, 50);
    	signLog4.setStatus(UploadStatus.NOT_UPLOAD);
    	signLog4.setRecipient("abc");
    	SignedLog signLog5 = new SignedLog("id127", "127", new Date(), null, SignedState.SIGNED_SUCCESS, Satisfaction.SATISFIED, "", 50, 50);
    	signLog5.setStatus(UploadStatus.NOT_UPLOAD);
    	signLog5.setRecipient("abc");
    	
    	ytoDBHelper.insertSignLog(signLog1);
    	ytoDBHelper.insertSignLog(signLog2);
    	ytoDBHelper.insertSignLog(signLog3);
    	ytoDBHelper.insertSignLog(signLog4);
    	ytoDBHelper.insertSignLog(signLog5);
    	
    	Vector<SignedLog> notUploadSignedLogs = ytoDBHelper.getUploadSignedLog();
    	assertEquals(3, notUploadSignedLogs.size());
    	
    	signLog1.setStatus(UploadStatus.UPLOAD_SUCCESS);
    	assertTrue(ytoDBHelper.updateStatusOfSignedLog(signLog1));
    	
    	notUploadSignedLogs = ytoDBHelper.getUploadSignedLog();
    	assertEquals(2, notUploadSignedLogs.size());
    }

}
