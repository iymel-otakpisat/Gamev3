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
        float height = 0.1f;
        float width = height * canvasHeight / canvasWidth;
        float platformSizeX = 10 / canvasWidth;
        float platformSizeY = 10 / canvasHeight;
        player = new Player(0, 0.5f,
                width, height);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2f, 0.6f, 0.1f, platformSizeY));

        platforms.add(new Platform(0.4f, 0.4f, 0.1f, platformSizeY));
        platforms.add(new Platform(0.6f, 0.2f, 0.1f, platformSizeY));
        platforms.add(new Platform(0.6f, 0.6f, 0.1f, platformSizeY));
        platforms.add(new Platform(1.0f, 0.4f, 0.15f, platformSizeY));
        platforms.add(new Platform(1.3f, 0.4f, 0.12f, platformSizeY));
        platforms.add(new Platform(1.55f, 0.4f, 0.1f, platformSizeY));
        platforms.add(new Platform(1.8f, 0.3f, 0.1f, platformSizeY));
        platforms.add(new Platform(2.0f, 0.2f, 0.1f, platformSizeY));


        platforms.add(new Platform(1.f / 2, 0.9f, 15, platformSizeY));
        platforms.add(new Platform(-0.3f, 0, platformSizeX, 1.8f));
        platforms.add(new Platform(2.3f, 0, platformSizeX, 1.8f));

        spikes = new ArrayList<>();
        boolean drawImageSpikes = gameProgress.graphicsLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES) {
            spikes.add(new Spike(0.4f, 0.4f, 0.03f, 0.04f, drawImageSpikes, spikeImage));
            spikes.add(new Spike(0.6f, 0.6f, 0.1f, 0.05f, drawImageSpikes, spikeImage));
            spikes.add(new Spike(1.3f, 0.4f, 0.05f, 0.05f, drawImageSpikes, spikeImage));
        }

        saws = new ArrayList<>();
        boolean drawImageSaw = gameProgress.graphicsLevel >= GameProgress.GRAPHICS_LEVEL_FOR_SAWS_IMAGES;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SAWS) {
            float sawHeight = 0.1f;
            float sawWidth = sawHeight * canvasHeight / canvasWidth;
            saws.add(new Saw(0.4f, 0.4f, sawWidth, sawHeight, 0.05f, 1f, drawImageSaw, sawImage));
            saws.add(new Saw(1.8f, 0.3f, sawWidth, sawHeight, 0.05f, 0.5f, drawImageSaw, sawImage));
        }


        finish = Finish.from_bottom_part(2, 0.2f, canvasWidth, canvasHeight, finishImage, finishDoorImage);
    }
}