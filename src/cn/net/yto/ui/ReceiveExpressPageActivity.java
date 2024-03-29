package cn.net.yto.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.application.AppContext;
import cn.net.yto.biz.ReceiveManager;
import cn.net.yto.common.Constants;
import cn.net.yto.utils.CommonUtils;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.OrderVO;
import cn.net.yto.vo.ReceiveVO;

import com.zltd.android.scan.ScanManager;
import com.zltd.android.scan.ScanResultListener;
import com.zltd.android.scan.impl.OneDimensionalSanManager;

public class ReceiveExpressPageActivity extends BaseActivity {
	private static ReceiveExpressPageActivity sOrderTab;
	private ViewPager mViewPager;
	private List<ViewPageItemAbs> mPageViews;
	private List<View> mTabViews;

	private ReceiveExpressViewPagerItem mReceiveNoOrderNomalItem;
	private ReceiveMoneyViewPagerItem mReceiveNoOrderMoneyItem;
	private ReceiveReturnBillViewPagerItem mReceiveNoOrderBackBillItem;
	private ReceiveChildrenViewPagerItem mReceiveNoOrderMultiItem;

	private ReceiveManager mReceiveManager;
	private AppContext mAppContext;
	private OrderVO mOrderVO;
	private ReceiveVO mReceiveVO;
	private boolean mAddMode;
	private int mCurPageIndex = 0;

