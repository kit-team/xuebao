package cn.net.yto.ui;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.ReceiveVO;

public class ReceiveNoOrderNomalActivity extends BaseActivity {
	private EditText mOrderNoEdit;
	private EditText mWayBillEdit;
	private EditText mDesCitySearchEdit;
	private Spinner  mCitySpinner;
	private EditText mReceiveAddressEdit;
	private EditText mReceiveClientEdit;
	private EditText mReceiveCallEdit;
	private EditText mPriceEdit;
	private EditText mVolumeEdit;
	private EditText mWeightEdit;
	private Spinner mEffectiveSpinner;
	private Button mBackBtn;
	private Button mSaveBtn;
	
	private BasicDataManager mBasicDataManager;
	private ReceiveManager mReceiveManager;
	private AppContext mAppContext;
	private ReceiveVO mReceiveVO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initContext();
		initViews();
	}

	private void initContext() {
		mAppContext = mAppContext.getAppContext();
		mBasicDataManager = mAppContext.getBasicDataManager();
		mReceiveManager = mAppContext.getReceiveManager();
	}

	private void initViews() {
		setContentView(R.layout.activity_no_order_receive);
		
		mOrderNoEdit = (EditText) findViewById(R.id.order_no_edit);
		mWayBillEdit = (EditText) findViewById(R.id.way_bill_no_edit);
		mDesCitySearchEdit = (EditText) findViewById(R.id.des_city_edit);
		mCitySpinner = (Spinner) findViewById(R.id.des_city_spinner);
		ArrayAdapter<CityVO> adapter = new ArrayAdapter<CityVO>(this,
				android.R.layout.simple_spinner_item,
				mBasicDataManager.getCityList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCitySpinner.setAdapter(adapter);
		
		mCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
//				MyLog.i(TAG, "onItemSelected position = " + position + " id = "
//						+ id);
//				changeNetworkType(position);
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		mCitySpinner.setAdapter(adapter);
		
		mReceiveAddressEdit = (EditText) findViewById(R.id.receive_address_edit);
		mReceiveClientEdit = (EditText) findViewById(R.id.receive_client_edit);
		mReceiveCallEdit = (EditText) findViewById(R.id.receive_call_edit);
		mPriceEdit = (EditText) findViewById(R.id.price_edit);
		mVolumeEdit = (EditText) findViewById(R.id.volume_edit);
		mWeightEdit = (EditText) findViewById(R.id.weight_edit);
		mEffectiveSpinner = (Spinner) findViewById(R.id.effective_spinner);
		
		ArrayAdapter<EffectiveTypeVO> effectiveAdapter = new ArrayAdapter<EffectiveTypeVO>(this,
				android.R.layout.simple_spinner_item,
				mBasicDataManager.getEffectiveTypeList());
		effectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mEffectiveSpinner.setAdapter(effectiveAdapter);
		
		mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackBtnClick();
			}
		});
		mSaveBtn = (Button) findViewById(R.id.save_btn);
		mSaveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
			}
		});
	}
	
	private void viewClear(){
		mOrderNoEdit.setText("");
		mWayBillEdit.setText("");
		mDesCitySearchEdit.setText("");
		mCitySpinner.setSelection(0);
		mReceiveAddressEdit.setText("");
		mReceiveClientEdit.setText("");
		mReceiveCallEdit.setText("");
		mPriceEdit.setText("");
		mVolumeEdit.setText("");
		mWeightEdit.setText("");
		mEffectiveSpinner.setSelection(0);
	}

	protected void onBackBtnClick() {
		finish();
	}

	protected void save() {
		if(CommonUtils.isEmpty(mWayBillEdit.getText().toString())){
			DialogHelper.showToast(mContext, R.string.way_bill_no_empty_tips);
			return;
		}
		
		if(mReceiveVO == null){
			mReceiveVO = new ReceiveVO();
		}
		
		mReceiveVO.setWaybillNo(mWayBillEdit.getText().toString());
		mReceiveVO.setCityId(mDesCitySearchEdit.getText().toString());
		mReceiveVO.setDestAddress(mReceiveAddressEdit.getText().toString());
		mReceiveVO.setReceiverName(mReceiveClientEdit.getText().toString());
		mReceiveVO.setReceiverPhone(mReceiveCallEdit.getText().toString());
		mReceiveVO.setFeeAmt(mPriceEdit.getText().toString());
		mReceiveVO.setGoodsSize(mVolumeEdit.getText().toString());
		mReceiveVO.setWeighWeight(mWeightEdit.getText().toString());
		mReceiveVO.setPracticalType("C005");
		
		mReceiveManager.saveReceive(mReceiveVO);
		mReceiveManager.upload(mReceiveVO, mContext);
	}
}
