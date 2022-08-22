package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Spike {
    final float x;
    final float y;
    final float width;
    final float height;
    final Paint paint;
    final Path path;

    public Spike(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
    }

    public void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY) {

        path.reset();

        path.moveTo(x, y);

        path.lineTo((x - this.width / 4  - cameraViewX) * width, (y - cameraViewY) * height);
        path.lineTo((x + this.width / 32 - cameraViewX) * width, (y - this.height * 4 - cameraViewY) * height);
        path.lineTo((x + this.width / 4 - cameraViewX) * width, (y - cameraViewY) * height);
        path.lineTo((x - this.width / 4 - cameraViewX) * width, (y - cameraViewY) * height);

        canvas.drawPath(path, paint);

    }
}
