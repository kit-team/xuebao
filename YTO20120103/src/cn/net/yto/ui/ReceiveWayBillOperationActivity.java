package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import cn.net.yto.R;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.ReceiveVO;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ReceiveWayBillOperationActivity extends BaseActivity {
	private ReceiveWayBillTabActivity mRecevieWayBillTab;
	private List<KeyValue> mRecevieWayBillList;
	private ReceiveVO mReceiveVO;
	
	private Button mPreviewBtn;
	private Button mNextBtn;
	private ListView mListView;
	private ReceiveWayBillOperationAdapter mReceiveWayBillAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initContext();
		initView();
	}
	

	private void initContext(){
		mRecevieWayBillTab = ReceiveWayBillTabActivity.getOrderTab();
		mRecevieWayBillList = new ArrayList<KeyValue>();
		mReceiveVO = getTestReceiveVo();
		convertReceiveToDisMap();
	}
	
	private void initView(){
		setContentView(R.layout.activity_receive_way_bill_operateion);
		mPreviewBtn = (Button) findViewById(R.id.btn_preview);
		mNextBtn = (Button) findViewById(R.id.btn_next);
		
		mListView = (ListView) findViewById(android.R.id.list);
		mReceiveWayBillAdapter = new ReceiveWayBillOperationAdapter(this, mRecevieWayBillList);
		mListView.setAdapter(mReceiveWayBillAdapter);
		mListView.setSelected(false);
		mListView.setFocusable(false);
		mListView.setFastScrollEnabled(true);
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
		addOneKeyValue(R.string.order_type_tode, mReceiveVO.getPracticalType());
		addOneKeyValue(R.string.backform_num_text, mReceiveVO.getReturnWaybillNo());
		addOneKeyValue(R.string.mainform_num_text, mReceiveVO.getParentWaybillNo());
		String state = mReceiveVO.getCurrentState();
		String currentState = ("-1".equals(state) ? 
				getString(R.string.tab_sign_success)
				: getString(R.string.tab_sign_failed));
		addOneKeyValue(R.string.current_state, currentState);
		addOneKeyValue(R.string.employ_code, mReceiveVO.getEmpCode());
		addOneKeyValue(R.string.volume_text, mReceiveVO.getGoodsSize());
		addOneKeyValue(R.string.unusual_reason, mReceiveVO.getCausesException());
		addOneKeyValue(R.string.order_channel_code, mReceiveVO.getOrderChannelCode());
		String inverted = mReceiveVO.getInvertedPay();
		String invertedPay = ("0".equals(inverted) ? getString(R.string.no) 
				: getString(R.string.ok));
		addOneKeyValue(R.string.label_is_freight_collect, invertedPay);
		String instead = mReceiveVO.getInsteadPay();
		String insteadPay = ("0".equals(instead) ? getString(R.string.no) 
				: getString(R.string.ok));
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
	
	private ReceiveVO getTestReceiveVo() {
		ReceiveVO vo = new ReceiveVO();
		vo.setOrderNo("5213698546");
		return vo;
	}
	
	private void showNoReceiveWayBillDialog(){
		DialogHelper.showAlertDialog(this,
		getResources().getString(R.string.order_tips_select_data),
		new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogHelper.closeAlertDialog();
				finish();
			}

		}, null);
	}
	
	class ReceiveWayBillOperationAdapter extends ArrayAdapter<KeyValue>{

		public ReceiveWayBillOperationAdapter(Context context, List<KeyValue> receiveList) {
			super(context, 0, receiveList);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final KeyValue kv = mRecevieWayBillList.get(position);

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
