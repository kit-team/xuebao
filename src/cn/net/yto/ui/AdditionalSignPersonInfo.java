package cn.net.yto.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;

import com.zltd.android.scan.ScanManager;
import com.zltd.android.scan.ScanResultListener;
import com.zltd.android.scan.impl.OneDimensionalSanManager;

public class AdditionalSignPersonInfo extends Activity implements OnItemClickListener {

    private ListView mListView;
    private SignListBasicAdapter mAdapter = null;

    private EditText mWaybillNo = null;
    private EditText mRecipient = null;
    private TextView mQueryCount;

    private SignedLogManager mSignedLogMgr = SignedLogManager.getInstance();

    private SignedLogVO mSelectedSignedLog = null;

	private int mSoundSuccessId;
	private ScanManager mScanManager;
	private Vibrator mVibrator;
	
	private ScanResultListener mScanResultListener = new ScanResultListener() {
		@Override
		public void onScan(ScanManager arg0, byte[] scanResultDate) {
			mWaybillNo.setText(new String(scanResultDate));
			mVibrator.vibrate(50);
			mSoundPool.play(mSoundSuccessId, 0.9f, 0.9f, 1, 0, 1f);
		}
	};
	private SoundPool mSoundPool;;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.additional_sign_person_info);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.additional_sign_person_title);

        mQueryCount = (TextView) findViewById(R.id.query_count);

        initViews();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mSoundSuccessId = mSoundPool.load(this, R.raw.success, 1);
    }
    
	@Override
	protected void onPause() {
    	mScanManager.unregisterResultListener();
        mScanManager.setEnable(false);
        mSoundPool.release();
		super.onPause();
	}


	@Override
	protected void onResume() {
        // register the scan listener.
        mScanManager = new OneDimensionalSanManager(this);
        mScanManager.setEnable(true);
        mScanManager.registerResultListener(mScanResultListener);
		super.onResume();
	}
    
    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_tracking_number);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_receipient);
        head2.setVisibility(View.VISIBLE);
        
        return headView;
    }

    private void setQueryCount(List<SignedLogVO> list) {
        String size = "0";
        if (list != null) {
            size = String.valueOf(list.size());
        }

        mQueryCount.setText(size);
    }

    private void initViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.addHeaderView(getListHeadView());
        mAdapter = new SignListAdditionalAdapter(getApplicationContext());
        mAdapter.setSingleSelection(true);
        final List<SignedLogVO> list = mSignedLogMgr.queryAllSignedLogWithNullRecipient();
        mAdapter.setData(list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        setQueryCount(list);

        mWaybillNo = (EditText) findViewById(R.id.edit_tracking_number);
        mRecipient = (EditText) findViewById(R.id.edit_receipient);

        findViewById(R.id.btn_query_additional).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String waybillNo = mWaybillNo.getText().toString().trim();
                List<SignedLogVO> list;

                if (TextUtils.isEmpty(waybillNo)) {
                    list = mSignedLogMgr.queryAllSignedLogWithNullRecipient();
                } else {
                    list = mSignedLogMgr.queryByWaybillnoWithNullRecipient(waybillNo);
                }

                mAdapter.setData(list);
                setQueryCount(list);
                mSelectedSignedLog = null;
            }
        });
        findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecipient.requestFocus();
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String recipient = mRecipient.getText().toString();
                final String id = mWaybillNo.getText().toString();

                if (TextUtils.isEmpty(id)) {
                    ToastUtils.showToast("请选择一条记录");
                    return;
                }

                if (TextUtils.isEmpty(recipient)) {
                    ToastUtils.showToast(R.string.toast_receipient_notify);
                    return;
                }

                final SignedLogVO sign = mSignedLogMgr.querySignedLog(id);
                if (sign == null || !TextUtils.isEmpty(sign.getRecipient())) {
                    ToastUtils.showToast("未找到该运单的相关签收记录！");
                    return;
                }

                final SignedLogVO log = new SignedLogVO();
                log.setRecipient(recipient);
                log.setWaybillNo(id);
                boolean result = mSignedLogMgr.saveSignedLog(log);
                ToastUtils.showOperationToast(Operation.SAVE, result);
                if (result) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdditionalSignPersonInfo.this.finish();
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
            mRecipient.setText(mSelectedSignedLog.getRecipient());
        }
    }

    private class SignListAdditionalAdapter extends SignListBasicAdapter {

        public SignListAdditionalAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            ItemHolder itemHolder = (ItemHolder) convertView.getTag();

            final SignListItem item = mData.get(position);
            itemHolder.view1.setText(item.getWaybillNo()); // 账单号
            itemHolder.view1.setVisibility(View.VISIBLE);
            itemHolder.view2.setText(item.getRecipient()); // 签收人
            itemHolder.view2.setVisibility(View.VISIBLE);

            return convertView;
        }
    }
}
