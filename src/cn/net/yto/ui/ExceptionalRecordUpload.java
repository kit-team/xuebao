package cn.net.yto.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Spinner;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.UploadStatus;

public class ExceptionalRecordUpload extends Activity {

    private ListView mListView;
    private SignListAdapter mAdapter = null;

    private Spinner mUploadStateSpinner = null;
    private String[] mUploadState = null;

    private SignedLogManager mSignedLogMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.exceptional_record_upload);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.exceptional_record_upload_title);

        mSignedLogMgr = new SignedLogManager(this);

        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView.addHeaderView(headView);
        mAdapter = new SignListAdapter(getApplicationContext());
        mAdapter.setSingleSelection(true);
        mAdapter.setData(mSignedLogMgr.queryByUploadSataus(UploadStatus.NOT_UPLOAD));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        mUploadState = getResources().getStringArray(R.array.array_upload_state);
        mUploadStateSpinner = (Spinner) findViewById(R.id.spinner_upload_state);

        findViewById(R.id.btn_query_sate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = mUploadStateSpinner.getSelectedItemPosition();
                String state = mUploadState[index];
                if (index == 0) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(UploadStatus.UPLOADING));
                } else if (index == 1) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(UploadStatus.UPLOAD_FAILURE));
                } else if (index == 2) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(UploadStatus.UPLOAD_SUCCESS));
                }
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExceptionalRecordUpload.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(ExceptionalRecordUpload.this, mSignedLogMgr);
            }
        });

        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
                if (signedLogs.isEmpty()) {
                    ToastUtils.showToast("请选择要上传的运单");
                    return;
                }
                SignedLogVO signedLog = signedLogs.get(0);

                mSignedLogMgr.upload(signedLog, AppContext.getAppContext().getDefaultContext());
            }
        });
    }
}
