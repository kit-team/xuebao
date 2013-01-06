package cn.net.yto.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;

/**
 * 催派件
 */
public class SignPromptActivity extends Activity {
    private ListView mListView = null;
    private SignPromptAdapter mAdapter = null;
    private SignedLogManager mSignedLogMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_prompt);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_prompt_title);

        mSignedLogMgr = new SignedLogManager(this);
        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_sign_out);
        // init data
        mAdapter = new SignPromptAdapter(this);
        mAdapter.setSingleSelection(true);
        // FIXME query 催辦派件
        mAdapter.setData(mSignedLogMgr.queryAllSignedLog());
        mListView.addHeaderView(getListHeadView());
        mListView.setAdapter(mAdapter);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignPromptActivity.this.finish();
            }
        });
    }

    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_prompt_waybillno);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_prompt_empcode);
        head2.setVisibility(View.VISIBLE);
        TextView head3 = (TextView) headView.findViewById(R.id.head3);
        head3.setText(R.string.list_head_prompt_empname);
        head3.setVisibility(View.VISIBLE);
        headView.findViewById(R.id.layout_second).setVisibility(View.VISIBLE);;
        TextView secondhead1 = (TextView) headView.findViewById(R.id.head_second1);
        secondhead1.setText(R.string.list_head_prompt_comment);
        secondhead1.setVisibility(View.VISIBLE);
        TextView secondhead2 = (TextView) headView.findViewById(R.id.head_second2);
        secondhead2.setText(R.string.list_head_prompt_time);
        secondhead2.setVisibility(View.VISIBLE);
        return headView;
    }

    /**
     * "waybillno - receipient id - receipient name"
     * "comment                       - prompt time"
     * 
     * @author Administrator
     * 
     */
    private class SignPromptAdapter extends SignListBasicAdapter {

        public SignPromptAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            ItemHolder itemHolder = (ItemHolder) convertView.getTag();
            final SignListItem item = getItem(position);
            itemHolder.view1.setText(item.getWaybillNo()); // 账单号
            itemHolder.view1.setVisibility(View.VISIBLE);
            itemHolder.view2.setText(item.getEmpCode()); // 接收人工号
            itemHolder.view2.setVisibility(View.VISIBLE);
            itemHolder.view3.setText(item.getEmpName()); // 接收人姓名
            itemHolder.view3.setVisibility(View.VISIBLE);
            itemHolder.viewSecond1.setText(item.getComment()); // 消息內容
            itemHolder.viewSecond1.setVisibility(View.VISIBLE);
            itemHolder.viewSecond2.setText(item.getSignTime()); // 催辦時間
            itemHolder.viewSecond2.setVisibility(View.VISIBLE);
            return convertView;
        }
    }
}
