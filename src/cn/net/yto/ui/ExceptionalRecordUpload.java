package cn.net.yto.ui;

import java.net.NetPermission;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListBasicAdapter;
import cn.net.yto.ui.menu.SignListItem;
import cn.net.yto.ui.menu.SignListItemClickListener;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.vo.SignedLogVO;

public class ExceptionalRecordUpload extends Activity {

    private ListView mListView;
    private SignListBasicAdapter mAdapter = null;
    private TextView mWayBillNumber;
    
    private Spinner mUploadStateSpinner = null;
    private String[] mUploadState = null;

    private SignedLogManager mSignedLogMgr = SignedLogManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.exceptional_record_upload);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.exceptional_record_upload_title);
        
        initViews();
    }
    
    private View getListHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.list_sign_head, null);
        TextView head1 = (TextView) headView.findViewById(R.id.head1);
        head1.setText(R.string.list_head_tracking_number);
        head1.setVisibility(View.VISIBLE);
        TextView head2 = (TextView) headView.findViewById(R.id.head2);
        head2.setText(R.string.list_head_upload_status);
        head2.setVisibility(View.VISIBLE);
        TextView head3 = (TextView) headView.findViewById(R.id.head3);
        head3.setText(R.string.list_head_comment);
        head3.setVisibility(View.VISIBLE);
        return headView;
    }

    private void initViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.addHeaderView(getListHeadView());
        // mAdapter = new SignListAdapter(getApplicationContext());
        mAdapter = new SignListNotUploadAdapter(getApplicationContext());
        mAdapter.setSingleSelection(true);
        mAdapter.setData(mSignedLogMgr.queryByUploadSataus(SignedLogVO.UPLOAD_STAUTS_WAITFORSEND));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SignListItemClickListener(mAdapter, true));

        mUploadState = getResources().getStringArray(R.array.array_upload_state);
        mUploadStateSpinner = (Spinner) findViewById(R.id.spinner_upload_state);

        findViewById(R.id.btn_query_sate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = mUploadStateSpinner.getSelectedItemPosition();
                String state = mUploadState[index];
                if (index == 0) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(SignedLogVO.UPLOAD_STAUTS_WAITFORSEND));
                } else if (index == 1) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(SignedLogVO.UPLOAD_STAUTS_FAILED));
                } else if (index == 2) {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(SignedLogVO.UPLOAD_STAUTS_SUCCESS));
                }  else {
                    mAdapter.setData(mSignedLogMgr.queryByUploadSataus(SignedLogVO.UPLOAD_STAUTS_WAITFORSEND));
                }
                updateWayBillNumber();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExceptionalRecordUpload.this.finish();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				new AlertDialog.Builder(ExceptionalRecordUpload.this).setTitle("删除签收数据")
						.setMessage("是否删除该数据：" + mAdapter.getSelectedItemWayBillNo())
						.setPositiveButton("是", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								 mAdapter.deleteSelectedItem(ExceptionalRecordUpload.this, mSignedLogMgr);
							}
							}).setNegativeButton("否", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// todo 
								}
							}).show();
            }
        });

        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SignedLogVO> signedLogs = mAdapter.getSelectedSignedLog();
                if (signedLogs.isEmpty()) {
                    ToastUtils.showToast("请选择要上传的运单");
                    return;
                }
                SignedLogVO signedLog = signedLogs.get(0);

                mSignedLogMgr.submitSignedLog(signedLog, AppContext.getAppContext().getDefaultContext());
            }
        });
        
        mWayBillNumber = (TextView) findViewById(R.id.right_count);
        
        updateWayBillNumber();
    }
    
    private void updateWayBillNumber() {
    	if(mAdapter != null) {
    		mAdapter.getCount();
    		mWayBillNumber.setText(String.valueOf(mAdapter.getCount()));
    		mWayBillNumber.postInvalidate();
    	}
    }
    
    private class SignListNotUploadAdapter extends SignListBasicAdapter {

        public SignListNotUploadAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            ItemHolder itemHolder = (ItemHolder) convertView.getTag();
            final SignListItem item = mData.get(position);
            itemHolder.view1.setText(item.getWaybillNo()); // 账单号
            itemHolder.view1.setVisibility(View.VISIBLE);
            itemHolder.view2.setText(item.getUploadStatus()); // 上传状态
            itemHolder.view2.setVisibility(View.VISIBLE);
            itemHolder.view3.setText(item.getComment()); // 备注
            itemHolder.view3.setVisibility(View.VISIBLE);
            return convertView;
        }
    }

}
