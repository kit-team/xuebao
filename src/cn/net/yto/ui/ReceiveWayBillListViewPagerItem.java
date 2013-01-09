﻿package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.zltd.android.scan.ScanManager;
import com.zltd.android.scan.ScanResultListener;
import com.zltd.android.scan.impl.OneDimensionalSanManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.BarcodeManager;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.Constants;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.RecvexpVO;

public class ReceiveWayBillListViewPagerItem extends ViewPageItemAbs {
	private static final String TAG = "ReceiveWayBillListViewPagerItem";
	private Calendar mFromCanlendar;
	private Calendar mToCalendar;
	private EditText mFromDateEdit;
	private EditText mToDateEdit;
	private EditText mWaybillNoEdit;
	private Button mBackBtn;
	private Button mDetailBtn;
	private Button mQueryBtn;
	private ListView mListView;
	private View mRootView;
	
	private ReceiveListAdapter mReceiveListAdapter;
	private ReceiveManager mReceiveManager;
	private List<ReceiveVO> mReceiveList;
	private int mSelectedIdx;
	private final String DAY_BEGIN = " 00:00:00";
	private final String DAY_END = " 23:59:59";
	
	
	public ReceiveWayBillListViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);

		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_way_bill_list, null);
		
		initContext();
		initViews();
	}
	
	@Override
	public boolean onScanned(String barcode) {
		if (CommonUtils.isEmpty(barcode)) {
			return false;
		}
		if (!BarcodeManager.getInstance().isWayBillNoValid(barcode)) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_invalid_tips);
			return false;
		}

		mWaybillNoEdit.setText(barcode);
		return true;
	}
	
	@Override
	public View getItemView() {
		return mRootView;
	}
	
	@Override
	public void onPageSelected() {
		super.onPageSelected();
//		mReceiveList.clear();
//		mReceiveListAdapter.notifyDataSetChanged();
		queryReceive(mFromDateEdit.getText().toString(),
				mToDateEdit.getText().toString(),
				mWaybillNoEdit.getText().toString());
	}
	
	private void initContext(){
		mReceiveList = new ArrayList<ReceiveVO>();
		mToCalendar = Calendar.getInstance(Locale.CHINA);
		mFromCanlendar = Calendar.getInstance(Locale.CHINA);
	}

	private void initViews() {
		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ReceiveWayBillTabActivity.getOrderTab().onBackPressed();
			}
		});

		mDetailBtn = (Button) mRootView.findViewById(R.id.detail_btn);
		mDetailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDetail();
			}
		});
		mQueryBtn = (Button) mRootView.findViewById(R.id.query_btn);
		mQueryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				queryReceive(mFromDateEdit.getText().toString(), mToDateEdit
								.getText().toString(), mWaybillNoEdit.getText().toString());
			}
		});

		mWaybillNoEdit = (EditText) mRootView.findViewById(R.id.way_bill_no_edit);
		mToDateEdit = (EditText) mRootView.findViewById(R.id.to_date_edit);
		mToDateEdit.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

		mToDateEdit.setOnTouchListener(new View.OnTouchListener() {
			private DatePickerDialog dialog;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					dialog = new DatePickerDialog(mContext,
							new OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									mToCalendar.set(Calendar.YEAR, year);
									mToCalendar
											.set(Calendar.MONTH, monthOfYear);
									mToCalendar.set(Calendar.DAY_OF_MONTH,
											dayOfMonth);
									updateDateDisplay(mToDateEdit, mToCalendar);
									dialog = null;
								}
							}, mToCalendar.get(Calendar.YEAR), mToCalendar
									.get(Calendar.MONTH), mToCalendar
									.get(Calendar.DAY_OF_MONTH));

					dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							dialog = null;
						}
					});
					dialog.show();
				}
				return true;
			}
		});
		updateDateDisplay(mToDateEdit, mToCalendar);

		mFromDateEdit = (EditText) mRootView.findViewById(R.id.from_date_edit);
		mFromDateEdit.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
		mFromDateEdit.clearFocus();

		mFromDateEdit.setOnTouchListener(new View.OnTouchListener() {
			private DatePickerDialog dialog;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					dialog = new DatePickerDialog(mContext,
							new OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									mFromCanlendar.set(Calendar.YEAR, year);
									mFromCanlendar.set(Calendar.MONTH,
											monthOfYear);
									mFromCanlendar.set(Calendar.DAY_OF_MONTH,
											dayOfMonth);
									updateDateDisplay(mFromDateEdit,
											mFromCanlendar);
									dialog = null;
								}
							}, mFromCanlendar.get(Calendar.YEAR),
							mFromCanlendar.get(Calendar.MONTH), mFromCanlendar
									.get(Calendar.DAY_OF_MONTH));

					dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							dialog = null;
						}
					});
					dialog.show();
				}
				return true;
			}
		});
		
		updateDateDisplay(mFromDateEdit, mFromCanlendar);

		mListView = (ListView) mRootView.findViewById(android.R.id.list);
		View headView = mContext.getLayoutInflater().inflate(R.layout.view_receive_waybill_head, null);
		mListView.addHeaderView(headView, null, false);
		mReceiveListAdapter = new ReceiveListAdapter(mContext, mReceiveList);
		mListView.setAdapter(mReceiveListAdapter);
		mListView.setItemsCanFocus(true);
		mListView.setSelected(true);
		mListView.setFocusable(true);
		mListView.setFocusableInTouchMode(true);
		mListView.setFastScrollEnabled(true);
		mListView.setDividerHeight(0);

		mListView.setEmptyView(mContext.findViewById(R.id.empty));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedIdx = position - 1;
				showDetail();
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mSelectedIdx = arg2 - 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void showDetail() {
		if(mReceiveList.size()==0) {
			return;
		}
		if(mSelectedIdx < mReceiveList.size() && mSelectedIdx >= 0){
			ReceiveWayBillTabActivity.getOrderTab().setReceiveVO(mReceiveList.get(mSelectedIdx));
			mViewPager.setCurrentItem(1);
		}
	}
	
	private void updateDateDisplay(EditText editText, Calendar date) {
		editText.setText(CommonUtils.getFormatedDateTime("yyyy-MM-dd",
				date.getTimeInMillis()));
	}
	
	private void queryReceive(String beginTime, String endTime, String waybillNo){
		List<ReceiveVO> result = ReceiveManager.getInstance().queryReceive(
				beginTime + DAY_BEGIN, endTime + DAY_END, waybillNo);
		mReceiveList.clear();
		mReceiveList.addAll(result);
		mReceiveListAdapter.notifyDataSetChanged();
	}

	private class ReceiveListAdapter extends ArrayAdapter<ReceiveVO> {
//		private String mWayBillNo = mContext.getString(R.string.way_bill_no);
//		private String mUsual = mContext.getString(R.string.receive_usual);
//		private String mUnusual = mContext.getString(R.string.state_unusual);
		private String mCancel = mContext.getString(R.string.receive_cancel);
//		private String mSuccess = mContext.getString(R.string.send_success);
		private String mNormal = mContext.getString(R.string.state_normal);
//		private String mCancelOrNot = mContext.getString(R.string.cancel_or_not);
//		private String mReceiveState = mContext.getString(R.string.state_receive_desc);
		private String mReceiveSuccess = mContext.getString(R.string.state_receive_replace_success);
		protected final static int COLOR_DEFAULT = Color.WHITE;
	    protected final static int COLOR_SELECTED = Color.GRAY;
		
		public ReceiveListAdapter(Context context, List<ReceiveVO> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ReceiveVO receive = mReceiveList.get(position);
			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.view_batch_receive_list,
						parent, false);
				convertView.setVisibility(View.VISIBLE);
			}

			TextView wayBillNoText = (TextView) convertView
					.findViewById(R.id.way_bill_no_text);
			
			if (wayBillNoText != null) {
				if(receive.getWaybillNo().startsWith(Constants.RECEIVE_NO_WAYBILLNO_PREX)){
					wayBillNoText.setText("");
				} else{
					wayBillNoText.setText(receive.getWaybillNo());
				}
			}
			
			TextView receiveStateText = (TextView) convertView
					.findViewById(R.id.datetime_text);
