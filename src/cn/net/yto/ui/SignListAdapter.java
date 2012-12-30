package cn.net.yto.ui;

import java.util.ArrayList;

import cn.net.yto.R;
import cn.net.yto.models.SignedLog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SignListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<SignListAdapterItem> mData = new ArrayList<SignListAdapterItem>();

    public SignListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        
        // TODO remove
        for (int i = 0; i < 20; i++) {
            SignListAdapterItem item = new SignListAdapterItem(SignedLog.getSignedLogForTest());
            mData.add(item);
        }
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
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_detail_item, parent, false);
            itemHolder = new ItemHolder();
            itemHolder.tranckingNumberView = (TextView)convertView.findViewById(R.id.item_tracking_number);
            itemHolder.signTypeView = (TextView)convertView.findViewById(R.id.item_sign_type);
            itemHolder.receipientView = (TextView)convertView.findViewById(R.id.item_receipient);
            itemHolder.signTimeView = (TextView)convertView.findViewById(R.id.item_sign_time);
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
            convertView.setBackgroundColor(60);
        }  else {
            convertView.setBackgroundColor(256);
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
