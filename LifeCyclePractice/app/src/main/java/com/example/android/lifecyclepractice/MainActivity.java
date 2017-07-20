package com.example.android.lifecyclepractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("check","Create");
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MyService.class);
                i.putExtra("command","show");
                startService(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("check","start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("check","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("check","destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("check","pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("check","resume");
    }

}
