package cn.net.yto.ui;

import cn.net.yto.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DispatchOrderList extends ListActivity {
    private TextView mDispatcher;
    private TextView mStaffId;
    private TextView mScannedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispatch_orderlist_view);

        initView();
    }

    private void initView() {
        mDispatcher = (TextView) findViewById(R.id.text_dispatcher);
        // fake dispatcher
        mDispatcher.setText("张三");
        mDispatcher.setVisibility(View.VISIBLE);

        mStaffId = (TextView) findViewById(R.id.text_staff_id);
        // fake staff id
        mStaffId.setText("12345678");
        mStaffId.setVisibility(View.VISIBLE);

        mScannedCount = (TextView) findViewById(R.id.text_scanned_count);
        // fake scanned count
        mScannedCount.setText("15");
        mScannedCount.setVisibility(View.VISIBLE);
    }
}