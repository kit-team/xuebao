package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.net.yto.R;

public class AdditionalSignPersonInfo extends Activity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_sign_person_info);
        mListView = (ListView) findViewById(android.R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.list_detail_head, null);
        mListView.addHeaderView(headView);
        mListView.setBackgroundColor(128);
        mListView.setAdapter(new SignListAdapter(getApplicationContext()));
    }
}
