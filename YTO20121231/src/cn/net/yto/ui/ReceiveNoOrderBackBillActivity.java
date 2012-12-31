package cn.net.yto.ui;

import cn.net.yto.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ReceiveNoOrderBackBillActivity extends BaseActivity implements OnClickListener{
	private EditText mMFormNumEdit ;
	private EditText mBFromNumEdit ;
	private Button   mRecSaveBtn ;
	private Button   mRecBackBtn ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_receive_no_order_back_bill);
		
		mMFormNumEdit = (EditText)findViewById(R.id.mform_num_edt);
		mBFromNumEdit = (EditText)findViewById(R.id.bfrom_num_edt);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rec_back_btn:
			
			break;
           case R.id.rec_save_btn:
			
			break;
		default:
			break;
		}
		
	}
}
