package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import cn.net.yto.R;

/**
 * 收派件统计查询
 */
public class ExpressStatisticalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.express_statistical);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.express_statistical_title);

        initViews();
    }

    private void initViews() {

    }
}
