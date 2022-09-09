package com.example.gamev3;

import android.content.Context;

import com.example.gamev3.Finish;
import com.example.gamev3.Platform;
import com.example.gamev3.Player;
import com.example.gamev3.Spike;

import java.util.ArrayList;

public class Level2GameView extends GameView {
    public Level2GameView(Context context, GameProgress gp) {
        super(context, gp);
    }

    @Override
    public void setup() {
        commonSetup();
        float height = 0.1f;
        float width = height * canvasHeight / canvasWidth;
        float platformSizeX = 10 / canvasWidth;
        float platformSizeY = 10 / canvasHeight;
        player = new Player(0, 0.5f,
                width, height);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2f, 0.6f, 0.1f, platformSizeY));
        platforms.add(new Platform(0.4f, 0.3f, 0.14f, platformSizeY));
        platforms.add(new Platform(0.6f, 0.3f, 0.14f, platformSizeY));
        platforms.add(new Platform(0.8f, 0.3f, 0.14f, platformSizeY));
        platforms.add(new Platform(1.0f, 0.1f, 0.1f, platformSizeY));
        platforms.add(new Platform(1.2f, 0.1f, 0.02f, platformSizeY));
        platforms.add(new Platform(1.3f, 0.1f, 0.02f, platformSizeY));
        platforms.add(new Platform(1.5f, 0.1f, 0.05f, platformSizeY));
        platforms.add(new Platform(1.8f, 0.1f, 0.4f, platformSizeY));
        platforms.add(new Platform(2.2f, 0.1f, 0.14f, platformSizeY));

        platforms.add(new Platform(1.f / 2, 0.9f, 15, platformSizeY));
        platforms.add(new Platform(-0.3f, 0, platformSizeX, 1.8f));
        platforms.add(new Platform(2.3f, 0, platformSizeX, 1.8f));

        spikes = new ArrayList<>();
        boolean drawImageSpikes = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SPIKES_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES) {
            spikes.add(new Spike(0.4f, 0.3f, 0.05f, 0.05f, drawImageSpikes, spikeImage));
            spikes.add(new Spike(0.6f, 0.3f, 0.05f, 0.05f, drawImageSpikes, spikeImage));
            spikes.add(new Spike(0.8f, 0.3f, 0.05f, 0.05f, drawImageSpikes, spikeImage));
        }

        saws = new ArrayList<>();
        boolean drawImageSaw = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SAWS_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SAWS) {
            float sawHeight = 0.1f;
            float sawWidth = sawHeight * canvasHeight / canvasWidth;
            saws.add(new Saw(1.8f, 0.1f, sawWidth, sawHeight, 0.2f, 1f, drawImageSaw, sawImage));
            saws.add(new Saw(1.8f, 0.1f, sawWidth, sawHeight, 0.2f, 0.5f, drawImageSaw, sawImage));
            saws.add(new Saw(0.6f, 0.8f, sawWidth, sawHeight, 0.1f, 1.2f, drawImageSaw, sawImage));
            saws.add(new Saw(1.1f, 0.8f, sawWidth, sawHeight, 0.1f, 0.8f, drawImageSaw, sawImage));
        }


        finish = Finish.from_bottom_part(2.2f, 0.1f, canvasWidth, canvasHeight, finishImage, finishDoorImage);
    }
}