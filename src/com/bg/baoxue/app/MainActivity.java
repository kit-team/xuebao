package com.bg.baoxue.app;

import com.bg.baoxue.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

}
