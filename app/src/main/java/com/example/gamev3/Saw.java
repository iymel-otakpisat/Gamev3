package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Saw extends Body{
    final double x;
    final double y;
    final double width;
    final double height;
    final Paint paint;
    final Path path;

    public Saw(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        float center_x, center_y;
        center_x = (float) x;
        center_y = (float) y;

        final RectF oval = new RectF();
        oval.set((float) ((center_x - this.width / 2 - cameraViewX) * width),
                (float) ((center_y + this.height) * width - cameraViewY * height),
                (float) ((center_x + this.width / 2 - cameraViewX) * width),
                (float) ((center_y) * width - cameraViewY * height));
        canvas.drawArc(oval, 180, 360, true, paint);

    }
}
