package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Saw {
    final float x;
    final float y;
    final float width;
    final float height;
    final Paint paint;
    final Path path;

    public Saw(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
    }

    public void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY) {

        float center_x, center_y;
        center_x = x;
        center_y = y;

        final RectF oval = new RectF();
        oval.set((center_x - this.width / 2 - cameraViewX) * width,
                    (center_y + this.height - cameraViewY) * height,
                    (center_x + this.width / 2 - cameraViewX) * width,
                    (center_y - cameraViewY) * height);
        canvas.drawArc(oval, 180, 180, true, paint);

    }
}
