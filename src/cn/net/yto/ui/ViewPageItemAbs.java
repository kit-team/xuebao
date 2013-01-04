package cn.net.yto.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * 
 * @author LL
 * 
 */
public abstract class ViewPageItemAbs implements ViewPageItem {

	protected Activity mContext;
	protected ViewPager mViewPager;

	public ViewPageItemAbs(Activity context, ViewPager viewPager) {
		mContext = context;
		mViewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onRestart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onPageSelected() {

	}

	@Override
	public boolean onScanned(String barcode) {
		return false;
	}

}
