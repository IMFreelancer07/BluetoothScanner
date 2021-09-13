package com.example.bluetooth_filter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bluetooth_filter.Adapters.localDataAdapter;
import com.example.bluetooth_filter.Adapters.myDbAdapter;
import com.example.bluetooth_filter.Helpers.localData;

import java.util.ArrayList;
import java.util.List;

public class viewDataActivity extends AppCompatActivity {
    myDbAdapter helper;
    Button vd;

    public String tp, rfid, time;
    public int status;

    List<com.example.bluetooth_filter.Helpers.localData> localData;
    ListView listViewNames;

    BroadcastReceiver broadcastReceiver;



    //adapterobject for list view
    localDataAdapter ldAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdata);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        registerReceiver(new NetworkStateChecker(getApplicationContext()), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        vd = (Button) findViewById(R.id.viewData);
        listViewNames = (ListView) findViewById(R.id.lv_localdata);

        //initialize Datebase helper
        helper =new myDbAdapter(this);
        localData = new ArrayList<>();

        loadData();

        refreshList();

        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadData();
            }
        };

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(MainActivity.DATA_SAVED_BROADCAST));

        vd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res =  helper.getData();
                if(res.getCount()==0){
                    Toast.makeText(viewDataActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("id :" +res.getString(0)+" ");
                    buffer.append("tp :"+res.getString(1)+" ");
                    buffer.append("rfid :"+res.getString(2)+" ");
                    buffer.append("time :"+res.getString(3)+"\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(viewDataActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();


            }
        });




    }



    private void loadData() {
        localData.clear();
        Cursor cursor = helper.getData();
        if (cursor.moveToFirst()) {
            do {
                localData ld = new localData(
                        cursor.getString(cursor.getColumnIndex(myDbAdapter.UID)),
                        cursor.getString(cursor.getColumnIndex(myDbAdapter.TP)),
                        cursor.getString(cursor.getColumnIndex(myDbAdapter.RFID)),
                        cursor.getString(cursor.getColumnIndex(myDbAdapter.TIME)),
                        cursor.getInt(cursor.getColumnIndex(myDbAdapter.COLUMN_STATUS))
                );
                localData.add(ld);
            } while (cursor.moveToNext());
        }

        ldAdapter = new localDataAdapter(this, R.layout.localdata, localData);
        listViewNames.setAdapter(ldAdapter);
    }

    private void refreshList() {
        ldAdapter.notifyDataSetChanged();
    }



}
