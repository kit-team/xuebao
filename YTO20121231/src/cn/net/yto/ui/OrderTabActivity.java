package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TabActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.net.yto.R;
import cn.net.yto.biz.OrderManager;
import cn.net.yto.ui.OrderDetailActivity.KeyValue;
import cn.net.yto.ui.SignScanActivity.OrderQueryView;
import cn.net.yto.ui.SignScanActivity.SignFailedView;
import cn.net.yto.ui.SignScanActivity.SignSuccessView;
import cn.net.yto.ui.SignScanActivity.SlideMenuAdapter;
import cn.net.yto.ui.SignScanActivity.SlideMenuChangeListener;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.OrderVO;

public class OrderTabActivity extends BaseActivity {
	private static OrderTabActivity sOrderTab;
	
	private ViewPager mViewPager;
	private List<ViewPageItemAbs> mPageViews;
	private ArrayList<View> mTabViews;
//	private TabHost mTabHost;
	private List<OrderVO> orders;
	private int selectedIdx;
	
	private OrderTabListItem mOrderListView;
	private OrderTabDetailItem mOrderDetailView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.order_tab_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_scan_title);
		
		TextView text = (TextView) findViewById(R.id.left_text);
		text.setText(R.string.order_manage);
		
		sOrderTab = this;
	
		
		setViewPage();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onCreate(savedInstanceState);
	//	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_scan_title);
	/*
		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("list")
				.setIndicator(createTabView(getString(R.string.order_list)))
				.setContent(getOrderListActivityIntent()));

		mTabHost.addTab(mTabHost.newTabSpec("detail")
				.setIndicator(createTabView(getString(R.string.order_detail)))
				.setContent(getOrderDetailActivityIntent()));
				*/
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onDestroy();
	}

	private void setViewPage() {
		
		mPageViews = new ArrayList<ViewPageItemAbs>();
		mTabViews  = new ArrayList<View>();
		
		TextView listTab = (TextView) findViewById(R.id.tab_order_list);
		listTab.setOnClickListener(mTabItemClickListener);
		TextView detailTab = (TextView) findViewById(R.id.tab_order_detail);
		detailTab.setOnClickListener(mTabItemClickListener);

		mTabViews.add(listTab);
		mTabViews.add(detailTab);

		mViewPager = (ViewPager) findViewById(R.id.order_viewPage);
		
		mOrderListView = new OrderTabListItem(this, mViewPager);
		mOrderDetailView = new OrderTabDetailItem(this, mViewPager);

		mPageViews.add(mOrderListView);
		mPageViews.add(mOrderDetailView);
		
		
		if (mViewPager != null) {
			mViewPager.setAdapter(new OrderPageAdapter());  
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		}
		
	}
	

	
	private OnClickListener mTabItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String tabTag = v.getTag().toString();
			View tabView = null;
			for(int idx = 0; idx < mTabViews.size(); idx++) {
				tabView = mTabViews.get(idx);
				if (tabView.getTag().equals(tabTag)) {
		//			tabView.setBackgroundResource(R.drawable.menu_bg);
					mViewPager.setCurrentItem(idx);
				} else {
		//			tabView.setBackgroundDrawable(null);
				}
			}
			
		}
	};
	
	private class PageChangeListener implements OnPageChangeListener {  
  	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  

        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {   
        	
        }  
  
        @Override  
        public void onPageSelected(int pageIndex) {
        	mPageViews.get(pageIndex).onPageSelected();
        	
        	View tabView;
        	for(int idx = 0; idx < mTabViews.size(); idx++) {
        		tabView = mTabViews.get(idx);
        		if (pageIndex != idx) {
        			tabView.setBackgroundDrawable(null);
        		} else {
        			tabView.setBackgroundResource(R.drawable.menu_bg);
        		}
        	}

        }  
    }  
	
	private class OrderPageAdapter extends PagerAdapter {  
	  	  
		@Override  
        public int getCount() {  
            return mPageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View view, Object object) {  
            return view == object;  
        }          
  
        @Override  
        public void destroyItem(View container, int position, Object object) {  
            ((ViewPager) container).removeView(mPageViews.get(position).getItemView());  
        }  
  
        @Override  
        public Object instantiateItem(View container, int position) {  
        	((ViewPager) container).addView(mPageViews.get(position).getItemView());
        	
        	if (position == 0) {
        		mTabViews.get(0).setBackgroundResource(R.drawable.menu_bg);
        	}
        	
            return mPageViews.get(position).getItemView();  
        }  

    } 

	/*
	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(text);
		return view;
	}

	private Intent getOrderListActivityIntent() {
		Intent intent = new Intent(this, OrderListActivity.class);
		return intent;
	}

	private Intent getOrderDetailActivityIntent() {
		Intent intent = new Intent(this, OrderDetailActivity.class);
		return intent;
	}
	
	*/
	public static OrderTabActivity getOrderTab(){
		return sOrderTab;
	}
	
	

	public void OpenOrderDetailActivity(){
//		mTabHost.setCurrentTabByTag("detail");
	}
	
	public void OpenOrderListActivity(){
//		mTabHost.setCurrentTabByTag("list");
	}
	
	public void setListOrderVO(List<OrderVO> orders){
		this.orders = orders;
	}
	
	public List<OrderVO> getOrders(){
		return orders;
	}

	public int getSelectedIdx() {
		return selectedIdx;
	}

	public void setSelectedIdx(int selectedIdx) {
		this.selectedIdx = selectedIdx;
	}
}
