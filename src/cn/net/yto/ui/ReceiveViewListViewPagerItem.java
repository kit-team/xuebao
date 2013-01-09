package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.Constants;
import cn.net.yto.utils.CalendarUtil;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.RecvexpVO;

public class ReceiveViewListViewPagerItem extends ViewPageItemAbs {
	private static final String TAG = "ReceiveViewListViewPagerItem";
	private View mRootView;
	
	private EditText mFromDateEdit;
	private EditText mToDateEdit;
	private Button mQueryBtn;
	private Button mBackBtn;
	private Button mDetailBtn;
	private ListView mListView;
	private ReceiveListAdapter mReceiveListAdapter;
	
	private List<ReceiveVO> mReceiveList;
	private Calendar mFromCanlendar;
	private Calendar mToCalendar;
	private int mSelectedIdx;
	private final String DAY_BEGIN = " 00:00:00";
	private String DAY_END = " 23:59:59";
	
	
	public ReceiveViewListViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);		
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_view_list, null);
		initContext();
		initViews();
	}
	
	private void initContext(){
		mReceiveList = new ArrayList<ReceiveVO>();
	}
	
	private void initViews(){
		mToCalendar = Calendar.getInstance(Locale.CHINA);
		mFromCanlendar = Calendar.getInstance(Locale.CHINA);
		mQueryBtn = (Button) mRootView.findViewById(R.id.query_btn);
		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);
		mDetailBtn = (Button) mRootView.findViewById(R.id.detail_btn);
		mQueryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				queryReceive(mFromDateEdit.getText().toString(),
						mToDateEdit.getText().toString());
			}
		});
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceiveViewTabActivity.getOrderTab().onBackPressed();
			}
		});
		mDetailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDetail();
			}
		});
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
									updateDateDisplay(mFromDateEdit,mFromCanlendar);
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
		mListView = (ListView) mRootView.findViewById(android.R.id.list);
		
		View headView = mContext.getLayoutInflater().inflate(R.layout.list_head_receive_query, null);
		mListView.addHeaderView(headView, null, false);

		mReceiveListAdapter = new ReceiveListAdapter(mContext, mReceiveList);
		mListView.setAdapter(mReceiveListAdapter);
		mListView.setSelected(true);
		mListView.setFocusable(true);
		mListView.setFastScrollEnabled(true);
		mListView.requestFocus();

		mListView.setEmptyView(mContext.findViewById(R.id.empty));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedIdx = position - 1;
				mReceiveListAdapter.notifyDataSetChanged();
				showDetail();
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedIdx = position - 1;
				mReceiveListAdapter.notifyDataSetChanged();
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
		ReceiveViewTabActivity.getOrderTab().setSelectedIdx(mSelectedIdx);
		ReceiveViewTabActivity.getOrderTab().setListReceiveVO(mReceiveList);
		mViewPager.setCurrentItem(1);
	}
	
	
	private void updateDateDisplay(EditText editText, Calendar date) {
		editText.setText(CommonUtils.getFormatedDateTime("yyyy-MM-dd",
				date.getTimeInMillis()));
	}
	
	@Override
	public View getItemView() {
		return mRootView;
	}
	
	private void queryReceive(String beginTime, String endTime){
		List<ReceiveVO> result = ReceiveManager.getInstance().queryReceive(
				beginTime + DAY_BEGIN, endTime + DAY_END, null);
		mReceiveList.clear();
		mReceiveList.addAll(result);
		mReceiveListAdapter.notifyDataSetChanged();
		ReceiveViewTabActivity.getOrderTab().setListReceiveVO(mReceiveList);
		ReceiveViewTabActivity.getOrderTab().setSelectedIdx(0);
	}
	
	private class ReceiveListAdapter extends ArrayAdapter<ReceiveVO> {
		
		private final static int COLOR_SELECTED = Color.GRAY;
		private final static int COLOR_DEFAULT = Color.WHITE;
		
		private String mWayBillNo = mContext.getString(R.string.way_bill_no);
		private String mCancel = mContext.getString(R.string.receive_cancel);
		private String mUploadWaiting = mContext.getString(R.string.state_upload_waiting);
		private String mNotCancel = mContext.getString(R.string.receive_not_cancel);
//		private String mNotUploaded = mContext.getString(R.string.state_not_upload);
		private String mCancelOrNot = mContext.getString(R.string.cancel_or_not);
		private String mUploadState = mContext.getString(R.string.order_upload_state);
		private String mUnusualReason = mContext.getString(R.string.unusual_reason);
		
		
		private String status;
		
		public ReceiveListAdapter(Context context, List<ReceiveVO> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ReceiveVO receive = mReceiveList.get(position);
			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.receive_query_item,
						parent, false);
			}

			TextView orderId = (TextView) convertView
					.findViewById(R.id.order_number_text);

			if(receive.getWaybillNo().startsWith(Constants.RECEIVE_NO_WAYBILLNO_PREX)){
				orderId.setText("");				
			} else{
				orderId.setText(receive.getWaybillNo());				
			}
			
			
			TextView orderState = (TextView) convertView
					.findViewById(R.id.order_state_text);
			orderState.setText("1".equals(receive.getIsInvalid()) ? mCancel : mNotCancel);

			TextView orderUploadState = (TextView) convertView
					.findViewById(R.id.order_upload_state_text);
			status = receive.getUploadStatu();
		
			if(null==status || status.length()==0) {
				orderUploadState.setText(mUploadWaiting);
			} else{
				orderUploadState.setText(receive.getGetStatus());				
			}
			
			if(position == mSelectedIdx){
				orderId.setBackgroundColor(COLOR_SELECTED);
				orderState.setBackgroundColor(COLOR_SELECTED);
				orderUploadState.setBackgroundColor(COLOR_SELECTED);
			} else {
				orderId.setBackgroundColor(COLOR_DEFAULT);
				orderState.setBackgroundColor(COLOR_DEFAULT);
				orderUploadState.setBackgroundColor(COLOR_DEFAULT);
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
