package com.example.gamev3;

import android.content.Context;

import java.util.ArrayList;

public class Level1GameView extends GameView {
    public Level1GameView(Context context, GameProgress gp) {
        super(context, gp);
    }

    @Override
    public void setup() {
        commonSetup();

        double height = 0.1;
        double width = height * canvasHeight / canvasWidth;
        double platformSizeX = 10 / canvasWidth, platformSizeY = 10 / canvasHeight;
        player = new Player((float) 0, (float) 0.5,
                width, height);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2, 0.6, 0.1, platformSizeY));

        platforms.add(new Platform(-0.05, 0.4, 0.15, platformSizeY));
        platforms.add(new Platform(0.05, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(0.2, 0.0, 0.2, platformSizeY));
        platforms.add(new Platform(0.5, -0.1, 0.1, platformSizeY));
        platforms.add(new Platform(0.9, -0.2, 0.4, platformSizeY));

        platforms.add(new Platform(0.4, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.7, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.9, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(1.1, 0.4, 0.1, platformSizeY));

        platforms.add(new Platform(1.3, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(1.6, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(1.8, 0.0, 0.1, platformSizeY));
        platforms.add(new Platform(1.8, -0.4, 0.1, platformSizeY));
        platforms.add(new Platform(2.0, 0.2, 0.15, platformSizeY));

        platforms.add(new Platform(1. / 2, 0.9, 15, platformSizeY));
        platforms.add(new Platform(-1, 0, platformSizeX, 1.8));
        platforms.add(new Platform(4, 0, platformSizeX, 1.8));

        spikes = new ArrayList<>();
        boolean drawImageSpikes = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SPIKES_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES) {
            spikes.add(new Spike((float) 0.2, (float) 0.0, (float) 0.05, (float) 0.05, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 0.9, (float) 0.4, (float) 0.025, (float) 0.04, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 1.1, (float) 0.4, (float) 0.025, (float) 0.04, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 0.8, (float) -0.2, (float) 0.04, (float) 0.05, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 1.0, (float) -0.2, (float) 0.04, (float) 0.05, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 0.9, (float) -0.2, (float) 0.03, (float) 0.04, drawImageSpikes, spikeImage));
            spikes.add(new Spike((float) 2.0, (float) 0.2, (float) 0.02, (float) 0.03, drawImageSpikes, spikeImage));
        }

        saws = new ArrayList<>();
        boolean drawImageSaw = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SAWS_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SAWS) {
            double sawHeight = 0.1;
            double sawWidth = sawHeight * canvasHeight / canvasWidth;
            saws.add(new Saw((float) 0.4, (float) 0.4, sawWidth, sawHeight, 0.05, 1, drawImageSaw, sawImage));
            saws.add(new Saw((float) 0.7, (float) 0.8, sawWidth, sawHeight, 0.05, 1, drawImageSaw, sawImage));
            saws.add(new Saw((float) 0.2, (float) -0.2, sawWidth, sawHeight, 0.13, 1, drawImageSaw, sawImage));
        }

        finish = Finish.from_bottom_part(2.0, 0.2, canvasWidth, canvasHeight, finishImage, finishDoorImage);

    }
}
