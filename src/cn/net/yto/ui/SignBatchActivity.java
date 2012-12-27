package cn.net.yto.ui;

import cn.net.yto.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Spinner;

public class SignBatchActivity extends Activity {

    private Spinner mSignTypeSp = null;
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_batch);

        mSignTypeSp = (Spinner) findViewById(R.id.signType);

        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.list_detail_head, null));
    }

}
