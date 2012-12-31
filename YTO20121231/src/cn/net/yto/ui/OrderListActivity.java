package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import cn.net.yto.biz.OrderManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.OrderVO;

public class OrderListActivity extends BaseActivity {
	private OrderTabActivity mOrderTab;
	private Calendar mFromCanlendar = Calendar.getInstance(Locale.CHINA);
	private Calendar mToCalendar = Calendar.getInstance(Locale.CHINA);
	private EditText mFromDateEdit;
	private EditText mToDateEdit;
	private Spinner mUserSpinner;
	private Button mBackBtn;
	private Button mDetailBtn;
	private Button mQueryBtn;

	private ListView mListView;
	private OrderListAdapter mOrderListAdapter;
	private ArrayList<OrderVO> mOrderList;
	private int mSeletedIdx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContext();
		initViews();
	}
	
	@Override
	protected void onStart() {
		hideInputMethod(mFromDateEdit);
		super.onStart();
	}

	private void initContext() {
		mOrderList = new ArrayList<OrderVO>();
//		mOrderList.addAll(mOrderManager.getOrderList());
		mOrderTab = OrderTabActivity.getOrderTab();
	}

	private void initViews() {
		setContentView(R.layout.activity_order_list);

		mUserSpinner = (Spinner) findViewById(R.id.order_state_spinner);

		mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		mDetailBtn = (Button) findViewById(R.id.detail_btn);
		mDetailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startOrderDetailActivity();
			}
		});
		mQueryBtn = (Button) findViewById(R.id.query_btn);
		mQueryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new QueryOrderTask().execute(
						mFromDateEdit.getText().toString(), mToDateEdit
								.getText().toString(), "0");
			}
		});

		mToDateEdit = (EditText) findViewById(R.id.to_date_edit);
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

		mFromDateEdit = (EditText) findViewById(R.id.from_date_edit);
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

		mListView = (ListView) findViewById(android.R.id.list);

		mOrderListAdapter = new OrderListAdapter(this, mOrderList);
		mListView.setAdapter(mOrderListAdapter);
		mListView.setSelected(true);
		mListView.setFocusable(true);
		mListView.setFastScrollEnabled(true);
		mListView.requestFocus();

		mListView.setEmptyView(findViewById(R.id.empty));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSeletedIdx = position;
				startOrderDetailActivity();
			}
		});
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mSeletedIdx = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
//				mSeletedIdx = 0;
			}
		});
	}

	protected void startOrderDetailActivity() {
		if(mOrderList.size() > 0) {
	//		mOrderTab.setCurrentOrder(mOrderList.get(mSeletedIdx));						
		} else {
	//		mOrderTab.setCurrentOrder(null);
		}
		if (mOrderTab != null) {
			mOrderTab.OpenOrderDetailActivity();
		} else {
			Intent intent = new Intent();
			intent.setClass(mContext, OrderDetailActivity.class);
			startActivity(intent);
		}
	}

	private void updateDateDisplay(EditText editText, Calendar date) {
		editText.setText(CommonUtils.getFormatedDateTime("yyyy-MM-dd",
				date.getTimeInMillis()));
	}

	private class OrderListAdapter extends ArrayAdapter<OrderVO> {
		public OrderListAdapter(Context context, List<OrderVO> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final OrderVO order = mOrderList.get(position);

			if (convertView == null) {
				final LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_item,
						parent, false);
			}

			TextView text = (TextView) convertView
					.findViewById(R.id.order_number_text);
			text.setText(order.getOrderNo());

			text = (TextView) convertView.findViewById(R.id.order_state_text);
			text.setText("order state " + order.getAdditionState());

			text = (TextView) convertView
					.findViewById(R.id.order_read_state_text);
			text.setText("read state " + position);

			text = (TextView) convertView
					.findViewById(R.id.order_upload_state_text);
			text.setText("upload state " + position);
			return convertView;
		}
	}
	
	private class QueryOrderTask extends AsyncTask<String, Void, List<OrderVO>> {
		@Override
		protected List<OrderVO> doInBackground(String... params) {
			String startTime = params[0];
			String endTime = params[1];
			String code = params[2];
			LogUtils.d(TAG, startTime +" "+endTime+" "+code);
			OrderManager service = mAppContext.getOrderService();
			
			return service.queryOrderVO(startTime, endTime, code);
		}

		@Override
		protected void onPreExecute() {
			DialogHelper.showProgressDialog(OrderListActivity.this, "updating");
		}

		@Override
		protected void onPostExecute(List<OrderVO> result) {
			mOrderList.clear();
			mOrderList.addAll(result);
			mOrderListAdapter.notifyDataSetChanged();
			DialogHelper.closeProgressDialog();
		}

	}

}
