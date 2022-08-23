package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Saw extends Body {
    final double x;
    final double y;
    final double width;
    final double height;
    final Paint paint;
    final Path path;
    double time;
    final double moveRange;
    final double moveSpeed;

    public Saw(double x, double y, double width, double height, double moveRange, double moveSpeed) {
        super(x, y, height, width);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
        this.moveRange = moveRange;
        time = 0;
        this.moveSpeed = moveSpeed;
    }

    public void update() {
        time += 0.05 * moveSpeed;
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        float center_x, center_y;
        center_x = (float) (x + Math.sin(time) * moveRange);
        System.out.println(center_x);
        center_y = (float) y;

        final RectF oval = new RectF();
        oval.set((float) ((center_x - this.width / 2 - cameraViewX) * width),
                (float) ((center_y - this.height / 2 - cameraViewY) * height),
                (float) ((center_x + this.width / 2 - cameraViewX) * width),
                (float) ((center_y + this.height / 2 - cameraViewY) * height));
        canvas.drawArc(oval, 180, 360, true, paint);

    }
}