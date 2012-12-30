package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import cn.net.yto.R;

public class ExceptionalRecordUpload extends Activity {

    private ListView mListView;
    private SignListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.exceptional_record_upload);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.exceptional_record_upload_title);

        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView.addHeaderView(headView);
        mAdapter = new SignListAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExceptionalRecordUpload.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(ExceptionalRecordUpload.this);
            }
        });

        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO upload select items
            }
        });
    }
}
