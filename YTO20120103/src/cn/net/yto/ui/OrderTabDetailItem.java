package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.vo.OrderVO;

public class OrderTabDetailItem extends ViewPageItemAbs{

	private View mRootView;
	
	private OrderTabActivity mOrderTab;
	private OrderVO mOrderVO;
	private ListView mListView;
	private OrderDetailAdapter mOrderDetailAdapter;
	private Button mLeftBtn;
	private Button mRightBtn;
	private Button mUsualReceiveBtn;
	private Button mUnusualReceiveBtn;
	private OrderTabActivity tab;

	private List<KeyValue> mOrderDetailList;
	
	OrderTabDetailItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_order_detail, null);
		
		initContext();
		initViews();
	}
	
	private void initContext() {
		tab = OrderTabActivity.getOrderTab();
		mOrderDetailList = new ArrayList<KeyValue>();
//		mOrderVO = tab.getOrders().get(tab.getSelectedIdx());
		mOrderVO = new OrderVO();
		convertOrderToDispMap();
	}
	
	@Override
	public void onPageSelected() {
		super.onPageSelected();
		if(mOrderVO == null) mOrderVO = new OrderVO();
		if(tab.getOrders()!= null) {
			mOrderVO = tab.getOrders().get(tab.getSelectedIdx());			
		}
		updateOrderState();
		mOrderDetailList.clear();
		convertOrderToDispMap();
		mOrderDetailAdapter.notifyDataSetChanged();
	}
	
	private void convertOrderToDispMap(){
		addOneKeyValue(R.string.order_number, mOrderVO.getOrderNo());
		
		addOneKeyValue(R.string.employ_code, mOrderVO.getEmpCode());
		
		addOneKeyValue(R.string.goods_content, mOrderVO.getGoodsContent());
		
		addOneKeyValue(R.string.order_channel_code, mOrderVO.getOrderChannelCode());
		
		addOneKeyValue(R.string.order_type_tode, mOrderVO.getOrderTypeCode());
		
		addOneKeyValue(R.string.order_create_time, mOrderVO.getOrderCreateTime());
		
		addOneKeyValue(R.string.sender_id, mOrderVO.getSenderId());
		
		addOneKeyValue(R.string.sender_name, mOrderVO.getSenderName());
		
		addOneKeyValue(R.string.sender_address, mOrderVO.getSenderAddress());
		
		addOneKeyValue(R.string.sender_mobile, mOrderVO.getSenderMobile());
		
		addOneKeyValue(R.string.sender_phone, mOrderVO.getSenderPhone());
		
		addOneKeyValue(R.string.recipient_name, mOrderVO.getRecipientName());
		
		addOneKeyValue(R.string.recipient_address, mOrderVO.getRecipientAddress());
		
		addOneKeyValue(R.string.weight, "" + mOrderVO.getGoodsTotalWeight());
		
		addOneKeyValue(R.string.remark, mOrderVO.getRemark());
	}

	private void addOneKeyValue(int resId, String content){
		KeyValue kv = new KeyValue();
		kv.key = resId;
		kv.value = content;
		mOrderDetailList.add(kv);
	}

	private void initViews() {

		mLeftBtn = (Button) mRootView.findViewById(R.id.preview_btn);
		mLeftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int idx = tab.getSelectedIdx() - 1;
				if(idx < 0) idx = tab.getOrders().size()-1;
				tab.setSelectedIdx(idx);
				mOrderVO = tab.getOrders().get(tab.getSelectedIdx());
				mOrderDetailList.clear();
				convertOrderToDispMap();
				mOrderDetailAdapter.notifyDataSetChanged();
			}
		});
		mRightBtn = (Button) mRootView.findViewById(R.id.next_btn);
		mRightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int idx = tab.getSelectedIdx() + 1;
				if(idx == tab.getOrders().size()) idx = 0;
				tab.setSelectedIdx(idx);
				mOrderVO = tab.getOrders().get(tab.getSelectedIdx());
				mOrderDetailList.clear();
				convertOrderToDispMap();
				mOrderDetailAdapter.notifyDataSetChanged();
			}
		});

		mUsualReceiveBtn = (Button) mRootView.findViewById(R.id.usual_receive_btn);
		mUsualReceiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSwitchTo()) return;
				Intent intent = new Intent();
				intent.setClass(tab, ReceiveExpressPageActivity.class);
				intent.putExtra("order", mOrderVO);
				tab.startActivity(intent);
			}
		});
		
		mUnusualReceiveBtn  = (Button) mRootView.findViewById(R.id.unusual_receive_btn);
		mUnusualReceiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSwitchTo()) return;
				Intent intent = new Intent();
				intent.setClass(tab, ReceiveNoOrderUnusualActivity.class);
				intent.putExtra("order", mOrderVO);
				tab.startActivity(intent);
			}
		});


		mListView = (ListView) mRootView.findViewById(android.R.id.list);

		mOrderDetailAdapter = new OrderDetailAdapter(mContext, mOrderDetailList);
		mListView.setAdapter(mOrderDetailAdapter);
		mListView.setSelected(false);
		mListView.setFocusable(false);
		mListView.setFastScrollEnabled(true);
		mListView.setDividerHeight(0);
	}

	class KeyValue{
		int key;
		String value;
	}
	
	private void updateOrderState(){
		Integer state = null;
		if(mOrderVO.getAdditionState()!=null){
			state = Integer.parseInt(mOrderVO.getAdditionState());
		}
		boolean update = false;
		if(state.equals(OrderManager.STATE_INIT)){
			update = true;
			mOrderVO.setAdditionState(String.valueOf(OrderManager.STATE_INIT_READED));
		} else if(state.equals(OrderManager.STATE_CANCELED)){
			update = true;
			mOrderVO.setAdditionState(String.valueOf(OrderManager.STATE_CANCELED_READED));
		} else if(state.equals(OrderManager.STATE_ENERGED)){
			update = true;
			mOrderVO.setAdditionState(String.valueOf(OrderManager.STATE_ENERGED_READED));
		}
		if(update){
			AppContext.getAppContext().getOrderService().updateOrder(mOrderVO);
		}
	}
	
	private boolean isSwitchTo(){
		Integer state = Integer.parseInt(mOrderVO.getAdditionState());
		if(OrderManager.STATE_INIT.equals(state) 
				|| OrderManager.STATE_INIT_READED.equals(state)
				|| OrderManager.STATE_ENERGED.equals(state)
				|| OrderManager.STATE_ENERGED_READED.equals(state)){
			return true;
		}
		return false;
	}
	
	private class OrderDetailAdapter extends ArrayAdapter<KeyValue> {
		public OrderDetailAdapter(Context context, List<KeyValue> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final KeyValue kv = mOrderDetailList.get(position);

			if (convertView == null) {
				final LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_detail_item,
						parent, false);
			}

			TextView text = (TextView) convertView.findViewById(R.id.key_text);
			text.setText(mContext.getString(kv.key));


			text = (TextView) convertView.findViewById(R.id.value_text);
			text.setText(kv.value);
			return convertView;
		}
	}

	@Override
	public View getItemView() {
		return mRootView;
	}
}
