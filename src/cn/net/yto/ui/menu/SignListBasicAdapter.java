package cn.net.yto.ui.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;

public abstract class SignListBasicAdapter extends BaseAdapter {

    protected final static int COLOR_DEFAULT = Color.WHITE;
    protected final static int COLOR_SELECTED = Color.YELLOW;

    protected LayoutInflater mInflater;

    protected ArrayList<SignListItem> mData = new ArrayList<SignListItem>();

    // if true, only one item could be selected; else support multi-selection
    protected boolean mIsSingleSelection = false;
    protected int mSelectedPosition = -1;

    public SignListBasicAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setSingleSelection(boolean singleSelection) {
        if (mIsSingleSelection != singleSelection) {
            mIsSingleSelection = singleSelection;
            notifyDataSetChanged();
        }
    }

    public void setData(List<SignedLogVO> signedLogs) {
        mSelectedPosition = -1;
        mData.clear();
        mData.addAll(buildSignListAdapterItem(signedLogs));
        notifyDataSetChanged();
    }

    public void addData(List<SignedLogVO> signedLogs) {
        mData.addAll(buildSignListAdapterItem(signedLogs));
        notifyDataSetChanged();
    }
    
    public void addData(SignedLogVO vo) {
        mData.add(new SignListItem(vo));
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public SignListItem getItem(int position) {
        return mData.get(position);
    }

    public ArrayList<SignedLogVO> getSelectedSignedLog() {
        ArrayList<SignedLogVO> selectedVOs = new ArrayList<SignedLogVO>();
        if (mIsSingleSelection && mSelectedPosition >= 0) {
            selectedVOs.add(mData.get(mSelectedPosition).getSignedLogVO());
        } else {
            for (int i = mData.size() - 1; i >= 0; i--) {
                SignListItem item = mData.get(i);
                if (item.isSelected()) {
                    selectedVOs.add(item.getSignedLogVO());
                }
            }
        }

        return selectedVOs;
    }

    public void deleteSelectedItem(Context context, SignedLogManager signedLogMgr) {
        int result = 0;
        if (mIsSingleSelection) {
            SignedLogVO selectVO = mData.get(mSelectedPosition).getSignedLogVO();
            result = signedLogMgr.deleteSignedLog(selectVO, context);
            if (result > 0) {
                mData.remove(mSelectedPosition);
                mSelectedPosition = -1;
            }
        } else {
            ArrayList<SignListItem> selectedItem = new ArrayList<SignListItem>();
            ArrayList<SignedLogVO> selectedVOs = new ArrayList<SignedLogVO>();
            for (int i = mData.size() - 1; i >= 0; i--) {
                SignListItem item = mData.get(i);
                if (item.isSelected()) {
                    selectedItem.add(item);
                    selectedVOs.add(item.getSignedLogVO());
                }
            }
            result = signedLogMgr.removeSignedLog(selectedVOs);
            if (result > 0) {
                mData.removeAll(selectedItem);
            }
        }

        ToastUtils.showOperationToast(Operation.DELETE, result > 0);
        if (result > 0) {
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onItemClick(int position) {
        SignListItem item = getItem(position);
        if (mIsSingleSelection) {
            if (mSelectedPosition == position) {
                mSelectedPosition = -1;
            } else {
                mSelectedPosition = position;
            }
        } else {
            item.setSelected(!item.isSelected());
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_sign_item, parent, false);
            ItemHolder itemHolder = new ItemHolder();
            itemHolder.view1 = (TextView) convertView.findViewById(R.id.item1);
            itemHolder.view2 = (TextView) convertView.findViewById(R.id.item2);
            itemHolder.view3 = (TextView) convertView.findViewById(R.id.item3);
            itemHolder.view4 = (TextView) convertView.findViewById(R.id.item4);
            itemHolder.viewSecond1 = (TextView) convertView.findViewById(R.id.item_second1);
            itemHolder.viewSecond2 = (TextView) convertView.findViewById(R.id.item_second2);
            convertView.setTag(itemHolder);
        }

        if (mIsSingleSelection) {
            if (mSelectedPosition == position) {
                convertView.setBackgroundColor(COLOR_SELECTED);
            } else {
                convertView.setBackgroundColor(COLOR_DEFAULT);
            }
        } else {
            final SignListItem item = mData.get(position);
            if (item.isSelected()) {
                convertView.setBackgroundColor(COLOR_SELECTED);
            } else {
                convertView.setBackgroundColor(COLOR_DEFAULT);
            }
        }

        return convertView;
    }

    public class ItemHolder {
        public TextView view1 = null;
        public TextView view2 = null;
        public TextView view3 = null;
        public TextView view4 = null;
        public TextView viewSecond1 = null;
        public TextView viewSecond2 = null;
    }

    public static List<SignListItem> buildSignListAdapterItem(List<SignedLogVO> signedLogs) {
        List<SignListItem> items = new ArrayList<SignListItem>();
        for (SignedLogVO vo : signedLogs) {
            items.add(new SignListItem(vo));
        }
        return items;
    }
}