package cn.net.yto.ui;

import cn.net.yto.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
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
        String[] signTypes = getResources().getStringArray(R.array.sign_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, signTypes);
        mSignTypeSp.setAdapter(adapter);

        mListView = (ListView) findViewById(R.id.list_details);
    }

}
