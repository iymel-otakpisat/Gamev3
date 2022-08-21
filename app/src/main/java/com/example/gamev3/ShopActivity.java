package com.example.gamev3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //Button continuation = findViewById(R.id.button_continue_game);
        //continuation.setOnClickListener(v -> {
        //    Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
        //    ShopActivity.this.startActivity(myIntent);
        //});
        Button sound = findViewById(R.id.sound_upgrade);
        sound.setOnClickListener(v -> {
            //мб создать публичную переменную, а дальше через if
            //тут надо будет хотя бы сделать звук прыжка(мб вообще уберу эту кнопку)
            Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
            ShopActivity.this.startActivity(myIntent);
        });
        Button graphic = findViewById(R.id.graphic_upgrade);
        graphic.setOnClickListener(v -> {
            //пока можно сделать тупо одно изменение графики(например: изначально финишь сделать прямоугольникам, а после то, что уже есть сейчас)
            Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
            ShopActivity.this.startActivity(myIntent);
        });
        Button danger = findViewById(R.id.warning_upgrade);
        danger.setOnClickListener(v -> {
            //добавление шипов
            //добавление пил
            Intent myIntent = new Intent(ShopActivity.this, LevelActivity.class);
            ShopActivity.this.startActivity(myIntent);
        });
    }
}