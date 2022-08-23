package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Spike extends Body{
    final double x;
    final double y;
    final double width;
    final double height;
    final Paint paint;
    final Path path;

    public Spike(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        path.reset();

        path.moveTo((float) x, (float) y);

        path.lineTo( (float) ((x - this.width / 2  - cameraViewX) * width),(float) ((y - cameraViewY) * height));
        path.lineTo( (float) ((x - cameraViewX) * width), (float) ((y - this.height - cameraViewY) * height));
        path.lineTo( (float) ((x + this.width / 2 - cameraViewX) * width), (float) ((y - cameraViewY) * height));
        path.lineTo((float) ((x - this.width / 2 - cameraViewX) * width), (float) ((y - cameraViewY) * height));

        canvas.drawPath(path, paint);

    }

}
