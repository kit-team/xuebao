package cn.net.yto.test;

import android.test.ApplicationTestCase;
import cn.net.yto.application.AppContext;
import cn.net.yto.vo.ExpressTraceVO;

public class ExpressTraceManagerTest extends ApplicationTestCase<AppContext>{
	
	public ExpressTraceManagerTest(Class<AppContext> applicationClass) {
		super(applicationClass);
	}
	
	public ExpressTraceManagerTest() {
		super(AppContext.class);
	}
	
	private AppContext app;

	@Override
	protected void setUp() throws Exception {
		createApplication();
		app = getApplication();
		super.setUp();
	}
	
	public void test_insert_expresstrace() {
		ExpressTraceVO expressTraceVO = new ExpressTraceVO();
		expressTraceVO.setWaybillNo("waybillNo111");
		expressTraceVO.setOpName("opName11");
		expressTraceVO.setOpTime("opTime11");
		expressTraceVO.setOrgCode("orgCode11");
		expressTraceVO.setOrgName("orgName11");
		expressTraceVO.setRevName("revName11");
		
    	app.getExpressTraceManager().saveExpressTrace(expressTraceVO);
    	
    	ExpressTraceVO newExpressTraceVO = app.getExpressTraceManager().queryExpressTrace(expressTraceVO.getWaybillNo());
    	
    	assertEquals(newExpressTraceVO.getWaybillNo(), "waybillNo111");
    	assertEquals(newExpressTraceVO.getOpName(), "opName11");
    	assertEquals(newExpressTraceVO.getOpTime(), "opTime11");
    	assertEquals(newExpressTraceVO.getOrgCode(), "orgCode11");
    	assertEquals(newExpressTraceVO.getOrgName(), "orgName11");
    	assertEquals(newExpressTraceVO.getRevName(), "revName11");
    	
    	expressTraceVO.setWaybillNo("waybillNo222");
    	app.getExpressTraceManager().saveExpressTrace(expressTraceVO);
    	
    	assertEquals(2, app.getExpressTraceManager().queryAllExpressTrace().size());
	}
}
