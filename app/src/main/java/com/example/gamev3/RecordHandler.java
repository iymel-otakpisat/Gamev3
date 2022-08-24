package com.example.gamev3;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

public class RecordHandler {
    final static int RECORDS_TO_HANDLE = 5;
    public static void updateScores(int score, SharedPreferences sp) {
        ArrayList<Integer> curRecords = getRecords(sp);
        curRecords.add(score);
        Collections.sort(curRecords);
        Collections.reverse(curRecords);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < RECORDS_TO_HANDLE; ++i) {
            editor.putInt("record_" + i, curRecords.get(i));
            System.out.printf("record %d %d\n", i, curRecords.get(i));
        }
        editor.apply();
    }

    public static ArrayList<Integer> getRecords(SharedPreferences sp) {
        ArrayList<Integer> curRecords = new ArrayList<>();
        for (int i = 0; i < RECORDS_TO_HANDLE; ++i) {
            curRecords.add(sp.getInt("record_" + i, 0));
            System.out.printf("record %d %d\n", i, curRecords.get(i));
        }
        return curRecords;
    }
}
