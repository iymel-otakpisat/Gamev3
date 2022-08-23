package com.example.gamev3;

import android.content.SharedPreferences;

public class GameProgress {
    boolean gameInProgress;
    int score;
    int levelCompleted;
    int dangerLevel;
    int graphicsLevel;
    int soundLevel;

    GameProgress(boolean gameInProgress, int score, int levelCompleted, int dangerLevel, int graphicsLevel, int soundLevel) {
        this.gameInProgress = gameInProgress;
        this.score = score;
        this.levelCompleted = levelCompleted;
        this.dangerLevel = dangerLevel;
        this.graphicsLevel = graphicsLevel;
        this.soundLevel = soundLevel;
    }


    static GameProgress fromPref(SharedPreferences sp) {
        return new GameProgress(
                sp.getBoolean("game_in_progress", false),
                sp.getInt("score", 0),
                sp.getInt("level_completed", 0),
                sp.getInt("danger_level", 0),
                sp.getInt("graphics_level", 0),
                sp.getInt("sound_level", 0)
        );
    }

    public void save(SharedPreferences sp) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("game_in_progress", gameInProgress);
        ed.putInt("level_completed", levelCompleted);
        ed.putInt("score", score);
        ed.putInt("danger_level", dangerLevel);
        ed.putInt("graphics_level", graphicsLevel);
        ed.putInt("sound_level", soundLevel);
        ed.apply();
    }

    public void clear(SharedPreferences sp) {
        gameInProgress = false;
        score = 0;
        dangerLevel = 0;
        graphicsLevel = 0;
        soundLevel = 0;
        levelCompleted = 0;
        save(sp);
    }
}
