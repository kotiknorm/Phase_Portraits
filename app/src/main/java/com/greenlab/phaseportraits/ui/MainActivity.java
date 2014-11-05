package com.greenlab.phaseportraits.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.greenlab.phaseportraits.R;
import com.greenlab.phaseportraits.models.Matrix;
import com.greenlab.phaseportraits.graphics.PhasePortraitPanel;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText matrix00, matrix01, matrix10, matrix11;
    private Button enter, clear;

    private PhasePortraitPanel portraitsPanel;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        portraitsPanel = (PhasePortraitPanel) findViewById(R.id.view);

        matrix00 = ((EditText) findViewById(R.id.editText1));
        matrix01 = ((EditText) findViewById(R.id.editText2));
        matrix10 = ((EditText) findViewById(R.id.editText3));
        matrix11 = ((EditText) findViewById(R.id.editText4));

        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
    }

    private Matrix getMatrixFromTextView() throws NumberFormatException {
        float[] arrayOfFloat = new float[4];

        String m00 = this.matrix00.getText().toString();
        if (m00.equals(""))
            m00 = "0";
        String m01 = this.matrix01.getText().toString();
        if (m01.equals(""))
            m01 = "0";
        String m10 = this.matrix10.getText().toString();
        if (m10.equals(""))
            m10 = "0";
        String m11 = this.matrix11.getText().toString();
        if (m11.equals(""))
            m11 = "0";

        arrayOfFloat[0] = Float.valueOf(m00).floatValue();
        arrayOfFloat[1] = Float.valueOf(m01).floatValue();
        arrayOfFloat[2] = Float.valueOf(m10).floatValue();
        arrayOfFloat[3] = Float.valueOf(m11).floatValue();

        return new Matrix(arrayOfFloat);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter:
                portraitsPanel.matrixChange(getMatrixFromTextView());
                break;
            case R.id.clear:
                portraitsPanel.clear();
                break;
        }
    }
}