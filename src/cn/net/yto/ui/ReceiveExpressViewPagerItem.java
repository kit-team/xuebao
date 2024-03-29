package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.BasicDataManager.CitySearch;
import cn.net.yto.biz.BarcodeManager;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.ReceiveVO;

public class ReceiveExpressViewPagerItem extends ViewPageItemAbs {

	private View mRootView;

	private EditText mWayBillEdit;
	private EditText mDesCitySearchEdit;
	private Spinner mCitySpinner;
	private ArrayAdapter<CityVO> mCitySpinnerAdapter;
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
	private boolean mCitySpinnerManulOperation = false;
	private ReceiveExpressPageActivity sParent;

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
		if (!BarcodeManager.getInstance().isWayBillNoValid(barcode)) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_invalid_tips);
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
	
	@Override
	public void onPageDeSelected() {
		
		ReceiveVO vo = mReceiveManager.getCurNomalReceiveVO();
		if(CommonUtils.length(vo.getWaybillNo()) < 1){
			String barcode = mWayBillEdit.getText().toString();
			if(BarcodeManager.getInstance().isWayBillNoValid(barcode)){
				vo.setWaybillNo(barcode);
			}
		}
		super.onPageDeSelected();
	}

	private void initContext() {
		mAppContext = AppContext.getAppContext();
		mBasicDataManager = mAppContext.getBasicDataManager();
		mReceiveManager = mAppContext.getReceiveManager();
		mCityList = new ArrayList<CityVO>();
		mEffectiveTypeList = new ArrayList<EffectiveTypeVO>();
		mEffectiveTypeList.addAll(mBasicDataManager.getEffectiveTypeList());
		sParent = ReceiveExpressPageActivity.getInstance();
	}

	private void displayCitySearchResult(CitySearch cs) {
		if (cs == null || (cs.targetCity == null && cs.cityList == null)) {
			DialogHelper.showToast(mContext, R.string.city_search_failed);
			return;
		}

		if (cs.targetCity != null) {
			mDesCitySearchEdit.setText(cs.targetCity.getCityName());
		}

		if (cs.cityList != null) {
			mCityList.clear();
			mCityList.addAll(cs.cityList);
			mCitySpinnerManulOperation = true;
			mCitySpinnerAdapter.notifyDataSetChanged();
		}
	}

	private void initViews() {

		mOrderNoText = (TextView) mRootView.findViewById(R.id.order_no_text);
		mWayBillEdit = (EditText) mRootView.findViewById(R.id.way_bill_no_edit);
		mDesCitySearchEdit = (EditText) mRootView
				.findViewById(R.id.des_city_edit);
		mDesCitySearchEdit.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event != null && event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
					String key = mDesCitySearchEdit.getText().toString();
					mDesCitySearchEdit.setText("");
					if (CommonUtils.isEmpty(key)) {
						DialogHelper.showToast(mContext,
								R.string.input_city_search_tips);
					} else {
						CitySearch cs = mBasicDataManager.searchCityList(key);
						displayCitySearchResult(cs);
					}
					return true;
				}
				return false;
			}
		});
		mDesCitySearchEdit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mDesCitySearchEdit.setImeActionLabel(
				mContext.getString(R.string.query),
				EditorInfo.IME_ACTION_SEARCH);
		mDesCitySearchEdit
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							String key = mDesCitySearchEdit.getText()
									.toString();
							mDesCitySearchEdit.setText("");
							if (CommonUtils.isEmpty(key)) {
								DialogHelper.showToast(mContext,
										R.string.input_city_search_tips);
							} else {
								CitySearch cs = mBasicDataManager
										.searchCityList(key);
								displayCitySearchResult(cs);
							}
							return true;
						}
						return false;
					}
				});
		mCitySpinner = (Spinner) mRootView.findViewById(R.id.des_city_spinner);
		mCitySpinnerAdapter = new ArrayAdapter<CityVO>(mContext,
				android.R.layout.simple_spinner_item, mCityList);
		mCitySpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCitySpinner.setAdapter(mCitySpinnerAdapter);

		mCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (mCitySpinnerManulOperation) {
					CityVO vo = mCityList.get(position);
					CitySearch cs = mBasicDataManager.searchCityListById(vo);
					displayCitySearchResult(cs);
				}
				mCitySpinnerManulOperation = false;
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
		mEffectiveSpinner.setSelection(mBasicDataManager.getDefaultEffectiveTypeIndex());
	}

	private void initViewByReceiveInfo() {
		viewClear();

		ReceiveVO vo = mReceiveManager.getCurNomalReceiveVO();
		if (!CommonUtils.isEmpty(vo.getOrderNo())) {
			mOrderNoText.setText(vo.getOrderNo());
			mRootView.findViewById(R.id.order_no_layout).setVisibility(
					View.VISIBLE);
		}
		
		if(!CommonUtils.isEmpty(vo.getWaybillNo())){
			mWayBillEdit.setText(vo.getWaybillNo());
			if(!sParent.isAddMode()) {
				mWayBillEdit.setEnabled(false);
			}
		}

		if (!CommonUtils.isEmpty(vo.getCityId())) {
			CityVO city = mBasicDataManager.getCityById(vo.getCityId());
			
			if (city != null && city.getCityName() != null) {
				CityVO pCity = mBasicDataManager.getParentCityById(city.getParentCityCode());
				List<CityVO> list = mBasicDataManager.getChildrenCityList(city.getParentCityCode());
				
				if(pCity != null && pCity.getCityName() != null){
					mDesCitySearchEdit.setText(pCity.getCityName());
				}
				
				if(list == null || list.size() < 1){
					list = new ArrayList<CityVO>();
					list.add(city);
				}
				
				if(list != null && list.size() > 0){
					mCityList.clear();
					mCityList.addAll(list);
					mCitySpinnerAdapter.notifyDataSetChanged();
					for(int i = 0; i < list.size(); i++){
						CityVO c = list.get(i);
						if(vo.getCityId().equals(c.getId())){
							mCitySpinner.setSelection(i);
							break;
						}
					}
				}
			}
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
			int i = 0;
			for (; i < mEffectiveTypeList.size(); i++) {
				String tempCode = mEffectiveTypeList.get(i).getCode();
				if (!CommonUtils.isEmpty(tempCode)
						&& tempCode.equals(vo.getPracticalType())) {
					mEffectiveSpinner.setSelection(i);
					break;
				}

				if ("C005".equals(tempCode)) {
					hour72Index = i;
				}
			}

			if (i == mEffectiveTypeList.size()) {
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
		} else if(!BarcodeManager.getInstance().isWayBillNoValid(mWayBillEdit.getText().toString())){
			DialogHelper.showToast(mContext, R.string.way_bill_no_invalid_tips);
			return;
		}
		ReceiveVO receiveVo = mReceiveManager.getCurNomalReceiveVO();
		if (receiveVo == null) {
			receiveVo = new ReceiveVO();
		}

		receiveVo.setWaybillNo(mWayBillEdit.getText().toString());
		if (mCityList.size() > 0) {
			receiveVo.setCityId(mCityList.get((int) mCitySpinner.getSelectedItemId()).getId());
			receiveVo.setDesOrgCode(mCityList.get((int) mCitySpinner.getSelectedItemId()).getCenterCode());
		}
		receiveVo.setDestAddress(mReceiveAddressEdit.getText().toString());
		receiveVo.setReceiverName(mReceiveClientEdit.getText().toString());
		receiveVo.setReceiverPhone(mReceiveCallEdit.getText().toString());
		receiveVo.setFeeAmt(mPriceEdit.getText().toString());
		receiveVo.setGoodsSize(mVolumeEdit.getText().toString());
		receiveVo.setWeighWeight(mWeightEdit.getText().toString());
		receiveVo.setCurrentState("-1");
		receiveVo.setIsInvalid("0");

		receiveVo.setPracticalType(mEffectiveTypeList.get(
				(int) mEffectiveSpinner.getSelectedItemId()).getCode());

		int result = 0;

		if (sParent.isAddMode()) {
			result = mReceiveManager.saveReceive(receiveVo);
			if (result == ReceiveManager.SAVE_RESULT_SUCCESS) {
				updateOrderState();
				mReceiveManager.backgroundSubmitReceive(receiveVo);
				DialogHelper.showToast(mContext, R.string.receive_normal_save_success_tips);
			} else if (result == ReceiveManager.SAVE_RESULT_FAILED_EXIST) {
				DialogHelper.showToast(mContext, R.string.repeat_receive_tips);
			} else {
				DialogHelper.showToast(mContext, R.string.operate_fail);
			}
		} else {
			result = mReceiveManager.editReceive(receiveVo);
			if (result == ReceiveManager.EDIT_RESULT_SUCCESS) {
				DialogHelper.showToast(mContext, R.string.receive_normal_save_success_tips);
			} else if (result == ReceiveManager.EDIT_RESULT_FAILED_INVALID) {
				DialogHelper.showToast(mContext, R.string.invalid_waybill_receive_eidit_tips);
			} else if (result == ReceiveManager.EDIT_RESULT_FAILED_NOT_EXIST) {
				DialogHelper.showToast(mContext, R.string.unexist_waybill_receive_eidit_tips);
			} else {
				DialogHelper.showToast(mContext, R.string.operate_fail);
			}
		}
		viewClear();
		mReceiveManager.clearCurNomalReceiveVO();
	}

	private void updateOrderState() {
		OrderVO vo = ReceiveExpressPageActivity.getInstance().getOrderVO();
		if (vo != null) {
			vo.setAdditionState(String.valueOf(OrderManager.STATE_FETCHED));
			OrderManager.getInstance().updateOrder(vo);
			OrderManager.getInstance().updateOrderState(vo);
		}
	}
}
