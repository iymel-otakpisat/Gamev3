package com.example.gamev3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Record extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        setContentView(R.layout.activity_record);

        Button backHome = findViewById(R.id.button_back_home);
        backHome.setOnClickListener(v -> {
            Intent myIntent = new Intent(Record.this, MainActivity.class);
            Record.this.startActivity(myIntent);
        });

        ArrayList<Integer> curRecords = RecordHandler.getRecords(sp);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        textView1.setText("1." + curRecords.get(0));
        textView2.setText("2." + curRecords.get(1));
        textView3.setText("3." + curRecords.get(2));
        textView4.setText("4." + curRecords.get(3));

    }
}