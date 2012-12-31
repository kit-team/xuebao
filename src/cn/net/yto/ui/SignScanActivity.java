package cn.net.yto.ui;

import java.io.IOException;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.models.DbTempUtils;
import cn.net.yto.models.SignedLog;
import cn.net.yto.models.SignedLog.Satisfaction;
import cn.net.yto.models.SignedLog.SignedState;
import cn.net.yto.models.SignedLog.UploadStatus;
import cn.net.yto.utils.ToastUtils;

public class SignScanActivity extends Activity {
    private static final String TAG = "ViewPagerTest";

    private ViewPager viewPager = null;
    private LayoutInflater mInflater = null;
    private ArrayList<View> mPageViews = null;
    private ArrayList<View> mTabViews = null;

    private SignSuccessView mSignSuccessView = null;
    private SignFailedView mSignFailedView = null;
    private OrderQueryView mOrderQueryView = null;

    private OnClickListener mTabItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String tabTag = v.getTag().toString();
            View tabView = null;
            for (int idx = 0; idx < mTabViews.size(); idx++) {
                tabView = mTabViews.get(idx);
                if (tabView.getTag().equals(tabTag)) {
                    tabView.setBackgroundResource(R.drawable.menu_bg);
                    viewPager.setCurrentItem(idx);
                } else {
                    tabView.setBackgroundDrawable(null);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_scan_view);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_scan_title);

        mInflater = getLayoutInflater();

        mPageViews = new ArrayList<View>();
        mTabViews = new ArrayList<View>();

        TextView tabSignSuccess = (TextView) findViewById(R.id.tab_sign_success);
        tabSignSuccess.setOnClickListener(mTabItemClickListener);
        TextView tabSignFailed = (TextView) findViewById(R.id.tab_sign_failed);
        tabSignFailed.setOnClickListener(mTabItemClickListener);
        TextView tabWaybillDetail = (TextView) findViewById(R.id.tab_waybill_detail);
        tabWaybillDetail.setOnClickListener(mTabItemClickListener);
        TextView tabOrderQuery = (TextView) findViewById(R.id.tab_order_query);
        tabOrderQuery.setOnClickListener(mTabItemClickListener);
        mTabViews.add(tabSignSuccess);
        mTabViews.add(tabSignFailed);
        mTabViews.add(tabWaybillDetail);
        mTabViews.add(tabOrderQuery);

        View signedSuccessView = mInflater.inflate(R.layout.signed_success_view, null);
        View signedFailedView = mInflater.inflate(R.layout.signed_failed_view, null);
        View waybillDetailView = mInflater.inflate(R.layout.waybill_detail_view, null);
        View orderQueryView = mInflater.inflate(R.layout.order_query_view, null);
        mPageViews.add(signedSuccessView);
        mPageViews.add(signedFailedView);
        mPageViews.add(waybillDetailView);
        mPageViews.add(orderQueryView);

        mSignSuccessView = new SignSuccessView(signedSuccessView);
        mOrderQueryView = new OrderQueryView(orderQueryView);
        mSignFailedView = new SignFailedView(signedFailedView);

