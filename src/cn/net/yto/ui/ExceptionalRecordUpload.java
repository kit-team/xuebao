package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import cn.net.yto.R;
import cn.net.yto.models.DbTempUtils;

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
        mListView = (ListView) findViewById(android.R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView.addHeaderView(headView);

        mAdapter = new SignListAdapter(getApplicationContext());
        mAdapter.setData(DbTempUtils.query(this));
        mListView.setAdapter(mAdapter);
    }
}
