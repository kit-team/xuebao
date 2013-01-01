package cn.net.yto.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;

public class AdditionalSignPersonInfo extends Activity implements OnItemClickListener {

    private ListView mListView;
    private SignListAdapter mAdapter = null;

    private EditText mWaybillNo = null;
    private EditText mReceipient = null;

    private SignedLogManager mSignedLogMgr = null;

    private SignedLogVO mSelectedSignedLog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.additional_sign_person_info);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.additional_sign_person_title);
        mSignedLogMgr = new SignedLogManager(this);

        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView.addHeaderView(headView);
        mAdapter = new SignListAdapter(getApplicationContext());
        mAdapter.setSingleSelection(true);
        mAdapter.setData(mSignedLogMgr.queryAllSignedLog());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mWaybillNo = (EditText) findViewById(R.id.edit_tracking_number);
        mReceipient = (EditText) findViewById(R.id.edit_receipient);

        findViewById(R.id.btn_query_additional).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String waybillNo = mWaybillNo.getText().toString();
                if (TextUtils.isEmpty(waybillNo)) {
                    ToastUtils.showToast(R.string.toast_waybillno_notify);
                    return;
                }
                mAdapter.setData(mSignedLogMgr.queryByWaybillno(waybillNo));
                mSelectedSignedLog = null;
            }
        });
        findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedSignedLog != null) {
                    final String receipient = mReceipient.getText().toString();
                    if (TextUtils.isEmpty(receipient)) {
                        ToastUtils.showToast(R.string.toast_receipient_notify);
                        return;
                    }
                    String oldReceipient = mSelectedSignedLog.getRecipient();
                    mSelectedSignedLog.setRecipient(receipient);

                    boolean result = mSignedLogMgr.saveSignedLog(mSelectedSignedLog);
                    ToastUtils.showOperationToast(Operation.SAVE, result);
                    if (result) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mSelectedSignedLog.setRecipient(oldReceipient);
                    }
                }
            }
        });
        // TODO check
        findViewById(R.id.btn_save).setVisibility(View.GONE);
//        .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdditionalSignPersonInfo.this.finish();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parten, View v, int position, long id) {
        if (position == 0) { // position is head view
            return;
        }
        mAdapter.onItemClick(position - 1);
        
        List<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
        if (signedLogs.isEmpty()) {
            mSelectedSignedLog = null;
        } else {
            mSelectedSignedLog = signedLogs.get(0);
        }

        if (mSelectedSignedLog != null) {
            mWaybillNo.setText(mSelectedSignedLog.getWaybillNo());
            mReceipient.setText(mSelectedSignedLog.getRecipient());
        }
    }
}
