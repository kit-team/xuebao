package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter;
import cn.net.yto.ui.menu.SignListDeleteAdapter;
import cn.net.yto.ui.menu.SignListItemClickListener;

public class SignDeleteActivity extends Activity {

    private ListView mListView = null;
    private SignListAdapter mAdapter = null;

    private EditText mTrackingNumber = null;

    private SignedLogManager mSignedLogMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_delete);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_delete_title);

        mSignedLogMgr = new SignedLogManager(this);

        initViews();
    }

    private void initViews() {
        mTrackingNumber = (EditText) findViewById(R.id.edit_tracking_number);

        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        headView.findViewById(R.id.head_sign_typ).setVisibility(View.VISIBLE);
        headView.findViewById(R.id.head_receipient).setVisibility(View.GONE);
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(headView);
//        mAdapter = new SignListAdapter(getApplicationContext());
        mAdapter = new SignListDeleteAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String wayBillNo = mTrackingNumber.getText().toString();
                if (TextUtils.isEmpty(wayBillNo)) {
                    return;
                }
                mAdapter.setData(mSignedLogMgr.queryByWaybillno(wayBillNo));
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignDeleteActivity.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(SignDeleteActivity.this, mSignedLogMgr);
            }
        });

        findViewById(R.id.btn_save).setVisibility(View.GONE);
    }

}