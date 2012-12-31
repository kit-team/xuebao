package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.vo.OrderVO;

public class OrderDetailActivity extends BaseActivity {
	private OrderTabActivity mOrderTab;
	private OrderVO mOrderVO;
	private ListView mListView;
	private OrderDetailAdapter mOrderDetailAdapter;
	private Button mBackBtn;
	private Button mUsualReceiveBtn;
	private Button mUnusualReceiveBtn;

	private List<KeyValue> mOrderDetailList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContext();
		initViews();
	}

	private void initContext() {
		mOrderTab = OrderTabActivity.getOrderTab();
		mOrderDetailList = new ArrayList<KeyValue>();
//		mOrderVO = mOrderTab.getCurrentOrder();
//		if(mOrderVO == null) mOrderVO = new OrderVO();
//		convertOrderToDispMap();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mOrderDetailList.clear();
//		mOrderVO = mOrderTab.getCurrentOrder();
		if(mOrderVO == null) mOrderVO = new OrderVO();
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
		setContentView(R.layout.activity_order_detail);

		mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				returnToOrderListActivity();
			}
		});

		mUsualReceiveBtn = (Button) findViewById(R.id.usual_receive_btn);
		mUsualReceiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		mUnusualReceiveBtn  = (Button) findViewById(R.id.unusual_receive_btn);
		mUsualReceiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});


		mListView = (ListView) findViewById(android.R.id.list);

		mOrderDetailAdapter = new OrderDetailAdapter(this, mOrderDetailList);
		mListView.setAdapter(mOrderDetailAdapter);
		mListView.setSelected(false);
		mListView.setFocusable(false);
		mListView.setFastScrollEnabled(true);
	}

	protected void returnToOrderListActivity() {
		mOrderTab.OpenOrderListActivity();
	}
	
	class KeyValue{
		int key;
		String value;
	}
	
	private class OrderDetailAdapter extends ArrayAdapter<KeyValue> {
		public OrderDetailAdapter(Context context, List<KeyValue> orderList) {
			super(context, 0, orderList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final KeyValue kv = mOrderDetailList.get(position);

			if (convertView == null) {
				final LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_order_detail_item,
						parent, false);
			}

			TextView text = (TextView) convertView
					.findViewById(R.id.key_text);
			text.setText(getString(kv.key));


			text = (TextView) convertView.findViewById(R.id.value_text);
			text.setText(kv.value);
			return convertView;
		}
	}
}
