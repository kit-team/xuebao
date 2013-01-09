package cn.net.yto.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.net.yto.R;
import cn.net.yto.ui.menu.ListMenuItemAdapter;
import cn.net.yto.ui.menu.MenuAction;
import cn.net.yto.ui.menu.MenuItem;

public class AssistantActivity extends BaseActivity {

	private ArrayList<MenuItem> mMenuItemList = new ArrayList<MenuItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_assistant);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		setTitleInfo(R.string.my_assitant);
		
		initMenuItem();
		initViews();
			
	}
	
	private void initMenuItem() { 
		addMenuItem(R.drawable.assistant_calculator, R.string.assistant_calculator);
		addMenuItem(R.drawable.assistant_notes, R.string.assistant_notes);
		addMenuItem(R.drawable.assistant_upload_log, R.string.assistant_upload_log);
		addMenuItem(R.drawable.assistant_delete_expired_data, R.string.assistant_delete_expired_data);
		addMenuItem(R.drawable.return_back, R.string.back); 
	}
	
	private void initViews() {

		ListView mlistView = (ListView) findViewById(R.id.list);

		mlistView.setAdapter(new ListMenuItemAdapter(this, mMenuItemList));

		mlistView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				mMenuItemList.get(position).doAction();
			}
		});
	
	} 
	
	private void addMenuItem(int imgResId, int textResId) {
		MenuItem item;
		
		switch(textResId) {
		case R.string.assistant_calculator:
			item = new MenuItem(imgResId, textResId, new MenuAction() {

				@Override
				public void action() {
					Intent intent = new Intent(); 
					intent.setClassName("com.android.calculator2", 
		                             "com.android.calculator2.Calculator"); 
		            startActivity(intent);				
				}
				
			});
			mMenuItemList.add(item);
			
			break;
			
		case R.string.assistant_notes:
			item = new MenuItem(imgResId, textResId, new MenuAction() {

				@Override
				public void action() {
					
				}
				
			});
			mMenuItemList.add(item);
			
			break;
			
		case R.string.assistant_upload_log:
			item = new MenuItem(imgResId, textResId, new MenuAction() {

				@Override
				public void action() {
				
					
				}
				
			});
			mMenuItemList.add(item);
			
			break;
			
		case R.string.assistant_delete_expired_data:
			item = new MenuItem(imgResId, textResId, new MenuAction() {

				@Override
				public void action() {
					Intent intent = new Intent(AssistantActivity.this, ExpiredDeleteActivity.class);
					startActivity(intent);						
				}
				
			});
			mMenuItemList.add(item);
			
			break;
			
		case R.string.back:
			item = new MenuItem(imgResId, textResId, new MenuAction() {

				@Override
				public void action() {
					finish();					
				}
				
			});
			
			mMenuItemList.add(item);
			
			break;
		}


	}
}
