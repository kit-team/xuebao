package cn.net.yto.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import cn.net.yto.R;


public class DispatchMain extends Activity {

    private GridView mGrid;
    private String[] mTaskLabel;
    private LayoutInflater mInflater;

    private static final int ITEM_SIGN_SCAN = 0;
    private static final int ITEM_SIGN_BATCH = 1;
    private static final int ITEM_UNUPLOAD_RECORD = 2;
    private static final int ITEM_ADDITIONAL_RECORD = 3;
    private static final int ITEM_DELETE_RECORD = 4;
    private static final int ITEM_ESC = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispatch_main);
        mInflater = LayoutInflater.from(getApplicationContext());
        mTaskLabel = getResources().getStringArray(R.array.dispatch_task_label);
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setOnItemClickListener(mOnItemClickListener);
        mGrid.setAdapter(new GridAdapter());
    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch(position) {
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

        @Override
        public int getCount() {
            return mTaskLabel.length;
        }

        @Override
        public Object getItem(int position) {
            return mTaskLabel[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final TextView text = (TextView) mInflater.inflate(R.layout.dispatch_main_grid_item, parent, false);
                text.setText(mTaskLabel[position]);
                switch(position) {
                case ITEM_SIGN_SCAN:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sign_scan, 0, 0);
                    break;
                case ITEM_ADDITIONAL_RECORD:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.additional_sign_record, 0, 0);
                    break;
                case ITEM_DELETE_RECORD:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.delete_sign_record, 0, 0);
                    break;
                case ITEM_SIGN_BATCH:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sign_batch, 0, 0);
                    break;
                case ITEM_UNUPLOAD_RECORD:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.unupload_record, 0, 0);
                    break;
                case ITEM_ESC:
                    text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.back, 0, 0);
                    break;
                }

                return text;
            }

            return convertView;
        }
    }
}
