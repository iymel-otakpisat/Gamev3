package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public abstract class Body {
    final float x;
    final float y;
    final float height;
    final float width;
    final Paint paint;

    public Body(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();

    }

    public boolean touched(Player p) {
        return Math.abs(p.x - x) <= width / 2 + p.width / 2 && Math.abs(p.y - y) <= height / 2 + p.height;
    }

    public abstract void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY);
}