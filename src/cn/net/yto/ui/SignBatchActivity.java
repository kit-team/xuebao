package cn.net.yto.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.ui.menu.SignListBasicAdapter.ItemHolder;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import cn.net.yto.vo.SignedLogVO.Satisfaction;

public class SignBatchActivity extends Activity implements OnItemClickListener {

    private ListView mListView = null;
    private SignListBasicAdapter mAdapter = null;

    private EditText mWaybillNo; // 运单号
    private Spinner mSignTypeSpinner; // 签收类型
    private RadioGroup mSatisfactory; // 满意度
    private EditText mReceipient; // 签收人

    private String[] mSignTypeString;

    private SignedLogManager mSignedLogMgr = null;

    private SignedLogVO mSelectedSignedLog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_batch);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_batch_title);

        mSignedLogMgr = ((AppContext) getApplication()).getSignedLogManager();
        mSignTypeString = getResources().getStringArray(R.array.sign_type);

        initViews();
    }
    
    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_tracking_number);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_sign_time);
        head2.setVisibility(View.VISIBLE);
        TextView head3 = (TextView) headView.findViewById(R.id.head3);
        head3.setText(R.string.list_head_receipient);
        head3.setVisibility(View.VISIBLE);
        return headView;
    }

    public void initViews() {
        mListView = (ListView) findViewById(R.id.list_details);
        mListView.addHeaderView(getListHeadView());
        mAdapter = new SignListBatchAdapter(getApplicationContext());
        mAdapter.setSingleSelection(true);
        mAdapter.setData(mSignedLogMgr.queryAllSignedLog());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mWaybillNo = (EditText) findViewById(R.id.edit_tracking_number);
        mSignTypeSpinner = (Spinner) findViewById(R.id.sign_type);
        mSatisfactory = (RadioGroup) findViewById(R.id.group_satisfactory);
        mReceipient = (EditText) findViewById(R.id.edit_receipient);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignBatchActivity.this.finish();
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteSelectedItem(SignBatchActivity.this, mSignedLogMgr);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputVaules()) {
                    boolean result = mSignedLogMgr.saveSignedLog(getSignedLogForSave());
                    if (result) {
                        mWaybillNo.setText("");
                        mSelectedSignedLog = null;
                        mAdapter.setData(mSignedLogMgr.queryAllSignedLog());
                    }
                    ToastUtils.showOperationToast(Operation.SAVE, result);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parten, View v, int position, long id) {
        if (position == 0) { // position is head view
            return;
        }
        mAdapter.onItemClick(position - 1);

        List<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
        if (signedLogs.isEmpty()) {
            mSelectedSignedLog = null;
        } else {
            mSelectedSignedLog = signedLogs.get(0);
        }

        if (mSelectedSignedLog != null) {
            mWaybillNo.setText(mSelectedSignedLog.getWaybillNo());

            String signOffType = mSelectedSignedLog.getSignOffTypeCode();
            for (int i = 0; i < mSignTypeString.length; i++) {
                if (mSignTypeString[i].endsWith(signOffType)) {
                    mSignTypeSpinner.setSelection(i);
                }
            }

            Satisfaction satisfaction = mSelectedSignedLog.getSatisfaction();
            if (satisfaction == Satisfaction.VERY_SATISFIED) {
                ((RadioButton) findViewById(R.id.very_satisfactory)).setChecked(true);
            } else if (satisfaction == Satisfaction.DISSATISFIED) {
                ((RadioButton) findViewById(R.id.unsatisfactory)).setChecked(true);
            } else {
                ((RadioButton) findViewById(R.id.satisfactory)).setChecked(true);
            }

            mReceipient.setText(mSelectedSignedLog.getRecipient());
        }
    }

    private boolean checkInputVaules() {
        if (TextUtils.isEmpty(mWaybillNo.getText().toString())) {
            ToastUtils.showToast(R.string.toast_waybillno_notify);
            return false;
        }
        // if (TextUtils.isEmpty(mReceipient.getText().toString())) {
        // ToastUtils.showToast(R.string.toast_receipient_notify);
        // return false;
        // }

        return true;
    }

    private SignedLogVO getSignedLogForSave() {
        SignedLogVO signedLog = null;
        String waybillno = mWaybillNo.getText().toString();
        if (mSelectedSignedLog != null && waybillno.equals(mSelectedSignedLog.getWaybillNo())) {
            signedLog = mSelectedSignedLog;
        } else {
            signedLog = new SignedLogVO();
        }

        signedLog.setWaybillNo(waybillno);
        final int typeIdx = mSignTypeSpinner.getSelectedItemPosition();
        signedLog.setRecieverSignOff(mSignTypeString[typeIdx]);
        signedLog.setSignOffTypeCode(SignedLogVO.SIGNOFFMAP.get(signedLog.getRecieverSignOff()));        
        if (TextUtils.isEmpty(mReceipient.getText().toString())) {
			final String signOffType = mSignTypeSpinner.getSelectedItem().toString();
			final String post = getResources().getString(R.string.sign_typ_post);
			final String gateKeeper = getResources().getString(R.string.sign_type_gate_keeper);
			if (signOffType.equals(post)) {
				signedLog.setRecieverSignOff(post);
			} else if (signOffType.equals(gateKeeper)) {
				signedLog.setRecieverSignOff(gateKeeper);
			} else {
				signedLog.setRecieverSignOff(getResources().getString(R.string.text_sign_off));
				signedLog.setSignOffTypeCode(SignedLogVO.SIGNOFF_TYPE_SELF);
			}
        } else {
        	signedLog.setRecieverSignOff(mReceipient.getText().toString());
			signedLog.setSignOffTypeCode(SignedLogVO.SIGNOFF_TYPE_SELF);
        }

        switch (mSatisfactory.getCheckedRadioButtonId()) {
        case R.id.very_satisfactory:
            signedLog.setSatisfaction(Satisfaction.VERY_SATISFIED);
            break;
        case R.id.unsatisfactory:
            signedLog.setSatisfaction(Satisfaction.DISSATISFIED);
            break;
        default:
            signedLog.setSatisfaction(Satisfaction.SATISFIED);
            break;
        }
        signedLog.setRecipient(mReceipient.getText().toString());

        return signedLog;
    }
    
    private class SignListBatchAdapter extends SignListBasicAdapter{
        public SignListBatchAdapter(Context context)  {
            super(context);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            ItemHolder itemHolder = (ItemHolder) convertView.getTag();
            final SignListItem item = mData.get(position);
            itemHolder.view1.setText(item.getWaybillNo()); // 账单号
            itemHolder.view1.setVisibility(View.VISIBLE);
            itemHolder.view2.setText(item.getSignTime()); // 签收类型
            itemHolder.view2.setVisibility(View.VISIBLE);
            itemHolder.view3.setText(item.getRecipient()); // 签收时间
            itemHolder.view3.setVisibility(View.VISIBLE);

            return convertView;
        }
    }

}
