package com.greenlab.phaseportraits.models;


public class FloatingPoint {

    private float x;
    private float y;

    public FloatingPoint(float _x, float _y, int width, int height) {
        x = _x - (float) (width * 0.5);
        y = (float) (height * 0.5) - _y;
    }

    public FloatingPoint(FloatingPoint floatingPoint, float var2, float var3) {
        x = var2 + floatingPoint.x;
        y = var3 + floatingPoint.y;
    }

    public boolean fitsInAreaOfInterests(int width, int height) {
        float areaX = getScreenX(width, height);
        float areaY = getScreenY(width, height);
        return (x > 2.0F || x < -2.0F || y > 2.0F || y < -2.0F) && areaX >= (float) (-width * 0.5) && areaY >= (float) (-height * 0.5) && areaX < (float) (width + width * 0.5) && areaY < (float) (height + height * 0.5);
    }

    public float getScreenX(int width, int height) {
        return (float) (width * 0.5) + x;
    }

    public float getScreenY(int width, int height) {
        return (float) (height * 0.5) - y;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

}
