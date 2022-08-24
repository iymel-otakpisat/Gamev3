package com.example.gamev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start;
    Button record;
    Button resetProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.main_start_button);
        start.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
            GameProgress gp = GameProgress.fromPref(sp);
            if (!gp.gameInProgress) {
                gp.clear(sp);
            }
            gp.gameInProgress = true;
            gp.save(sp);
            Intent myIntent = new Intent(MainActivity.this, LevelActivity.class);
            MainActivity.this.startActivity(myIntent);
        });
        record = findViewById(R.id.record_button);
        record.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
            GameProgress gp = GameProgress.fromPref(sp);
            if (!gp.gameInProgress) {
                gp.clear(sp);
            }
            gp.gameInProgress = true;
            gp.save(sp);
            Intent myIntent = new Intent(MainActivity.this, Record.class);
            MainActivity.this.startActivity(myIntent);
        });
        resetProgress = findViewById(R.id.reset_progress);
        resetProgress.setOnClickListener(v -> {
            clearGame();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Прогресс сброшен", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    void clearGame() {
        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        GameProgress.fromPref(sp).clear(sp);
    }
}