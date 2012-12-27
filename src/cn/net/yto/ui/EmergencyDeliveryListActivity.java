package cn.net.yto.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.net.yto.R;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class EmergencyDeliveryListActivity extends ListActivity{

	private ListView mListView;
	private EmergencyDeliveryAdapter<EmergencyDelivery> mEmDeliveryAdapter;
	private ArrayList<EmergencyDelivery> mEmDeliveries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_delivery_list);
		mListView = getListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.setupList();
	}
	
	private void setupList() {
		int resId = R.layout.emergency_delivery_item;
		// TODO: use data from the database
		mEmDeliveries = new ArrayList<EmergencyDeliveryListActivity.EmergencyDelivery>();
		for(int i = 0; i < 5; i++) {
			EmergencyDelivery delivery = new EmergencyDelivery("订单 "+String.valueOf(i), new Date(), "工号123456", "张三", "xxxxx");
			mEmDeliveries.add(delivery);
		}
		mEmDeliveryAdapter = new EmergencyDeliveryAdapter<EmergencyDelivery>(this, resId, mEmDeliveries);
		setListAdapter(mEmDeliveryAdapter);
	}
	
	private final class EmergencyDeliveryAdapter<T> extends ArrayAdapter<T> {
		int mResource;
		
		public EmergencyDeliveryAdapter(Context context,
				int resource, List<T> objects) {
			super(context, resource, objects);
			this.mResource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout emergencyDeliveryView;
			EmergencyDelivery delivery = (EmergencyDelivery)getItem(position);
			if (convertView == null) {
				emergencyDeliveryView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater li;
				li = (LayoutInflater)getContext().getSystemService(inflater);
				li.inflate(mResource, emergencyDeliveryView, true);
			} else {
				emergencyDeliveryView = (LinearLayout)convertView;
			}
			
			TextView deadTimeView = (TextView)emergencyDeliveryView.findViewById(R.id.emergency_delivery_deadtime);
			SimpleDateFormat sdFormat = new SimpleDateFormat("hh/dd/MM/yy");
			deadTimeView.setText(sdFormat.format(delivery.getDeadTime()));
			TextView orderIdView = (TextView)emergencyDeliveryView.findViewById(R.id.emergency_delivery_id);
			orderIdView.setText(delivery.mOrderId);
			TextView messageContentView = (TextView)emergencyDeliveryView.findViewById(R.id.emergency_delivery_message_content);
			messageContentView.setText(delivery.getMessageContent());
			TextView receiverIdView = (TextView)emergencyDeliveryView.findViewById(R.id.emergency_delivery_receiver_id);
			receiverIdView.setText(delivery.getReceiverId());
			TextView receiverNameView = (TextView)emergencyDeliveryView.findViewById(R.id.emergency_delivery_receiver_name);
			receiverNameView.setText(delivery.getReceiverName());

			return emergencyDeliveryView;
		}
	}
	
	private final class EmergencyDelivery {
		// 催办面单号
		private String mOrderId;
		
		// 催办时间
		private Date mDeadTime;
		
		// 接收人工号
		private String mReceiverId;
		
		// 接收人姓名
		private String mReceiverName;
		
		// 消息内容
		private String mMessageContent;
		
		public EmergencyDelivery(String orderId, Date deadTime, String receiverId, String receiverName, String messageContent) {
			this.mOrderId = orderId;
			this.mDeadTime = deadTime;
			this.mReceiverId = receiverId;
			this.mReceiverName = receiverName;
			this.mMessageContent = messageContent;
		}
		
		public String getmOrderId() {
			return mOrderId;
		}

		public void setmOrderId(String mOrderId) {
			this.mOrderId = mOrderId;
		}

		public Date getDeadTime() {
			return mDeadTime;
		}

		public void setDeadTime(Date deadTime) {
			this.mDeadTime = deadTime;
		}

		public String getReceiverId() {
			return mReceiverId;
		}

		public void setmReceiverId(String receiverId) {
			this.mReceiverId = receiverId;
		}

		public String getReceiverName() {
			return mReceiverName;
		}

		public void setReceiverName(String receiverName) {
			this.mReceiverName = receiverName;
		}

		public String getMessageContent() {
			return mMessageContent;
		}

		public void setmMessageContent(String messageContent) {
			this.mMessageContent = messageContent;
		}
	}
	
}
