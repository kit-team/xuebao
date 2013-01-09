package cn.net.yto.ui;

import java.util.ArrayList;

import cn.net.yto.R;
import cn.net.yto.models.DbTempUtils;
import cn.net.yto.models.SignedLog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SignListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<SignListAdapterItem> mData = null;

    public SignListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mData = DbTempUtils.query(context);
    }

    public void setData(ArrayList<SignListAdapterItem> datas) {
        mData.clear();
        mData.addAll(datas);
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

    public void deleteSelectedItem(Context context) {
        ArrayList<SignedLog> selectedSignedLogs = new ArrayList<SignedLog>();
        for (int i = mData.size() - 1; i >= 0; i--) {
            SignListAdapterItem item = mData.get(i);
            if (item.isSelected()) {
                selectedSignedLogs.add(item.getSignedLog());
                mData.remove(i);
            }
        }

        DbTempUtils.delete(context, selectedSignedLogs);

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onItemClick(int position) {
        SignListAdapterItem item = getItem(position);
        boolean checked = item.isSelected();
        item.setSelected(!checked);
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
            itemHolder.signTypeView = (TextView) convertView.findViewById(R.id.item_tracking_number);
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
        if (item.isSelected()) {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    public class ItemHolder {
        TextView tranckingNumberView = null;
        TextView signTypeView = null;
        TextView receipientView = null;
        TextView signTimeView = null;
    }
}
