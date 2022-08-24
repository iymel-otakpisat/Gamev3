package com.example.gamev3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Record extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        GameProgress gp = GameProgress.fromPref(sp);
        setContentView(R.layout.activity_record);

        Button backHome = findViewById(R.id.button_back_home);
        backHome.setOnClickListener(v -> {
            Intent myIntent = new Intent(Record.this, MainActivity.class);
            Record.this.startActivity(myIntent);
        });


        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        textView1.setText("1." + gp.one);
        textView2.setText("2." + gp.two);
        textView3.setText("3." + gp.three);
        textView4.setText("4." + gp.four);

    }
}