//			if("-1".equals(receive.getCurrentState())){
//				receiveStateText.setText(mSuccess);
//			} else {
//				receiveStateText.setText(getExceptionDesByCode(receive.getCurrentState()));
//			}
			receiveStateText.setText(receive.getGetStatus());

			TextView isValidText = (TextView) convertView.findViewById(R.id.price_text);
			String invalid = receive.getIsInvalid();
			if("1".equals(invalid)){
				isValidText.setText(mCancel);
			} else if("2".equals(invalid)){
				isValidText.setText(mReceiveSuccess);
			} else{
				isValidText.setText(mNormal);
			}
			if(position == mSelectedIdx){
				wayBillNoText.setBackgroundColor(COLOR_SELECTED);
				receiveStateText.setBackgroundColor(COLOR_SELECTED);
				isValidText.setBackgroundColor(COLOR_SELECTED);
			} else {
				wayBillNoText.setBackgroundColor(COLOR_DEFAULT);
				receiveStateText.setBackgroundColor(COLOR_DEFAULT);
				isValidText.setBackgroundColor(COLOR_DEFAULT);
			}
			return convertView;
		}
		
		private String getExceptionDesByCode(String code){
			List<RecvexpVO> exceptions = BasicDataManager.getInstance().getRecvexpList();
			for(int i = 0; i < exceptions.size(); i++){
				RecvexpVO vo = exceptions.get(i);
				if(code.equals(vo.getFailureCode())){
					return vo.getFailureReason();
				}
			}
			return "";
		}
	}
}
