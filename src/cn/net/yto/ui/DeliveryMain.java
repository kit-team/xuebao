package cn.net.yto.ui;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import cn.net.yto.R;


public class DeliveryMain extends Activity {

    private GridView mGrid;
    private String[] mTaskLabel;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispatch_main);
        mInflater = LayoutInflater.from(getApplicationContext());
        mTaskLabel = getResources().getStringArray(R.array.dispatch_task_label);
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new GridAdapter());
        mGrid.setOnItemClickListener(mOnItemClickListener);
        mGrid.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //TODO: launch the task;
                switch(position) {
                case 5:
                    finish();
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                
            }
        });
    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //TODO: launch the task;
            switch(position) {
            case 5:
                finish();
                return;
            }
        }
    };

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
                final Button text = (Button) mInflater.inflate(R.layout.dispatch_main_grid_item, parent, false);
                text.setText(mTaskLabel[position]);
                return text;
            }

            return convertView;
        }
    }
}
