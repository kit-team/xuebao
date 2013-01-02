package cn.net.yto.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.Satisfaction;

public class SignScanActivity extends Activity {
    private static final String TAG = "ViewPagerTest";
    
    private ViewPager viewPager = null;
    private LayoutInflater mInflater = null;
    private ArrayList<View> mPageViews = null;
    private ArrayList<View> mTabViews = null;

    private SignSuccessView mSignSuccessView = null;
    private SignFailedView mSignFailedView = null;
    private OrderQueryView mOrderQueryView = null;

    private SignedLogManager mSignedLogMgr = null;

    private OnClickListener mTabItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final String pageTag = v.getTag().toString();
            switchPage(pageTag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_scan_view);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_scan_title);

        mInflater = getLayoutInflater();
        mSignedLogMgr = ((AppContext)getApplication()).getSignedLogManager();

        mPageViews = new ArrayList<View>();
        mTabViews = new ArrayList<View>();

        TextView tabSignSuccess = (TextView) findViewById(R.id.tab_sign_success);
        tabSignSuccess.setOnClickListener(mTabItemClickListener);
        TextView tabSignFailed = (TextView) findViewById(R.id.tab_sign_failed);
        tabSignFailed.setOnClickListener(mTabItemClickListener);
        TextView tabOrderQuery = (TextView) findViewById(R.id.tab_order_query);
        tabOrderQuery.setOnClickListener(mTabItemClickListener);
        mTabViews.add(tabSignSuccess);
        mTabViews.add(tabSignFailed);
        mTabViews.add(tabOrderQuery);

        View signedSuccessView = mInflater.inflate(R.layout.signed_success_view, null);
        View signedFailedView = mInflater.inflate(R.layout.signed_failed_view, null);
        View orderQueryView = mInflater.inflate(R.layout.order_query_view, null);
        mPageViews.add(signedSuccessView);
        mPageViews.add(signedFailedView);
        mPageViews.add(orderQueryView);

        mSignSuccessView = new SignSuccessView(signedSuccessView);
        mOrderQueryView = new OrderQueryView(orderQueryView);
        mSignFailedView = new SignFailedView(signedFailedView);

        viewPager = (ViewPager) findViewById(R.id.slideMenu);
        viewPager.setAdapter(new SlideMenuAdapter());
        viewPager.setOnPageChangeListener(new SlideMenuChangeListener());
    }

    private void updateSignedLog(SignedLogVO data) {
    	if (mSignSuccessView != null) {
    		switchPage(getResources().getString(R.string.tab_sign_success));
    		mSignSuccessView.updateViews(data);
    	}
    }
    
    public void switchPage(String pageTag) {
        View tabView = null;
        for (int idx = 0; idx < mTabViews.size(); idx++) {
            tabView = mTabViews.get(idx);
            if (tabView.getTag().equals(pageTag)) {
                tabView.setBackgroundResource(R.drawable.menu_bg);
                viewPager.setCurrentItem(idx);
            } else {
                tabView.setBackgroundDrawable(null);
            }
        }
    }

    class SlideMenuAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mPageViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mPageViews.get(position));
            return mPageViews.get(position);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            // Nothing need to do
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
            // Nothing need to do
        }

        @Override
        public void finishUpdate(View container) {
            // Nothing need to do
        }
    }

    class SlideMenuChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            Log.d(TAG, "onPageScrollStateChanged: " + String.valueOf(arg0));

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onPageScrolled: " + String.valueOf(arg0));

        }

        @Override
        public void onPageSelected(int pageIndex) {
            Log.d(TAG, "onPageSelected: " + String.valueOf(pageIndex));
            View tabView = null;
            for (int idx = 0; idx < mTabViews.size(); idx++) {
                tabView = mTabViews.get(idx);
                if (pageIndex != idx) {
                    tabView.setBackgroundDrawable(null);
                } else {
                    tabView.setBackgroundResource(R.drawable.menu_bg);
                }
            }
        }
    }

    class SignSuccessView {
        private EditText mWaybillNo; // 运单号
        private EditText mAmountCollected; // 到付金额
        private EditText mAmountAgency; // 代收金额
        private Spinner mSignTypeSpinner; // 签收类型
        private RadioGroup mSatisfactory; // 满意度
        private EditText mReceipient; // 签收人

        private String[] mSignTypeString = null;

        SignSuccessView(View view) {
            initView(view);

            mSignTypeString = getResources().getStringArray(R.array.sign_type);
        }

        public void updateViews(SignedLogVO data) {
        	mWaybillNo.setText(data.getWaybillNo());
        	mWaybillNo.setEnabled(false);
        	mAmountCollected.setText(String.valueOf(data.getAmountCollected()));
        	mAmountAgency.setText(String.valueOf(data.getAmountAgency()));
        	int signOffTypePos = 0;
        	for(int i = 0; i < mSignTypeString.length; i++) {
        		if (mSignTypeString[i].equals(data.getSignOffTypeCode())) {
        			signOffTypePos = i;
        			break;
        		}
        	}
        	mSignTypeSpinner.setSelection(signOffTypePos);
        	
			switch (data.getSatisfaction()) {
			case VERY_SATISFIED:
				mSatisfactory.check(R.id.very_satisfactory);
				break;
			case SATISFIED:
				mSatisfactory.check(R.id.satisfactory);
				break;
			case DISSATISFIED:
				mSatisfactory.check(R.id.unsatisfactory);
				break;
			default:
				break;
			}
        	mReceipient.setText(data.getRecipient());
        }
        
        private void initView(View view) {
            mWaybillNo = (EditText) view.findViewById(R.id.edit_tracking_number);
            mAmountCollected = (EditText) view.findViewById(R.id.edit_amount_collected);
            mAmountAgency = (EditText) view.findViewById(R.id.edit_amount_agency);
            mSignTypeSpinner = (Spinner) view.findViewById(R.id.spinner_sign_type);
            mSatisfactory = (RadioGroup) view.findViewById(R.id.satisfactory_score);
            mReceipient = (EditText) view.findViewById(R.id.edit_receipient);

            view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInputVaules()) {
                        // save to DB
                        boolean result = mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                        if (result) {
                            mWaybillNo.setText("");
                            mAmountCollected.setText("");
                            mAmountAgency.setText("");
                            mReceipient.setText("");
                        }
                        ToastUtils.showOperationToast(Operation.SAVE, result);

                        // Upload to server
                        mSignedLogMgr.submitSignedLog(getSignedLogForSave(), AppContext.getAppContext()
                                .getDefaultContext());
                    }
                }
            });
        }

        private boolean checkInputVaules() {
            if (TextUtils.isEmpty(mWaybillNo.getText().toString())) {
                ToastUtils.showToast(R.string.toast_waybillno_notify);
                return false;
            }
            if (TextUtils.isEmpty(mAmountCollected.getText().toString())) {
            	ToastUtils.showToast(R.string.toast_amount_collected_notify);
                return false;
            }
            if(TextUtils.isEmpty(mAmountAgency.getText().toString())) {
            	ToastUtils.showToast(R.string.toast_amount_agency_notify);
                return false;
            }
            if (TextUtils.isEmpty(mReceipient.getText().toString())) {
                ToastUtils.showToast(R.string.toast_receipient_notify);
                return false;
            }
            return true;
        }

        private SignedLogVO getSignedLogForSave() {
            SignedLogVO signedLog = new SignedLogVO();
            signedLog.setEmpCode("");
            signedLog.setWaybillNo(mWaybillNo.getText().toString());
            if (!TextUtils.isEmpty(mAmountCollected.getText())) {
                signedLog.setAmountCollected(Long.valueOf(mAmountCollected.getText().toString()));
            }
            if (!TextUtils.isEmpty(mAmountAgency.getText())) {
                signedLog.setAmountAgency(Long.valueOf(mAmountAgency.getText().toString()));
            }

            final int typeIdx = mSignTypeSpinner.getSelectedItemPosition();
            signedLog.setSignOffTypeCode(mSignTypeString[typeIdx]);

            switch (mSatisfactory.getCheckedRadioButtonId()) {
            case R.id.very_satisfactory:
                signedLog.setSatisfaction(Satisfaction.VERY_SATISFIED);
                break;
            case R.id.unsatisfactory:
                signedLog.setSatisfaction(Satisfaction.DISSATISFIED);
                break;
            default:
                signedLog.setSatisfaction(Satisfaction.SATISFIED);
                break;
            }

            signedLog.setRecipient(mReceipient.getText().toString());

            return signedLog;
        }
    }

    class SignFailedView {
        private EditText mWaybillNo = null;
        private Spinner mExceptionReasonSpinner = null;
        private EditText mExceptionDescription = null;

        private String[] mExceptionReasons;

        public SignFailedView(View view) {
            initView(view);
            mExceptionReasons = getResources().getStringArray(R.array.exception_reason);
        }

        private void initView(View view) {
            mWaybillNo = (EditText) view.findViewById(R.id.edit_tracking_number);
            mExceptionReasonSpinner = (Spinner) view.findViewById(R.id.spinner_exception_reason);
            mExceptionDescription = (EditText) view.findViewById(R.id.failed_description);
            view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInputVaules()) {
                        // save to DB
                        boolean result = mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                        if (result) {
                            mWaybillNo.setText("");
                            mExceptionDescription.setText("");
                        }
                        ToastUtils.showOperationToast(Operation.SAVE, result);
                        
                        // upload to server
                        mSignedLogMgr.submitSignedLog(getSignedLogForSave(), AppContext.getAppContext()
                                .getDefaultContext());
                    }
                }
            });
        }

        private boolean checkInputVaules() {
            if (TextUtils.isEmpty(mWaybillNo.getText().toString())) {
                ToastUtils.showToast(R.string.toast_waybillno_notify);
                return false;
            }
            return true;
        }

        private SignedLogVO getSignedLogForSave() {
            SignedLogVO signedLog = new SignedLogVO();

            signedLog.setWaybillNo(mWaybillNo.getText().toString());
            final int exceptIdx = mExceptionReasonSpinner.getSelectedItemPosition();
            signedLog.setSignedStateInfo(mExceptionReasons[exceptIdx]);
            signedLog.setExpSignedDescription(mExceptionDescription.getText().toString());
            return signedLog;
        }
    }

    class OrderQueryView {
        private EditText mDateFrom;
        private EditText mDateTo;
        private Button mSignedLogQuery;
        private Button mSignedLogModify;
        private ListView mSignedLogList;
        private SignListAdapter mAdapter = null;
       // private Spinner mDateFromSpinner;
       // private Spinner mDateToSpinner;

        private int mYearFrom;
        private int mMonthFrom;
        private int mDayFrom;
        private int mYearTo;
        private int mMonthTo;
        private int mDayTo;

        OrderQueryView(View view) {
            initView(view);
        }

        private void initView(View view) {
            mDateFrom = (EditText) view.findViewById(R.id.text_date_from);
            mDateFrom.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
            mDateFrom.clearFocus();
            mDateFrom.setOnTouchListener(new View.OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				if (event.getAction() == MotionEvent.ACTION_UP) {
    					Dialog dateDialog = new DatePickerDialog(SignScanActivity.this,
	                            mDateFromListener, mYearFrom, mMonthFrom, mDayFrom);
	                    dateDialog.show();
    				}
    				return true;
    			}
    		});

            mDateTo = (EditText) view.findViewById(R.id.text_date_to);
            mDateTo.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
            mDateTo.clearFocus();
            mDateTo.setOnTouchListener(new View.OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				if (event.getAction() == MotionEvent.ACTION_UP) {
    					Dialog dateDialog = new DatePickerDialog(SignScanActivity.this,
	                            mDateToListener, mYearTo, mMonthTo, mDayTo);
	                    dateDialog.show();
    				}
    				return true;
    			}
    		});
            
            mSignedLogQuery = (Button) view.findViewById(R.id.btn_query_signed_log);
            mSignedLogQuery.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						TimUtils.dumpDatabase(getPackageName());
					} catch (IOException e) {
						e.printStackTrace();
					}
					final Date dateFrom = getFromDate();
					final Date dateTo = getToDate();
					mAdapter.setData(mSignedLogMgr.querySignedLogByTime(dateFrom, dateTo));
				}
			});
            
            mSignedLogModify = (Button)view.findViewById(R.id.btn_signed_log_modify);
            mSignedLogModify.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ArrayList<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
					if (signedLogs.size() > 0) {
						updateSignedLog(mAdapter.getSelectedSignedLog().get(0));
					}
				}
			});
            
            View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
            mSignedLogList = (ListView) view.findViewById(R.id.list_signed_log);
            mSignedLogList.addHeaderView(headView);
            mAdapter = new SignListAdapter(getApplicationContext());
            mAdapter.setSingleSelection(true);
            mAdapter.setData(mSignedLogMgr.queryAllSignedLog());
            mSignedLogList.setAdapter(mAdapter);
            mSignedLogList.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));
            final Calendar c = Calendar.getInstance();
            mYearFrom = c.get(Calendar.YEAR);
            mMonthFrom = c.get(Calendar.MONTH);
            mDayFrom = c.get(Calendar.DAY_OF_MONTH);
            mYearTo = c.get(Calendar.YEAR);
            mMonthTo = c.get(Calendar.MONTH);
            mDayTo = c.get(Calendar.DAY_OF_MONTH);
            updateDate(mYearFrom, mMonthFrom, mDayFrom, mDateFrom);
            updateDate(mYearTo, mMonthTo, mDayTo, mDateTo);
        }

        private void updateDate(int year, int month, int day, TextView view) {
            StringBuilder builder = new StringBuilder();
            builder.append(year).append("-").append(month + 1).append("-").append(day);
            view.setText(builder.toString());
        }
        
        private Date getFromDate() {
        	return new Date(mYearFrom - 1900, mMonthFrom, mDayFrom, 0, 0, 0);
        }
        
        private Date getToDate() {
        	return new Date(mYearTo - 1900, mMonthTo, mDayTo, 23, 59, 59);
        }

        private DatePickerDialog.OnDateSetListener mDateFromListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYearFrom = year;
                mMonthFrom = monthOfYear;
                mDayFrom = dayOfMonth;
                updateDate(mYearFrom, mMonthFrom, mDayFrom, mDateFrom);
            }
        };

        private DatePickerDialog.OnDateSetListener mDateToListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYearTo = year;
                mMonthTo = monthOfYear;
                mDayTo = dayOfMonth;
                updateDate(mYearTo, mMonthTo, mDayTo, mDateTo);
            }
        };

    }

}
