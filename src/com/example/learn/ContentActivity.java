package com.example.learn;

import contentprovider.UserProvider;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContentActivity extends Activity implements OnClickListener, OnItemClickListener {
    private Cursor cur;
    private CursorAdapter cur_adapter;
    private ListView lv;

    private final Uri USERS_CONTENT_URI = Uri
            .parse("content://usersprovider/users");
    public final String USER_NAME = "User_name";
    private int mCon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.additem).setOnClickListener(this);
        findViewById(R.id.deleteitem).setOnClickListener(this);
        findViewById(R.id.updateitem).setOnClickListener(this);
        this.cur = this.getContentResolver().query(USERS_CONTENT_URI, null,
                null, null, null);

        this.cur_adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, this.cur,
                new String[] { USER_NAME }, new int[] { android.R.id.text1 });

        lv = (ListView) this.findViewById(R.id.listView1);
        lv.setAdapter(this.cur_adapter);
//        cur_adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        Long id;
        ContentValues v = new ContentValues();
        switch (arg0.getId()) {
        case R.id.additem:
            v.put(UserProvider.INSERT_USER, "DNIWE");
            getContentResolver().insert(USERS_CONTENT_URI, v);
            cur.requery();
            cur_adapter.notifyDataSetChanged();
            break;

        case R.id.deleteitem:            
            this.getContentResolver().delete(USERS_CONTENT_URI, String.valueOf(0), null);            
            this.cur.requery();
            this.cur_adapter.notifyDataSetChanged();
            break;
        case R.id.updateitem:
            v.put(UserProvider.UPDATE_USER, "RAQ");            
            getContentResolver().update(USERS_CONTENT_URI, v, String.valueOf(mCon++), null);            
            cur.requery();
            cur_adapter.notifyDataSetChanged();
            break;
        default:
            break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ContentValues v = new ContentValues();
        v.put(UserProvider.UPDATE_USER, "RAQ");    
        getContentResolver().update(USERS_CONTENT_URI, v, String.valueOf(arg2), null);
        cur.requery();
        cur_adapter.notifyDataSetChanged();
        
    }
}
