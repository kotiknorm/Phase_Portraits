package com.greenlab.phaseportraits.models;

public class Matrix {

    private float[] data = new float[4];

    public Matrix() {
        data[0] = 0.0F;
        data[1] = 0.0F;
        data[2] = 0.0F;
        data[3] = 0.0F;
    }

    public Matrix(float[] _data) {
        for (int i = 0; i < 4; ++i) {
            float[] tempData = data;
            float tempValue;
            if (_data != null && i <= _data.length) {
                tempValue = _data[i];
            } else {
                tempValue = 0.0F;
            }

            tempData[i] = tempValue;
        }
    }

    public float[] getData() {
        return data;
    }

    public int hashCode() {
        return data.hashCode();
    }

}
