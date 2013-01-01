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
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.Satisfaction;

public class SignBatchActivity extends Activity {

    private ListView mListView = null;
    private SignListAdapter mAdapter = null;

    private EditText mWaybillNo; // 运单号
    private Spinner mSignTypeSpinner; // 签收类型
    private RadioGroup mSatisfactory; // 满意度
    private EditText mReceipient; // 签收人

    private String[] mSignTypeString;
    
    private SignedLogManager mSignedLogMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_batch);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_batch_title);
        
        mSignedLogMgr = ((AppContext)getApplication()).getSignedLogManager();
        mSignTypeString = getResources().getStringArray(R.array.sign_type);
        
        initViews();
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
<<<<<<< HEAD
                    boolean result = mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                    if (result) {
                        mWaybillNo.setText("");
                    }
                    ToastUtils.showOperationToast(Operation.SAVE, result);
=======
//                    DbTempUtils.insert(SignBatchActivity.this, getSignedLogForSave());
                    mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                    mSignedLogMgr.upload(getSignedLogForSave(), ((AppContext)getApplication()).getDefaultContext());
                    mWaybillNo.setText("");
>>>>>>> 9bb7afd9c375cb25e1344cd06f6e3fde1afa941d
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

    private SignedLogVO getSignedLogForSave() {
        SignedLogVO signedLog = new SignedLogVO();

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
