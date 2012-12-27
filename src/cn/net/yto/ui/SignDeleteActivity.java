package cn.net.yto.ui;

import cn.net.yto.R;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.app.Activity;

public class SignDeleteActivity extends Activity {

    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_delete);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_delete_title);

        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(headView);
        mListView.setBackgroundColor(128);
        mListView.setAdapter(new SignListAdapter(getApplicationContext()));
    }
}
