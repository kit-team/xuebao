package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.vo.ReceiveVO;

public class ReceiveViewTabActivity extends BaseActivity {
	private static ReceiveViewTabActivity sTab;
	
	private ViewPager mViewPager; 
	private List<ViewPageItemAbs> mPageViews;
	private List<View> mTabViews;
	
	private ReceiveViewListViewPagerItem mListPagerItem;
	private ReceiveViewDetailViewPagerItem mDetailItem;
	
	private List<ReceiveVO> mListReceiveVO;
	private int selectedIdx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_receive_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		setTitleInfo(R.string.receive_view);
		
		sTab = this;
		
		setViewPage();
		
		
		for (ViewPageItemAbs item : mPageViews) 
			item.onCreate(savedInstanceState);
	}
	
	private void setViewPage() {
		
		mPageViews = new ArrayList<ViewPageItemAbs>();
		mTabViews  = new ArrayList<View>();
		
		TextView usualTab = (TextView) findViewById(R.id.tab_list);
		usualTab.setOnClickListener(mTabItemClickListener);
		TextView freightTab = (TextView) findViewById(R.id.tab_detail);
		freightTab.setOnClickListener(mTabItemClickListener);


		mTabViews.add(usualTab);
		mTabViews.add(freightTab);


		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		
		mListPagerItem = new ReceiveViewListViewPagerItem(this, mViewPager);
		mDetailItem = new ReceiveViewDetailViewPagerItem(this, mViewPager);
		
		mPageViews.add(mListPagerItem);
		mPageViews.add(mDetailItem);
		
		
		if (mViewPager != null) {
			mViewPager.setAdapter(new BillPagerAdapter());  
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		}
		
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
		if(mViewPager.getCurrentItem() == 1){
			mViewPager.setCurrentItem(0);
			return;
		}
		super.onBackPressed();
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
	
	private class BillPagerAdapter extends PagerAdapter {  
	  	  
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

	public static ReceiveViewTabActivity getOrderTab(){
		return sTab;
	}
	
	public void setListReceiveVO(List<ReceiveVO> receives){
		this.mListReceiveVO = receives;
	}
	
	public List<ReceiveVO> getReceives(){
		return mListReceiveVO;
	}

	public int getSelectedIdx() {
		return selectedIdx;
	}

	public void setSelectedIdx(int selectedIdx) {
		this.selectedIdx = selectedIdx;
	}

}
