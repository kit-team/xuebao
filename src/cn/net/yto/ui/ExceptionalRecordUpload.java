package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.net.yto.R;
import cn.net.yto.models.DbTempUtils;

public class ExceptionalRecordUpload extends Activity implements OnItemClickListener {

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
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.onItemClick(position - 1); // position == 0 is head view.
    }
}
