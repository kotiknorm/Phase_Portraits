package com.greenlab.phaseportraits.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.greenlab.phaseportraits.utils.Constants;
import com.greenlab.phaseportraits.models.FloatingPoint;
import com.greenlab.phaseportraits.models.Matrix;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class PhasePortraitPanel extends SurfaceView
        implements SurfaceHolder.Callback, GestureDetector.OnGestureListener {

    private Context context;
    private Drawer drawer;
    private GestureDetector gestureDetector;

    private int height;
    private int width;

    private Matrix matrix;
    private List<FloatingPoint> points = new ArrayList<FloatingPoint>();
    private Renderer renderer;

    public PhasePortraitPanel(Context paramContext) {
        super(paramContext);
        this.context = paramContext;
        initUI();
    }

    public PhasePortraitPanel(Context paramContext, AttributeSet attrs, int defStyle) {
        super(paramContext, attrs, defStyle);
        context = paramContext;
        initUI();
    }

    public PhasePortraitPanel(Context paramContext, AttributeSet attrs) {
        super(paramContext, attrs);
        context = paramContext;
        initUI();
    }

    private void initUI() {
        matrix = new Matrix();
        drawer = new Drawer(getResources());
        gestureDetector = new GestureDetector(context, this);
        getHolder().addCallback(this);
    }

    private List<FloatingPoint> calculateNewPoints(List<FloatingPoint> paramList1, List<FloatingPoint> paramList2) {
        ArrayList localArrayList = new ArrayList();
        float[] arrayOfFloat = matrix.getData();
        Iterator localIterator = paramList1.iterator();
        while (true) {
            if (!localIterator.hasNext())
                return localArrayList;
            FloatingPoint localFloatingPoint1 = (FloatingPoint) localIterator.next();
            FloatingPoint localFloatingPoint2 = new FloatingPoint(localFloatingPoint1, 0.05F * (arrayOfFloat[0] * localFloatingPoint1.getX() + arrayOfFloat[1] * localFloatingPoint1.getY()), 0.05F * (arrayOfFloat[2] * localFloatingPoint1.getX() + arrayOfFloat[3] * localFloatingPoint1.getY()));
            if (localFloatingPoint2.fitsInAreaOfInterests(width, height))
                localArrayList.add(localFloatingPoint2);
            else
                paramList2.add(localFloatingPoint1);
        }
    }

    private void renderFrame(Canvas paramCanvas) {
        if (paramCanvas != null) {
            drawer.clear(paramCanvas);
            drawer.drawCoordinateSystem(paramCanvas, width, height);
        }
        synchronized (points) {
            ArrayList localArrayList = new ArrayList();
            points = calculateNewPoints(points, localArrayList);
            points.removeAll(localArrayList);
            drawer.drawPoints(paramCanvas, points, width, height);
            drawer.drawActivePointsNumber(paramCanvas, points.size(), width);
        }
    }

    public void clear() {
        synchronized (points) {
            points.clear();
        }
    }

    public void matrixChange(Matrix newMatrix) {
        matrix = newMatrix;
    }

    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        synchronized (points) {
            points.add(new FloatingPoint(paramMotionEvent2.getX(), paramMotionEvent2.getY(), width, height));
        }
        return true;
    }

    public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
        synchronized (points) {
            points.add(new FloatingPoint(paramMotionEvent.getX(), paramMotionEvent.getY(), width, height));
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        return gestureDetector.onTouchEvent(paramMotionEvent);
    }

    public void onShowPress(MotionEvent paramMotionEvent) {
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {
    }

    public boolean onDown(MotionEvent paramMotionEvent) {
        return true;
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        return true;
    }

    public void onLongPress(MotionEvent paramMotionEvent) {
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        width = getWidth();
        height = getHeight();
        points = new ArrayList();
        renderer = new Renderer();
        renderer.start();
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        this.renderer.finish();
        while (true) {
            if (!renderer.isAlive())
                return;
            try {
                renderer.join();
            } catch (InterruptedException e) {
                Log.e(Constants.TAG_LOG, "exception", e);
            }
        }
    }

    private class Renderer extends Thread {
        private volatile boolean running = false;

        public void finish() {
            running = false;
        }

        public void run() {
            running = true;
            while (true) {
                if (!running)
                    return;
                Canvas localCanvas = null;
                try {
                    localCanvas = getHolder().lockCanvas();
                    synchronized (getHolder()) {
                        long l1 = System.currentTimeMillis();
                        renderFrame(localCanvas);
                        long l2 = System.currentTimeMillis();
                        long l3 = l2 - l1;
                        long l4 = 0;
                        if (l3 < 10L)
                            l4 = 10L - l3;
                        try {
                            Thread.sleep(l4);
                        } catch (InterruptedException e) {
                            Log.e(Constants.TAG_LOG, "exception", e);
                            return;
                        } catch (ConcurrentModificationException e) {
                            Log.e(Constants.TAG_LOG, "exception", e);
                        }
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG_LOG, "exception", e);
                } finally {
                    if (localCanvas != null)
                        getHolder().unlockCanvasAndPost(localCanvas);
                }
            }
        }
    }
}