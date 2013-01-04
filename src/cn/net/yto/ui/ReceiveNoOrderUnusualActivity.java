package cn.net.yto.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.RecvexpVO;

public class ReceiveNoOrderUnusualActivity extends BaseActivity {
	private EditText mEditName;
	private EditText mEditPhone;
	private EditText mEditAddress;
	private EditText mEditRemark;
	private Button mBtnSave;
	private Button mBtnBack;
	private Spinner mSpinnerExp;
	
	private OrderVO orderVO;
	private ReceiveVO receiveVO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
		initContext();
	}
	
	private void initViews(){
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_no_order_unusual_receive);
		mEditName = (EditText) findViewById(R.id.sender_name_edit);
		mEditPhone = (EditText) findViewById(R.id.sender_tel_edit);
		mEditAddress = (EditText) findViewById(R.id.sender_addr_edit);
		mEditRemark = (EditText) findViewById(R.id.remark_text);
		mSpinnerExp = (Spinner) findViewById(R.id.unusual_reason_spinner);
		List<RecvexpVO> exceptions = AppContext.getAppContext().getBasicDataManager().getRecvexpList();
		ArrayAdapter<RecvexpVO> adapter = new ArrayAdapter<RecvexpVO>(
                this, android.R.layout.simple_spinner_item,  exceptions);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerExp.setAdapter(adapter);
		mBtnSave = (Button) findViewById(R.id.save_btn);
		mBtnBack = (Button) findViewById(R.id.back_btn);
		mBtnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
			}
		});
		mBtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initContext(){
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		orderVO = getIntent().getParcelableExtra("order");
		if(orderVO != null){
			setTitleInfo(R.string.receive_order_unusaul);
			initData(orderVO);
		} else{
			setTitleInfo(R.string.receive_no_order_unusaul);			
		}
		
		
	}
	
	private void initData(OrderVO vo){
		mEditName.setText(vo.getSenderName());
		mEditPhone.setText(vo.getSenderMobile());
		mEditAddress.setText(vo.getSenderAddress());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void save(){
		if (CommonUtils.isEmpty(mEditName.getText().toString())) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_empty_tips);
			return;
		}
		updateOrderVOState();
	}
	
	private void updateOrderVOState(){
		if(orderVO!=null){
			orderVO.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED_EXCEPTION));
			OrderManager.getInstance().updateOrder(orderVO);
			OrderManager.getInstance().updateOrderState(orderVO);
		}
	}
}
