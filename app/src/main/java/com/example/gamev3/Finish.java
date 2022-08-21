package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Finish extends Body {
    final Drawable finishImage;
    final Drawable finishDoorImage;
    public static final double REL_HEIGHT = 0.15;

    public Finish(double x, double y, double width, double height, Drawable finishImage_, Drawable finishDoorImage_) {
        super(x, y, height, width);
        finishImage = finishImage_;
        finishDoorImage = finishDoorImage_;
    }

    public static Finish from_bottom_part(double x, double y, double canvasWidth, double canvasHeight, Drawable finishImage, Drawable finishDoorImage) {
        double height = REL_HEIGHT;
        double width = REL_HEIGHT * canvasHeight / canvasWidth;
        return new Finish(x, y - height / 2, width, height, finishImage, finishDoorImage);
    }

    @Override
    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {
        paint.setColor(0xff00ff00);
        finishImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                (int) ((y - this.height / 2 - cameraViewY) * height),
                (int) ((x + this.width / 2 - cameraViewX) * width),
                (int) ((y + this.height / 2 - cameraViewY) * height));
        finishImage.draw(canvas);
    }

    public void drawDoor(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {
        paint.setColor(0xff00ff00);
        finishDoorImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                (int) ((y - this.height / 2 - cameraViewY) * height),
                (int) ((x + this.width / 2 - cameraViewX) * width),
                (int) ((y + this.height / 2 - cameraViewY) * height));
        finishDoorImage.draw(canvas);
    }
}