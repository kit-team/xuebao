package cn.net.yto.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import cn.net.yto.R;

public class MainSettingActivity extends PreferenceActivity {
	private Preference device_number;
	private EditTextPreference mPortEditText;
	Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mContext = getApplicationContext();
        device_number = (Preference)findPreference("key_device_number");
        TelephonyManager mgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        device_number.setSummary(mgr.getDeviceId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
