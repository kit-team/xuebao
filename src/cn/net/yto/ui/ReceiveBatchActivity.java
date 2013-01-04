package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.Constants;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.ReceiveVO;

import com.zltd.android.scan.ScanManager;
import com.zltd.android.scan.ScanResultListener;
import com.zltd.android.scan.impl.OneDimensionalSanManager;

public class ReceiveBatchActivity extends BaseActivity implements
		OnClickListener {
	private EditText mDesCitySearchEdit;
	private EditText mPriceEdit;
	private EditText mWayBillEdit;

	private Button mBackBtn;
	private Button mDeleteBtn;
	private Button mSaveBtn;
	private ArrayList<ReceiveVO> mReceiveList;
	private ReceiveAdapter mListAdapter;
	private ListView mListView;
	private int selectedId = -1;
	private TextView mTitleText;

	private BasicDataManager mBasicDataManager;
	private ReceiveManager mReceiveManager;
	private List<CityVO> mCityList;
	private List<EffectiveTypeVO> mEffectiveTypeList;
	private Spinner mCitySpinner;
	private Spinner mEffectiveSpinner;
	private ReceiveVO mCurReceiveVO;
	private boolean mAddMode = true;
	private ScanResultListener mScanResultListener = new ScanResultListener() {
		@Override
		public void onScan(ScanManager arg0, byte[] scanResultDate) {
			if (scanResultDate != null) {
				String barcode = new String(scanResultDate);
				if (onScanned(barcode)) {
					playSound(Constants.SOUND_TYPE_SUCCESS);
				} else {
					playSound(Constants.SOUND_TYPE_WARNING);
				}
			}
			mVibrator.vibrate(39);
		}
	};
	private OneDimensionalSanManager mScanManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContext();
		initViews();
		
		mScanManager = new OneDimensionalSanManager(mContext);
		mScanManager.setEnable(true);
		mScanManager.registerResultListener(mScanResultListener);
	}
	
	@Override
	protected void onStart() {
		hideInputMethod(mWayBillEdit);
		super.onStart();
	}
	
	public boolean onScanned(String barcode) {
		hideInputMethod(mWayBillEdit);
		if (CommonUtils.isEmpty(barcode)) {
			return false;
		}
		save();
		mWayBillEdit.setText(barcode);
		return true;
	}

	@Override
	protected void onDestroy() {
		for(ReceiveVO vo : mReceiveList){
			mReceiveManager.backgroundUpload(vo);
		}
		mScanManager.unregisterResultListener();
		mScanManager.setEnable(false);
		mScanManager = null;
		super.onDestroy();
	}
	private void initContext() {
		mReceiveManager = ReceiveManager.getInstance();
		mBasicDataManager = BasicDataManager.getInstance();
		mCityList = mBasicDataManager.getCityList();
		mEffectiveTypeList = mBasicDataManager.getEffectiveTypeList();

		mReceiveList = new ArrayList<ReceiveVO>();
	}

	private void initViews() {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_receive_batch);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		setTitleInfo(R.string.receive_batch);

		mDesCitySearchEdit = (EditText) findViewById(R.id.city_search_edit);
		mCitySpinner = (Spinner) findViewById(R.id.city_spinner);
		ArrayAdapter<CityVO> adapter = new ArrayAdapter<CityVO>(mContext,
				android.R.layout.simple_spinner_item, mCityList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCitySpinner.setAdapter(adapter);

		mEffectiveSpinner = (Spinner) findViewById(R.id.effective_type_spinner);
		ArrayAdapter<EffectiveTypeVO> effectiveAdapter = new ArrayAdapter<EffectiveTypeVO>(
				mContext, android.R.layout.simple_spinner_item,
				mEffectiveTypeList);
		effectiveAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mEffectiveSpinner.setAdapter(effectiveAdapter);

		mPriceEdit = (EditText) findViewById(R.id.price_edit);
		mWayBillEdit = (EditText) findViewById(R.id.way_bill_no_edit);
		mWayBillEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() < 1) {
					mSaveBtn.setEnabled(false);
				} else {
					mSaveBtn.setEnabled(true);
				}
			}
		});

		mBackBtn = (Button) findViewById(R.id.btn_back);
		mDeleteBtn = (Button) findViewById(R.id.btn_delete);
		if (mReceiveList.size() < 1) {
			mDeleteBtn.setEnabled(false);
		}
		mSaveBtn = (Button) findViewById(R.id.btn_save);
		mBackBtn.setOnClickListener(this);
		mDeleteBtn.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
		mSaveBtn.setEnabled(false);

		mListView = (ListView) findViewById(android.R.id.list);
		mListAdapter = new ReceiveAdapter(this,
				R.layout.view_batch_receive_list, mReceiveList);
		mListView.setAdapter(mListAdapter);
		mListView.setItemsCanFocus(true);
		mListView.setSelected(true);
		mListView.setFocusable(true);
		mListView.setFocusableInTouchMode(true);
		mListView.setFastScrollEnabled(true);
		mListView.setDividerHeight(1);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mCurReceiveVO = mReceiveList.get(arg2);
				initViewByReceiveInfo(mCurReceiveVO);
				mAddMode = false;
				mDeleteBtn.setEnabled(true);
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				save();
				mCurReceiveVO = mReceiveList.get(arg2);
				initViewByReceiveInfo(mCurReceiveVO);
				mAddMode = false;
				mDeleteBtn.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
		case R.id.btn_delete:
			onDeleteBtnClicked();
			break;
		case R.id.btn_save:
			onSaveBtnClicked();
			break;
		}
	}

	private void onDeleteBtnClicked() {
		if(!mAddMode && mCurReceiveVO != null){
			mReceiveList.remove(mCurReceiveVO);
			mReceiveManager.deleteReceive(mCurReceiveVO.getWaybillNo());
			mListAdapter.notifyDataSetChanged();
			clear();
		}
	}

	private void clear() {
		mWayBillEdit.setText("");
		mDesCitySearchEdit.setText("");
		mCitySpinner.setSelection(0);
		mPriceEdit.setText("");
		//mEffectiveSpinner.setSelection(mBasicDataManager.getDefaultEffectiveTypeIndex());
		mAddMode = true;
		mDeleteBtn.setEnabled(false);
	}

	private void initViewByReceiveInfo(ReceiveVO vo) {
		clear();

		if (!CommonUtils.isEmpty(vo.getCityId())) {
			CityVO city = mBasicDataManager.getCityById(vo.getCityId());

			if (city != null && city.getCityName() != null) {
				mDesCitySearchEdit.setText(city.getCityName());
			}
		}

		if (!CommonUtils.isEmpty(vo.getWaybillNo())) {
			mWayBillEdit.setText(vo.getWaybillNo());
		}

		if (!CommonUtils.isEmpty(vo.getCityId())) {
			mDesCitySearchEdit.setText(vo.getCityId());
		}

		if (!CommonUtils.isEmpty(vo.getFeeAmt())) {
			mPriceEdit.setText(vo.getFeeAmt());
		}

		if (!CommonUtils.isEmpty(vo.getPracticalType())) {
			int i = 0;
			for (; i < mEffectiveTypeList.size(); i++) {
				String tempCode = mEffectiveTypeList.get(i).getCode();
				if (!CommonUtils.isEmpty(tempCode)
						&& tempCode.equals(vo.getPracticalType())) {
					mEffectiveSpinner.setSelection(i);
					break;
				}
			}

			if (i == mEffectiveTypeList.size()) {
				mEffectiveSpinner.setSelection(mBasicDataManager.getDefaultEffectiveTypeIndex());
			}
		}
	}
	
	private void onSaveBtnClicked() {
		if (CommonUtils.isEmpty(mWayBillEdit.getText().toString())) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_empty_tips);
			return;
		}

		save();
	}

	private void save() {
		if (CommonUtils.isEmpty(mWayBillEdit.getText().toString())) {
			return;
		}
		
		if (mCurReceiveVO == null) {
			mCurReceiveVO = new ReceiveVO();
		}

		mCurReceiveVO.setWaybillNo(mWayBillEdit.getText().toString());
		mCurReceiveVO.setCityId(mDesCitySearchEdit.getText().toString());
		mCurReceiveVO.setFeeAmt(mPriceEdit.getText().toString());

		mCurReceiveVO.setPracticalType(mEffectiveTypeList.get(
				(int) mEffectiveSpinner.getSelectedItemId()).getCode());

		mReceiveManager.saveReceive(mCurReceiveVO);

		if (!mReceiveList.contains(mCurReceiveVO)) {
			mReceiveList.add(mCurReceiveVO);
		}
		mListAdapter.notifyDataSetChanged();
		mCurReceiveVO = null;
		clear();
	}

	private class ReceiveAdapter extends ArrayAdapter<ReceiveVO> {
		public ReceiveAdapter(Context context, int textViewResourceId,
				ArrayList<ReceiveVO> items) {
			super(context, textViewResourceId, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.view_batch_receive_list, null);
				v.setVisibility(View.VISIBLE);
			}
			ReceiveVO o = mReceiveList.get(position);

			if (o != null) {
				TextView wayBillNoText = (TextView) v
						.findViewById(R.id.way_bill_no_text);
				TextView desCityText = (TextView) v
						.findViewById(R.id.des_city_text);
				if (wayBillNoText != null) {
					wayBillNoText.setText(o.getWaybillNo());
				}
				if (desCityText != null && !CommonUtils.isEmpty(o.getCityId())) {
					CityVO city = mBasicDataManager.getCityById(o.getCityId());
					if (city != null
							&& !CommonUtils.isEmpty(city.getCityName())) {
						desCityText.setText(city.getCityName());
					}
				} else {
					desCityText.setText("");
				}
			}
			return v;
		}
	}
}
