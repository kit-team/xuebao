package cn.net.yto.ui;

import cn.net.yto.R;
import cn.net.yto.biz.BarcodeManager;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.ReceiveVO;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiveChildrenViewPagerItem extends ViewPageItemAbs {

	private View mRootView;

	private EditText mMainWayBillNoEdit;
	private EditText mOtherWayBillNoEdit;
	private TextView mTotalNumText;
	private Button mDelBtn;
	private Button mSaveBtn;
	private Button mBackBtn;

	private ReceiveManager mReceiveManager;
	private BarcodeManager mBarcodeManager;
	private BasicDataManager mBasicDataManager;
	private ReceiveExpressPageActivity sParent;

	public ReceiveChildrenViewPagerItem(Activity context, ViewPager viewPager) {
		super(context, viewPager);
		
		mReceiveManager = ReceiveManager.getInstance();
		mBasicDataManager = BasicDataManager.getInstance();
		mBarcodeManager = BarcodeManager.getInstance();
		sParent = ReceiveExpressPageActivity.getInstance();
		

		LayoutInflater inflater = mContext.getLayoutInflater();
		mRootView = inflater.inflate(R.layout.activity_receive_no_order_multi,
				null);
		initViews();
	}
	
	@Override
	public void onPageSelected() {
		ReceiveVO vo = mReceiveManager.getCurNomalReceiveVO();
		if (vo == null || CommonUtils.isEmpty(vo.getWaybillNo())) {
			DialogHelper.showToast(mContext, R.string.way_bill_no_empty_tips);
			onBackBtnClicked();
		} else {
			mMainWayBillNoEdit.setEnabled(true);
			mMainWayBillNoEdit.setText(vo.getWaybillNo());
			mMainWayBillNoEdit.setEnabled(false);
		}
		super.onPageSelected();
	}

	@Override
	public void onPageDeSelected() {
		onSaveBtnClicked();
		super.onPageDeSelected();
	}
	
	private void onSaveBtnClicked() {
		// TODO Auto-generated method stub
		
	}

	protected void onBackBtnClicked() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				sParent.setPageIndex(0);
			}
		}, 200);
	}

	private void initViews() {
		mMainWayBillNoEdit = (EditText) mRootView
				.findViewById(R.id.main_way_bill_no_edit);
		mOtherWayBillNoEdit = (EditText) mRootView
				.findViewById(R.id.other_way_bill_no_edit);
		mTotalNumText = (TextView) mRootView.findViewById(R.id.total_num_text);
		
		mSaveBtn = (Button) mRootView.findViewById(R.id.save_btn);
		mBackBtn = (Button) mRootView.findViewById(R.id.back_btn);

		mSaveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSaveBtnClicked();
			}
		});
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackBtnClicked();
			}
		});
	}

	@Override
	public View getItemView() {
		return mRootView;
	}

}
