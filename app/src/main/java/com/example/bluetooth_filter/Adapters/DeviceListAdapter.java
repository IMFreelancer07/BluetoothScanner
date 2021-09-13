package com.example.bluetooth_filter.Adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bluetooth_filter.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private int  mViewResourceId;

    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices){
        super(context, tvResourceId,devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        BluetoothDevice device = mDevices.get(position);

        if (device != null) {

            TextView deviceName = (TextView) convertView.findViewById(R.id.tvDeviceName);
//            TextView deviceAdress = (TextView) convertView.findViewById(R.id.tvDeviceAddress);
            TextView datetime = (TextView) convertView.findViewById(R.id.tvDateTime);

            if (deviceName != null) {
                deviceName.setText(device.getName());
                String datetimenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                datetime.setText(datetimenow);


                myDbAdapter dbAdapter;
                dbAdapter = new myDbAdapter(getContext());
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                final int NAME_NOT_SYNCED_WITH_SERVER = 0;
                dbAdapter.insertData("timing point 1", device.getName(), time, NAME_NOT_SYNCED_WITH_SERVER);

            }
//            if (deviceAdress != null) {
//                deviceAdress.setText(device.getAddress());
//            }
        }

        return convertView;
    }

}





