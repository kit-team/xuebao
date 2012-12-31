package cn.net.yto.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import cn.net.yto.R;

public class ReceiveNoOrderTabActivity extends TabActivity {
	private static ReceiveNoOrderTabActivity sTab;
	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		sTab = this;

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("receive")
				.setIndicator(createTabView(getString(R.string.receive_usual)))
				.setContent(getNoOrderReceiveActivityIntent()));

		mTabHost.addTab(mTabHost.newTabSpec("cfad")
				.setIndicator(createTabView(getString(R.string.receive_collection_freight_at_des)))
				.setContent(getMoneyActivityIntent()));
		
		mTabHost.addTab(mTabHost.newTabSpec("return")
				.setIndicator(createTabView(getString(R.string.receive_return_receipt)))
				.setContent(getBackBillActivityIntent()));

		mTabHost.addTab(mTabHost.newTabSpec("multi")
				.setIndicator(createTabView(getString(R.string.piece_of_ticket)))
				.setContent(getMultiActivityIntent()));
	}

	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(text);
		return view;
	}

	private Intent getNoOrderReceiveActivityIntent() {
		Intent intent = new Intent(this, ReceiveNoOrderNomalActivity.class);
		return intent;
	}

	private Intent getMoneyActivityIntent() {
		Intent intent = new Intent(this, ReceiveNoOrderMoneyActivity.class);
		return intent;
	}
	
	private Intent getBackBillActivityIntent() {
		Intent intent = new Intent(this, ReceiveNoOrderBackBillActivity.class);
		return intent;
	}
	
	private Intent getMultiActivityIntent() {
		Intent intent = new Intent(this, ReceiveNoOrderMultiActivity.class);
		return intent;
	}
	
	public static ReceiveNoOrderTabActivity getOrderTab(){
		return sTab;
	}
	
	public void OpenOrderDetailActivity(){
		mTabHost.setCurrentTabByTag("detail");
	}
	
	public void OpenOrderListActivity(){
		mTabHost.setCurrentTabByTag("list");
	}
}
