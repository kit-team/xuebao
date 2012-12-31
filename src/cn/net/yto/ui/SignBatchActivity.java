package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import cn.net.yto.R;
import cn.net.yto.models.DbTempUtils;
import cn.net.yto.models.SignedLog;
import cn.net.yto.models.SignedLog.Satisfaction;

public class SignBatchActivity extends Activity {

    private ListView mListView = null;
    private SignListAdapter mAdapter = null;

    private EditText mWaybillNo; // 运单号
    private Spinner mSignTypeSpinner; // 签收类型
    private RadioGroup mSatisfactory; // 满意度
    private EditText mReceipient; // 签收人

    private String[] mSignTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_batch);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_batch_title);

        initViews();

        mSignTypeString = getResources().getStringArray(R.array.sign_type);
    }

    public void initViews() {
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(headView);
        mAdapter = new SignListAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        mWaybillNo = (EditText) findViewById(R.id.edit_tracking_number);
        mSignTypeSpinner = (Spinner) findViewById(R.id.sign_type);
        mSatisfactory = (RadioGroup) findViewById(R.id.group_satisfactory);
        mReceipient = (EditText) findViewById(R.id.edit_receipient);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignBatchActivity.this.finish();
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(SignBatchActivity.this);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputVaules()) {
                    DbTempUtils.insert(SignBatchActivity.this, getSignedLogForSave());

                    mWaybillNo.setText("");
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

        signedLog.setWaybillNo(mWaybillNo.getText().toString());
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
