package cn.net.yto.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;

public class ReceiveReplaceActivity extends BaseActivity {
	private TextView mWaybillNo;
	private EditText mNewWaybillNo;
	private Button mOKbtn;
	private Button mCancelBtn;
	private ReceiveVO mReceiveVO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initContext();
		initViews();
	}
	
	private void initContext(){
		mReceiveVO = ReceiveManager.getInstance().getCurNomalReceiveVO();
	}
	
	private void initViews(){
		setContentView(R.layout.activity_receive_replace);
		mWaybillNo = (TextView) findViewById(R.id.receive_replace_wayno);
		mNewWaybillNo = (EditText) findViewById(R.id.receive_replace_new_wayno);
		mOKbtn = (Button) findViewById(R.id.ok_btn);
		mCancelBtn = (Button) findViewById(R.id.cancel_btn);
		mCancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				ReceiveManager.getInstance().setCurNomalReceiveVO(null);
//				ReceiveWayBillTabActivity.getOrderTab().onBackPressed();
			}
		});
		mOKbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String newBillNo = mNewWaybillNo.getText().toString();
				if(newBillNo != null && newBillNo.length() > 5){
					new ReplaceReceiveAsyn().execute(mReceiveVO.getWaybillNo(), newBillNo);
					ReceiveManager.getInstance().setCurNomalReceiveVO(null);
				}
			}
		});
		mWaybillNo.setText(mReceiveVO.getWaybillNo());
	}
	
	private class ReplaceReceiveAsyn extends AsyncTask<String, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			DialogHelper.showProgressDialog(ReceiveReplaceActivity.this, R.string.receive_cancel_request_tips);
		}
		
		@Override
		protected Boolean doInBackground(final String... params) {
			final ReceiveManager manager = AppContext.getAppContext().getReceiveManager();
			Listener listener = new Listener(){
				@Override
				public void onPreSubmit() {
				}
				
				@Override
				public void onPostSubmit(Object response, Integer responseType) {
					if(response != null){
						CommonResponseMsgVO comm = (CommonResponseMsgVO) response;
						if(comm.getRetVal()==1){
							DialogHelper.showToast(ReceiveReplaceActivity.this, R.string.operate_success);
							manager.removeReceive(mReceiveVO);
							mReceiveVO.setWaybillNo(params[1]);
							manager.saveReceive(mReceiveVO);
							ReceiveWayBillTabActivity.getOrderTab().onBackPressed();
						} else{
							DialogHelper.showToast(ReceiveReplaceActivity.this, comm.getFailMessage());							
						}
					} else {
						DialogHelper.showToast(ReceiveReplaceActivity.this, R.string.operate_fail);
					}
					ReceiveReplaceActivity.this.finish();
				}
			};
			boolean success = false;
			try {
				success = manager.ReplaceReceive(ReceiveReplaceActivity.this, listener, params[0],params[1]);
			} catch (NetworkUnavailableException e) {
				LogUtils.e(TAG, e);
				e.printStackTrace();
			}
			return success;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			DialogHelper.closeProgressDialog();
		}
	}
}
