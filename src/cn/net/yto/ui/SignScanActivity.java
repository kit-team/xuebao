package cn.net.yto.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cn.net.yto.R;

public class SignScanActivity extends Activity {
	private static final String TAG = "ViewPagerTest" ;
	
	private ViewPager viewPager = null;
	private LayoutInflater mInflater = null;
	private ArrayList<View> mPageViews = null;
	private ArrayList<View> mTabViews = null;
	
	private SignSuccessView mSignSuccessView = null;
	
	private OnClickListener mTabItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String tabTag = v.getTag().toString();
			View tabView = null;
			for(int idx = 0; idx < mTabViews.size(); idx++) {
				tabView = mTabViews.get(idx);
				if (tabView.getTag().equals(tabTag)) {
					tabView.setBackgroundResource(R.drawable.menu_bg);
					viewPager.setCurrentItem(idx);
				} else {
					tabView.setBackgroundDrawable(null);
				}
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.sign_scan_view);
		
		mInflater = getLayoutInflater();
		
		mPageViews = new ArrayList<View>();
		mTabViews  = new ArrayList<View>();
		
		TextView tabSignSuccess = (TextView) findViewById(R.id.tab_sign_success);
		tabSignSuccess.setOnClickListener(mTabItemClickListener);
		TextView tabSignFailed = (TextView) findViewById(R.id.tab_sign_failed);
		tabSignFailed.setOnClickListener(mTabItemClickListener);
		TextView tabWaybillDetail = (TextView) findViewById(R.id.tab_waybill_detail);
		tabWaybillDetail.setOnClickListener(mTabItemClickListener);
		TextView tabOrderQuery = (TextView) findViewById(R.id.tab_order_query);
		tabOrderQuery.setOnClickListener(mTabItemClickListener);
		mTabViews.add(tabSignSuccess);
		mTabViews.add(tabSignFailed);
		mTabViews.add(tabWaybillDetail);
		mTabViews.add(tabOrderQuery);
		
		View signedSuccessView = mInflater.inflate(R.layout.signed_success_view, null);
		View signedFailedView = mInflater.inflate(R.layout.signed_failed_view, null);
		View waybillDetailView = mInflater.inflate(R.layout.waybill_detail_view, null);
		View orderQueryView = mInflater.inflate(R.layout.order_query_view, null);
		mPageViews.add(signedSuccessView);
		mPageViews.add(signedFailedView);
		mPageViews.add(waybillDetailView);
		mPageViews.add(orderQueryView);
		
		mSignSuccessView = new SignSuccessView(signedSuccessView);
		
		viewPager = (ViewPager) findViewById(R.id.slideMenu);
		viewPager.setAdapter(new SlideMenuAdapter());  
        viewPager.setOnPageChangeListener(new SlideMenuChangeListener());
	}
	
	public void onTabClicked(View view) {
		String tabTag = view.getTag().toString();
		View tabView = null;
		for(int idx = 0; idx < mTabViews.size(); idx++) {
			tabView = mTabViews.get(idx);
			if (tabView.equals(tabTag)) {
				tabView.setBackgroundResource(R.drawable.menu_bg);
			} else {
				tabView.setBackgroundDrawable(null);
			}
		}
	}
	
    class SlideMenuAdapter extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return mPageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View view, Object object) {  
            return view == object;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View container, int position, Object object) {  
            ((ViewPager) container).removeView(mPageViews.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(View container, int position) {  
        	((ViewPager) container).addView(mPageViews.get(position));
            return mPageViews.get(position);  
        }  
  
        @Override  
        public void restoreState(Parcelable state, ClassLoader loader) {  
            // Nothing need to do
        }  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View container) {  
            // Nothing need to do
        }  
  
        @Override  
        public void finishUpdate(View container) {  
            // Nothing need to do
         }  
    } 
    
    // »¬¶¯²Ëµ¥¸ü¸ÄÊÂ¼þ¼àÌýÆ÷
    class SlideMenuChangeListener implements OnPageChangeListener {  
    	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            Log.d(TAG, "onPageScrollStateChanged: " + String.valueOf(arg0));  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
        	Log.d(TAG, "onPageScrolled: " + String.valueOf(arg0));  
        	
        }  
  
        @Override  
        public void onPageSelected(int pageIndex) {
        	Log.d(TAG, "onPageSelected: " + String.valueOf(pageIndex));
        	View tabView = null;
        	for(int idx = 0; idx < mTabViews.size(); idx++) {
        		tabView = mTabViews.get(idx);
        		if (pageIndex != idx) {
        			tabView.setBackgroundDrawable(null);
        		} else {
        			tabView.setBackgroundResource(R.drawable.menu_bg);
        		}
        	}
        	/*
        	int pageCount = mPageViews.size() - 1;
        	pagerIndex = arg0;
        	
        	// ÏÔÊ¾ÓÒ±ßµ¼º½Í¼Æ¬
        	if(arg0 >= 0 && arg0 < pageCount){
        		imageNext.setVisibility(View.VISIBLE);
        	}else{
        		imageNext.setVisibility(View.INVISIBLE);
        	}
        	
        	// ÏÔÊ¾×ó±ßµ¼º½Í¼Æ¬
        	if(arg0 > 0 && arg0 <= pageCount){
        		imagePrevious.setVisibility(View.VISIBLE);
        	}else{
        		imagePrevious.setVisibility(View.INVISIBLE);
        	}
        	*/
        }  
    }  
    
    class SignSuccessView {
    	private EditText mEditTrackingNo;
    	private Spinner  mSignTypeSpinner;
    	
    	SignSuccessView(View view) {
    		initView(view);
    	}
    	
		private void initView(View view) {
			mEditTrackingNo = (EditText) view
					.findViewById(R.id.edit_tracking_number);
			mEditTrackingNo.setText("12345678");
			
			mSignTypeSpinner = (Spinner) view.findViewById(R.id.spinner_sign_type);
			ArrayAdapter<String> signTypeAdapter = new ArrayAdapter<String>( SignScanActivity.this,
																		     android.R.layout.simple_spinner_item, 
					                                                         getResources().getStringArray(R.array.sign_type));
			signTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mSignTypeSpinner.setAdapter(signTypeAdapter);
		}
    	
    }

}
