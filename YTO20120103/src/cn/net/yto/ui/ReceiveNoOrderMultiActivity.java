package cn.net.yto.ui;

import cn.net.yto.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiveNoOrderMultiActivity extends BaseActivity {
	private EditText mMFormNumEdit ;
	private EditText mOFormNumSEdit ;
	private EditText mOFormNumEdit ;
	private TextView mTotalNumTextv ;
	private Button   mDelFromBtn ;
	private Button   mRecSaveBtn ;
	private Button   mRecBackBtn ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_receive_no_order_multi);
		
	}
}
