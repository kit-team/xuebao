package cn.net.yto.models;

import java.text.SimpleDateFormat;
import java.util.Vector;

import cn.net.yto.models.SignedLog.SignedState;
import cn.net.yto.models.SignedLog.UploadStatus;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard.Row;

public class YtoDBHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "ytoDatabbase.db";
	private static final int DATABASE_VERSION = 1;
    private Object mutex = new Object();
    
    public static final String SIGNEDLOG_TABLE                = "SignedLog";
    
    public static final String C_EMPCODE                      = "empCode";
    public static final String C_WAYBILLNO                    = "waybillNo";
    public static final String C_SIGNED_TIME                  = "signedTime";
    public static final String C_PICTURE_DATA                 = "pictureData";
    public static final String C_SIGNED_STATE                 = "signedState";
    public static final String C_STATISFACION                 = "satisfaction";
    public static final String C_EXPSIGNED_DESCRIPTION        = "expSignedDescription";
    public static final String C_AMOUNT_COLLECTED             = "amountCollected";
    public static final String C_AMOUNT_AGENCY                = "amountAgency";
    public static final String C_STATUS                  	  = "status";
    public static final String C_RECIPIENT                	  = "recipient";    
    public static final String C_IS_SCAN                	  = "is_scan";    
    public static final String C_SIGNOFF_TYPE_CODE            = "signOffTypeCode";    
    public static final String C_RECEIVER_SIGNOFF             = "receiverSignOff";    
    public static final String C_PAD_NUMBER                	  = "pdaNumber";    
    public static final String C_SIGNEDSTATE_INFO             = "signedStateInfo";    
    public static final String C_EMP_NAME			          = "empName";    
    public static final String C_IS_RECEIVER_SIGNOFF          = "isReceiverSignOff";    
    public static final String C_IS_PICTURE             	  = "isPicture";    
    public static final String C_SINGEDLOG_ID             	  = "signedlog_id";    
    
    
	public YtoDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public static final String CREATE_TABLE_SIGNLOG = "create table if not exists SignedLog ( empCode text, waybillNo text, signedTime date, pictureData blob, signedState long, satisfaction long, " +
	    " expSignedDescription text, amountCollected long, amountAgency long, status long, recipient text, is_scan long, signOffTypeCode text, receiverSignOff text, " +
	    " pdaNumber text, signedStateInfo text, empName text, isReceiverSignOff long, isPicture long, signedlog_id text, PRIMARY KEY (empCode, waybillNo))";
	   ;

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SIGNLOG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrade the existing database to conform to the new version. Multiple
		// previous versions can be handled by comparing oldVersion and newVersion
		// values
		
		// The simplest case is to drop the old table and create a new one.
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_NAME);
		// Create a new one.
		onCreate(db);
	}
	
	public YtoDBHelper(Context context) {
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
    private void insertOrIgnore(ContentValues values, String tableName)
    {
        synchronized (mutex) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
        }
    }
	
	public void insertSignLog(SignedLog signLog) {
		synchronized (mutex) {
			insertOrIgnore(signLog.toContentValues(), SIGNEDLOG_TABLE);
		}
	}
	
	public Vector<SignedLog> getUploadSignedLog() {
		synchronized (mutex) {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = null;			
			Vector<SignedLog> ret = new Vector<SignedLog>();
			try {
				c = db.query(SIGNEDLOG_TABLE, null, C_STATUS+"=?", new String[] {String.valueOf(UploadStatus.NOT_UPLOAD.getUploadStatus())}, null, null, C_SIGNED_TIME);
				for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
					SignedLog signedLog = new SignedLog();
					signedLog.setEmpCode(c.getString(0));
					signedLog.setWaybillNo(c.getString(1));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateString = c.getString(2);
					signedLog.setSignedTime(dateFormat.parse(dateString));
					signedLog.setPictureData(c.getBlob(3));
					signedLog.setSignedState(SignedLog.GetSignedState(c.getInt(4)));
					signedLog.setSatisfaction(SignedLog.GetSatisfaction(c.getInt(5)));
					signedLog.setExpSignedDescription(c.getString(6));
					signedLog.setAmountCollected(c.getLong(7));
					signedLog.setAmountAgency(c.getLong(8));
					signedLog.setStatus(SignedLog.GetUploadStatus(c.getInt(9)));
					signedLog.setRecipient(c.getString(10));
					signedLog.setIsScan(c.getLong(11));
					signedLog.setSignOffTypeCode(c.getString(12));
					signedLog.setRecieverSignOff(c.getString(13));
					signedLog.setPdaNumber(c.getString(14));
					signedLog.setSignedStateInfo(c.getString(15));
					signedLog.setEmpName(c.getString(16));
					signedLog.setIsReceiverSignOff(c.getLong(17));
					signedLog.setIsPicture(c.getLong(18));
					signedLog.setSignedLogId(c.getString(19));

					ret.add(signedLog);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
				db.close();
			}
			return ret;
		}
	}
	
	public boolean updateStatusOfSignedLog(SignedLog log) {
		synchronized (mutex) {
			SQLiteDatabase db = this.getWritableDatabase();
			long rows = 0;
			try {
				ContentValues values = new ContentValues();
				values.put(C_STATUS, log.getStatus().getUploadStatus());
				rows = db.update(SIGNEDLOG_TABLE, values, C_EMPCODE+"=? and " + C_WAYBILLNO+"=?", new String[] {log.getEmpCode(), log.getWaybillNo()});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
			return rows == 1 ? true : false;
		}
	} 
}
