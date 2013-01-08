package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;

/**
 * 收派件统计查询
 */
public class ExpressStatisticalActivity extends Activity {

    private EditText mDateFrom;
    private EditText mDateTo;
    
    private int mYearFrom;
    private int mMonthFrom;
    private int mDayFrom;
    private int mYearTo;
    private int mMonthTo;
    private int mDayTo;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.express_statistical);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.express_statistical_title);

        initViews();
    }
    
    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_time);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_branch);
        head2.setVisibility(View.VISIBLE);
        TextView head3 = (TextView) headView.findViewById(R.id.head3);
        head3.setText(R.string.list_head_operation);
        head3.setVisibility(View.VISIBLE);
        return headView;
    }

    private void initViews() {
        mDateFrom = (EditText) findViewById(R.id.text_date_from);
        mDateTo =(EditText) findViewById(R.id.text_date_to);
        
        findViewById(R.id.btn_query_express_statistical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressStatisticalActivity.this.finish();
            }
        });
    }
}
