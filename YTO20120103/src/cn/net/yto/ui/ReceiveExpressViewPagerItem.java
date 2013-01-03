package cn.net.yto.ui;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.ReceiveVO;

public class ReceiveExpressViewPagerItem extends ViewPageItemAbs {

	private View mRootView;

	private EditText mWayBillEdit;
	private EditText mDesCitySearchEdit;
	private Spinner mCitySpinner;
	private EditText mReceiveAddressEdit;
	private EditText mReceiveClientEdit;
	private EditText mReceiveCallEdit;
	private EditText mPriceEdit;
	private EditText mVolumeEdit;
	private EditText mWeightEdit;
	private TextView mOrderNoText;
	private Spinner mEffectiveSpinner;
	private Button mBackBtn;
	private Button mSaveBtn;

	private BasicDataManager mBasicDataManager;
	private ReceiveManager mReceiveManager;
	private AppContext mAppContext;
	private List<CityVO> mCityList;
	private List<EffectiveTypeVO> mEffectiveTypeList;

	public ReceiveExpressViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);

		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_no_order_receive, null);

		initContext();
		initViews();
	}

	@Override
	public View getItemView() {

		return mRootView;
	}

	@Override
	public boolean onScanned(String barcode) {
		if (CommonUtils.isEmpty(barcode)) {
			return false;
		}
		mWayBillEdit.setText(barcode);
		return true;
	}

	@Override
	public void onResume() {
		initViewByReceiveInfo();
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onPageSelected() {
		initViewByReceiveInfo();
		super.onPageSelected();
	}

	private void initContext() {
		mAppContext = mAppContext.getAppContext();
		mBasicDataManager = mAppContext.getBasicDataManager();
		mReceiveManager = mAppContext.getReceiveManager();
		mCityList = mBasicDataManager.getCityList();
		mEffectiveTypeList = mBasicDataManager.getEffectiveTypeList();
	}

	private void initViews() {

		mOrderNoText = (TextView) mRootView.findViewById(R.id.order_no_text);
		mWayBillEdit = (EditText) mRootView.findViewById(R.id.way_bill_no_edit);
		mDesCitySearchEdit = (EditText) mRootView
				.findViewById(R.id.des_city_edit);
		mCitySpinner = (Spinner) mRootView.findViewById(R.id.des_city_spinner);
		ArrayAdapter<CityVO> adapter = new ArrayAdapter<CityVO>(mContext,
				android.R.layout.simple_spinner_item, mCityList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCitySpinner.setAdapter(adapter);

		mCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// MyLog.i(TAG, "onItemSelected position = " + position +
				// " id = "
				// + id);
				// changeNetworkType(position);
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mReceiveAddressEdit = (EditText) mRootView
				.findViewById(R.id.receive_address_edit);
		mReceiveClientEdit = (EditText) mRootView
				.findViewById(R.id.receive_client_edit);
		mReceiveCallEdit = (EditText) mRootView
				.findViewById(R.id.receive_call_edit);
		mPriceEdit = (EditText) mRootView.findViewById(R.id.price_edit);
		mVolumeEdit = (EditText) mRootView.findViewById(R.id.volume_edit);
		mWeightEdit = (EditText) mRootView.findViewById(R.id.weight_edit);
		mEffectiveSpinner = (Spinner) mRootView
				.findViewById(R.id.effective_spinner);

		ArrayAdapter<EffectiveTypeVO> effectiveAdapter = new ArrayAdapter<EffectiveTypeVO>(
				mContext, android.R.layout.simple_spinner_item,
				mBasicDataManager.getEffectiveTypeList());
		effectiveAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mEffectiveSpinner.setAdapter(effectiveAdapter);

		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackBtnClick();
			}
		});
		mSaveBtn = (Button) mRootView.findViewById(R.id.save_btn);
		mSaveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
			}
		});
	}

	private void viewClear() {
		mOrderNoText.setText("");
		mRootView.findViewById(R.id.order_no_layout).setVisibility(View.GONE);
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

	private void initViewByReceiveInfo() {
		viewClear();

		ReceiveVO vo = mReceiveManager.getCurNomalReceiveVO();
		if (!CommonUtils.isEmpty(vo.getOrderNo())) {
			mOrderNoText.setText(vo.getOrderNo());
			mRootView.findViewById(R.id.order_no_layout).setVisibility(View.VISIBLE);
		}

		if (!CommonUtils.isEmpty(vo.getCityId())) {
			CityVO city = mBasicDataManager.getCityById(vo.getCityId());

			if (city != null && city.getCityName() != null) {
				mDesCitySearchEdit.setText(city.getCityName());
			}
		}

		if (!CommonUtils.isEmpty(vo.getCityId())) {
			mDesCitySearchEdit.setText(vo.getCityId());
		}

		if (!CommonUtils.isEmpty(vo.getDestAddress())) {
			mReceiveAddressEdit.setText(vo.getDestAddress());
		}

		if (!CommonUtils.isEmpty(vo.getReceiverName())) {
			mReceiveClientEdit.setText(vo.getReceiverName());
		}

		if (!CommonUtils.isEmpty(vo.getReceiverPhone())) {
			mReceiveCallEdit.setText(vo.getReceiverPhone());
		}

		if (!CommonUtils.isEmpty(vo.getFeeAmt())) {
			mPriceEdit.setText(vo.getFeeAmt());
		}

		if (!CommonUtils.isEmpty(vo.getWeighWeight())) {
			mWeightEdit.setText(vo.getWeighWeight());
		}

		if (!CommonUtils.isEmpty(vo.getGoodsSize())) {
			mVolumeEdit.setText(vo.getGoodsSize());
		}
		
		if (!CommonUtils.isEmpty(vo.getPracticalType())) {
			int hour72Index = 0;
			int i= 0;
			for(; i < mEffectiveTypeList.size(); i++){
				String tempCode = mEffectiveTypeList.get(i).getCode();
				if(!CommonUtils.isEmpty(tempCode) && tempCode.equals(vo.getPracticalType())){
					mEffectiveSpinner.setSelection(i);
					break;
				}
				
				if("C005".equals(tempCode)){
					hour72Index = i;
				}
			}
			
			if(i == mEffectiveTypeList.size()){
				mEffectiveSpinner.setSelection(hour72Index);
			}
		}
		
	}

	protected void onBackBtnClick() {
		mContext.finish();
	}

	protected void save() {
		if (CommonUtils.isEmpty(mWayBillEdit.getText().toString())) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_empty_tips);
			return;
		}
		ReceiveVO receiveVo = mReceiveManager.getCurNomalReceiveVO();
		if (receiveVo == null) {
			receiveVo = new ReceiveVO();
		}

		receiveVo.setWaybillNo(mWayBillEdit.getText().toString());
		receiveVo.setCityId(mDesCitySearchEdit.getText().toString());
		receiveVo.setDestAddress(mReceiveAddressEdit.getText().toString());
		receiveVo.setReceiverName(mReceiveClientEdit.getText().toString());
		receiveVo.setReceiverPhone(mReceiveCallEdit.getText().toString());
		receiveVo.setFeeAmt(mPriceEdit.getText().toString());
		receiveVo.setGoodsSize(mVolumeEdit.getText().toString());
		receiveVo.setWeighWeight(mWeightEdit.getText().toString());
		
		receiveVo.setPracticalType(mEffectiveTypeList.get((int) mEffectiveSpinner.getSelectedItemId()).getCode());

		mReceiveManager.saveReceive(receiveVo);
		mReceiveManager.upload(receiveVo, mContext);

		viewClear();
		mReceiveManager.clearCurNomalReceiveVO();
	}
}
