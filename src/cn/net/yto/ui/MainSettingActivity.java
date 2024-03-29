package cn.net.yto.ui;

import android.app.AlertDialog;
import android.app.PendingIntent.OnFinished;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;
import cn.net.yto.R;
import cn.net.yto.common.Constants;

public class MainSettingActivity extends PreferenceActivity implements OnPreferenceChangeListener ,OnPreferenceClickListener{
	private Preference device_number;
	private Preference mDownloadPreference;
	private Preference mReturnPreference;
	private Preference mQuitPreference;
	
	private EditTextPreference mServerEditText;
	private EditTextPreference mSiteEditText;
	
	Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mContext = getApplicationContext();
        device_number = (Preference)findPreference("key_device_number");
        TelephonyManager mgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        device_number.setSummary(mgr.getDeviceId());
        
        mDownloadPreference = (Preference)findPreference("key_package_download");
        mServerEditText = (EditTextPreference)findPreference("key_server_address");
        mSiteEditText = (EditTextPreference)findPreference("key_site_number");
        mReturnPreference = (Preference)findPreference("key_return_fuction");
        mQuitPreference = (Preference)findPreference("key_quit_function");
        mInitialContext();
        mServerEditText.setOnPreferenceChangeListener(this);
        mServerEditText.setOnPreferenceClickListener(this);
        mSiteEditText.setOnPreferenceChangeListener(this);
        mSiteEditText.setOnPreferenceClickListener(this);
        mDownloadPreference.setOnPreferenceClickListener(this);
        mReturnPreference.setOnPreferenceClickListener(this);
        mQuitPreference.setOnPreferenceClickListener(this);
    }

    private void mInitialContext() {
    	SharedPreferences mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
    	String ServerUrl = mSharedPreferences.getString(Constants.KEY_PDA_SERVER_URL,
    			mContext.getString(R.string.default_server_url));
    	mServerEditText.setSummary(ServerUrl);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		System.out.println("onPreferenceChange");
		String key = preference.getKey();
		if (key.equals("key_server_address")) {
			System.out.println("key_server_address");
			mServerEditText.setSummary((String) newValue);
			mServerEditText.setDefaultValue((String) newValue);
			writeServerInfo();
			
		} else if (key.equals("key_site_number")) {
			
		}else{

		}
		return true;
	}
	private void writeServerInfo() {
		String mserver = mServerEditText.getSummary().toString().trim();
		SharedPreferences mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(Constants.KEY_PDA_SERVER_URL, mserver);
		editor.commit();
		
		String mPdaServerUrl = mSharedPreferences.getString(Constants.KEY_PDA_SERVER_URL,
				mContext.getString(R.string.default_server_url));
		Toast.makeText(MainSettingActivity.this, mPdaServerUrl, 1000).show();
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		System.out.println("onPreferenceClick");
		String key = preference.getKey() ;
		if (key.equals("key_package_download")) {
			System.out.println("key_package_download");
			
			 new AlertDialog.Builder(MainSettingActivity.this)
			 .setTitle(R.string.package_download)
			 .setMessage(R.string.package_download_intr)
            .setPositiveButton(R.string.login_ok, new DialogInterface.OnClickListener(){
            @Override                
            public void onClick(DialogInterface dialog, int which) 
            {   // TODO Auto-generated method stub                  
                             
            mDownloadPackage();
            Toast.makeText(MainSettingActivity.this, "download and install the apk", 1000).show();
            
            }
		}).setNegativeButton(R.string.login_no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
		}else if (key.equals("key_return_fuction")) {
			System.out.println("key_return_fuction");
			finish();
		}else if (key.equals("key_quit_function")) {
			setResult(Constants.EXIT_APP);
			System.out.println("退出程序,setResult==111");
			finish();

			
		}
		return false;
	}
  //download the software apk 
	protected void mDownloadPackage() {
		// TODO Auto-generated method stub
		
	}

}
