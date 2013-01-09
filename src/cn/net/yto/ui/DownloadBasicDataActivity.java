package cn.net.yto.ui;


import java.util.Observable;
import java.util.Observer;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import cn.net.yto.R;


public class DownloadBasicDataActivity extends BaseActivity implements Observer , OnItemSelectedListener , android.view.View.OnClickListener{
	private String[] mDataTypeString;
	private Spinner mDataTypeSpinner; // 数据类型
	private Button mDownloadAll_btn;
	private Button mDownloadOne_btn;
	private Button mDeleteAll_btn;
	private Button mDeleteOne_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.download_basicdata);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		setTitleInfo(R.string.data_download);
		
		mDataTypeString = getResources().getStringArray(R.array.basic_data_type);
		mDataTypeSpinner = (Spinner)findViewById(R.id.download_type);
		mDataTypeSpinner.setOnItemSelectedListener(this);
		mDownloadAll_btn = (Button)findViewById(R.id.download_all_btn);
		mDownloadOne_btn = (Button)findViewById(R.id.download_one_btn);
		mDeleteAll_btn   = (Button)findViewById(R.id.delete_all_btn);
		mDeleteOne_btn   = (Button)findViewById(R.id.delete_one_btn);
		mDeleteAll_btn.setOnClickListener(this);
		mDeleteOne_btn.setOnClickListener(this);
		mDeleteAll_btn.setOnClickListener(this);
		mDeleteOne_btn.setOnClickListener(this);
	
		
		}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	BaseAdapter ba = new BaseAdapter() {
		 
		public int getCount() {
		// 一共12个选项
		return 12;
		}
		 
		public Object getItem(int position) {
		return null;
		}
		 
		public long getItemId(int position) {
		return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		};


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		 String value = (String) arg0.getItemAtPosition(position);// 得到选中的选项
		 System.out.println("onItemClick");
		// Toast.makeText(DownloadBasicDataActivity.this, "click "+value, Toast.LENGTH_SHORT).show();
			
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
}