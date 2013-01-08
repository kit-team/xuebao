package cn.net.yto.ui.menu;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SignListItemClickListener implements OnItemClickListener {
    private SignListBasicAdapter mAdapter = null;
    private boolean mHasHeadView = false;

    public SignListItemClickListener(SignListBasicAdapter adapter, boolean hasHeadView) {
        mAdapter = adapter;
        mHasHeadView = hasHeadView;
    }

    @Override
    public void onItemClick(AdapterView<?> parten, View v, int position, long id) {
    	if (id == -1) {
    		return; // headerView or footerView is clicked.  
    	}
    	mAdapter.onItemClick((int)id);
    }

}