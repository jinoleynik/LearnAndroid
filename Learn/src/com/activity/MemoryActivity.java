package com.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dao.MemData;
import com.dao.MemList;
import com.example.learn.R;
import com.service.SHZService;
import com.system.MyConst;

public class MemoryActivity extends BaseActivity {

	private ListView mList;
	private TextView mTextall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory);
		// mList = (ListView) findViewById(R.id.lv_memory);
		Intent getmem = new Intent(this, SHZService.class);
		getmem.setAction(MyConst.ACTION_GET_MEMORY);
		startService(getmem);
		mTextall = (TextView) findViewById(R.id.textall);
	}

	protected void onResume() {
		IntentFilter filter = new IntentFilter(MyConst.OUT_MEMORY);
		registerReceiver(mReciver, filter);

		super.onResume();

	}

	protected void onPause() {
		unregisterReceiver(mReciver);
		super.onPause();
	}

	BroadcastReceiver mReciver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			List<MemData> list = ((MemList) intent.getExtras().get(
					MyConst.OUTDATA)).getList();
			String text = "";
			for (int i = 0; i < list.size(); i++) {
				text += list.get(i).getText()+"\n\n\n";
			}
			mTextall.setText(text);
		}
	};

	@Override
	public void onClick(View arg0) {

	}

}
