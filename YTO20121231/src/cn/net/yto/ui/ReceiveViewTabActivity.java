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

public class ReceiveViewTabActivity extends TabActivity {
	private static ReceiveViewTabActivity sTab;
	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		sTab = this;

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("list")
				.setIndicator(createTabView(getString(R.string.receive_view)))
				.setContent(getListActivityIntent()));

		mTabHost.addTab(mTabHost.newTabSpec("detail")
				.setIndicator(createTabView(getString(R.string.detail_info)))
				.setContent(getDetailActivityIntent()));
	}

	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(text);
		return view;
	}

	private Intent getListActivityIntent() {
		Intent intent = new Intent(this, ReceiveViewListActivity.class);
		return intent;
	}

	private Intent getDetailActivityIntent() {
		Intent intent = new Intent(this, ReceiveViewDetailActivity.class);
		return intent;
	}
	
	public static ReceiveViewTabActivity getOrderTab(){
		return sTab;
	}
	
	public void OpenOrderDetailActivity(){
		mTabHost.setCurrentTabByTag("detail");
	}
	
	public void OpenOrderListActivity(){
		mTabHost.setCurrentTabByTag("list");
	}
}
