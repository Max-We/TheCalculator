package com.example.thecalculator;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MotionEventCompat;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    ScaleGestureDetector scaleDetector;

    ScriptEngine js_engine;

    TextView formula_text;
    TextView result_text;

    LinearLayout pad;
    ConstraintLayout container;

    String formula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        ScriptEngineManager mgr = new ScriptEngineManager();
        js_engine = mgr.getEngineByName("javascript");

        // Output
        formula_text = (TextView)findViewById(R.id.formula_input);
        result_text =  (TextView)findViewById(R.id.result_text);

        // Layout
        container = (ConstraintLayout)findViewById(R.id.container);
        pad =  (LinearLayout)findViewById(R.id.Pad);

        // Launch with Pad zoomed in
        //setPadScaleFactor(1.49f);

        ImageView trackpad = (ImageView) findViewById(R.id.trackpad);
        final GestureDetector onSingleTap = new GestureDetector(this, new SingleTapConfirm());
        final OnClickListener onClick_Pad = new PadOnClickListener();
        trackpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(false) {
                    Log.d("PadLayout", "Scaling");
                    return true;
                } else {
                    Log.d("PadLayout", "No Scale detected, returning false.");
                    return false;
                }
            }
        });


        // Numbers
        Button btn_num1 = (Button)findViewById(R.id.btn_num_1);
        btn_num1.setOnClickListener(onClick_Pad);
        Button btn_num2 = (Button)findViewById(R.id.btn_num_2);
        btn_num2.setOnClickListener(onClick_Pad);
        Button btn_num3 = (Button)findViewById(R.id.btn_num_3);
        btn_num3.setOnClickListener(onClick_Pad);
        Button btn_num4 = (Button)findViewById(R.id.btn_num_4);
        btn_num4.setOnClickListener(onClick_Pad);
        Button btn_num5 = (Button)findViewById(R.id.btn_num_5);
        btn_num5.setOnClickListener(onClick_Pad);
        Button btn_num6 = (Button)findViewById(R.id.btn_num_6);
        btn_num6.setOnClickListener(onClick_Pad);
        Button btn_num7 = (Button)findViewById(R.id.btn_num_7);
        btn_num7.setOnClickListener(onClick_Pad);
        Button btn_num8 = (Button)findViewById(R.id.btn_num_8);
        btn_num8.setOnClickListener(onClick_Pad);
        Button btn_num9 = (Button)findViewById(R.id.btn_num_9);
        btn_num9.setOnClickListener(onClick_Pad);
        Button btn_num0 = findViewById(R.id.btn_num_0);
        btn_num0.setOnClickListener(onClick_Pad);

        // Operators
        Button btn_minus = (Button)findViewById(R.id.btn_op_plus);
        btn_minus.setOnClickListener(onClick_Pad);
        Button btn_multiply = (Button)findViewById(R.id.btn_op_subtract);
        btn_multiply.setOnClickListener(onClick_Pad);
        Button btn_divide = (Button)findViewById(R.id.btn_op_multiply);
        btn_divide.setOnClickListener(onClick_Pad);
        Button btn_equals = (Button)findViewById(R.id.btn_op_divide);
        btn_equals.setOnClickListener(onClick_Pad);

        // Serparators
        Button btn_openBraces = (Button)findViewById(R.id.btn_sep_openBraces);
        btn_openBraces.setOnClickListener(onClick_Pad);
        Button btn_closeBraces = (Button)findViewById(R.id.btn_sep_closeBraces);
        btn_closeBraces.setOnClickListener(onClick_Pad);
        Button btn_comma = (Button)findViewById(R.id.btn_sep_comma);
        btn_comma.setOnClickListener(onClick_Pad);

        // Actions
        Button btn_plus = (Button)findViewById(R.id.btn_result);
        btn_plus.setOnClickListener(onClick_Pad);
        ImageButton btn_delete = (ImageButton)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(onClick_Pad);
    }

    private class PadOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_num_0:
                    formula = formula.concat("0");
                    break;
                case R.id.btn_num_1:
                    formula = formula.concat("1");
                    break;
                case R.id.btn_num_2:
                    formula = formula.concat("2");
                    break;
                case R.id.btn_num_3:
                    formula = formula.concat("3");
                    break;
                case R.id.btn_num_4:
                    formula = formula.concat("4");
                    break;
                case R.id.btn_num_5:
                    formula = formula.concat("5");
                    break;
                case R.id.btn_num_6:
                    formula = formula.concat("6");
                    break;
                case R.id.btn_num_7:
                    formula = formula.concat("7");
                    break;
                case R.id.btn_num_8:
                    formula = formula.concat("8");
                    break;
                case R.id.btn_num_9:
                    formula = formula.concat("9");
                    break;
                case R.id.btn_op_plus:
                    formula = formula.concat("+");
                    break;
                case R.id.btn_op_subtract:
                    formula = formula.concat("-");
                    break;
                case R.id.btn_op_multiply:
                    formula = formula.concat("*");
                    break;
                case R.id.btn_op_divide:
                    formula = formula.concat("/");
                    break;
                case R.id.btn_sep_openBraces:
                    formula = formula.concat("(");
                    break;
                case R.id.btn_sep_closeBraces:
                    formula = formula.concat(")");
                    break;
                case R.id.btn_sep_comma:
                    formula = formula.concat(".");
                    break;
                case R.id.btn_delete:
                    RemoveLastInput();
                    break;
                case R.id.btn_result:
                    String result;
                    try {
                        result = "" + js_engine.eval(formula);
                    } catch (ScriptException e) {
                        result = "Error";
                    }
                    result_text.setText(result);
                    break;
            }

            formula_text.setText(formula);
        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        float factor = 1;

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            factor *= scaleGestureDetector.getScaleFactor();
            factor = Math.max(1, Math.min(factor, ResourcesCompat.getFloat(getResources(), R.dimen.pad_large)));
            setPadScaleFactor(factor);

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector){
            if (factor <= 1.05f) {
                factor = ResourcesCompat.getFloat(getResources(), R.dimen.pad_small);
            } else if (factor <= 1.3f) {
                factor =  ResourcesCompat.getFloat(getResources(), R.dimen.pad_medium);
            } else {
                factor =  ResourcesCompat.getFloat(getResources(), R.dimen.pad_large);
            }

            setPadScaleFactor(factor);
        }
    }

    private void setPadScaleFactor(float factor) {
        pad.setScaleX(factor);
        pad.setScaleY(factor);
        pad.setPivotX(0);
        pad.setPivotY(pad.getHeight());
    }

    private Boolean LastInputIsOperator(){
        if (formula.endsWith("+") || formula.endsWith("-") || formula.endsWith("*") || formula.endsWith("/")) {
            return true;
        } else {
            return false;
        }
    }

    private void RemoveLastInput() {
        if (formula.length() > 0) {
            formula = formula.substring(0, formula.length() - 1);
        }
        formula_text.setText(formula);
    }
}