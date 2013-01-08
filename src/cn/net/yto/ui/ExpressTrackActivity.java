package cn.net.yto.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.ExpressTraceManager;
import cn.net.yto.vo.ExpressTraceVO;

/**
 * 快件跟踪
 */
public class ExpressTrackActivity extends Activity {

    private EditText mEditWaybillNo = null;
    private ListView mListView = null;
    private SignTrackAdapter mAdapter = null;
    
    private ExpressTraceManager mExpressTraceMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.express_track);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.express_track_title);

        mExpressTraceMgr = ExpressTraceManager.getInstance(getApplicationContext());
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
        mEditWaybillNo = (EditText) findViewById(R.id.edit_waybillno);
        mListView = (ListView) findViewById(R.id.list_express_track);
        mListView.addHeaderView(getListHeadView());
        mAdapter = new SignTrackAdapter();
        mListView.setAdapter(mAdapter);

        findViewById(R.id.btn_query_express).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	final String wayBillNo = mEditWaybillNo.toString();
            	if (!TextUtils.isEmpty(wayBillNo)) {
            		//mExpressTraceManager.retrieveExpressTrace(wayBillNo);
            	}
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressTrackActivity.this.finish();
            }
        });
    }

    private class SignTrackAdapter extends BaseAdapter {
        protected LayoutInflater mInflater;
        private ArrayList<ExpressTraceVO> mItems = new ArrayList<ExpressTraceVO>();

        private SignTrackAdapter() {
            mInflater = LayoutInflater.from(ExpressTrackActivity.this);
        }

        public void addItem(ExpressTraceVO item) {
            mItems.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public ExpressTraceVO getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_sign_item, parent, false);
                itemHolder = new ItemHolder();
                itemHolder.time = (TextView) convertView.findViewById(R.id.item1);
                itemHolder.branch = (TextView) convertView.findViewById(R.id.item2);
                itemHolder.operation = (TextView) convertView.findViewById(R.id.item3);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            ExpressTraceVO item = getItem(position);
            itemHolder.time.setText(item.getOpTime());
            itemHolder.time.setVisibility(View.VISIBLE);
            itemHolder.branch.setText(item.getOpName());
            itemHolder.branch.setVisibility(View.VISIBLE);
            itemHolder.operation.setText(item.getOrgCode());
            itemHolder.operation.setVisibility(View.VISIBLE);
            return convertView;
        }

        class ItemHolder {
            TextView time = null;
            TextView branch = null;
            TextView operation = null;
        }
    }
}
