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
	
	public ReceiveWayBillListViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);

		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_way_bill_list, null);
		
		initContext();
		initViews();
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
		new QueryReceiveTask().execute(
				mFromDateEdit.getText().toString(), mToDateEdit
						.getText().toString(), mWaybillNoEdit.getText().toString());
	}
	
	private void initContext(){
		mReceiveList = new ArrayList<ReceiveVO>();
		mToCalendar = Calendar.getInstance(Locale.CHINA);
		mFromCanlendar = CalendarUtil.rollSomeDays(mToCalendar, -7);
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
				new QueryReceiveTask().execute(
						mFromDateEdit.getText().toString(), mToDateEdit
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
		ReceiveWayBillTabActivity.getOrderTab().setReceiveVO(mReceiveList.get(mSelectedIdx));
		mViewPager.setCurrentItem(1);
	}
	
	private void updateDateDisplay(EditText editText, Calendar date) {
		editText.setText(CommonUtils.getFormatedDateTime("yyyy-MM-dd",
				date.getTimeInMillis()));
	}

	private class QueryReceiveTask extends AsyncTask<String, Void, List<ReceiveVO>> {
		@Override
		protected List<ReceiveVO> doInBackground(String... params) {
			String startTime = params[0];
			String endTime = params[1];
			String waybillNo = params[2];
			LogUtils.d(TAG, startTime +" "+endTime);
			ReceiveManager service = ReceiveManager.getInstance();
			
			return service.queryReceive(startTime, endTime, waybillNo);
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
//			DialogHelper.closeProgressDialog();
		}
	}
	
	private class ReceiveListAdapter extends ArrayAdapter<ReceiveVO> {
		private String mWayBillNo = AppContext.getAppContext().getString(R.string.way_bill_no);
		private String mNormal = AppContext.getAppContext().getString(R.string.state_normal);
		private String mUnusual = AppContext.getAppContext().getString(R.string.state_unusual);
		private String mCancel = AppContext.getAppContext().getString(R.string.receive_cancel);
		
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
			if("-1".equals(receive.getCurrentState())){
				text.setText(mNormal);
			} else {
				text.setText(mUnusual);
			}

			text = (TextView) convertView
					.findViewById(R.id.order_upload_state_text);
			String invalid = receive.getIsInvalid();
			if("1".equals(invalid)){
				text.setText(mCancel);				
			} else{
				text.setText("");
			}
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
