package cn.net.yto.ui.menu;

import java.util.List;

import cn.net.yto.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListMenuItemAdapter extends ArrayAdapter<MenuItem> {
	private List<MenuItem> mMenuItemList;
	private Activity mActivity;

	public ListMenuItemAdapter(Activity activity, List<MenuItem> menuItemList) {
		super(activity, 0, menuItemList);
		mActivity = activity;
		mMenuItemList = menuItemList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MenuItem item = mMenuItemList.get(position);
		Holder tag;
		if (convertView == null) {
			tag = new Holder();
			
			final LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.list_main_menu_item,
					parent, false);
			
			tag.img = (ImageView) convertView.findViewById(R.id.icon);
			tag.text = (TextView) convertView.findViewById(R.id.text);
			
		} else {
			tag = (Holder) convertView.getTag();
		}

		tag.img.setBackgroundResource(item.mDrawableResId);
		tag.text.setText(item.mTextResId);
		
		convertView.setTag(tag);
	//	convertView.setFocusableInTouchMode(true);
	//	convertView.setFocusable(true);
		
		return convertView;
	}
	
	private class Holder {
		ImageView img;
		TextView text;
	}
}
