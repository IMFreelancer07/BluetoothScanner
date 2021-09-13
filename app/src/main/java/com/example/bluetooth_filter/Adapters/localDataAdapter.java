package com.example.bluetooth_filter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetooth_filter.R;
import com.example.bluetooth_filter.Helpers.localData;

import java.util.List;

public class localDataAdapter extends ArrayAdapter<localData> {

    //storing local data in list
    private List<localData> ld;

    //context object
    private Context context;

    public localDataAdapter(Context context, int resource, List<localData> ld){
        super(context, resource, ld);

        this.context = context;
        this.ld     = ld;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {

        //getting layout inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview items
        View ListViewItem = inflater.inflate(R.layout.localdata, null, true);

        TextView textview_id = (TextView) ListViewItem.findViewById(R.id.d_id);
        TextView textview_tp = (TextView) ListViewItem.findViewById(R.id.d_tp);
        TextView textview_rfid = (TextView) ListViewItem.findViewById(R.id.d_rfid);
        TextView textview_time = (TextView) ListViewItem.findViewById(R.id.d_time);
        ImageView ImageView_Status = (ImageView) ListViewItem.findViewById(R.id.d_Status);

        //getting current local data
        localData localData = ld.get(position);

        //setting values to textViews
        textview_id.setText(localData.getId());
        textview_tp.setText(localData.getTp());
        textview_rfid.setText(localData.getRfid());
        textview_time.setText(localData.getTime());

        if(localData.getStatus() == 0)
        {
            ImageView_Status.setBackgroundResource(R.drawable.stopwatch);
        }
        else
        {
            ImageView_Status.setBackgroundResource(R.drawable.success);
        }

        return ListViewItem;


    }
}
