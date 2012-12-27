package cn.net.yto.ui;

import cn.net.yto.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class SignBatchActivity extends Activity {

    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_batch);

        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(headView);
        mListView.setBackgroundColor(128);
        mListView.setAdapter(new SignListAdapter(getApplicationContext()));
    }

}
