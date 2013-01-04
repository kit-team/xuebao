package cn.net.yto.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.net.yto.R;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
	}
	
	public void onLaunchDispatchOrderList(View view) {
		Intent intent = new Intent(this, DispatchOrderList.class);
		startActivity(intent);
	}
	
	public void onLaunchSign(View view) {
		Intent intent = new Intent(this, SignScanActivity.class);
		startActivity(intent);
	}

	public void onLaunchEmergencyDelivery(View view) {
		Intent intent = new Intent(this, EmergencyDeliveryListActivity.class);
		startActivity(intent);
	}

}