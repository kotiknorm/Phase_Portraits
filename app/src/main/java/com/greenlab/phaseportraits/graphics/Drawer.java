package com.greenlab.phaseportraits.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.greenlab.phaseportraits.R;
import com.greenlab.phaseportraits.models.FloatingPoint;

import java.util.List;

public class Drawer {

    private final Paint coordinatesPaint = new Paint();
    private final Paint pointsPaint = new Paint();
    private final Paint textPaint = new Paint();

    public Drawer(Resources resources) {
        textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, 0));
        textPaint.setTextSize((float) resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin));
    }

    public void clear(Canvas canvas) {
        canvas.drawColor(-1);
    }

    public void drawActivePointsNumber(Canvas canvas, int countPoints, int width) {
        textPaint.setTextAlign(Align.RIGHT);
        canvas.drawText(Integer.toString(countPoints), (float) (width - 1), 1.0F + textPaint.getTextSize(), textPaint);
    }

    public void drawCoordinateSystem(Canvas canvas, int centreX, int centreY) {
        canvas.drawLine((float) (centreX * 0.5), 0.0F, (float) (centreX * 0.5), (float) centreY, this.coordinatesPaint);
        canvas.drawLine(0.0F, (float) (centreY * 0.5), (float) centreX, (float) (centreY * 0.5), this.coordinatesPaint);
    }

    public void drawPoints(Canvas canvas, List<FloatingPoint> listPoints, int var5, int var6) {
        for (int i = 0; i < listPoints.size(); ++i) {
            float pointX = listPoints.get(i).getScreenX(var5, var6);
            float pointY = listPoints.get(i).getScreenY(var5, var6);
            canvas.drawCircle(pointX, pointY, 5, pointsPaint);
        }
    }
}
