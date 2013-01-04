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
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.utils.CalendarUtil;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.OrderVO;

public class OrderTabListItem extends ViewPageItemAbs {
	private static String TAG = "OrderTabListItem";

	private Calendar mFromCanlendar;
	private Calendar mToCalendar;
	private EditText mFromDateEdit;
	private EditText mToDateEdit;
	private Spinner mUserSpinner;
	private Button mBackBtn;
	private Button mDetailBtn;
	private Button mQueryBtn;

	private ListView mListView;
	private OrderListAdapter mOrderListAdapter;
	private OrderManager mOrderManager;
	private View mRootView;
	private List<OrderVO> mOrderList;
	private Integer mCurrentOrderState = OrderManager.STATE_INIT;
	private Integer[] mOrderStates;
	
	
	OrderTabListItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_order_list, null);
		
		mToCalendar = Calendar.getInstance(Locale.CHINA);
		
		mFromCanlendar = CalendarUtil.rollSomeDays(mToCalendar, -7);
		
		initContext();
		
		initViews();
	}
	
	public void insertOrder(OrderVO order) {
		mOrderList.add(order);
	}
	
	public void deleteOrder(OrderVO order) {
		mOrderList.remove(order);
	}
	
	private void initContext() {
		mOrderList = new ArrayList<OrderVO>();
		mOrderManager = new OrderManager();
		mOrderStates = initOrderState();
	//	mOrderList.addAll(mOrderManager.getOrderList());
	}
	
	private void initViews() {
		
		mUserSpinner = (Spinner) mRootView.findViewById(R.id.order_state_spinner);
		mUserSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mCurrentOrderState = mOrderStates[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
	//			finish();
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
				new QueryOrderTask().execute(
						mFromDateEdit.getText().toString(), mToDateEdit
								.getText().toString(), String.valueOf(mCurrentOrderState));
			}
		});

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

		mOrderListAdapter = new OrderListAdapter(mContext, mOrderList);
		mListView.setAdapter(mOrderListAdapter);
		mListView.setSelected(true);
		mListView.setFocusable(true);
		mListView.setFastScrollEnabled(true);
		mListView.requestFocus();

		mListView.setEmptyView(mContext.findViewById(R.id.empty));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OrderTabActivity.getOrderTab().setSelectedIdx(position);
				showDetail();
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				OrderTabActivity.getOrderTab().setSelectedIdx(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	@Override
	public void onPageSelected() {
		super.onPageSelected();
		new QueryOrderTask().execute(
				mFromDateEdit.getText().toString(), mToDateEdit
						.getText().toString(), String.valueOf(mCurrentOrderState));
	}
	
	private void updateDateDisplay(EditText editText, Calendar date) {
		editText.setText(CommonUtils.getFormatedDateTime("yyyy-MM-dd",
				date.getTimeInMillis()));
	}
	
	private void showDetail() {
		if(mOrderList.size()==0) return;
		OrderTabActivity.getOrderTab().setListOrderVO(mOrderList);
		mViewPager.setCurrentItem(1);
	}
	
	private Integer[] initOrderState(){
		Integer[] mOrderStates = {
				OrderManager.STATE_INIT,
				OrderManager.STATE_INIT_READED,
				OrderManager.STATE_FETCHED,
				OrderManager.STATE_FETCHED_EXCEPTION,
				OrderManager.STATE_CANCELED,
				OrderManager.STATE_ENERGED
		};
		return mOrderStates;
	}
	
	private class OrderListAdapter extends ArrayAdapter<OrderVO> {
		private String mRead = AppContext.getAppContext().getString(R.string.state_read);
		private String mUnread = AppContext.getAppContext().getString(R.string.state_not_read);
		private String mUpload = AppContext.getAppContext().getString(R.string.state_upload);
		private String mNotUpload = AppContext.getAppContext().getString(R.string.state_not_upload);
		private String mNormal = AppContext.getAppContext().getString(R.string.state_normal);
		private String mUnusual = AppContext.getAppContext().getString(R.string.state_unusual);
		
		
		public OrderListAdapter(Context context, List<OrderVO> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final OrderVO order = mOrderList.get(position);
			int state = Integer.parseInt(order.getAdditionState());
			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_item,
						parent, false);
			}

			TextView text = (TextView) convertView
					.findViewById(R.id.order_number_text);
			text.setText(order.getOrderNo());

			text = (TextView) convertView.findViewById(R.id.order_state_text);
			if(state == OrderManager.STATE_CANCELED
					|| state == OrderManager.STATE_CANCELED_READED){
				text.setText(mUnusual);
			} else{
				text.setText(mNormal);
			}

			text = (TextView) convertView
					.findViewById(R.id.order_read_state_text);
			if(state == OrderManager.STATE_INIT
					|| state == OrderManager.STATE_CANCELED
					|| state == OrderManager.STATE_ENERGED){
				text.setText(mUnread);
			} else if(state == OrderManager.STATE_FETCHED
					|| state == OrderManager.STATE_INIT_READED
					|| state == OrderManager.STATE_FETCHED_EXCEPTION
					|| state == OrderManager.STATE_FETCHED_UPLOADED_EXCEPTION
					|| state == OrderManager.STATE_CANCELED_READED
//					|| state == OrderManager.STATE_CANCELED_UPLOADED
					|| state == OrderManager.STATE_ENERGED_READED
//					|| state == OrderManager.STATE_ENERGED_UPLOADED
					|| state == OrderManager.STATE_FETCHED_UPLOADED){
				text.setText(mRead);
			}

			text = (TextView) convertView
					.findViewById(R.id.order_upload_state_text);
			if(state == OrderManager.STATE_INIT
					|| state == OrderManager.STATE_FETCHED
					|| state == OrderManager.STATE_INIT_READED
					|| state == OrderManager.STATE_CANCELED
					|| state == OrderManager.STATE_CANCELED_READED
					|| state == OrderManager.STATE_ENERGED
					|| state == OrderManager.STATE_ENERGED_READED
					|| state == OrderManager.STATE_FETCHED_EXCEPTION){
				text.setText(mNotUpload);
			} else if(state == OrderManager.STATE_FETCHED_UPLOADED
					|| state == OrderManager.STATE_FETCHED_UPLOADED_EXCEPTION
				  /*|| state == OrderManager.STATE_CANCELED_UPLOADED
					|| state == OrderManager.STATE_ENERGED_UPLOADED*/){
				text.setText(mUpload);
			}
			return convertView;
		}
	}

	@Override
	public View getItemView() {
		return mRootView;
	}
	
	private class QueryOrderTask extends AsyncTask<String, Void, List<OrderVO>> {
		@Override
		protected List<OrderVO> doInBackground(String... params) {
			String startTime = params[0];
			String endTime = params[1];
			String code = params[2];
			LogUtils.d(TAG, startTime +" "+endTime+" "+code);
			
			return OrderManager.getInstance().queryOrderVO(startTime, endTime, code);
		}

		@Override
		protected void onPreExecute() {
//			DialogHelper.showProgressDialog(AppContext.getAppContext(), "updating");
		}

		@Override
		protected void onPostExecute(List<OrderVO> result) {
			mOrderList.clear();
			mOrderList.addAll(result);
			mListView.scrollBy(0,0);
			mListView.invalidate();
			mOrderListAdapter.notifyDataSetChanged();
			OrderTabActivity.getOrderTab().setListOrderVO(mOrderList);
			OrderTabActivity.getOrderTab().setSelectedIdx(0);
//			DialogHelper.closeProgressDialog();
		}

	}
}
