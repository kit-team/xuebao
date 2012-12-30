package cn.net.yto.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SignListItemClickListener implements OnItemClickListener {
    private SignListAdapter mAdapter = null;
    private boolean mHasHeadView = false;

    public SignListItemClickListener(SignListAdapter adapter, boolean hasHeadView) {
        mAdapter = adapter;
        mHasHeadView = hasHeadView;
    }

    @Override
    public void onItemClick(AdapterView<?> parten, View v, int position, long id) {
        if (mHasHeadView) { // position is head view
            mAdapter.onItemClick(position - 1);
        } else {
            mAdapter.onItemClick(position);
        }
    }

}
