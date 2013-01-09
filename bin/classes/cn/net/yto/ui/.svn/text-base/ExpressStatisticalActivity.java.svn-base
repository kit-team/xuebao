package cn.net.yto.ui;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import cn.net.yto.R;

/**
 * 收派件统计查询
 */
public class ExpressStatisticalActivity extends Activity {

    private EditText mDateFrom;
    private EditText mDateTo;

    private int mYearFrom;
    private int mMonthFrom;
    private int mDayFrom;
    private int mYearTo;
    private int mMonthTo;
    private int mDayTo;

    private TextView mSignedNumber = null; // 已取订单数
    private TextView mReceiveNumber = null; // 已收件数
    private TextView mSignNumber = null; // 待收订单数
    private TextView mReceiveFailedNumber = null; // 收件失败数
    private TextView mEccNumber = null; // 待派件数
    private TextView mEccDoneNumber = null; // 已派件数
    private TextView mEccExpNumber = null; // 异常派件数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.express_statistical);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.express_statistical_title);

        initViews();
    }

    private void initViews() {
        mDateFrom = (EditText) findViewById(R.id.text_date_from);
        mDateFrom.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        mDateFrom.clearFocus();
        mDateFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Dialog dateDialog = new DatePickerDialog(ExpressStatisticalActivity.this,
                            mDateFromListener, mYearFrom, mMonthFrom, mDayFrom);
                    dateDialog.show();
                }
                return true;
            }
        });

        mDateTo = (EditText) findViewById(R.id.text_date_to);
        mDateTo.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        mDateTo.clearFocus();
        mDateTo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Dialog dateDialog = new DatePickerDialog(ExpressStatisticalActivity.this,
                            mDateToListener, mYearTo, mMonthTo, mDayTo);
                    dateDialog.show();
                }
                return true;
            }
        });

        findViewById(R.id.btn_query_express_statistical).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Date dateFrom = getFromDate();
                        final Date dateTo = getToDate();

                        // start query
                        // mSignedNumber.setText("");
                        // mReceiveNumber.setText("");
                        // mSignNumber.setText("");
                        // mReceiveFailedNumber.setText("");
                        // mEccNumber.setText("");
                        // mEccDoneNumber.setText("");
                        // mEccExpNumber.setText("");
                    }
                });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressStatisticalActivity.this.finish();
            }
        });

        final Calendar c = Calendar.getInstance();
        mYearFrom = c.get(Calendar.YEAR);
        mMonthFrom = c.get(Calendar.MONTH);
        mDayFrom = c.get(Calendar.DAY_OF_MONTH);
        mYearTo = c.get(Calendar.YEAR);
        mMonthTo = c.get(Calendar.MONTH);
        mDayTo = c.get(Calendar.DAY_OF_MONTH);
        updateDate(mYearFrom, mMonthFrom, mDayFrom, mDateFrom);
        updateDate(mYearTo, mMonthTo, mDayTo, mDateTo);

        mSignedNumber = (TextView) findViewById(R.id.signed_number);
        mReceiveNumber = (TextView) findViewById(R.id.receive_number);
        mSignNumber = (TextView) findViewById(R.id.sign_number);
        mReceiveFailedNumber = (TextView) findViewById(R.id.receive_failed_number);
        mEccNumber = (TextView) findViewById(R.id.ecc_number);
        mEccDoneNumber = (TextView) findViewById(R.id.ecc_done_number);
        mEccExpNumber = (TextView) findViewById(R.id.ecc_exp_number);
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
}
