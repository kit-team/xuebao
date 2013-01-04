package cn.net.yto.ui.menu;

import java.util.ArrayList;
import java.util.List;

import cn.net.yto.R;
import cn.net.yto.biz.SignedLogManager;
import cn.net.yto.ui.menu.SignListAdapter.ItemHolder;
import cn.net.yto.utils.ToastUtils;
import cn.net.yto.utils.ToastUtils.Operation;
import cn.net.yto.vo.SignedLogVO;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SignListNotUploadAdapter extends SignListAdapter {

    public SignListNotUploadAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_notupload_item, parent, false);
            itemHolder = new ItemHolder();
            itemHolder.tranckingNumberView = (TextView) convertView
                    .findViewById(R.id.item_tracking_number);
            itemHolder.uploadStatusView = (TextView) convertView.findViewById(R.id.item_upload_status);
            itemHolder.commentView = (TextView) convertView.findViewById(R.id.item_comment);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        final SignListAdapterItem item = mData.get(position);
        itemHolder.tranckingNumberView.setText(item.getWaybillNo());
        itemHolder.uploadStatusView.setText(item.getUploadStatus());
        itemHolder.commentView.setText(item.getComment());
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

    public class ItemHolder {
        TextView tranckingNumberView = null;
        TextView uploadStatusView = null;
        TextView commentView = null;
    }

    public static List<SignListAdapterItem> buildSignListAdapterItem(List<SignedLogVO> signedLogs) {
        List<SignListAdapterItem> items = new ArrayList<SignListAdapterItem>();
        for (SignedLogVO vo : signedLogs) {
            items.add(new SignListAdapterItem(vo));
        }
        return items;
    }
}