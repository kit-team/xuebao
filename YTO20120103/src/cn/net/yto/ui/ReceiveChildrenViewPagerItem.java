package cn.net.yto.ui;

import cn.net.yto.R;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiveChildrenViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	private EditText mMFormNumEdit ;
	private EditText mOFormNumSEdit ;
	private EditText mOFormNumEdit ;
	private TextView mTotalNumTextv ;
	private Button   mDelFromBtn ;
	private Button   mRecSaveBtn ;
	private Button   mRecBackBtn ;
	
	public ReceiveChildrenViewPagerItem(Activity context,
			ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_no_order_multi, null);
		
	}

	@Override
	public View getItemView() {
		return mRootView;
	}

}
