package cn.net.yto.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.BarcodeManager;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.biz.UserManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.Satisfaction;

import com.zltd.android.scan.ScanManager;
import com.zltd.android.scan.ScanResultListener;
import com.zltd.android.scan.impl.OneDimensionalSanManager;

public class SignScanActivity extends Activity {
    private static final String TAG = "ViewPagerTest";

    private ViewPager viewPager = null;
    private LayoutInflater mInflater = null;
    private ArrayList<View> mPageViews = null;
    private ArrayList<View> mTabViews = null;

    private SignSuccessView mSignSuccessView = null;
    private SignFailedView mSignFailedView = null;
    private OrderQueryView mOrderQueryView = null;

    private SignedLogManager mSignedLogMgr = SignedLogManager.getInstance();

    private OnClickListener mTabItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final String pageTag = v.getTag().toString();
            switchPage(pageTag);
        }
    };

    private int mSoundSuccessId;
    private ScanManager mScanManager;
    private Vibrator mVibrator;

    private ScanResultListener mScanResultListener = new ScanResultListener() {
        @Override
        public void onScan(ScanManager arg0, byte[] scanResultDate) {
            if (viewPager.getCurrentItem() == 0) {
                mSignSuccessView.onScan(arg0, scanResultDate);
            } else if (viewPager.getCurrentItem() == 1) {
                mSignFailedView.onScan(arg0, scanResultDate);
            }
            mVibrator.vibrate(50);
            mSoundPool.play(mSoundSuccessId, 0.9f, 0.9f, 1, 0, 1f);
        }
    };
    private SoundPool mSoundPool;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_scan_view);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_scan_title);
        TextView tv = (TextView) findViewById(R.id.left_text);
        String userName = UserManager.getInstance().getUserVO().getRealName();
        if (!TextUtils.isEmpty(userName)) {
            tv.append(userName);
        }

        mInflater = getLayoutInflater();

        mPageViews = new ArrayList<View>();
        mTabViews = new ArrayList<View>();

        TextView tabSignSuccess = (TextView) findViewById(R.id.tab_sign_success);
        tabSignSuccess.setOnClickListener(mTabItemClickListener);
        TextView tabSignFailed = (TextView) findViewById(R.id.tab_sign_failed);
        TextView tabWaybillDetail = (TextView) findViewById(R.id.tab_way_detail);
        tabWaybillDetail.setOnClickListener(mTabItemClickListener);
        tabWaybillDetail.setVisibility(View.GONE);
        tabSignFailed.setOnClickListener(mTabItemClickListener);
        TextView tabOrderQuery = (TextView) findViewById(R.id.tab_order_query);
        tabOrderQuery.setOnClickListener(mTabItemClickListener);
        mTabViews.add(tabSignSuccess);
        mTabViews.add(tabSignFailed);
//        mTabViews.add(tabWaybillDetail);
        mTabViews.add(tabOrderQuery);

        View signedSuccessView = mInflater.inflate(R.layout.signed_success_view, null);
        View signedFailedView = mInflater.inflate(R.layout.signed_failed_view, null);
//        View waybillDetailView = mInflater.inflate(R.layout.waybill_detail_view, null);
        View orderQueryView = mInflater.inflate(R.layout.order_query_view, null);
        mPageViews.add(signedSuccessView);
        mPageViews.add(signedFailedView);
