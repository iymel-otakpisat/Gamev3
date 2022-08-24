package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class Spike extends Body{
    final double x;
    final double y;
    final double width;
    final double height;
    final Paint paint;
    final Path path;
    final Drawable spikeImage;
    final boolean drawImageSpikes;

    public Spike(double x, double y, double width, double height,
                 boolean drawImageSpikes, Drawable spikeImage) {
        super(x, y, height, width);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
        path = new Path();
        this.spikeImage = spikeImage;
        this.drawImageSpikes = drawImageSpikes;
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        if (drawImageSpikes) {
            spikeImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                    (int) ((y - this.height - cameraViewY) * height),
                    (int) ((x + this.width / 2 - cameraViewX) * width),
                    (int) ((y - cameraViewY) * height));
            spikeImage.draw(canvas);
        } else {
            path.reset();

            path.moveTo((float) x, (float) y);

            path.lineTo( (float) ((x - this.width / 2  - cameraViewX) * width),(float) ((y - cameraViewY) * height));
            path.lineTo( (float) ((x - cameraViewX) * width), (float) ((y - this.height - cameraViewY) * height));
            path.lineTo( (float) ((x + this.width / 2 - cameraViewX) * width), (float) ((y - cameraViewY) * height));
            path.lineTo((float) ((x - this.width / 2 - cameraViewX) * width), (float) ((y - cameraViewY) * height));

            canvas.drawPath(path, paint);
        }

    }

}
