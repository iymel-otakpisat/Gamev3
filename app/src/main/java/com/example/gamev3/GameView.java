package com.example.gamev3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;


public abstract class GameView extends SurfaceView implements Runnable {
    private static final int MAX_STREAMS = 5;
    Player player;
    final Context context;
    final Thread thread;
    int direction = 0;
    int score;
    float cameraViewX = 0, cameraViewY = 0;
    boolean goJump = false;
    final float cameraAngleX = 0.2f;
    final float cameraAngleY = 0.8f;
    ArrayList<Platform> platforms;
    ArrayList<Spike> spikes;
    ArrayList<Saw> saws;
    volatile float canvasHeight = 0, canvasWidth = 0;
    final int FPS = 40;
    final SurfaceHolder holder;
    Finish finish;
    boolean running = true;
    boolean finished = false;
    boolean lost = false;
    final Drawable finishImage;
    final Drawable finishDoorImage;
    final Drawable spikeImage;
    final Drawable sawImage;
    GameProgress gameProgress;
    Random random;
    SoundPool sp;

    final int j1;
    final int j2;
    final int d1;
    final int d2;
    final int d3;
    final int d4;
    final int d5;


    public GameView(Context context, GameProgress gp) {
        super(context);
        this.context = context;
        gameProgress = gp;
        score = 1500;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES) {
            score = score * 2;
        }
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SAWS) {
            score = score * 2;
        }
        random = new Random();

        finishImage = ContextCompat.getDrawable(this.context, R.drawable.finish);
        finishDoorImage = ContextCompat.getDrawable(this.context, R.drawable.only_door);
        spikeImage = ContextCompat.getDrawable(this.context, R.drawable.spike);
        sawImage = ContextCompat.getDrawable(this.context, R.drawable.circular_saw);

        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        j1 = sp.load(getContext(), R.raw.jump1, 1);
        j2 = sp.load(getContext(), R.raw.jump2, 1);
        d1 = sp.load(getContext(), R.raw.death, 1);
        d2 = sp.load(getContext(), R.raw.death2, 1);
        d3 = sp.load(getContext(), R.raw.death3, 1);
        d4 = sp.load(getContext(), R.raw.death4, 1);
        d5 = sp.load(getContext(), R.raw.death5, 1);

        holder = getHolder();

        thread = new Thread(this);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    setup();
                    thread.start();
                }
            });
        }
    }


    protected void commonSetup() {
        cameraViewX = 0;
        cameraViewY = 0;
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        running = true;
        finished = false;
        lost = false;
        direction = 0;
        goJump = false;
    }

    abstract public void setup();

    public void update() {
        if (score > 0 && !finished && !lost) {
            score--;
        }

        if (!finished && !lost) {
            if (goJump) {
                if (player.jump(platforms)) {
                    ArrayList<Integer> possibleJumpSounds = new ArrayList<>();
                    if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_JUMP_1_SOUND) {
                        possibleJumpSounds.add(j1);
                    }
                    if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_JUMP_2_SOUND) {
                        possibleJumpSounds.add(j2);
                    }
                    if (possibleJumpSounds.size() >= 1) {
                        int sound_id = random.nextInt(possibleJumpSounds.size());
                        sp.play((possibleJumpSounds.get(sound_id)), 1, 1, 0, 0, 1);
                    }
                }
                goJump = false;
            }
            if (direction == 2) {
                player.accelerate(0, 0.002f);
            }
            if (direction == 3) {
                player.accelerate(-0.001f, 0);
            }
            if (direction == 4) {
                player.accelerate(0.001f, 0);
            }
        }
        for (Saw saw : saws) {
            saw.update();
        }
        if (player.getX() - cameraViewX > 0.5 + cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5f + cameraAngleX / 2);
        } else if (player.getX() - cameraViewX < 0.5f - cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5f - cameraAngleX / 2);
        }
        if (player.getY() - cameraViewY > 0.5 + cameraAngleY / 2) {
            cameraViewY += 0.01;
        } else if (player.getY() - cameraViewY < 0.5 - cameraAngleY / 2) {
            cameraViewY -= 0.005;
        }
        player.update(platforms);
        if (finish.touched(player) && !finished) {
            finished = true;
            Handler mainHandler = new Handler(Looper.getMainLooper());

            LevelActivity levelActivity = (LevelActivity) this.context;
            Runnable myRunnable = () -> levelActivity.completeLevel(score);
            mainHandler.post(myRunnable);
        }
        if (!lost) {
            for (Spike spike : spikes) {
                if (spike.touched(player)) {
                    lost = true;
                    if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_DEATH_SOUND) {
                        playDeathSound();
                    }
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    LevelActivity levelActivity = (LevelActivity) this.context;
                    Runnable myRunnable = levelActivity::lostLevel;
                    mainHandler.post(myRunnable);
                }
            }
            for (Saw saw : saws) {
                if (player.touchSaw(saw)) {
                    lost = true;
                    if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_DEATH_SOUND) {
                        playDeathSound();
                    }
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    LevelActivity levelActivity = (LevelActivity) this.context;
                    Runnable myRunnable = levelActivity::lostLevel;
                    mainHandler.post(myRunnable);
                }
            }
        }

    }

    private void playDeathSound() {
        float x = random.nextFloat();
        if (x < 0.5) {
            sp.play(d1, 1, 1, 0, 0, 1);
        } else if (x < 0.7) {
            sp.play(d2, 1, 1, 0, 0, 1);
        } else if (x < 0.9) {
            sp.play(d3, 1, 1, 0, 0, 1);
        } else if (x < 0.95) {
            sp.play(d4, 1, 1, 0, 0, 1);
        } else {
            sp.play(d5, 1, 1, 0, 0, 1);
        }
    }

    public void stopRunning() {
        running = false;
    }

    void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) {
                return;
            }
            Paint paint = new Paint();
            paint.setColor(0xffffffff);
            canvas.drawPaint(paint);

            finish.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);

            player.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            finish.drawDoor(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);

            paint.setTextSize(30);
            paint.setColor(Color.BLACK);
            canvas.drawText("Очки: " + score, 10, 25, paint);

            for (Platform platform : platforms) {
                platform.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            }
            for (Spike spike : spikes) {
                spike.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            }
            for (Saw saw : saws) {
                saw.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            }
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void jump() {
        goJump = true;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    @Override
    public void run() {
        update();
        long time = System.currentTimeMillis(), time2;
        long dt = 1000 / FPS;
        while (running) {
            update();
            draw();
            time2 = System.currentTimeMillis();
            if (time2 - time < dt) {
                try {
                    Thread.sleep(dt - (time2 - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = time2;
        }

    }
}