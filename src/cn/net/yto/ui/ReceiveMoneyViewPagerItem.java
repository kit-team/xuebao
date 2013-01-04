package cn.net.yto.ui;

import cn.net.yto.R;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ReceiveMoneyViewPagerItem extends ViewPageItemAbs {

	private View mRootView;
	
	private CheckBox mToPayCbox ;
	private CheckBox mColPayCbox ;
	private EditText mCusNameEdit ;
	private EditText mGoodsValEdit ;
	
	private Button   mSelectBtn ;
	private Button   mRecSaveBtn ;
	private Button   mRecBackBtn ;
	
	public ReceiveMoneyViewPagerItem(Activity context,
			ViewPager viewPager) {
		super(context, viewPager);
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_no_order_money, null);
		
		initViews();
	}

	
	@Override
	public View getItemView() {
		return mRootView;
	}
	
	private void initViews() {
		mToPayCbox = (CheckBox) mRootView.findViewById(R.id.to_pay_cbox);
		mColPayCbox = (CheckBox) mRootView.findViewById(R.id.col_payment_cbox);
		mCusNameEdit = (EditText) mRootView.findViewById(R.id.cus_name_edt);
		mGoodsValEdit = (EditText) mRootView.findViewById(R.id.goods_val_edt);
		mSelectBtn = (Button) mRootView.findViewById(R.id.select_btn);
		mRecSaveBtn = (Button) mRootView.findViewById(R.id.rec_save_btn);
		mRecBackBtn = (Button) mRootView.findViewById(R.id.rec_back_btn);
		mToPayCbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
				}
				
			}
		});
		mColPayCbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
				}
			}
			
		});
	}

}
