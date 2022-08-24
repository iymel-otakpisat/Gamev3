package com.example.gamev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class LevelActivity extends AppCompatActivity implements View.OnTouchListener {
    private GameView level;

    DirectionButton buttonUp;
    DirectionButton buttonLeft;
    DirectionButton buttonRight;
    GameProgress gp;

    GameView getGameView() {
        Random random = new Random();
        int x = random.nextInt(2);
        GameView[] lvls = {new Level0GameView(this, gp), new Level1GameView(this, gp)};

        return lvls[x];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        gp = GameProgress.fromPref(sp);
        level = getGameView();

        super.onCreate(savedInstanceState);
        if (gp.soundLevel >= GameProgress.SOUND_LEVEL_FOR_SONGS) {
            startService(new Intent(LevelActivity.this, LevelSoundService.class));
        }

        startLevel();
    }

    private void startLevel() {

        setContentView(level);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_level, null, false);
        addContentView(secondLayerView, lp);

        buttonUp = findViewById(R.id.buttonUp);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        buttonUp.setOnTouchListener(this);
        buttonLeft.setOnTouchListener(this);
        buttonRight.setOnTouchListener(this);

    }

    public void completeLevel(int score) {
        View.OnTouchListener none = (v, event) -> false;
        buttonUp.setOnTouchListener(none);
        buttonLeft.setOnTouchListener(none);
        buttonRight.setOnTouchListener(none);

        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        GameProgress gp = GameProgress.fromPref(sp);
        gp.score += score;
        gp.levelCompleted += 1;
        gp.save(sp);

        ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView2 = LayoutInflater.from(this).inflate(R.layout.activity_level_complete, null, false);
        addContentView(secondLayerView2, lp2);
        Button backHome = findViewById(R.id.button_back_home);

        backHome.setOnClickListener(v -> {
            level.stopRunning();
            Intent myIntent = new Intent(LevelActivity.this, MainActivity.class);
            LevelActivity.this.startActivity(myIntent);
            finish();

        });
        Button continuation = findViewById(R.id.button_continue_shop);
        continuation.setOnClickListener(v -> {
            Intent myIntent = new Intent(LevelActivity.this, ShopActivity.class);
            LevelActivity.this.startActivity(myIntent);
            finish();
        });
    }

    public void lostLevel() {
        View.OnTouchListener none = (v, event) -> false;
        buttonUp.setOnTouchListener(none);
        buttonLeft.setOnTouchListener(none);
        buttonRight.setOnTouchListener(none);

        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        GameProgress gp = GameProgress.fromPref(sp);
        gp.gameInProgress = false;
        gp.save(sp);


        Toast toast = Toast.makeText(getApplicationContext(),
                "Игра завершена! Финальный счёт: " + gp.score, Toast.LENGTH_SHORT);
        toast.show();

        ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView2 = LayoutInflater.from(this).inflate(R.layout.activity_level_gameover, null, false);
        addContentView(secondLayerView2, lp2);
        Button backHome = findViewById(R.id.button_back_home);
        backHome.setOnClickListener(v -> {
            level.stopRunning();
            Intent myIntent = new Intent(LevelActivity.this, MainActivity.class);
            LevelActivity.this.startActivity(myIntent);
            finish();
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v == buttonUp) {
                System.out.println("up pressed");
                level.jump();
            }
            if (v == buttonLeft) {
                level.setDirection(3);
                System.out.println("left pressed");

            }
            if (v == buttonRight) {
                System.out.println("right pressed");

                level.setDirection(4);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP && v != buttonUp) {
            level.setDirection(0);
            System.out.println("unpressed");

            return v.performClick();
        }
        return true;
    }

    protected void onPause() {
        stopService(new Intent(LevelActivity.this, LevelSoundService.class));
        super.onPause();
    }

}