package com.example.gamev3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        GameProgress gp = GameProgress.fromPref(sp);
        setContentView(R.layout.activity_shop);


        if (gp.soundLevel >= GameProgress.SOUND_LEVEL_FOR_SONGS) {
            startService(new Intent(ShopActivity.this, ShopSoundService.class));
        }

        TextView t = findViewById(R.id.textView);
        String text = "Уровней пройдено: " + gp.levelCompleted + "\nОбщий счёт: " + gp.score;
        text += "\n Уровень графики: " + gp.graphicsLevel;
        text += "\n Уровень звука: " + gp.soundLevel;
        text += "\n Уровень опасностей: " + gp.dangerLevel;
        t.setText(text);
        //Button continuation = findViewById(R.id.button_continue_game);
        //continuation.setOnClickListener(v -> {
        //    Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
        //    ShopActivity.this.startActivity(myIntent);
        //});
        Button sound = findViewById(R.id.sound_upgrade);
        sound.setOnClickListener(v -> {
            ++gp.soundLevel;
            gp.save(sp);
            startLevel();
        });
        Button graphic = findViewById(R.id.graphic_upgrade);
        graphic.setOnClickListener(v -> {
            ++gp.graphicsLevel;
            gp.save(sp);
            startLevel();
        });
        Button danger = findViewById(R.id.warning_upgrade);
        danger.setOnClickListener(v -> {
            ++gp.dangerLevel;
            gp.save(sp);
            startLevel();
        });
    }

    void startLevel() {
        Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
        ShopActivity.this.startActivity(myIntent);
        finish();
    }

    protected void onPause() {
        stopService(new Intent(ShopActivity.this, ShopSoundService.class));
        super.onPause();
    }

}