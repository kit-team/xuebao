package cn.net.yto.common.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class PowerManagerUtil {
	
	private WakeLock mWakeLock;
	private Context mContext;
	
	public PowerManagerUtil (Context context) {
		mContext = context;
	}
	
	public void acquireWakeLock() {
		if (mWakeLock == null) {
			PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
			mWakeLock.acquire();
		}
	}


	public void releaseWakeLock() {
		if (mWakeLock !=null && mWakeLock.isHeld()) {
			mWakeLock.release();
			mWakeLock =null;
		}
	}

}
