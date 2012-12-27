package cn.net.yto.ui;

import cn.net.yto.R;
import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;

public class SignDeleteActivity extends Activity {

    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_delete);

        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.list_detail_head, null));
    }
}
