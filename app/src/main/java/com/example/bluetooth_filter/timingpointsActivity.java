package com.example.bluetooth_filter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bluetooth_filter.Helpers.SessionHandler;
import com.example.bluetooth_filter.Helpers.User;

public class timingpointsActivity extends AppCompatActivity {

    private Button tp1, tp2, tp3, tp4, tp5, tp6, vd, logout;
    private SessionHandler session;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timing_points);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();


        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(timingpointsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        Asynctask asynctask = new Asynctask();
        asynctask.execute();

    }


    public class Asynctask extends AsyncTask{

         protected Object doInBackground (Object[] objects)
         {
//             Timer timer;
//             timer = new Timer();
//             timer.schedule(new TimerTask() {
//                 @Override
//                 public void run() {
//
//                    registerReceiver(new NetworkStateChecker(getApplicationContext()), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//                 }
//             },5*60*1000);

             init();
             return null;
         }
     }




    private void init()
    {
        tp1 = (Button) findViewById(R.id.btntp1);
        tp2 = (Button) findViewById(R.id.btntp2);
        tp3 = (Button) findViewById(R.id.btntp3);
        tp4 = (Button) findViewById(R.id.btntp4);
        tp5 = (Button) findViewById(R.id.btntp5);
        tp6 = (Button) findViewById(R.id.btntp6);
        vd = (Button) findViewById(R.id.btnviewData);

        tp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp1");
            }
        });

        tp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp2");
            }
        });

        tp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp3");
            }
        });

        tp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp4");
            }
        });

        tp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp5");
            }
        });
        tp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "tp6");
            }
        });
        vd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTimingPoint( "vd");
            }
        });



    }
    private void SendUserToTimingPoint(String s)
    {
        if (s=="tp1") {
            Intent mainIntent = new Intent(this, tp1Activity.class);
            startActivity(mainIntent);
        }

        else if (s=="tp2") {
            Intent mainIntent = new Intent(this, tp2Activity.class);
            startActivity(mainIntent);
        }
        else if (s=="tp3") {
            Intent mainIntent = new Intent(this, tp3Activity.class);
            startActivity(mainIntent);
        }
        else if (s=="tp4") {
            Intent mainIntent = new Intent(this, tp4Activity.class);
            startActivity(mainIntent);
        }
        else if (s=="tp5") {
            Intent mainIntent = new Intent(this, tp5Activity.class);
            startActivity(mainIntent);
        }
        else if (s=="tp6"){
            Intent mainIntent = new Intent(this, tp6Activity.class);
            startActivity(mainIntent);
        }
        else {
            Intent mainIntent = new Intent(this, viewDataActivity.class);
            startActivity(mainIntent);
        }
    }
}
