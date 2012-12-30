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
    
    public void test_insert_signedlog() {
    	SignedLog signLog = new SignedLog();
    	signLog.setAmountAgency(60);
    	signLog.setAmountCollected(70);
    	signLog.setEmpCode("id123");
    	signLog.setWaybillNo("123");
    	signLog.setExpSignedDescription("");
    	signLog.setRecipient("张三");
    	signLog.setEmpName("王五");
    	signLog.setPdaNumber("pda123");
    	signLog.setSignedStateInfo("正常接受");
    	signLog.setSignOffTypeCode("SELF");
    	signLog.setSignedLogId("id12345");
    	signLog.setStatus(UploadStatus.NOT_UPLOAD);
    	signLog.setRecieverSignOff("已签收");
    	
    	ytoDBHelper.insertSignLog(signLog);
    	Vector<SignedLog> notUploadSignedLogs = ytoDBHelper.getUploadSignedLog();
    	assertEquals(1, notUploadSignedLogs.size());
    	
    	SignedLog signlog1 = notUploadSignedLogs.get(0);
    	
    	assertEquals(signlog1.getAmountAgency(), 60);
    	assertEquals(signlog1.getEmpCode(), "id123");    	
    	assertEquals(signlog1.getWaybillNo(), "123");    	
    	assertEquals(signlog1.getSignedState(), SignedState.SIGNED_SUCCESS);    	
    	assertEquals(signlog1.getSatisfaction(), Satisfaction.SATISFIED);    	
    	assertEquals(signlog1.getExpSignedDescription(), "");    	
    	assertEquals(signlog1.getStatus(), UploadStatus.NOT_UPLOAD);    	
    	assertEquals(signlog1.getRecipient(), "张三");    	
    	assertEquals(signlog1.getSignedLogId(), "id12345");    	
    	assertEquals(signlog1.isScan(), 0);    	
    	assertEquals(signlog1.getSignOffTypeCode(), "SELF");    	
    	assertEquals(signlog1.getRecieverSignOff(), "已签收");    	
    	assertEquals(signlog1.getPdaNumber(), "pda123");    	
    	assertEquals(signlog1.getAmountCollected(), 70);    	
    	assertEquals(signlog1.getSignedStateInfo(), "正常接受");    	
    	assertEquals(signlog1.getEmpName(), "王五");    	
    	assertEquals(signlog1.isReceiverSignOff(), 0);    	
    	assertEquals(signlog1.isPicture(), 0);    	

    }
    
}
