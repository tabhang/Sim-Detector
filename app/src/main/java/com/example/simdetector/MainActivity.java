package com.example.simdetector;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonStart,buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.button);
        buttonStop = findViewById(R.id.button2);

        buttonStart.setOnClickListener( this);
        buttonStop.setOnClickListener( this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonStart){
            Intent serviceIntent = new Intent(this, b_service.class);
            ContextCompat.startForegroundService(this,serviceIntent);
        }
        if(v==buttonStop){
            stopService(new Intent(this,b_service.class));
        }
    }
}
