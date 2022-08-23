package com.example.gamev3;

import android.content.Context;

import com.example.gamev3.Finish;
import com.example.gamev3.Platform;
import com.example.gamev3.Player;
import com.example.gamev3.Spike;

import java.util.ArrayList;

public class Level0GameView extends GameView {
    public Level0GameView(Context context) {
        super(context);
    }

    @Override
    public void setup() {
        commonSetup();
        double height = 0.1;
        double width = height * canvasHeight / canvasWidth;
        double platformSizeX = 10 / canvasWidth;
        double platformSizeY = 10 / canvasHeight;
        player = new Player((float) 0, (float) 0.5,
                width, height);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2, 0.6, 0.1, platformSizeY));

        platforms.add(new Platform(0.4, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.7, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.85, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(1.15, 0.2, 0.1, platformSizeY));

        platforms.add(new Platform(1. / 2, 0.9, 15, platformSizeY));
        platforms.add(new Platform(-0.3, 0, platformSizeX, 1.8));
        platforms.add(new Platform(1.3, 0, platformSizeX, 1.8));

        spikes = new ArrayList<>();
        spikes.add(new Spike((float) 0.2, (float) 0.6, (float) 0.1, (float) platformSizeY));

        saws = new ArrayList<>();
        double sawHeight = 0.1;
        double sawWidth = sawHeight * canvasHeight / canvasWidth;
        saws.add(new Saw((float) 0.4, (float) 0.4, sawWidth, sawHeight, 0.05, 1));


        finish = Finish.from_bottom_part(1.15, 0.2, canvasWidth, canvasHeight, finishImage, finishDoorImage);
    }
}