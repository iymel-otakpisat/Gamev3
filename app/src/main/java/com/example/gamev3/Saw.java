package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class Saw extends Body {
    float x;
    float y;
    final float startX;
    final float startY;
    final float width;
    final float height;
    final Paint paint;
    final Path path;
    float time;
    final float moveRange;
    final float moveSpeed;
    final Drawable sawImage;
    final boolean drawImage;


    public Saw(float x, float y, float width, float height, float moveRange, float moveSpeed,
               boolean drawImage, Drawable sawImage) {
        super(x, y, height, width);
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
        this.moveRange = moveRange;
        time = 0;
        this.moveSpeed = moveSpeed;
        this.sawImage = sawImage;
        this.drawImage = drawImage;
    }

    public void update() {
        time += 0.05 * moveSpeed;
        x = (float) (startX + Math.sin(time) * moveRange);
    }

    public void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY) {

        if (drawImage) {
            sawImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                    (int) ((y - this.height / 2 - cameraViewY) * height),
                    (int) ((x + this.width / 2 - cameraViewX) * width),
                    (int) ((y + this.height / 2 - cameraViewY) * height));
            sawImage.draw(canvas);
        } else {
            final RectF oval = new RectF();
            oval.set(((x - this.width / 2 - cameraViewX) * width),
                    ((y - this.height / 2 - cameraViewY) * height),
                    ((x + this.width / 2 - cameraViewX) * width),
                    ((y + this.height / 2 - cameraViewY) * height));
            canvas.drawArc(oval, 180, 360, true, paint);
        }
    }
}