//        mPageViews.add(waybillDetailView);
        mPageViews.add(orderQueryView);

        mSignSuccessView = new SignSuccessView(signedSuccessView);
        mOrderQueryView = new OrderQueryView(orderQueryView);
        mSignFailedView = new SignFailedView(signedFailedView);

        viewPager = (ViewPager) findViewById(R.id.slideMenu);
        viewPager.setAdapter(new SlideMenuAdapter());
        viewPager.setOnPageChangeListener(new SlideMenuChangeListener());

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundSuccessId = mSoundPool.load(this, R.raw.success, 1);
    }

    @Override
    protected void onPause() {
        mScanManager.unregisterResultListener();
        mScanManager.setEnable(false);
        mSoundPool.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // register the scan listener.
        mScanManager = new OneDimensionalSanManager(this);
        mScanManager.setEnable(true);
        mScanManager.registerResultListener(mScanResultListener);
        super.onResume();
    }

    private void updateSignedLog(SignedLogVO data) {
        final String state = data.getSignedState();
        if (state.equals("1")) {
            switchPage(getResources().getString(R.string.tab_sign_success));
            mSignSuccessView.updateViews(data);
        } else {
            switchPage(getResources().getString(R.string.tab_sign_failed));
            mSignFailedView.updateViews(data);
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
        private CheckBox mSignTypeCheck; // 签收类型check
        private RadioGroup mSatisfactory; // 满意度
        private EditText mReceipient; // 签收人
        private CheckBox mReceipientCheck; // 签收人check

        private String[] mSignTypeString = null;
        private boolean mModifySign = false;

        SignSuccessView(View view) {
            initView(view);
            mSignTypeString = getResources().getStringArray(R.array.sign_type);
        }

        public void setWabillNoEditText(String str) {
            mWaybillNo.setText(str);
        }

        public void updateViews(SignedLogVO data) {
            mModifySign = true;
            mWaybillNo.setText(data.getWaybillNo());
            mWaybillNo.setEnabled(false);
            mAmountCollected.setText(String.valueOf(data.getAmountCollected()));
            mAmountAgency.setText(String.valueOf(data.getAmountAgency()));
            updateAmountViews(data.getWaybillNo());

            int signOffTypePos = 0;
            for (int i = 0; i < mSignTypeString.length; i++) {
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

        private void updateAmountViews(String waybillno) {
            if (waybillno != null && BarcodeManager.getInstance().isReturnBillNoValid(waybillno)) {
                mAmountCollected.setEnabled(true);
                mAmountAgency.setEnabled(true);
            } else {
                mAmountCollected.setEnabled(false);
                mAmountAgency.setEnabled(false);
            }
        }

        public void onScan(ScanManager arg0, byte[] scanResultDate) {
            // save to DB
            final SignedLogVO signedLogInfo = getSignedLogForSave();
            if (signedLogInfo != null) {
                boolean result = mSignedLogMgr.saveSignedLog(signedLogInfo);
                if (result) {
                    mWaybillNo.setText("");
                    mAmountCollected.setText("");
                    mAmountAgency.setText("");
                    mReceipient.setText("");
                }
                ToastUtils.showOperationToast(Operation.SAVE, result);
            }

            setWabillNoEditText(new String(scanResultDate));
        }

        private void initView(View view) {
            mWaybillNo = (EditText) view.findViewById(R.id.edit_tracking_number);
            mAmountCollected = (EditText) view.findViewById(R.id.edit_amount_collected);
            mAmountAgency = (EditText) view.findViewById(R.id.edit_amount_agency);
            mSignTypeSpinner = (Spinner) view.findViewById(R.id.spinner_sign_type);
            mSignTypeCheck = (CheckBox) view.findViewById(R.id.sign_type_lock);
            mSatisfactory = (RadioGroup) view.findViewById(R.id.satisfactory_score);
            mReceipient = (EditText) view.findViewById(R.id.edit_receipient);
            mReceipientCheck = (CheckBox) view.findViewById(R.id.receipient_input);
            updateAmountViews(null);

            view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInputVaules()) {
                        final SignedLogVO signedLogInfo = getSignedLogForSave();
                        if (!mModifySign) { // not modify
                            final SignedLogVO dbVO = mSignedLogMgr.querySignedLog(signedLogInfo.getWaybillNo());
                            if (dbVO != null) {
                                ToastUtils.showToast(R.string.toast_repeat_data);
                                return;
                            }
                        }
                        // save to DB
                        boolean result = mSignedLogMgr.saveSignedLog(signedLogInfo);
                        if (result) {
                            mWaybillNo.setText("");
                            mAmountCollected.setText("");
                            mAmountAgency.setText("");
                            mReceipient.setText("");
                        }
                        ToastUtils.showOperationToast(Operation.SAVE, result);
                        mModifySign = false;
                        mWaybillNo.setEnabled(true);
                    }
                }
            });
            view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignScanActivity.this.finish();
                }
            });

            mSignTypeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mSignTypeSpinner.setEnabled(false);
                    } else {
                        mSignTypeSpinner.setEnabled(true);
                    }
                }
            });
        }

        private boolean checkInputVaules() {
            final String wayBillNo = mWaybillNo.getText().toString();
            if (TextUtils.isEmpty(wayBillNo)) {
                ToastUtils.showToast(R.string.toast_waybillno_notify);
                return false;
            }
            if (!BarcodeManager.getInstance().isWayBillNoValid(wayBillNo)) {
                ToastUtils.showToast(R.string.toast_invalid_waybillno);
                return false;
            }
            if (mReceipientCheck.isChecked() && TextUtils.isEmpty(mReceipient.getText().toString())) {
                ToastUtils.showToast(R.string.toast_receipient_notify);
                return false;
            }
            return true;
        }

        private SignedLogVO getSignedLogForSave() {
            final String wayBillNo = mWaybillNo.getText().toString();
            if (!BarcodeManager.getInstance().isWayBillNoValid(wayBillNo)) {
                ToastUtils.showToast(R.string.toast_invalid_waybillno);
                ToastUtils.showToast(R.string.toast_waybillno_notify);
                return null;
            }
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
            signedLog.setRecieverSignOff(mSignTypeString[typeIdx]);
            signedLog
                    .setSignOffTypeCode(SignedLogVO.SIGNOFFMAP.get(signedLog.getRecieverSignOff()));

            if (TextUtils.isEmpty(mReceipient.getText().toString())) {
                final String signOffType = mSignTypeSpinner.getSelectedItem().toString();
                final String post = getResources().getString(R.string.sign_typ_post);
                final String gateKeeper = getResources().getString(R.string.sign_type_gate_keeper);
                if (signOffType.equals(post)) {
                    signedLog.setRecieverSignOff(post);
                } else if (signOffType.equals(gateKeeper)) {
                    signedLog.setRecieverSignOff(gateKeeper);
                } else {
                    signedLog.setRecieverSignOff(getResources().getString(R.string.text_sign_off));
                    signedLog.setSignOffTypeCode(SignedLogVO.SIGNOFF_TYPE_SELF);
                }
            } else {
                signedLog.setRecieverSignOff(mReceipient.getText().toString());
                signedLog.setSignOffTypeCode(SignedLogVO.SIGNOFF_TYPE_SELF);
                signedLog.setRecipient(mReceipient.getText().toString());
            }

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

            return signedLog;
        }
    }

    class SignFailedView {
        private EditText mWaybillNo = null;
        private Spinner mExceptionReasonSpinner = null;
        private EditText mExceptionDescription = null;

        private String[] mExceptionNames;
        private String[] mExceptionCodes;
        
        private boolean mModifySign = false;

        public SignFailedView(View view) {
            initView(view);
            mExceptionNames = getResources().getStringArray(R.array.exception_reason);
            mExceptionCodes = getResources().getStringArray(R.array.exception_codes);
        }

        public void setWabillNoEditText(String str) {
            mWaybillNo.setText(str);
        }

        private void initView(View view) {
            mWaybillNo = (EditText) view.findViewById(R.id.edit_tracking_number);
            mExceptionReasonSpinner = (Spinner) view.findViewById(R.id.spinner_exception_reason);
            mExceptionDescription = (EditText) view.findViewById(R.id.failed_description);
            view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInputVaules()) {
                        final SignedLogVO signedLogInfo = getSignedLogForSave();
                        if (!mModifySign) { // not modify
                            final SignedLogVO dbVO = mSignedLogMgr.querySignedLog(signedLogInfo.getWaybillNo());
                            if (dbVO != null) {
                                ToastUtils.showToast(R.string.toast_repeat_data);
                                return;
                            }
                        }
                        // save to DB
                        boolean result = mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                        if (result) {
                            mWaybillNo.setText("");
                            mExceptionDescription.setText("");
                        }
                        ToastUtils.showOperationToast(Operation.EXP_SAVE, result);
                        mModifySign = false;
                        mWaybillNo.setEnabled(true);
                    }
                }
            });
        }

        public void updateViews(SignedLogVO data) {
            mModifySign = true;
            mWaybillNo.setText(data.getWaybillNo());
            mWaybillNo.setEnabled(false);
            mExceptionReasonSpinner.setSelection(getSelectedId(data.getExpSignedDescription()));
        }

        private int getSelectedId(String content) {
            if (content != null) {
                for (int i = 0; i < mExceptionNames.length; i++) {
                    if (content.equals(mExceptionNames[i])) {
                        return i;
                    }
                }
            }

            return -1;
        }

        private boolean checkInputVaules() {
            String waybillno = mWaybillNo.getText().toString();
            if (TextUtils.isEmpty(waybillno)) {
                ToastUtils.showToast(R.string.toast_waybillno_notify);
                return false;
            }
            if (!BarcodeManager.getInstance().isWayBillNoValid(waybillno)) {
                ToastUtils.showToast(R.string.toast_invalid_waybillno);
                return false;
            }
            final int exceptIdx = mExceptionReasonSpinner.getSelectedItemPosition();
            if (TextUtils.isEmpty(mExceptionNames[exceptIdx].trim())) {
                ToastUtils.showToast(R.string.toast_select_exp_reason);
                return false;
            }
            return true;
        }

        private SignedLogVO getSignedLogForSave() {
            SignedLogVO signedLog = new SignedLogVO();
            signedLog.setWaybillNo(mWaybillNo.getText().toString());
            final int exceptIdx = mExceptionReasonSpinner.getSelectedItemPosition();
            signedLog.setExpSignedDescription(mExceptionNames[exceptIdx]);
            signedLog.setSignedState(mExceptionCodes[exceptIdx]);
            return signedLog;
        }

        public void onScan(ScanManager arg0, byte[] scanResultDate) {
            mSignFailedView.setWabillNoEditText(new String(scanResultDate));
        }
    }

    class OrderQueryView {
        private EditText mDateFrom;
        private EditText mDateTo;
        private Button mSignedLogQuery;
        private Button mSignedLogModify;
        private ListView mSignedLogList;
        private SignListBasicAdapter mAdapter = null;
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

            mSignedLogModify = (Button) view.findViewById(R.id.btn_signed_log_modify);
            mSignedLogModify.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
                    if (!signedLogs.isEmpty()) {
                        SignedLogVO vo = mAdapter.getSelectedSignedLog().get(0);
                        if (SignedLogVO.UPLOAD_STAUTS_SUCCESS.equals(vo.getUploadStatus())) {
                            updateSignedLog(mAdapter.getSelectedSignedLog().get(0));
                        } else {
                            ToastUtils.showToast(R.string.toast_modify_not_upload);
                        }
                    }
                }
            });

            mSignedLogList = (ListView) view.findViewById(R.id.list_signed_log);
            mSignedLogList.addHeaderView(getListHeadView());
            mAdapter = new SignListDetailAdapter(getApplicationContext());
            mAdapter.setSingleSelection(true);
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

        private View getListHeadView() {
            View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
            TextView head1 = (TextView) headView.findViewById(R.id.head1); // 运单号
            head1.setText(R.string.list_head_tracking_number);
            head1.setVisibility(View.VISIBLE);
            TextView head2 = (TextView) headView.findViewById(R.id.head2); // 签收人
            head2.setText(R.string.list_head_receipient);
            head2.setVisibility(View.VISIBLE);
            TextView head3 = (TextView) headView.findViewById(R.id.head3); // 签收时间
            head3.setText(R.string.list_head_sign_time);
            head3.setVisibility(View.VISIBLE);
            TextView head4 = (TextView) headView.findViewById(R.id.head4); // 签收状态
            head4.setText(R.string.list_head_sign_type);
            head4.setVisibility(View.VISIBLE);
            TextView secondHead1 = (TextView) headView.findViewById(R.id.head_second1); // 备注
            secondHead1.setText(R.string.list_head_comment);
            secondHead1.setVisibility(View.VISIBLE);
            TextView secondHead2 = (TextView) headView.findViewById(R.id.head_second2); // 上传状态
            secondHead2.setText(R.string.list_head_upload_status);
            secondHead2.setVisibility(View.VISIBLE);
            return headView;
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

        private class SignListDetailAdapter extends SignListBasicAdapter {

            public SignListDetailAdapter(Context context) {
                super(context);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = super.getView(position, convertView, parent);
                ItemHolder itemHolder = (ItemHolder) convertView.getTag();

                final SignListItem item = mData.get(position);
                itemHolder.view1.setText(item.getWaybillNo()); // 运单号
                itemHolder.view1.setVisibility(View.VISIBLE);
                itemHolder.view2.setText(item.getRecipient()); // 签收人
                itemHolder.view2.setVisibility(View.VISIBLE);
                itemHolder.view3.setText(item.getSignTime()); // 签收时间
                itemHolder.view3.setVisibility(View.VISIBLE);
                itemHolder.view4.setText(item.getSignedState()); // 签收状态
                itemHolder.view4.setVisibility(View.VISIBLE);

                itemHolder.viewSecond1.setText(item.getComment()); // 备注
                itemHolder.viewSecond1.setVisibility(View.VISIBLE);
                itemHolder.viewSecond2.setText(item.getUploadStatus()); // 上传状态
                itemHolder.viewSecond2.setVisibility(View.VISIBLE);

                return convertView;
            }
        }
    }

}
