package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class Saw extends Body {
    double x;
    double y;
    final double startX;
    final double startY;
    final double width;
    final double height;
    final Paint paint;
    final Path path;
    double time;
    final double moveRange;
    final double moveSpeed;
    final Drawable sawImage;
    final boolean drawImage;


    public Saw(double x, double y, double width, double height, double moveRange, double moveSpeed,
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
        x = (startX + Math.sin(time) * moveRange);
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        if (drawImage) {
            sawImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                    (int) ((y - this.height / 2 - cameraViewY) * height),
                    (int) ((x + this.width / 2 - cameraViewX) * width),
                    (int) ((y + this.height / 2 - cameraViewY) * height));
            sawImage.draw(canvas);
        } else {
            final RectF oval = new RectF();
            oval.set((float) ((x - this.width / 2 - cameraViewX) * width),
                    (float) ((y - this.height / 2 - cameraViewY) * height),
                    (float) ((x + this.width / 2 - cameraViewX) * width),
                    (float) ((y + this.height / 2 - cameraViewY) * height));
            canvas.drawArc(oval, 180, 360, true, paint);
        }
    }
}