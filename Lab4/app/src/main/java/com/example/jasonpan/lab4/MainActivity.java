package com.example.jasonpan.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMp3, btnMp4;
        btnMp3 = (Button)findViewById(R.id.button);
        btnMp4 = (Button)findViewById(R.id.button2);

        btnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp3 = new Intent();
                mp3.setClass(MainActivity.this, Mp3Activity.class);
                startActivity(mp3);
            }
        });

        btnMp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp4 = new Intent();
                mp4.setClass(MainActivity.this, Mp4Activity.class);
                startActivity(mp4);
            }
        });

    }
}
