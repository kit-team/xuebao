package cn.net.yto.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.UserManager;
import cn.net.yto.common.Constants;

public class BaseActivity extends Activity implements View.OnTouchListener{
	protected static final String TAG = "YTO";
	protected TextView mDateTimeText;
	protected TextView mNetworkText;
	protected TextView mBatteryText;
	protected TextView mPhoneNameText;
	protected TextView mUserInfoText;
	protected TextView mInstructionText;
	protected Toast mToast;
	protected boolean mResume;
	protected ConnectivityManager mConnectivityManager;
	protected SharedPreferences mSharedPreferences;
	protected Vibrator mVibrator;
	protected Context mContext = this;
	protected static String mNetworkStatus;
	protected static String mBatteryStatus;
	protected AppContext mAppContext = AppContext.getAppContext();
	protected UserManager mUserService;
	private SoundPool mSoundPool;
	private int mSoundWarningId;
	private int mSoundSuccessId;
	private int mSoundQueryId;
	private AudioManager mAudioManager;
	private boolean mBaseViewsInited;
	
	protected InputMethodManager mInputMethodManager;
	protected Handler mMainHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (onHandleMessage(msg)) {
				return;
			}
			super.handleMessage(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		mUserService = mAppContext.getUserService();
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		initBaseView();
		mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		mSoundWarningId = mSoundPool.load(mContext, R.raw.warning, 1);
		mSoundSuccessId = mSoundPool.load(mContext, R.raw.success, 1);
		mSoundQueryId = mSoundPool.load(mContext, R.raw.query, 1);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_TICK);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBaseIntentReceiver, filter);
		
		super.onStart();
	}

	@Override
	protected void onResume() {
		mResume = true;
		super.onResume();
		hideInputMethod();
	}

	@Override
	protected void onPause() {
		mResume = false;
		super.onPause();
	}

	@Override
	protected void onStop() {
		mSoundPool.release();
		cancelToast();
		unregisterReceiver(mBaseIntentReceiver);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	protected void hideInputMethod() {
		mDateTimeText = new TextView(mContext);
		if (mDateTimeText != null) {
			mInputMethodManager.hideSoftInputFromWindow(
					mDateTimeText.getWindowToken(), 0);
		}
	}
	
	protected void hideInputMethod(View view) {
		if (view != null) {
			mInputMethodManager.hideSoftInputFromWindow(
					view.getWindowToken(), 0);
		}
	}

	protected boolean onHandleMessage(Message msg) {
		return false;
	}
	
	private void initBaseView() {
		if (mBaseViewsInited) {
			return;
		}


		mBaseViewsInited = true;

	}

	protected void playSound(int soundType) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_RING);
		streamVolume = streamVolume
				/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

		int soundResId = mSoundSuccessId;
		switch (soundType) {
		case Constants.SOUND_TYPE_SUCCESS:
			soundResId = mSoundSuccessId;
			break;

		case Constants.SOUND_TYPE_WARNING:
			soundResId = mSoundWarningId;
			break;

		case Constants.SOUND_TYPE_QUERY:
			soundResId = mSoundQueryId;
			break;

		default:
			break;
		}

		mSoundPool.play(soundResId, streamVolume, streamVolume, 1, 0, 1f);
	}

	protected void vibrate(int milliseconds) {
		mVibrator.vibrate(milliseconds);
	}

	protected void setTitleInfo(int titleResId) {
		setTitle(getString(titleResId));
	}

	protected void setTitleInfo(String titleInfo) {
		TextView titleText = (TextView) findViewById(R.id.title_text);
		if (titleText != null) {
			titleText.setText(titleInfo);
		}
	}

	protected void setUserInfo(String userInfo) {
		if (mUserInfoText != null) {
			mUserInfoText.setText(userInfo);
		}
	}

	protected void setUserInfo(int userInfoResId) {
		if (mUserInfoText != null) {
			mUserInfoText.setText(userInfoResId);
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
		case KeyEvent.KEYCODE_CALL:
		case KeyEvent.KEYCODE_CAMERA:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_VOLUME_UP:
		case 29: // F1
		case 47: // F2
		case 32: // F3
		case 34: // F4
		case 6: // call cancel
			hideInputMethod();
			return true;
			
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
		case KeyEvent.KEYCODE_STAR:
		case KeyEvent.KEYCODE_POUND:
			hideInputMethod();
			break;
			
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
		case KeyEvent.KEYCODE_CALL:
		case KeyEvent.KEYCODE_CAMERA:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_VOLUME_UP:
		case 29: // F1
		case 47: // F2
		case 32: // F3
		case 34: // F4
		case 6: // call cancel
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
		case KeyEvent.KEYCODE_CALL:
		case KeyEvent.KEYCODE_CAMERA:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_VOLUME_UP:
		case 29: // F1
		case 47: // F2
		case 32: // F3
		case 34: // F4
		case 6: // call cancel
			return true;
		default:
			break;
		}
		return super.onKeyLongPress(keyCode, event);
	}

	protected void showToast(int stringResId) {
		showToast(getString(stringResId));
	}

	protected void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}
	
	protected void showNetworkErrorTips(int networkResultType) {
//		if (networkResultType == HttpAsyncTask.TYPE_ERROR_404) {
//			showToast(R.string.http_404_tips);
//		} else if (networkResultType == HttpAsyncTask.TYPE_ERROR_NETWORK_DEACTIVE) {
//			showToast(R.string.http_no_active_network_tips);
//			// } else if (networkResultType == HttpAsyncTask.TYPE_ERROR_OHTER) {
//			// showToast(context, R.string.network_error);
//			// }
//		} else {
//			showToast(R.string.network_error);
//		}
	}

	protected void showToast(final String msg) {
		if (mToast != null) {
			mToast.cancel();
		}
		mMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
				mToast.show();
			}
		}, 100);
	}

	private BroadcastReceiver mBaseIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
		}
	};
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v != null && (Build.MODEL.contains("simphone") || Build.MODEL.contains("W500"))){
			v.requestFocus();
			return true;
		}
		return false;
	}
}
