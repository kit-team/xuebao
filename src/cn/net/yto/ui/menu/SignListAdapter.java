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

public class SignListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<SignListAdapterItem> mData = new ArrayList<SignListAdapterItem>();
    
    // if true, only one item could be selected; else support multi-selection
    private boolean mIsSingleSelection = false;
    private int mSelectedPosition = -1;

    public SignListAdapter(Context context) {
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

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public SignListAdapterItem getItem(int position) {
        return mData.get(position);
    }

    public ArrayList<SignedLogVO> getSelectedSignedLog() {
        ArrayList<SignedLogVO> selectedVOs = new ArrayList<SignedLogVO>();
        if (mIsSingleSelection && mSelectedPosition >= 0) {
            selectedVOs.add(mData.get(mSelectedPosition).getSignedLogVO());
        } else {
            for (int i = mData.size() - 1; i >= 0; i--) {
                SignListAdapterItem item = mData.get(i);
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
            result = signedLogMgr.removeSignedLog(selectVO);
            if (result >0) {
                mData.remove(mSelectedPosition);
                mSelectedPosition = -1;
            }
        } else {
            ArrayList<SignListAdapterItem> selectedItem = new ArrayList<SignListAdapterItem>();
            ArrayList<SignedLogVO> selectedVOs = new ArrayList<SignedLogVO>();
            for (int i = mData.size() - 1; i >= 0; i--) {
                SignListAdapterItem item = mData.get(i);
                if (item.isSelected()) {
                    selectedItem.add(item);
                    selectedVOs.add(item.getSignedLogVO());
                }
            }
            result = signedLogMgr.removeSignedLog(selectedVOs);
            if (result >0) {
                mData.removeAll(selectedItem);
            }
        }

        
        ToastUtils.showOperationToast(Operation.DELETE, result >0);
        if (result >0) {
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onItemClick(int position) {
        SignListAdapterItem item = getItem(position);
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
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_detail_item, parent, false);
            itemHolder = new ItemHolder();
            itemHolder.tranckingNumberView = (TextView) convertView
                    .findViewById(R.id.item_tracking_number);
            itemHolder.signTypeView = (TextView) convertView.findViewById(R.id.item_sign_type);
            itemHolder.receipientView = (TextView) convertView.findViewById(R.id.item_receipient);
            itemHolder.signTimeView = (TextView) convertView.findViewById(R.id.item_sign_time);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        final SignListAdapterItem item = mData.get(position);
        itemHolder.tranckingNumberView.setText(item.getWaybillNo());
        itemHolder.signTypeView.setText(item.getSignType());
        itemHolder.receipientView.setText(item.getRecipient());
        itemHolder.signTimeView.setText(item.getSignTime());
        if (mIsSingleSelection) {
            if (mSelectedPosition == position) {
                convertView.setBackgroundColor(Color.GREEN);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
        } else {
            if (item.isSelected()) {
                convertView.setBackgroundColor(Color.GREEN);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
        }

        return convertView;
    }

    public class ItemHolder {
        TextView tranckingNumberView = null;
        TextView signTypeView = null;
        TextView receipientView = null;
        TextView signTimeView = null;
    }

    public static List<SignListAdapterItem> buildSignListAdapterItem(List<SignedLogVO> signedLogs) {
        List<SignListAdapterItem> items = new ArrayList<SignListAdapterItem>();
        for (SignedLogVO vo : signedLogs) {
            items.add(new SignListAdapterItem(vo));
        }
        return items;
    }
}
