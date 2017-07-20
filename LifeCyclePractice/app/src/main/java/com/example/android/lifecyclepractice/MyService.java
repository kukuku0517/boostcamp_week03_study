package com.example.android.lifecyclepractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("servicecheck","Create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("servicecheck","Startcommand");
        for(int i=0;i<5;i++){
            try{
                Thread.sleep(1000);
            }catch(Exception e){

            }
            Log.i("servicecheck","Startcommand"+i);
        }
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);


        startActivity(i);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.i("servicecheck","dest");
        super.onDestroy();
    }
}
