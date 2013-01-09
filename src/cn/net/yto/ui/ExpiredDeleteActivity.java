package cn.net.yto.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import cn.net.yto.R;

public class ExpiredDeleteActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_expired_delete);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		setTitleInfo(R.string.my_assitant);
				
	}
	
	public void delete(View v) {
		Spinner days = (Spinner) findViewById(R.id.spinner);
		Object o = days.getSelectedItem();
		o.toString();
	}
	
	public void back(View v) {
		finish();
	}
}
