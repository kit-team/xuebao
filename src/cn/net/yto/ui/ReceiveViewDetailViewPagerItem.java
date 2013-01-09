package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import cn.net.yto.common.Constants;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.CityVO;
import cn.net.yto.vo.EffectiveTypeVO;
import cn.net.yto.vo.OrderChannelVO;
import cn.net.yto.vo.ReceiveVO;
import cn.net.yto.vo.RecvexpVO;

public class ReceiveViewDetailViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	private ReceiveViewTabActivity mReceiveViewTab;
	private ReceiveVO mReceiveVO;
	private ListView mListView;
	private ReceiveViewDetailAdapter mReceiveViewDetailAdapter;
	private Button mPreviouslBtn;
	private Button mNextBtn;
	private TextView mOrderType;
	
	private List<KeyValue> mReceiveViewDetailList;
	
	public ReceiveViewDetailViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_view_detail, null);
		
		initContext();
		initView();
	}

	@Override
	public View getItemView() {
		return mRootView;
	}
	
	private void initContext(){
		mReceiveViewTab = ReceiveViewTabActivity.getOrderTab();
		mReceiveViewDetailList = new ArrayList<KeyValue>();
	}
	
	private void initView(){
		mOrderType = (TextView) mRootView.findViewById(R.id.order_type);
		mPreviouslBtn = (Button) mRootView.findViewById(R.id.btn_previous);
		mNextBtn = (Button) mRootView.findViewById(R.id.btn_next);
		mPreviouslBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mReceiveVO == null) return;
				int idx = ReceiveViewTabActivity.getOrderTab().getSelectedIdx() - 1;
				if(idx < 0) idx = ReceiveViewTabActivity.getOrderTab().getReceives().size()-1;
				ReceiveViewTabActivity.getOrderTab().setSelectedIdx(idx);
				mReceiveVO = ReceiveViewTabActivity.getOrderTab().getReceives().get(idx);
				mReceiveViewDetailList.clear();
				convertReceiveToDisMap();
				mReceiveViewDetailAdapter.notifyDataSetChanged();
			}
		});
		mNextBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mReceiveVO == null) return;
				int idx = ReceiveViewTabActivity.getOrderTab().getSelectedIdx() + 1;
				if(idx == ReceiveViewTabActivity.getOrderTab().getReceives().size()) idx = 0;
				ReceiveViewTabActivity.getOrderTab().setSelectedIdx(idx);
				mReceiveVO = ReceiveViewTabActivity.getOrderTab().getReceives().get(idx);
				mReceiveViewDetailList.clear();
				convertReceiveToDisMap();
				mReceiveViewDetailAdapter.notifyDataSetChanged();
			}
		});
		
		mListView = (ListView) mRootView.findViewById(android.R.id.list);
		mReceiveViewDetailAdapter = new ReceiveViewDetailAdapter(mContext, mReceiveViewDetailList);
		mListView.setAdapter(mReceiveViewDetailAdapter);
		mListView.setSelected(false);
		mListView.setFocusable(false);
		mListView.setFastScrollEnabled(true);
		mListView.setDividerHeight(0);
	}
	
	@Override
	public void onPageSelected() {
		super.onPageSelected();
		mOrderType.setVisibility(View.GONE);
		List<ReceiveVO> list = ReceiveViewTabActivity.getOrderTab().getReceives();
		int idx = ReceiveViewTabActivity.getOrderTab().getSelectedIdx();
		if(list != null && idx < list.size()){
			mReceiveVO = list.get(idx);
		} else{
			mReceiveVO = null;			
		}
		mReceiveViewDetailList.clear();
		if(mReceiveVO == null) {
			DialogHelper.showToast(mContext, R.string.order_tips_select_data);
			mPreviouslBtn.setEnabled(false);
			mNextBtn.setEnabled(false);
			mReceiveViewDetailAdapter.notifyDataSetChanged();
			return;
		}
		mOrderType.setVisibility(View.VISIBLE);
		mPreviouslBtn.setEnabled(true);
		mNextBtn.setEnabled(true);
		convertReceiveToDisMap();
		mReceiveViewDetailAdapter.notifyDataSetChanged();
		initOrderType();
	}
	
	private void initOrderType(){
		if(isNormal() && hasOrderNo()){
			mOrderType.setText(R.string.receive_order_normal);
		} else if(isNormal() && !hasOrderNo()){
			mOrderType.setText(R.string.receive_no_order);
		} else if(!isNormal() && !hasOrderNo()){
			mOrderType.setText(R.string.receive_no_order_unusaul);
		} else if(!isNormal() && !hasOrderNo()){
			mOrderType.setText(R.string.receive_order_unusaul);
		}
	}
	
	private boolean isNormal(){
		return "-1".equals(mReceiveVO.getCurrentState());
	}
	
	private boolean hasOrderNo(){
		String orderNo = mReceiveVO.getOrderNo();
		if(orderNo != null && orderNo.length() > 0){
			return true;
		} else{
			return false;
		}
	}
	
	private void convertReceiveToDisMap(){
		String orderNo = mReceiveVO.getOrderNo();
		if(orderNo != null && orderNo.length() > 0){
			addOneKeyValue(R.string.order_number, orderNo);						
		}
//		addOneKeyValue(R.string.source_org_code, mReceiveVO.getSourceOrgCode());
		String waybillNo = mReceiveVO.getWaybillNo();
		if(waybillNo.startsWith(Constants.RECEIVE_NO_WAYBILLNO_PREX)){
//			addOneKeyValue(R.string.way_bill_no, "");
		} else{
			addOneKeyValue(R.string.way_bill_no, waybillNo);
		}
		String parentWaybillNo = mReceiveVO.getParentWaybillNo();
		if(parentWaybillNo != null && parentWaybillNo.length() > 0){
			addOneKeyValue(R.string.piece_of_ticket, mContext.getString(R.string.ok));
		} else{
			addOneKeyValue(R.string.piece_of_ticket, mContext.getString(R.string.no));
		}
		addOneKeyValue(R.string.rec_time_text, mReceiveVO.getSalesmanTime());
		addOneKeyValue(R.string.city_id, getCityById(mReceiveVO.getCityId()));
		addOneKeyValue(R.string.state_upload_desc, mReceiveVO.getGetStatus());
		String state = mReceiveVO.getCurrentState();
		if(!"-1".equals(state)) {
			addOneKeyValue(R.string.state_upload_failed_reason, getExceptionDesByCode(state));
		}
		addOneKeyValue(R.string.order_type_tode, getEffectiveByCode(mReceiveVO.getPracticalType()));
		addOneKeyValue(R.string.tran_pri_text, mReceiveVO.getFeeAmt());
		String isValid = mReceiveVO.getIsInvalid();
		if("1".equals(isValid)){
			addOneKeyValue(R.string.cancel_or_not, mContext.getString(R.string.receive_cancel));
		} else if("2".equals(isValid)){
			addOneKeyValue(R.string.cancel_or_not, mContext.getString(R.string.state_receive_replace_success));
		}else{
			addOneKeyValue(R.string.cancel_or_not, mContext.getString(R.string.state_normal));
		}
		addOneKeyValue(R.string.weight_text, mReceiveVO.getWeighWeight());
		addOneKeyValue(R.string.pkg_qty, mReceiveVO.getPkgQty());
		addOneKeyValue(R.string.volume_text, mReceiveVO.getGoodsSize());
		String inverted = mReceiveVO.getInvertedPay();
		String invertedPay = ("0".equals(inverted) ? mContext.getString(R.string.no) 
				: mContext.getString(R.string.ok));
		addOneKeyValue(R.string.label_is_freight_collect, invertedPay);
		String instead = mReceiveVO.getInsteadPay();
		String insteadPay = ("0".equals(instead) ? mContext.getString(R.string.no) 
				: mContext.getString(R.string.ok));
		addOneKeyValue(R.string.label_is_agency, insteadPay);
		addOneKeyValue(R.string.dest_address, mReceiveVO.getDestAddress());
		addOneKeyValue(R.string.rec_client_text, mReceiveVO.getReceiverName());
		addOneKeyValue(R.string.rec_call_text, mReceiveVO.getReceiverPhone());
		
//		addOneKeyValue(R.string.backform_num_text, mReceiveVO.getReturnWaybillNo());
//		addOneKeyValue(R.string.mainform_num_text, mReceiveVO.getParentWaybillNo());
//		addOneKeyValue(R.string.employ_code, mReceiveVO.getEmpCode());
//		addOneKeyValue(R.string.unusual_reason, mReceiveVO.getCausesException());
//		addOneKeyValue(R.string.order_channel_code, getOrderChannelByCode(mReceiveVO.getOrderChannelCode()));
//		addOneKeyValue(R.string.label_cargo_amount, mReceiveVO.getGoodsAmount());
//		addOneKeyValue(R.string.sender_name, mReceiveVO.getCustomerName());
//		addOneKeyValue(R.string.sender_address, mReceiveVO.getSendAddress());
	}
	
	private String getCityById(String cityId){
		CityVO city = BasicDataManager.getInstance().getCityById(cityId);
		StringBuilder cityName = new StringBuilder();
		if(city != null){
			String pcode = city.getParentCityCode();
			if(pcode != null && pcode.length() > 0){
				CityVO parentCity = BasicDataManager.getInstance().getParentCityById(pcode);
				if(parentCity != null){
					cityName.append(parentCity.getCityName());
				}
			}
			cityName.append(city.getCityName());
		}
		return cityName.toString();
	}
	
	private String getOrderChannelByCode(String code){
		List<OrderChannelVO> orderChannels = BasicDataManager.getInstance().getOrderChannelList();
		for(int i = 0; i < orderChannels.size(); i++){
			OrderChannelVO vo = orderChannels.get(i);
			if(code.equals(vo.getOrderChannelCode())){
				return vo.getOrderChannelName();
			}
		}
		return "";
	}
	
	private String getEffectiveByCode(String code){
		List<EffectiveTypeVO> effectives = BasicDataManager.getInstance().getEffectiveTypeList();
		for(int i = 0; i < effectives.size(); i++){
			EffectiveTypeVO vo = effectives.get(i);
			if(code.equals(vo.getCode())){
				return vo.getName();
			}
		}
		return "";
	}
	
	private String getExceptionDesByCode(String code){
		List<RecvexpVO> exceptions = BasicDataManager.getInstance().getRecvexpList();
		for(int i = 0; i < exceptions.size(); i++){
			RecvexpVO vo = exceptions.get(i);
			if(code.equals(vo.getFailureCode())){
				return vo.getFailureReason();
			}
		}
		return "";
	}
	
	private void addOneKeyValue(int resId, String content){
		KeyValue kv = new KeyValue();
		kv.key = resId;
		kv.value = content;
		mReceiveViewDetailList.add(kv);
	}
	
	class KeyValue{
		int key;
		String value;
	}
	
	class ReceiveViewDetailAdapter extends ArrayAdapter<KeyValue>{

		public ReceiveViewDetailAdapter(Context context, List<KeyValue> receiveList) {
			super(context, 0, receiveList);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final KeyValue kv = mReceiveViewDetailList.get(position);

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