	private ScanResultListener mScanResultListener = new ScanResultListener() {
		@Override
		public void onScan(ScanManager arg0, byte[] scanResultDate) {
			if (scanResultDate != null) {
				String barcode = new String(scanResultDate);
				if (onScanned(barcode)) {
					playSound(Constants.SOUND_TYPE_SUCCESS);
				} else {
					playSound(Constants.SOUND_TYPE_WARNING);
				}
			}
			mVibrator.vibrate(39);
		}
	};
	private OneDimensionalSanManager mScanManager;
	private OrderPageAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sOrderTab = this;
		mAddMode = true;

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_no_order_pager);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);

		setTitleInfo(R.string.receive_no_order);

		mReceiveManager = ReceiveManager.getInstance();

		Intent intent = getIntent();
		if (Constants.ACTION_ADD_RECEIVE.equals(intent.getAction())) {
			mAddMode = true;
			mOrderVO = getIntent().getParcelableExtra("order");
			initCurReceiveInfo(mOrderVO);
			setTitleInfo(R.string.receive_order);
		} else if (Constants.ACTION_EDIT_RECEIVE.equals(intent.getAction())) {
			mAddMode = false;
			setTitle(R.string.receive_edit);
		}

		setViewPage();

		for (ViewPageItemAbs item : mPageViews) {
			item.onCreate(savedInstanceState);
		}

		mScanManager = new OneDimensionalSanManager(mContext);
		mScanManager.setEnable(true);
		mScanManager.registerResultListener(mScanResultListener);
	}

	protected boolean onScanned(String barcode) {
		return mPageViews.get(mCurPageIndex).onScanned(barcode);
	}

	@Override
	protected void onStart() {
		super.onStart();

		hideInputMethod(mViewPager);

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
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mOrderVO = intent.getParcelableExtra("order");
		initCurReceiveInfo(mOrderVO);
		if (mOrderVO != null) {
			setTitleInfo(R.string.receive_order);
		} else {
			setTitleInfo(R.string.receive_no_order);
		}
	}

	public OrderVO getOrderVO() {
		return mOrderVO;
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

		mScanManager.unregisterResultListener();
		mScanManager.setEnable(false);
		mScanManager = null;
		mReceiveManager.clearCurNomalReceiveVO();
	}

	private void initCurReceiveInfo(OrderVO o) {
		if (o != null) {
			mReceiveManager.clearCurNomalReceiveVO();
			ReceiveVO rec = mReceiveManager.getCurNomalReceiveVO();
			rec.setWeighWeight("" + o.getGoodsTotalWeight());
			rec.setOrderNo(o.getOrderNo());
			rec.setOrderId(o.getId());
			rec.setOrderChannelCode(o.getOrderChannelCode());
			rec.setDestAddress(o.getRecipientAddress());
			rec.setReceiverName(o.getRecipientName());
			rec.setSendAddress(o.getSenderAddress());
			rec.setRemarks(o.getRemark());
			if (!CommonUtils.isEmpty(o.getSenderMobile())) {
				rec.setContactPhone(o.getSenderMobile());
			} else {
				rec.setContactPhone(o.getSenderPhone());
			}
		}
	}

	public static ReceiveExpressPageActivity getInstance() {
		return sOrderTab;
	}
	
	public void setPageIndex(int index){
		mViewPager.setCurrentItem(index, true);
		View tabView;
		for (int idx = 0; idx < mTabViews.size(); idx++) {
			tabView = mTabViews.get(idx);
			
			if (index != idx) {
				tabView.setBackgroundDrawable(null);
			} else {
				tabView.setBackgroundResource(R.drawable.menu_bg);
			}
		}
		mViewPagerAdapter.notifyDataSetChanged();
	}

	private void setViewPage() {

		mPageViews = new ArrayList<ViewPageItemAbs>();
		mTabViews = new ArrayList<View>();

		TextView usualTab = (TextView) findViewById(R.id.tab_no_order_usual);
		usualTab.setOnClickListener(mTabItemClickListener);
		TextView freightTab = (TextView) findViewById(R.id.tab_no_order_freight);
		freightTab.setOnClickListener(mTabItemClickListener);
		TextView receiptTab = (TextView) findViewById(R.id.tab_no_order_receipt);
		receiptTab.setOnClickListener(mTabItemClickListener);
		TextView ticketTab = (TextView) findViewById(R.id.tab_no_order_ticket);
		ticketTab.setOnClickListener(mTabItemClickListener);

		mTabViews.add(usualTab);
		mTabViews.add(freightTab);
		mTabViews.add(receiptTab);
		mTabViews.add(ticketTab);

		mViewPager = (ViewPager) findViewById(R.id.order_viewPage);

		mReceiveNoOrderNomalItem = new ReceiveExpressViewPagerItem(this,
				mViewPager);
		mReceiveNoOrderMoneyItem = new ReceiveMoneyViewPagerItem(this,
				mViewPager);
		mReceiveNoOrderBackBillItem = new ReceiveReturnBillViewPagerItem(this,
				mViewPager);
		mReceiveNoOrderMultiItem = new ReceiveChildrenViewPagerItem(this,
				mViewPager);

		mPageViews.add(mReceiveNoOrderNomalItem);
		mPageViews.add(mReceiveNoOrderMoneyItem);
		mPageViews.add(mReceiveNoOrderBackBillItem);
		mPageViews.add(mReceiveNoOrderMultiItem);

		if (mViewPager != null) {
			mViewPagerAdapter = new OrderPageAdapter();
			mViewPager.setAdapter(mViewPagerAdapter);
			mViewPager.setOnPageChangeListener(new PageChangeListener());
		}

	}

	private OnClickListener mTabItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String tabTag = v.getTag().toString();
			View tabView = null;
			for (int idx = 0; idx < mTabViews.size(); idx++) {
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
			LogUtils.i(TAG, "onPageScrollStateChanged arg0 = " + arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			LogUtils.i(TAG, "onPageScrolled arg0 = " + arg0 + "  arg1 = " + arg1 + " arg2 = " + arg2);
		}

		@Override
		public void onPageSelected(int pageIndex) {
			if(mCurPageIndex != pageIndex){
				mPageViews.get(mCurPageIndex).onPageDeSelected();
				mPageViews.get(pageIndex).onPageSelected();
				mCurPageIndex = pageIndex;
			} else {
				return;
			}
			
			LogUtils.i(TAG, "onPageSelected pageIndex = " + pageIndex);

			View tabView;
			for (int idx = 0; idx < mTabViews.size(); idx++) {
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
			((ViewPager) container).removeView(mPageViews.get(position)
					.getItemView());
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mPageViews.get(position)
					.getItemView());

			if (position == 0) {
				mTabViews.get(0).setBackgroundResource(R.drawable.menu_bg);
			}

			return mPageViews.get(position).getItemView();
		}

	}
	
	public boolean isAddMode(){
		return mAddMode;
	}
}
