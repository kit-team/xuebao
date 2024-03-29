package cn.net.yto.ui;

import cn.net.yto.utils.LogUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * 
 * @author LL
 * 
 */
public abstract class ViewPageItemAbs implements ViewPageItem {

	private static final String TAG = "ViewPageItemAbs";
	protected Activity mContext;
	protected ViewPager mViewPager;

	public ViewPageItemAbs(Activity context, ViewPager viewPager) {
		mContext = context;
		mViewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtils.i(TAG, "onCreate");
	}

	@Override
	public void onStart() {
		LogUtils.i(TAG, "onStart");
	}

	@Override
	public void onRestart() {
		LogUtils.i(TAG, "onRestart");
	}

	@Override
	public void onResume() {
		LogUtils.i(TAG, "onResume");
	}

	@Override
	public void onPause() {
		LogUtils.i(TAG, "onPause");
	}

	@Override
	public void onStop() {
		LogUtils.i(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		LogUtils.i(TAG, "onDestroy");
	}

	@Override
	public void onPageSelected() {
		LogUtils.i(TAG, "onPageSelected");
	}
	
	@Override
	public void onPageDeSelected() {
		LogUtils.i(TAG, "onPageDeSelected");
	}

	@Override
	public boolean onScanned(String barcode) {
		return false;
	}

}
