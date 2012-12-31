package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import cn.net.yto.R;

public class SignDeleteActivity extends Activity {

    private ListView mListView = null;
    private SignListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_delete);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_delete_title);

        initViews();
    }

    private void initViews() {
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(headView);
        mAdapter = new SignListAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignDeleteActivity.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(SignDeleteActivity.this);
            }
        });

        findViewById(R.id.btn_save).setVisibility(View.GONE);
    }

}