package cn.net.yto.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.BasicDataManager;
import cn.net.yto.biz.UserManager;
import cn.net.yto.service.YtoBizService;
import cn.net.yto.ui.menu.MenuAction;
import cn.net.yto.ui.menu.MenuItem;
import cn.net.yto.ui.menu.GridMenuItemAdapter;

public class MainMenuActivity extends BaseActivity {
	private static final String TAG = "MainMenuActivity";
	private GridView mGridView;
	private TextView mTitleView;
	private ArrayList<MenuItem> mMenuItemList = new ArrayList<MenuItem>();
	private UserManager mUserService;
	private BasicDataManager mBasicDataManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContext();
		initMenuItem();
		initViews();
		YtoBizService.startDownloadOrder();
		YtoBizService.startUploadRepeatAlarm();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		StringBuffer sb = new StringBuffer();
		sb.append(getString(R.string.main_menu));
		
		setTitleInfo(sb.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		YtoBizService.cancelDownloadOrder();
		YtoBizService.cancelUploadRepeatAlarm();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean result = true;

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_STAR:
			exitAppDialog();
			break;

//		case 29:// F1
//			CommonUtils.startWifiSettingActivity(mContext);
//			break;

		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
			int index = keyCode - KeyEvent.KEYCODE_1;
			if (index >= 0 && index < mMenuItemList.size()) {
				mMenuItemList.get(index).doAction();
			}
			break;
		default:
			result = false;
			break;
		}
		if (result) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initViews() {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main_menu);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sign_batch_title);

		mGridView = (GridView) findViewById(R.id.gridview);
		GridMenuItemAdapter adapter = new GridMenuItemAdapter(this, mMenuItemList);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				mMenuItemList.get(position).doAction();
			}
		});

		mTitleView = (TextView) findViewById(R.id.title_text);
		mTitleView.setText(R.string.receive);
	}

	private void initMenuItem() {
		addMenuItem(R.drawable.mainmenu_order, R.string.order_manage, /*OrderListActivity.class*/OrderTabActivity.class);
		addMenuItem(R.drawable.mainmenu_receive, R.string.receive, ReceiveMenuActivity.class);
		addMenuItem(R.drawable.mainmenu_deliver, R.string.deliver, DispatchMain.class);
		addMenuItem(R.drawable.mainmenu_query, R.string.msg_query, null);
		addLogoutMenuItem(R.drawable.mainmenu_exit, R.string.back);
	}

	private void addMenuItem(int imgResId, int textResId, final Class<? extends Activity> cls) {
		MenuAction action = new MenuAction() {
			@Override
			public void action() {
				if(cls != null){
					Intent intent = new Intent(mContext, cls);
					startActivity(intent);
				}
			}
		};
		MenuItem item = new MenuItem(imgResId, textResId, action);
		mMenuItemList.add(item);
	}
	
	private void addLogoutMenuItem(int imgResId, int textResId) {
		MenuAction action = new MenuAction() {
			@Override
			public void action() {
				exitAppDialog();
			}
		};
		MenuItem item = new MenuItem(imgResId, textResId, action);
		mMenuItemList.add(item);
	}

	private void initContext() {
		mUserService = mAppContext.getUserService();
		mBasicDataManager = new BasicDataManager(mContext);
		mBasicDataManager.downloadBasicData();
	}

	protected void exitAppDialog() {
//		DialogHelper.showAlertDialog(this,
//				getString(R.string.app_exit_message),
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						DialogHelper.closeAlertDialog();
//					}
//				}, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						DialogHelper.closeAlertDialog();
//					}
//				});
//		playSound(Constants.SOUND_TYPE_QUERY);
		mUserService.loginOut(mContext, null);
		finish();
	}
	
}