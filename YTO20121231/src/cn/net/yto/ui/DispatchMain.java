package cn.net.yto.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.net.yto.R;

public class DispatchMain extends Activity {
    private static final int ITEM_SIGN_SCAN = 0;
    private static final int ITEM_SIGN_BATCH = 1;
    private static final int ITEM_UNUPLOAD_RECORD = 2;
    private static final int ITEM_ADDITIONAL_RECORD = 3;
    private static final int ITEM_DELETE_RECORD = 4;
    private static final int ITEM_ESC = 5;

    private String[] mTaskLabels;
    private GridView mGrid;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.dispatch_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.dispatch_main_title);
        mInflater = LayoutInflater.from(getApplicationContext());

        mTaskLabels = getResources().getStringArray(R.array.dispatch_task_label);

        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new GridAdapter());
        mGrid.setOnItemClickListener(mOnItemClickListener);
    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch (position) {
            case ITEM_SIGN_SCAN:
                launchSignScan();
                return;
            case ITEM_ADDITIONAL_RECORD:
                launchAdditionalSignPersonInfo();
                return;
            case ITEM_DELETE_RECORD:
                launchSignDelete();
                return;
            case ITEM_SIGN_BATCH:
                launchSignBatch();
                return;
            case ITEM_UNUPLOAD_RECORD:
                launchExceptionalRecordUpload();
                return;
            case ITEM_ESC:
                finish();
                return;
            }
        }
    };

    private void launchExceptionalRecordUpload() {
        final Intent intent = new Intent(this, ExceptionalRecordUpload.class);
        startActivity(intent);
    }

    private void launchAdditionalSignPersonInfo() {
        final Intent intent = new Intent(this, AdditionalSignPersonInfo.class);
        startActivity(intent);
    }

    private void launchSignBatch() {
        final Intent intent = new Intent(this, SignBatchActivity.class);
        startActivity(intent);
    }

    private void launchSignDelete() {
        final Intent intent = new Intent(this, SignDeleteActivity.class);
        startActivity(intent);
    }

    private void launchSignScan() {
        final Intent intent = new Intent(this, SignScanActivity.class);
        startActivity(intent);
    }

    private final class GridAdapter extends BaseAdapter {
        private final int[] ICONS = { R.drawable.sign_scan, R.drawable.additional_sign_record,
                R.drawable.delete_sign_record, R.drawable.sign_batch, R.drawable.unupload_record,
                R.drawable.back };

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public Object getItem(int position) {
            return mTaskLabels[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImgTextWrapper wrapper;
            if (convertView == null) {
                wrapper = new ImgTextWrapper();
                convertView = mInflater.inflate(R.layout.dispatch_main_grid_item, null);
                convertView.setTag(wrapper);
            } else {
                wrapper = (ImgTextWrapper) convertView.getTag();
            }

            wrapper.imageIcon = (ImageView) convertView.findViewById(R.id.icon);
            wrapper.imageIcon.setBackgroundResource(ICONS[position]);
            wrapper.textView = (TextView) convertView.findViewById(R.id.label);
            wrapper.textView.setText(mTaskLabels[position]);

            return convertView;
        }

        class ImgTextWrapper {
            ImageView imageIcon;
            TextView textView;
        }
    }

}
