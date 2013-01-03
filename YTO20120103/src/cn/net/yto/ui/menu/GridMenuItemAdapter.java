package cn.net.yto.ui.menu;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.net.yto.R;

public class GridMenuItemAdapter  extends ArrayAdapter<MenuItem> {
	List<MenuItem> mMenuItemList;
	Activity mActivity;
	public GridMenuItemAdapter(Activity activity, List<MenuItem> menuItemList) {
		super(activity, 0, menuItemList);
		mActivity = activity;
		mMenuItemList = menuItemList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MenuItem item = mMenuItemList.get(position);

		if (convertView == null) {
			final LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.grid_main_menu_item,
					parent, false);
		}

		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		img.setBackgroundResource(item.mDrawableResId);
		
		TextView text = (TextView) convertView.findViewById(R.id.text);
		text.setText(item.mTextResId);
		return convertView;
	}
}
