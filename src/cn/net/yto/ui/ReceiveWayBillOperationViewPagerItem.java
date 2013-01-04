package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.NetworkUnavailableException;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.message.CommonResponseMsgVO;

public class ReceiveWayBillOperationViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	private ReceiveWayBillTabActivity mRecevieWayBillTab;
	private List<KeyValue> mRecevieWayBillList;
	private ReceiveVO mReceiveVO;
	
	private Button mCancelBtn;
	private Button mExchangeBtn;
	private Button mModifyBtn;
	private ListView mListView;
	private ReceiveWayBillOperationAdapter mReceiveWayBillAdapter;
	
	public ReceiveWayBillOperationViewPagerItem(Activity context,
			ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_way_bill_operateion, null);
		
		initContext();
		initView();
	}

	@Override
	public View getItemView() {
		return mRootView;
	}
	
	private void initContext(){
		mRecevieWayBillTab = ReceiveWayBillTabActivity.getOrderTab();
		mRecevieWayBillList = new ArrayList<KeyValue>();
//		mReceiveVO = getTestReceiveVo();
//		convertReceiveToDisMap();
	}
	
	private void initView(){
		mCancelBtn = (Button) mRootView.findViewById(R.id.btn_cancel);
		mExchangeBtn = (Button) mRootView.findViewById(R.id.btn_exchange);
		mModifyBtn = (Button) mRootView.findViewById(R.id.btn_modify);
		mCancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mReceiveVO == null) return;
				if("1".equals(mReceiveVO.getIsInvalid())){
					DialogHelper.showToast(mRecevieWayBillTab, R.string.receive_cancel_already);
					return;
				}
				new CancelReceiveAsyn().execute(mReceiveVO.getWaybillNo());
			}
		});
		mExchangeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mReceiveVO == null) return;
				if("1".equals(mReceiveVO.getIsInvalid())){
					DialogHelper.showToast(mRecevieWayBillTab, R.string.receive_cancel_already);
					return;
				}
				ReceiveManager.getInstance().setCurNomalReceiveVO(mReceiveVO);
				Intent intent = new Intent();
				intent.setClass(mRecevieWayBillTab, ReceiveReplaceActivity.class);
				mRecevieWayBillTab.startActivity(intent);
			}
		});
		mModifyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mReceiveVO == null) return;
				if("1".equals(mReceiveVO.getIsInvalid())){
					DialogHelper.showToast(mRecevieWayBillTab, R.string.receive_cancel_already);
					return;
				}
				Intent intent = new Intent();
				intent.setClass(mRecevieWayBillTab, ReceiveExpressPageActivity.class);
				ReceiveManager mReceiveManager = ReceiveManager.getInstance();
				mReceiveManager.clearCurNomalReceiveVO();
				mReceiveManager.setCurNomalReceiveVO(mReceiveVO);
				mRecevieWayBillTab.startActivity(intent);
				mRecevieWayBillTab.onBackPressed();
			}
		});
		
		mListView = (ListView) mRootView.findViewById(android.R.id.list);
		mReceiveWayBillAdapter = new ReceiveWayBillOperationAdapter(mContext, mRecevieWayBillList);
		mListView.setAdapter(mReceiveWayBillAdapter);
		mListView.setSelected(false);
		mListView.setFocusable(false);
		mListView.setFastScrollEnabled(true);
		mListView.setDividerHeight(0);
	}
	
	@Override
	public void onPageSelected() {
		super.onPageSelected();
		mReceiveVO = mRecevieWayBillTab.getReceiveVO();
		mRecevieWayBillTab.setReceiveVO(null);
		mRecevieWayBillList.clear();
		if(mReceiveVO == null) {
			DialogHelper.showToast(mRecevieWayBillTab, R.string.order_tips_select_data);
			mCancelBtn.setEnabled(false);
			mExchangeBtn.setEnabled(false);
			mModifyBtn.setEnabled(false);
			mReceiveWayBillAdapter.notifyDataSetChanged();
			return;
		}
		mCancelBtn.setEnabled(true);
		mExchangeBtn.setEnabled(true);
		mModifyBtn.setEnabled(true);
		convertReceiveToDisMap();
		mReceiveWayBillAdapter.notifyDataSetChanged();
	}
	
	private void convertReceiveToDisMap(){
		addOneKeyValue(R.string.order_number, mReceiveVO.getOrderNo());
		addOneKeyValue(R.string.source_org_code, mReceiveVO.getSourceOrgCode());
		addOneKeyValue(R.string.way_bill_no, mReceiveVO.getWaybillNo());
		addOneKeyValue(R.string.city_id, mReceiveVO.getCityId());
		addOneKeyValue(R.string.dest_address, mReceiveVO.getDestAddress());
		addOneKeyValue(R.string.weight_text, mReceiveVO.getWeighWeight());
		addOneKeyValue(R.string.pkg_qty, mReceiveVO.getPkgQty());
		addOneKeyValue(R.string.tran_pri_text, mReceiveVO.getFeeAmt());
		String codeType = mReceiveVO.getPracticalType();
		BasicDataManager manager = BasicDataManager.getInstance();
		EffectiveTypeVO vo = manager.getEffectiveTypeByCode(codeType);
		String c = "";
		if(vo!=null) {
			c = vo.toString();
		}
		addOneKeyValue(R.string.order_type_tode, c);
		addOneKeyValue(R.string.backform_num_text, mReceiveVO.getReturnWaybillNo());
		addOneKeyValue(R.string.mainform_num_text, mReceiveVO.getParentWaybillNo());
		String state = mReceiveVO.getCurrentState();
		String currentState = ("-1".equals(state) ? 
				mContext.getString(R.string.tab_sign_success)
				: mContext.getString(R.string.tab_sign_failed));
		addOneKeyValue(R.string.current_state, currentState);
		addOneKeyValue(R.string.employ_code, mReceiveVO.getEmpCode());
		addOneKeyValue(R.string.volume_text, mReceiveVO.getGoodsSize());
		addOneKeyValue(R.string.unusual_reason, mReceiveVO.getCausesException());
		addOneKeyValue(R.string.order_channel_code, mReceiveVO.getOrderChannelCode());
		String inverted = mReceiveVO.getInvertedPay();
		String invertedPay = ("0".equals(inverted) ? mContext.getString(R.string.no) 
				: mContext.getString(R.string.ok));
		addOneKeyValue(R.string.label_is_freight_collect, invertedPay);
		String instead = mReceiveVO.getInsteadPay();
		String insteadPay = ("0".equals(instead) ? mContext.getString(R.string.no) 
				: mContext.getString(R.string.ok));
		addOneKeyValue(R.string.label_is_agency, insteadPay);
		addOneKeyValue(R.string.label_cargo_amount, mReceiveVO.getGoodsAmount());
		addOneKeyValue(R.string.sender_name, mReceiveVO.getCustomerName());
		addOneKeyValue(R.string.sender_address, mReceiveVO.getSendAddress());
	}
	
	private void addOneKeyValue(int resId, String content){
		KeyValue kv = new KeyValue();
		kv.key = resId;
		kv.value = content;
		mRecevieWayBillList.add(kv);
	}
	
	class KeyValue{
		int key;
		String value;
	}
	
	private class CancelReceiveAsyn extends AsyncTask<String, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			DialogHelper.showProgressDialog(mRecevieWayBillTab, R.string.receive_cancel_request_tips);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			final ReceiveManager manager = ReceiveManager.getInstance();
			Listener listener = new Listener(){
				@Override
				public void onPreSubmit() {
				}

				@Override
				public void onPostSubmit(Object response, Integer responseType) {
					if(response != null){
						CommonResponseMsgVO comm = (CommonResponseMsgVO) response;
						if(comm.getRetVal()==1){
							DialogHelper.showToast(mRecevieWayBillTab, R.string.operate_success);
							mReceiveVO.setIsInvalid("1");
							manager.saveReceive(mReceiveVO);
							mRecevieWayBillTab.onBackPressed();
						} else{
							DialogHelper.showToast(mRecevieWayBillTab, comm.getFailMessage());							
						}
					} else{
						DialogHelper.showToast(mRecevieWayBillTab, R.string.operate_fail);						
					}
				}
			};
			boolean success = false;
			try {
				success = manager.CancelReceive(mRecevieWayBillTab, listener, params[0]);
			} catch (NetworkUnavailableException e) {
				LogUtils.e("ReceiveWayBillOperationViewPagerItem", e);
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
	
	class ReceiveWayBillOperationAdapter extends ArrayAdapter<KeyValue>{

		public ReceiveWayBillOperationAdapter(Context context, List<KeyValue> receiveList) {
			super(context, 0, receiveList);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final KeyValue kv = mRecevieWayBillList.get(position);

			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_detail_item,
						parent, false);
			}

			TextView text = (TextView) convertView
					.findViewById(R.id.key_text);
			text.setText(mContext.getString(kv.key));


			text = (TextView) convertView.findViewById(R.id.value_text);
			text.setText(kv.value);
			return convertView;
		}
	}

}
