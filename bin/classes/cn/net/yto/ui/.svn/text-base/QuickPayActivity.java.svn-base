package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.net.yto.R;

public class QuickPayActivity extends BaseActivity {
	
	private ViewPager mViewPager;
	private List<ViewPageItemAbs> mPageViews;
	private ArrayList<View> mTabViews;;
	
	private QueryItem mQueryItem;
	private DetailItem mDetailItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_quick_pay);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		setTitleInfo(R.string.quick_pay);
			
		setViewPage();
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onCreate(savedInstanceState);

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
	
	@Override
	public void onBackPressed() {
		if(mViewPager.getCurrentItem()==1){
			mViewPager.setCurrentItem(0);
			return;
		}
		super.onBackPressed();
	}

	private void setViewPage() {
		
		mPageViews = new ArrayList<ViewPageItemAbs>();
		mTabViews  = new ArrayList<View>();
		
		TextView queryTab = (TextView) findViewById(R.id.query);
		queryTab.setOnClickListener(mTabItemClickListener);
		TextView detailTab = (TextView) findViewById(R.id.detail);
		detailTab.setOnClickListener(mTabItemClickListener);

		mTabViews.add(queryTab);
		mTabViews.add(detailTab);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		
		mQueryItem = new QueryItem(this, mViewPager);
		mDetailItem = new DetailItem(this, mViewPager);

		mPageViews.add(mQueryItem);
		mPageViews.add(mDetailItem);
		
		
		
			mViewPager.setAdapter(new OrderPageAdapter());  
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		
		
	}
	
	private OnClickListener mTabItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String tabTag = v.getTag().toString();
			View tabView = null;
			for(int idx = 0; idx < mTabViews.size(); idx++) {
				tabView = mTabViews.get(idx);
				if (tabView.getTag().equals(tabTag)) {
					mViewPager.setCurrentItem(idx);
				} else {
					
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
	
	private class QueryItem extends ViewPageItemAbs {
		
		private View mRootView;

		public QueryItem(Activity context, ViewPager viewPager) {
			super(context, viewPager);
			
			initViews();

		}

		@Override
		public View getItemView() {
			return mRootView;
		}
		
		private void initViews() {
			LayoutInflater inflater = mContext.getLayoutInflater();
			mRootView = inflater.inflate(R.layout.pay_pager_item_query, null);
		}
		
	}
	
	
	
	private class DetailItem extends ViewPageItemAbs {

		private View mRootView;
		
		public DetailItem(Activity context, ViewPager viewPager) {
			super(context, viewPager);

			initViews();
		}

		@Override
		public View getItemView() {
			return mRootView;
		}
		
		private void initViews() {
			LayoutInflater inflater = mContext.getLayoutInflater();
			mRootView = inflater.inflate(R.layout.pay_pager_item_detail, null);
		}
		
	}

}
