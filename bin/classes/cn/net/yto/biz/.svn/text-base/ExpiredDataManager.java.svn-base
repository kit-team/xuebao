package cn.net.yto.biz;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;

import cn.net.yto.application.AppContext;
import cn.net.yto.dao.DatabaseHelper;
import cn.net.yto.utils.CalendarUtil;
import cn.net.yto.vo.ExpressTraceVO;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.SignedLogVO;

public class ExpiredDataManager {

	/** 收件 */
	private Dao<ReceiveVO, String> mReceiveDao;

	/** 订单 */
	private Dao<OrderVO, String> mOrderDao;

	/** 派件 */
	private Dao<SignedLogVO, String> mSignedLogDao;

	/** 快件跟踪 */
	private Dao<ExpressTraceVO, String> mExpressTraceDa;

	private DatabaseHelper mDbHelper;

	private Calendar currentCalendar;
	
	private String empCode;

	public ExpiredDataManager(Context c) throws SQLException {
		mDbHelper = new DatabaseHelper(c);

		currentCalendar = Calendar.getInstance(Locale.CHINA);

		if (mDbHelper != null) {
			mReceiveDao = mDbHelper.getReceiveDao();
			mOrderDao = mDbHelper.getOrderDao();
			mSignedLogDao = mDbHelper.getSignedLogDao();
			mExpressTraceDa = mDbHelper.getExpressTraceDao();
			
			empCode = UserManager.getInstance().getUserVO().getEmpCode();
		}
	}

	public void deleteExpiredData(int days) {
		OrderManager om = OrderManager.getInstance();
		ReceiveManager rm = ReceiveManager.getInstance();
		SignedLogManager sm = SignedLogManager.getInstance();

		
	}

	private void deleteReceive(int days) throws SQLException {
		DeleteBuilder<ReceiveVO, String> builder = mReceiveDao.deleteBuilder();

		Calendar old = CalendarUtil.rollSomeDays(currentCalendar, -days);
		builder.where()
				.le("salesmanTime", CalendarUtil.toString(old, null))
				.and()
				.eq("empCode", empCode);
		mReceiveDao.delete(builder.prepare());
	}
	
	private void deleteSigned(int days) throws SQLException {
	/*	DeleteBuilder<SignedLogVO, String> builder = mSignedLogDao.deleteBuilder();

		Calendar old = CalendarUtil.rollSomeDays(currentCalendar, -days);
		builder.where()
				.le("downLoadTime", CalendarUtil.toString(old, null))
				.and()
				.eq("empCode", empCode);
		mSignedLogDao.delete(builder.prepare());
		*/
	}
	
	private void deleteOrder(int days) throws SQLException {
		DeleteBuilder<OrderVO, String> builder = mOrderDao.deleteBuilder();

		Calendar old = CalendarUtil.rollSomeDays(currentCalendar, -days);
		builder.where()
				.le("downLoadTime", CalendarUtil.toString(old, null))
				.and()
				.eq("empCode", empCode);
		mOrderDao.delete(builder.prepare());
	}
}
