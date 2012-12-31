package cn.net.yto.ui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.yto.R;
import cn.net.yto.utils.DialogHelper;
import cn.net.yto.vo.ReceiveVO;

public class ReceiveBatchActivity extends BaseActivity implements OnClickListener{
	private EditText mCityNumber = null;
	private EditText mPrice = null;
	private EditText mReceiveNumber = null;
	private Spinner mCities = null;
	private Spinner mTimeliness = null;
	private Button mBackBtn = null;
	private Button mDeleteBtn = null;
	private Button mSaveBtn = null;
	private ArrayList<ReceiveVO> mOrder = null;
	private ReceiveAdapter mAdapter = null;
	private ListView mList = null;
	private Runnable mViewOrders =null;
	private int selectedId = -1;
	private TextView mTitleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	private void initViews(){
		setContentView(R.layout.activity_receive_batch);
		mTitleText = (TextView) findViewById(R.id.title_text);
		mTitleText = (TextView) findViewById(R.id.title_text);
		mTitleText.setText(R.string.receive_batch);
		
		mCityNumber = (EditText) findViewById(R.id.city_number);
		mCities = (Spinner) findViewById(R.id.batch_delivery_city);
		mPrice = (EditText) findViewById(R.id.batch_delivery_price);
		mTimeliness = (Spinner) findViewById(R.id.batch_delivery_timeliness);
		mReceiveNumber = (EditText) findViewById(R.id.batch_delivery_number);
		mBackBtn = (Button) findViewById(R.id.btn_back);
		mDeleteBtn = (Button) findViewById(R.id.btn_delete);
		mSaveBtn = (Button) findViewById(R.id.btn_save);
		mBackBtn.setOnClickListener(this);
		mDeleteBtn.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
		mList = (ListView) findViewById(R.id.batch_deliver_list);
		mOrder = new ArrayList<ReceiveVO>();
		mAdapter = new ReceiveAdapter(this, 
				R.layout.view_batch_receive_list, mOrder);
		mList.setAdapter(mAdapter);
		mList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectedId = arg2;
				if(arg1.isFocused()){
					arg1.setBackgroundColor(getResources().getColor(R.color.gray));
				} else {
					arg1.setBackgroundColor(getResources().getColor(R.color.white));
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				selectedId = -1;
				
			}
		});
		
		String[] cities = {"ChengDu","YaAn","LeShan"};
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item,  cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCities.setAdapter(adapter);
        
        String[] timeliness = {getString(R.string.type_region_intraday),
        		getString(R.string.type_morrow_arrive),
        		getString(R.string.type_private_plane),
        		getString(R.string.type_city_wide_intraday),
        		getString(R.string.type_country_wide_morrow_morning),
        		getString(R.string.type_country_wide_morrow)};
        ArrayAdapter<CharSequence> timelineAdapter = new ArrayAdapter<CharSequence>(
        		this, android.R.layout.simple_spinner_item,  timeliness);
        timelineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeliness.setAdapter(timelineAdapter);
		mList.setItemsCanFocus(true);
		
		mViewOrders = new Runnable(){
            @Override
            public void run() {
                getOrders();
            }
        };
        Thread thread =  new Thread(null, mViewOrders, "MagentoBackground");
        thread.start();
        DialogHelper.showProgressDialog(this, "Retrieving data ...");
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_back:
			onBackPressed();
			break;
		case R.id.btn_delete:
			if(selectedId == -1){
//			Toast.makeText(this, "please select a order to delete", 
//					Toast.LENGTH_SHORT).show();
			} else{
				mOrder.remove(selectedId);
			}
			break;
		case R.id.btn_save:
			if(selectedId == -1){
//				Toast.makeText(this, "please select a order to save", 
//						Toast.LENGTH_SHORT).show();				
			} else{
				Toast.makeText(this, "save order successful.", 
						Toast.LENGTH_SHORT).show();								
			}
			break;
		}
	}
	
	
	private void getOrders(){
        try{
            mOrder = new ArrayList<ReceiveVO>();
            ReceiveVO o1 = new ReceiveVO();
            o1.setOrderNo("2345321456");
           // o1.setOperateTime("2012年12月21日");
            o1.setFeeAmt("25");
            ReceiveVO o2 = new ReceiveVO();
            o2.setOrderNo("7689786543");
           // o2.setOperateTime("2012年12月29日");
            o2.setFeeAmt("15");
            mOrder.add(o1);
            mOrder.add(o2);
            Log.i("ARRAY", ""+ mOrder.size());
          } catch (Exception e) {
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            if(mOrder != null && mOrder.size() > 0){
                mAdapter.notifyDataSetChanged();
                for(int i=0;i<mOrder.size();i++)
                	mAdapter.add(mOrder.get(i));
            }
            DialogHelper.closeProgressDialog();
            mAdapter.notifyDataSetChanged();
        }
      };
	
	private class ReceiveAdapter extends ArrayAdapter<ReceiveVO> {

        private ArrayList<ReceiveVO> items;

        public ReceiveAdapter(Context context, int textViewResourceId, ArrayList<ReceiveVO> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.view_batch_receive_list, null);
                    v.setVisibility(View.VISIBLE);
                }
                ReceiveVO o = items.get(position);
                if (o != null) {
                        TextView tv1 = (TextView) v.findViewById(R.id.txt_list_1);
                        TextView tv2 = (TextView) v.findViewById(R.id.txt_list_2);
                        TextView tv3 = (TextView) v.findViewById(R.id.txt_list_3);
                        if (tv1 != null) {
                        	tv1.setText(o.getOrderNo());
                        }
                        if(tv2 != null){
                        	//tv2.setText(o.getOperateTime());
                        }
                        if(tv3 != null){
                        	tv3.setText(o.getFeeAmt());
                        }
                }
                return v;
        }
	}
}
