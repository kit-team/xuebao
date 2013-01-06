package cn.net.yto.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;
import cn.net.yto.ui.menu.SignListItemClickListener;

public class SignDeleteActivity extends Activity {

    private ListView mListView = null;
    private SignListBasicAdapter mAdapter = null;

    private EditText mTrackingNumber = null;

    private SignedLogManager mSignedLogMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_delete);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_delete_title);

        mSignedLogMgr = new SignedLogManager(this);

        initViews();
    }
    
    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_tracking_number);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_sign_type);
        head2.setVisibility(View.VISIBLE);
        TextView head3 = (TextView) headView.findViewById(R.id.head3);
        head3.setText(R.string.list_head_receipient);
        head3.setVisibility(View.VISIBLE);
        return headView;
    }

    private void initViews() {
        mTrackingNumber = (EditText) findViewById(R.id.edit_tracking_number);

        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(getListHeadView());
        mAdapter = new SignListDeleteAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String wayBillNo = mTrackingNumber.getText().toString();
                if (TextUtils.isEmpty(wayBillNo)) {
                    return;
                }
                mAdapter.setData(mSignedLogMgr.queryByWaybillno(wayBillNo));
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignDeleteActivity.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(SignDeleteActivity.this, mSignedLogMgr);
            }
        });

        findViewById(R.id.btn_save).setVisibility(View.GONE);
    }

    private class SignListDeleteAdapter extends SignListBasicAdapter {

        public SignListDeleteAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            ItemHolder itemHolder = (ItemHolder) convertView.getTag();
            final SignListItem item = mData.get(position);
            itemHolder.view1.setText(item.getWaybillNo()); // 账单号
            itemHolder.view1.setVisibility(View.VISIBLE);
            itemHolder.view2.setText(item.getSignType()); // 签收类型
            itemHolder.view2.setVisibility(View.VISIBLE);
            itemHolder.view3.setText(item.getSignTime()); // 签收时间
            itemHolder.view3.setVisibility(View.VISIBLE);

            return convertView;
        }
    }
}