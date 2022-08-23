package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public abstract class Body {
    final double x;
    final double y;
    final double height;
    final double width;
    final Paint paint;

    public Body(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();

    }

    public boolean touched(Player p) {
        return Math.abs(p.x - x) <= width / 2 + p.width / 2 && Math.abs(p.y - y) <= height / 2 + p.height;
    }

    public boolean touchspike(Player p) {

        if(x - width / 2 <= x + width / 2 &&
                y + height / 2 >= p.y &&
                y < p.y &&
                x + width / 2 >= p.x - p.width / 2) {
            return true;
        }
        if(x - width / 4 <= p.x + p.width / 2 &&
                y + height >= p.y &&
                y + height <= p.y &&
                x + width / 4 >= p.x - p.width / 2){
            return true;
        }
        return false;
    }

//            if(p.x - p.width / 2 <= x + width / 2 &&
//    p.y + p.height / 2 >= y &&
//    p.y < y &&
//    p.x + p.width / 2 >= x - width / 2) {
//        return true;
//    }
//        if(p.x - p.width / 4 <= x + width / 2 &&
//    p.y + p.height >= y &&
//    p.y + p.height <= y &&
//    p.x + p.width / 4 >= x - width / 2){

    public abstract void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY);
}