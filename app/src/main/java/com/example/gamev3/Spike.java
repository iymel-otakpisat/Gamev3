package com.example.gamev3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class Spike extends Body {
    final float x;
    final float y;
    final float width;
    final float height;
    final Paint paint;
    final Path path;
    final Drawable spikeImage;
    final boolean drawImageSpikes;

    public Spike(float x, float y, float width, float height,
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

    public boolean touched(Player p) {
        return Math.abs(p.x - x) <= width / 2 + p.width / 2 && Math.abs(p.y - y + height / 2) <= height / 2 + p.height / 2;
    }

    public void draw(Canvas canvas, float height, float width, float cameraViewX, float cameraViewY) {

        if (drawImageSpikes) {
            spikeImage.setBounds((int) ((x - this.width / 2 - cameraViewX) * width),
                    (int) ((y - this.height - cameraViewY) * height),
                    (int) ((x + this.width / 2 - cameraViewX) * width),
                    (int) ((y - cameraViewY) * height));
            spikeImage.draw(canvas);
        } else {
            path.reset();

            path.moveTo(x, y);

            path.lineTo(((x - this.width / 2 - cameraViewX) * width), ((y - cameraViewY) * height));
            path.lineTo(((x - cameraViewX) * width), ((y - this.height - cameraViewY) * height));
            path.lineTo(((x + this.width / 2 - cameraViewX) * width), ((y - cameraViewY) * height));
            path.lineTo(((x - this.width / 2 - cameraViewX) * width), ((y - cameraViewY) * height));

            canvas.drawPath(path, paint);
        }

    }

}