        viewPager = (ViewPager) findViewById(R.id.slideMenu);
        viewPager.setAdapter(new SlideMenuAdapter());
        viewPager.setOnPageChangeListener(new SlideMenuChangeListener());
    }

    public void onTabClicked(View view) {
        String tabTag = view.getTag().toString();
        View tabView = null;
        for (int idx = 0; idx < mTabViews.size(); idx++) {
            tabView = mTabViews.get(idx);
            if (tabView.equals(tabTag)) {
                tabView.setBackgroundResource(R.drawable.menu_bg);
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

    // �����˵�����¼�������
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
        private EditText mCollectionAmount; // 代收金额
        private EditText mFreightToCollect; // 到付金额
        private Spinner mSignTypeSpinner; // 签收类型
        private RadioGroup mSatisfactory; // 满意度
        private EditText mReceipient; // 签收人

        private String[] mSignTypeString = null;

        SignSuccessView(View view) {
            initView(view);

            mSignTypeString = getResources().getStringArray(R.array.sign_type);
        }

        private void initView(View view) {
            mWaybillNo = (EditText) view.findViewById(R.id.edit_tracking_number);
            mCollectionAmount = (EditText) view.findViewById(R.id.collection_amount);
            mFreightToCollect = (EditText) view.findViewById(R.id.freight_to_collect);
            mSignTypeSpinner = (Spinner) view.findViewById(R.id.spinner_sign_type);
            mSatisfactory = (RadioGroup) view.findViewById(R.id.satisfactory_score);
            mReceipient = (EditText) view.findViewById(R.id.edit_receipient);

            view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInputVaules()) {
                        DbTempUtils.insert(SignScanActivity.this, getSignedLogForSave());
                        mWaybillNo.setText("");
                        mCollectionAmount.setText("");
                        mFreightToCollect.setText("");
                        mReceipient.setText("");
                    }
                }
            });
        }

        private boolean checkInputVaules() {
            if (TextUtils.isEmpty(mWaybillNo.getText().toString())) {
                ToastUtils.showToast("运单号不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mReceipient.getText().toString())) {
                ToastUtils.showToast("签收人不能为空");
                return false;
            }

            return true;
        }

        private SignedLog getSignedLogForSave() {
            SignedLog signedLog = new SignedLog();

            signedLog.setEmpCode("");
            signedLog.setWaybillNo(mWaybillNo.getText().toString());
            if (!TextUtils.isEmpty(mCollectionAmount.getText())) {
                signedLog.setAmountCollected(Long.valueOf(mCollectionAmount.getText().toString()));
            }
            if (!TextUtils.isEmpty(mFreightToCollect.getText())) {
                signedLog.setAmountAgency(Long.valueOf(mFreightToCollect.getText().toString()));
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
                        DbTempUtils.insert(SignScanActivity.this, getSignedLogForSave());

                        mWaybillNo.setText("");
                        mExceptionDescription.setText("");
                    }
                }
            });
        }

        public boolean checkInputVaules() {
            if (TextUtils.isEmpty(mWaybillNo.getText().toString())) {
                ToastUtils.showToast("运单号不能为空");
                return false;
            }
            return true;
        }

        public SignedLog getSignedLogForSave() {
            SignedLog signedLog = new SignedLog();

            signedLog.setWaybillNo(mWaybillNo.getText().toString());
            final int exceptIdx = mExceptionReasonSpinner.getSelectedItemPosition();
            signedLog.setSignedStateInfo(mExceptionReasons[exceptIdx]);
            signedLog.setExpSignedDescription(mExceptionDescription.getText().toString());
            return signedLog;
        }
    }

    class OrderQueryView {
        private TextView mDateFrom;
        private TextView mDateTo;
        private Button mChooseFromDate;
        private Button mChooseToDate;
        private Spinner mDateFromSpinner;
        private Spinner mDateToSpinner;

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
            mDateFrom = (TextView) view.findViewById(R.id.text_date_from);
            mDateTo = (TextView) view.findViewById(R.id.text_date_to);
            mChooseFromDate = (Button) view.findViewById(R.id.btn_choose_date_from);
            mChooseFromDate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dateDialog = new DatePickerDialog(SignScanActivity.this,
                            mDateFromListener, mYearFrom, mMonthFrom, mDayFrom);
                    dateDialog.show();
                }
            });
            mChooseToDate = (Button) view.findViewById(R.id.btn_choose_date_to);
            mChooseToDate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dateDialog = new DatePickerDialog(SignScanActivity.this,
                            mDateToListener, mYearTo, mMonthTo, mDayTo);
                    dateDialog.show();
                }
            });
            mDateFromSpinner = (Spinner) view.findViewById(R.id.spinner_date_from);
            mDateToSpinner = (Spinner) view.findViewById(R.id.spinner_date_to);
            ArrayAdapter<String> fakeDateAdapter = new ArrayAdapter<String>(SignScanActivity.this,
                    android.R.layout.simple_spinner_item, getResources().getStringArray(
                            R.array.fake_date));
            fakeDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mDateFromSpinner.setAdapter(fakeDateAdapter);
            mDateToSpinner.setAdapter(fakeDateAdapter);

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
            builder.append(year).append("年").append(month).append("月").append(day).append("日");
            view.setText(builder.toString());
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
