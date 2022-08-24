package com.example.gamev3;

import android.content.Context;

import com.example.gamev3.Finish;
import com.example.gamev3.Platform;
import com.example.gamev3.Player;
import com.example.gamev3.Spike;

import java.util.ArrayList;

public class Level0GameView extends GameView {
    public Level0GameView(Context context, GameProgress gp) {
        super(context, gp);
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
        platforms.add(new Platform(0.6, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(0.6, 0.6, 0.1, platformSizeY));
        platforms.add(new Platform(1.0, 0.4, 0.15, platformSizeY));
        platforms.add(new Platform(1.3, 0.4, 0.12, platformSizeY));
        platforms.add(new Platform(1.55, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(1.8, 0.3, 0.1, platformSizeY));
        platforms.add(new Platform(2.0, 0.2, 0.1, platformSizeY));


        platforms.add(new Platform(1. / 2, 0.9, 15, platformSizeY));
        platforms.add(new Platform(-0.3, 0, platformSizeX, 1.8));
        platforms.add(new Platform(2.3, 0, platformSizeX, 1.8));

        spikes = new ArrayList<>();
        boolean drawImageSpikes = gameProgress.graphicsLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES) {
            spikes.add(new Spike((float) 0.4, (float) 0.4, (float) 0.03, (float) 0.04, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 0.6, (float) 0.6, (float) 0.1, (float) 0.05, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 1.3, (float) 0.4, (float) 0.05, (float) 0.05, drawImageSpikes, spikeImage));
        }

        saws = new ArrayList<>();
        boolean drawImageSaw = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SAWS_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SAWS) {
            double sawHeight = 0.1;
            double sawWidth = sawHeight * canvasHeight / canvasWidth;
            saws.add(new Saw((float) 0.4, (float) 0.4, sawWidth, sawHeight, 0.05, 1, drawImageSaw, sawImage));
            saws.add(new Saw((float) 1.8, (float) 0.3, sawWidth, sawHeight, 0.05, 0.5, drawImageSaw, sawImage));
        }


        finish = Finish.from_bottom_part(2, 0.2, canvasWidth, canvasHeight, finishImage, finishDoorImage);
    }
}