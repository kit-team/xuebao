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
import android.os.AsyncTask;
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
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.utils.CalendarUtil;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.LogUtils;
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
		mFromCanlendar = CalendarUtil.rollSomeDays(mToCalendar, -7);
		mQueryBtn = (Button) mRootView.findViewById(R.id.query_btn);
		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);
		mDetailBtn = (Button) mRootView.findViewById(R.id.detail_btn);
		mQueryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new QueryReceiveTask().execute(
						mFromDateEdit.getText().toString(), mToDateEdit
								.getText().toString());
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
				mSelectedIdx = position;
				showDetail();
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mSelectedIdx = arg2;
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
	
	private class QueryReceiveTask extends AsyncTask<String, Void, List<ReceiveVO>> {
		@Override
		protected List<ReceiveVO> doInBackground(String... params) {
			String startTime = params[0];
			String endTime = params[1];
			LogUtils.d(TAG, startTime +" "+endTime);
			ReceiveManager service = ReceiveManager.getInstance();
			
			return service.queryReceive(startTime, endTime, null);
		}

		@Override
		protected void onPreExecute() {
//			DialogHelper.showProgressDialog(AppContext.getAppContext(), "updating");
		}

		@Override
		protected void onPostExecute(List<ReceiveVO> result) {
			LogUtils.d(TAG, "get receive from local db. the size is "+result.size());
			mReceiveList.clear();
			mReceiveList.addAll(result);
			mReceiveListAdapter.notifyDataSetChanged();
			ReceiveViewTabActivity.getOrderTab().setListReceiveVO(mReceiveList);
			ReceiveViewTabActivity.getOrderTab().setSelectedIdx(0);
//			DialogHelper.closeProgressDialog();
		}
	}
	
	private class ReceiveListAdapter extends ArrayAdapter<ReceiveVO> {
		private String mWayBillNo = AppContext.getAppContext().getString(R.string.way_bill_no);
		private String mWaiting = AppContext.getAppContext().getString(R.string.state_waiting);
		private String mUploading = AppContext.getAppContext().getString(R.string.state_uploading);
		private String mUploaded = AppContext.getAppContext().getString(R.string.state_upload);
		private String mNotUploaded = AppContext.getAppContext().getString(R.string.state_not_upload);
		private String status;
		
		public ReceiveListAdapter(Context context, List<ReceiveVO> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ReceiveVO receive = mReceiveList.get(position);
			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_item,
						parent, false);
			}

			TextView text = (TextView) convertView
					.findViewById(R.id.order_number_descrip);
			text.setText(mWayBillNo);
			
			text = (TextView) convertView
					.findViewById(R.id.order_number_text);
			text = (TextView) convertView.findViewById(R.id.order_state_text);
			text.setText(receive.getWaybillNo());

			text = (TextView) convertView
					.findViewById(R.id.order_read_state_text);
			status = receive.getSendStatus();
			if(ReceiveVO.SEND_STATUS_WAITING.equals(status)){
				text.setText(mWaiting);
			} else if(ReceiveVO.SEND_STATUS_UPLOADING.equals(status)) {
				text.setText(mUploading);
			} else if(ReceiveVO.SEND_STATUS_UPLOADED.equals(status)){
				text.setText(mUploaded);				
			} else{
				text.setText(mNotUploaded);								
			}

			text = (TextView) convertView
					.findViewById(R.id.order_upload_state_text);
			text.setText(receive.getCausesException());
			return convertView;
		}
		
		private String getExceptionDesByCode(String code){
			List<RecvexpVO> exceptions = AppContext.getAppContext().getBasicDataManager().getRecvexpList();
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
