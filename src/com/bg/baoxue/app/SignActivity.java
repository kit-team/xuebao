package com.bg.baoxue.app;

import com.bg.baoxue.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignActivity extends Activity {
	private Spinner mSignResult; 
	private Spinner mExceptionReason;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_view);
		initView();
	}
	
	private void initView() {
		mSignResult = (Spinner) findViewById(R.id.spinner_sign_result);
		ArrayAdapter<String> signResultAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.sign_result));
		signResultAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSignResult.setAdapter(signResultAdapter);
		mExceptionReason = (Spinner) findViewById(R.id.spinner_exception_reason);
		ArrayAdapter<String> exceptionReasonAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.exception_reason));
		exceptionReasonAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mExceptionReason.setAdapter(exceptionReasonAdapter);
	}

}
