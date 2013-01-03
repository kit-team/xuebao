package cn.net.yto.ui.menu;

import cn.net.yto.R;
import cn.net.yto.ui.menu.SignListAdapter.ItemHolder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SignListAdditionalAdapter extends SignListAdapter {

    public SignListAdditionalAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_detail_item, parent, false);
            itemHolder = new ItemHolder();
            itemHolder.tranckingNumberView = (TextView) convertView
                    .findViewById(R.id.item_tracking_number);
            itemHolder.receipientView = (TextView) convertView.findViewById(R.id.item_receipient);
            itemHolder.signTimeView = (TextView) convertView.findViewById(R.id.item_sign_time);
            itemHolder.signTimeView.setVisibility(View.GONE);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        final SignListAdapterItem item = mData.get(position);
        itemHolder.tranckingNumberView.setText(item.getWaybillNo());
        itemHolder.receipientView.setText(item.getRecipient());
        if (mIsSingleSelection) {
            if (mSelectedPosition == position) {
                convertView.setBackgroundColor(COLOR_SELECTED);
            } else {
                convertView.setBackgroundColor(COLOR_DEFAULT);
            }
        } else {
            if (item.isSelected()) {
                convertView.setBackgroundColor(COLOR_SELECTED);
            } else {
                convertView.setBackgroundColor(COLOR_DEFAULT);
            }
        }

        return convertView;
    }
}
