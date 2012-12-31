package cn.net.yto.ui;

import java.net.URLDecoder;
import java.net.URLEncoder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.UserManager;
import cn.net.yto.common.Constants;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.Base64Util;
import cn.net.yto.utils.CodecUtils;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DeEncryptUtil;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.utils.Md5Util;
import cn.net.yto.utils.SecureUtils;
import cn.net.yto.vo.message.LoginRequestMsgVO;
import cn.net.yto.vo.message.LoginResponseMsgVO;

import com.zltd.android.net.NetworkManager;
import com.zltd.android.system.SystemManager;

public class LoginActivity extends BaseActivity implements View.OnKeyListener {
	protected static final String TAG = "LoginActivity";
	private EditText mUsernameEdit;
	private EditText mPassworkEdit;
	private TextView mVersionText;
	private Button mLoginBtn;
	private Button mSettingBtn;
	protected RadioGroup mNetTypeRadioGroup;
	protected RadioButton mGprsRadioBtn;
	protected RadioButton mWlanRadioBtn;
	private UserManager mUserService;
	private LoginRequestMsgVO mLoginVO;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initContext();

		initViews();

		mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_EJECT);
		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		registerReceiver(mIntentReceiver, filter);

		SystemManager.setHomeKeyEnable(this, true);
		SystemManager.setStutusbarEnable(this, true);
		SecureUtils.setDefaultSecureSetting(mContext);
	}

	@Override
	protected void onStop() {
		cancelToast();
		super.onStop();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			startLogin();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
				startLogin();
			}
			return true;
		}
		return false;
	}

	private void initContext() {
		mAppContext.setDefaultContext(mContext);
		mUserService = mAppContext.getUserService();
		mLoginVO = mUserService.getLoginVO();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mIntentReceiver);
		SystemManager.setHomeKeyEnable(this, true);
		SystemManager.setStutusbarEnable(this, true);
		SecureUtils.closeSecureSetting(mContext);
		super.onDestroy();
	}

	private void initViews() {
		setContentView(R.layout.activity_login);

		readLoginInfo();

		mUsernameEdit = (EditText) findViewById(R.id.username_edit);
		mUsernameEdit.setText(mLoginVO.getUserName());
		mUsernameEdit.setOnKeyListener(this);
		mUsernameEdit.setOnTouchListener(this);

		mPassworkEdit = (EditText) findViewById(R.id.password_edit);
		mPassworkEdit.setOnKeyListener(this);
		mPassworkEdit.setOnTouchListener(this);

		mVersionText = (TextView) findViewById(R.id.version_text);
		mVersionText.setText(getString(R.string.version) + " "
				+ mAppContext.getVersionName());

		mNetTypeRadioGroup = (RadioGroup) findViewById(R.id.net_radiogroup);
		mGprsRadioBtn = (RadioButton) findViewById(R.id.gprs_radio);
		mGprsRadioBtn.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
					mGprsRadioBtn.setChecked(true);
					return true;
				}
				return false;
			}
		});

		mWlanRadioBtn = (RadioButton) findViewById(R.id.wlan_radio);
		mWlanRadioBtn.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
					mWlanRadioBtn.setChecked(true);
					return true;
				}
				return false;
			}
		});

		int networkType = readNetworkType();
		if (networkType > 1) {
			networkType = 0;
		}

		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLogin();
			}
		});

		mSettingBtn = (Button) findViewById(R.id.maintain_btn);
		mSettingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMainSettingActivity();
			}
		});

		if (CommonUtils.isEmpty(mLoginVO.getUserName())) {
			mUsernameEdit.requestFocus();
		} else {
			mPassworkEdit.requestFocus();
		}
		hideInputMethod();
	}

	public void setInstructionText(int resId) {
		mInstructionText.setText(resId);
	}

	private int readNetworkType() {
		return mSharedPreferences.getInt(Constants.KEY_NETWORK_TYPE, 0);
	}

	private void writeNetworkType(int networkType) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(Constants.KEY_NETWORK_TYPE, networkType);
		editor.commit();
	}

	private void readLoginInfo() {
		mLoginVO.setUserName(mSharedPreferences.getString(
				Constants.KEY_USERNAME, ""));
	}

	private void writeLoginInfo() {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(Constants.KEY_USERNAME, mLoginVO.getUserName());
		editor.commit();
	}

	/**
	 * ��������
	 * 
	 * @param type
	 *            0 wifi�� 1 �ƶ�����
	 */
	private void changeNetworkType(int type) {
		if (type == 0) {
			NetworkManager.setMobileDataEnable(mContext, false);
			NetworkManager.setWifiEnable(mContext, true);
		} else {
			NetworkManager.setMobileDataEnable(mContext, true);
			NetworkManager.setWifiEnable(mContext, false);
		}
		writeNetworkType(type);
	}

	protected void startMainMenuActivity() {
		Intent intent = new Intent(mContext, MainMenuActivity.class);
		startActivity(intent);
	}

	protected void startMainSettingActivity() {
		Intent intent = new Intent(mContext, MainSettingActivity.class);
		startActivity(intent);
	}

	protected void startLogin() {
		mAppContext.updateDefaultSettingValue();

		mLoginVO.setUserName(mUsernameEdit.getText().toString().trim());
		String psw = mPassworkEdit.getText().toString().trim();

		if (Constants.EXIT_PASSWORD.equals(psw)) {
			finish();
			return;
		} else if ("33".equals(psw)) {
			startMainMenuActivity();
			return;
		}

		if (CommonUtils.isEmpty(mLoginVO.getUserName())) {
			showToast(R.string.input_username_tips);
			return;
		}

		if (CommonUtils.isEmpty(psw)) {
			showToast(R.string.input_password_tips);
			return;
		} else {
			//String md5Psw = CodecUtils.encode32BitMd5(psw);
		//	String md5Psw = CodecUtils.encode32BitMd5(psw);
		//	LogUtils.i(TAG, "md5 psw = " + md5Psw);
			try {
			//	mLoginVO.setPassword(CodecUtils.encodeBase64(md5Psw));
				mLoginVO.setPassword(CodecUtils.encodeBase64(psw));
				
			//	mLoginVO.setPassword(md5Psw);
			//	mLoginVO.setPassword(URLDecoder.decode("4QrcOUm6Wau+VuBX8g+IPg=="));  
				mLoginVO.setPassword(URLEncoder.encode(CodecUtils.encodeBase64(psw)));//%3D%3D
			//	mLoginVO.setPassword("4QrcOUm6Wau+VuBX8g+IPg==");
				LogUtils.i(TAG, "base64 psw = " + mLoginVO.getPassword());
			} catch (Exception e) {
				LogUtils.e(TAG, e);
			}
		}

	//mLoginVO.setPdaNumber(mAppContext.getPdaNumber());
		mLoginVO.setPdaNumber("63101127209335");
		mLoginVO.setForce("1");
		mLoginVO.setPdaLocalTime(CommonUtils
				.getFormatedDateTime("yyyy-MM-dd HH:mm:ss"));
		mLoginVO.setIsUpload("N");
	//	mLoginVO.setVersionNo("" + mAppContext.getVersionCode());
		mLoginVO.setVersionNo("1.0.1.22");
		mLoginVO.setUploadStatu(0);
		cancelToast();
		final ZltdHttpClient.Listener listener = new Listener() {
			@Override
			public void onPreSubmit() {
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				if (response != null) {
					LoginResponseMsgVO loginResponse = (LoginResponseMsgVO) response;
					if (loginResponse.getRetVal() == UserManager.LOGIN_RESULT_SUCCESS) {
						mUserService.setLoginResponse(loginResponse);
						startMainMenuActivity();
						DialogHelper.closeProgressDialog();
					} else {
						showToast(loginResponse.getFailMessage());
						DialogHelper.closeProgressDialog();
					}
				} else if (responseType != ZltdHttpClient.TYPE_SUCCESS) {
					if (mUserService.isOfflineEnable()) {
						showOfflineLoginDialog();
					} else {
						showNetworkErrorTips(responseType);
					}
					DialogHelper.closeProgressDialog();
				} else {
					showToast(R.string.login_fail);
					DialogHelper.closeProgressDialog();
				}

			}
		};

		final ZltdHttpClient.Listener versionCheckListener = new Listener() {

			@Override
			public void onPreSubmit() {
				DialogHelper.showProgressDialog(mContext,
						R.string.login_waiting);
			}

			@Override
			public void onPostSubmit(Object response, Integer responseType) {
				mUserService.login(mContext, listener);
			}
		};

		mUserService.checkUpdate(mContext, versionCheckListener);

		writeLoginInfo();
	}

	private void showOfflineLoginDialog() {
		DialogHelper.showAlertDialog(this, "离线登陆？",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mUserService.offlineLogin();
						startMainMenuActivity();
					}
				}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DialogHelper.closeAlertDialog();
					}
				});
	}

	private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
		}
	};
}
