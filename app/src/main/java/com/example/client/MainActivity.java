package com.example.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import example.com.servicetest.MyAidlService;

public class MainActivity extends AppCompatActivity {

    private MyAidlService myAIDLService;
    String tag = "ClientTag";

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAIDLService = MyAidlService.Stub.asInterface(service);
            try {
                int result = myAIDLService.plus(50, 50);
                String upperStr = myAIDLService.toUpperCase("comes from ClientTest");
                Log.d(tag, "result is " + result);
                Log.d(tag, "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bindService = (Button) findViewById(R.id.bind_service);
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("example.com.servicetest.MyAidlService");
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });


    }
}
