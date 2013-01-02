package cn.net.yto.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.net.yto.application.AppContext;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.Satisfaction;
import cn.net.yto.vo.SignedLogVO.SignedState;
import cn.net.yto.vo.SignedLogVO.UploadStatus;
import android.test.ApplicationTestCase;

public class SignedLogManagerTest extends ApplicationTestCase<AppContext>{

	public SignedLogManagerTest(Class<AppContext> applicationClass) {
		super(applicationClass);
	}
	
	public SignedLogManagerTest() {
		super(AppContext.class);
	}
	
	private AppContext app;

	@Override
	protected void setUp() throws Exception {
		createApplication();
		app = getApplication();
		super.setUp();
	}
	
	public void test_insert_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setAmountAgency(60);
		signedLogVO1.setAmountCollected(70);
		signedLogVO1.setEmpCode("id123");
		signedLogVO1.setWaybillNo("123");
		signedLogVO1.setExpSignedDescription("");
		signedLogVO1.setRecipient("张三");
		signedLogVO1.setEmpName("王五");
		signedLogVO1.setPdaNumber("pda123");
    	signedLogVO1.setSignedStateInfo("正常接受");
    	signedLogVO1.setSignOffTypeCode("SELF");
    	signedLogVO1.setSignedLogId("id12345");
    	signedLogVO1.setRecieverSignOff("已签收");
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);
    	
    	List<SignedLogVO> notUploadSignedLogs = app.getSignedLogManager().queryAllSignedLog(); 
    	assertEquals(1, notUploadSignedLogs.size());
    	
    	SignedLogVO signlog1 = notUploadSignedLogs.get(0);
    	
    	assertEquals(signlog1.getAmountAgency(), 60);
    	assertEquals(signlog1.getEmpCode(), "id123");    	
    	assertEquals(signlog1.getWaybillNo(), "123");    	
    	assertEquals(signlog1.getSignedState(), SignedState.SIGNED_SUCCESS);    	
    	assertEquals(signlog1.getSatisfaction(), Satisfaction.SATISFIED);    	
    	assertEquals(signlog1.getExpSignedDescription(), "");    	
    	assertEquals(signlog1.getUploadStatus(), UploadStatus.NOT_UPLOAD);    	
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
	
	public void test_query_subwaybill_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("a123");
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);

		SignedLogVO signedLogVO2 = new SignedLogVO();
		signedLogVO2.setWaybillNo("b213");
    	app.getSignedLogManager().saveSignedLog(signedLogVO2);

		SignedLogVO signedLogVO3 = new SignedLogVO();
		signedLogVO3.setWaybillNo("c344");
    	app.getSignedLogManager().saveSignedLog(signedLogVO3);

		SignedLogVO signedLogVO4 = new SignedLogVO();
		signedLogVO4.setWaybillNo("d666");
    	app.getSignedLogManager().saveSignedLog(signedLogVO4);
		
    	List<SignedLogVO> notUploadSignedLogs = app.getSignedLogManager().queryByWaybillno("a%"); 
    	assertEquals(1, notUploadSignedLogs.size());
    	assertEquals(notUploadSignedLogs.get(0).getWaybillNo(), "a123");    	
	}
	
	public void test_delete_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("a123");
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);

		assertEquals(1, app.getSignedLogManager().removeSignedLog(signedLogVO1));
	}
	
	public void test_update_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("x123");
		signedLogVO1.setSignedLogId("123");
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);
		signedLogVO1.setSignedLogId("124");
		app.getSignedLogManager().saveSignedLog(signedLogVO1);
		
    	List<SignedLogVO> notUploadSignedLogs = app.getSignedLogManager().queryByWaybillno("x%"); 
    	assertEquals(notUploadSignedLogs.get(0).getSignedLogId(), "124");    	
	}

	public void test_query_signedlog_bytime() {
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, 2006);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		Date date1=cal.getTime();
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("ff123");
		signedLogVO1.setSignedTime(date1);
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);
    	
		cal.set(Calendar.YEAR, 2006);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		Date date2=cal.getTime();
		SignedLogVO signedLogVO2 = new SignedLogVO();
		signedLogVO2.setWaybillNo("cc123");
		signedLogVO2.setSignedTime(date2);    	
		app.getSignedLogManager().saveSignedLog(signedLogVO2);
		
		cal.set(Calendar.YEAR, 2006);
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		Date fromDate=cal.getTime();
		
		cal.set(Calendar.YEAR, 2006);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_MONTH, 30);
		Date toDate=cal.getTime();
		List<SignedLogVO> signedLogs = app.getSignedLogManager().querySignedLogByTime(fromDate, toDate);
    	assertEquals(signedLogs.get(0).getWaybillNo(), "ff123");    	
	}

	public void test_query_need_update_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("777");
		signedLogVO1.setUploadStatus(UploadStatus.NEED_UPDATE);
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);

		SignedLogVO signedLogVO2 = new SignedLogVO();
		signedLogVO2.setWaybillNo("888");
		signedLogVO2.setUploadStatus(UploadStatus.NEED_UPDATE);
    	app.getSignedLogManager().saveSignedLog(signedLogVO2);

		SignedLogVO signedLogVO3 = new SignedLogVO();
		signedLogVO3.setWaybillNo("999");
		signedLogVO3.setUploadStatus(UploadStatus.NEED_UPDATE);
    	app.getSignedLogManager().saveSignedLog(signedLogVO3);

		SignedLogVO signedLogVO4 = new SignedLogVO();
		signedLogVO4.setWaybillNo("666");
    	app.getSignedLogManager().saveSignedLog(signedLogVO4);
  	
    	assertEquals(3, app.getSignedLogManager().queryNeedUpdateSignedLog().size());
	}
	
	public void test_query_need_upload_signedlog() {
		SignedLogVO signedLogVO1 = new SignedLogVO();
		signedLogVO1.setWaybillNo("7777");
    	app.getSignedLogManager().saveSignedLog(signedLogVO1);

		SignedLogVO signedLogVO2 = new SignedLogVO();
		signedLogVO2.setWaybillNo("8888");
		signedLogVO2.setUploadStatus(UploadStatus.UPLOAD_FAILURE);
    	app.getSignedLogManager().saveSignedLog(signedLogVO2);

		SignedLogVO signedLogVO3 = new SignedLogVO();
		signedLogVO3.setWaybillNo("9999");
		signedLogVO3.setUploadStatus(UploadStatus.NEED_UPDATE);
    	app.getSignedLogManager().saveSignedLog(signedLogVO3);

		SignedLogVO signedLogVO4 = new SignedLogVO();
		signedLogVO4.setWaybillNo("6666");
		signedLogVO4.setUploadStatus(UploadStatus.UPLOAD_SUCCESS);
    	app.getSignedLogManager().saveSignedLog(signedLogVO4);
    	
    	assertEquals(2, app.getSignedLogManager().queryNeedUploadSignedLog().size());
	}
	
}
