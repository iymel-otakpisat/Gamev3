package com.example.gamev3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;


public abstract class GameView extends SurfaceView implements Runnable {
    Player player;
    final Context context;
    final Thread thread;
    int direction = 0;
    int score;
    double cameraViewX = 0, cameraViewY = 0;
    boolean goJump = false;
    final double cameraAngleX = 0.2;
    final double cameraAngleY = 0.8;
    ArrayList<Platform> platforms;
    ArrayList<Spike> spikes;
    ArrayList<Saw> saws;
    volatile double canvasHeight = 0, canvasWidth = 0;
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

    MediaPlayer jumpsound1;
    MediaPlayer jumpsound2;
    MediaPlayer deathsound;

    public GameView(Context context, GameProgress gp) {
        super(context);
        this.context = context;
        gameProgress = gp;
        score = 1500;
        if (gameProgress.dangerLevel >= GameProgress.DANGER_LEVEL_FOR_SPIKES){
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

        jumpsound1 = MediaPlayer.create(context, R.raw.jump1);
        jumpsound2 = MediaPlayer.create(context, R.raw.jump2);
        deathsound = MediaPlayer.create(context, R.raw.death);

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
                player.jump(platforms);
                goJump = false;
                ArrayList<MediaPlayer> possibleJumpSounds = new ArrayList<>();
                if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_JUMP_1_SOUND) {
                    possibleJumpSounds.add(jumpsound1);
                }
                if (gameProgress.soundLevel >= GameProgress.SOUND_LEVEL_FOR_JUMP_2_SOUND) {
                    possibleJumpSounds.add(jumpsound2);
                }
                if (possibleJumpSounds.size() >= 1) {
                    int sound_id = random.nextInt(possibleJumpSounds.size());
                    (possibleJumpSounds.get(sound_id)).start();
                }
            }
            if (direction == 2) {
                player.accelerate(0, 0.002);
            }
            if (direction == 3) {
                player.accelerate(-0.001, 0);
            }
            if (direction == 4) {
                player.accelerate(0.001, 0);
            }
        }
        for (Saw saw : saws) {
            saw.update();
        }
        if (player.getX() - cameraViewX > 0.5 + cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5 + cameraAngleX / 2);
        } else if (player.getX() - cameraViewX < 0.5 - cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5 - cameraAngleX / 2);
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
                        deathsound.start();
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
                        deathsound.start();
                    }
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    LevelActivity levelActivity = (LevelActivity) this.context;
                    Runnable myRunnable = levelActivity::lostLevel;
                    mainHandler.post(myRunnable);
                }
            }
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
            canvas.drawText("Очки: " + score, 10, 25,paint);

            for (Platform platform : platforms) {
                platform.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            }
            for (Spike spike : spikes) {
                spike.draw(canvas, (float) canvasHeight, (float) canvasWidth, (float) cameraViewX, (float) cameraViewY);
            }
            for (Saw saw : saws) {
                saw.draw(canvas, (float) canvasHeight, (float) canvasWidth, (float) cameraViewX, (float) cameraViewY);
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