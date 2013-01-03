package cn.net.yto.ui;

import cn.net.yto.R;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReceiveReturnBillViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	private EditText mMFormNumEdit ;
	private EditText mBFromNumEdit ;
	private Button   mRecSaveBtn ;
	private Button   mRecBackBtn ;
	
	public ReceiveReturnBillViewPagerItem(Activity context,
			ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_no_order_back_bill, null);
		
		mMFormNumEdit = (EditText) mRootView.findViewById(R.id.mform_num_edt);
		mBFromNumEdit = (EditText) mRootView.findViewById(R.id.bfrom_num_edt);
	}

	
	
	@Override
	public View getItemView() {
		return mRootView;
	}

}
