package cn.net.yto.ui;

import cn.net.yto.R;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class ReceiveViewListViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	public ReceiveViewListViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_view_list, null);
	}

	@Override
	public View getItemView() {
		return mRootView;
	}

}
