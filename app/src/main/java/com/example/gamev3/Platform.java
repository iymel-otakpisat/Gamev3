package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Platform {
    final float x;
    final float y;
    final float width;
    final float height;
    final Paint paint;

    public Platform(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
    }

    public void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY) {

        canvas.drawRect(
                ((x - this.width / 2 - cameraViewX) * width),
                ((y - this.height / 2 - cameraViewY) * height),
                ((x + this.width / 2 - cameraViewX) * width),
                ((y + this.height / 2 - cameraViewY) * height), paint);

    }
}
