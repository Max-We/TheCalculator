package com.example.thecalculator;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ScaleGestureDetector scaleDetector;

    ScriptEngine js_engine;

    TextView user_input_view;
    TextView result_output_view;

    LinearLayout numPad;
    ConstraintLayout container;

    String calculus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        ScriptEngineManager mgr = new ScriptEngineManager();
        js_engine = mgr.getEngineByName("javascript");

        // Output
        user_input_view = (TextView)findViewById(R.id.arithmetic_input);
        result_output_view =  (TextView)findViewById(R.id.result_text);

        // Layout
        container = (ConstraintLayout)findViewById(R.id.container);
        numPad = (LinearLayout)findViewById(R.id.NumPad);

        ImageView trackpad = (ImageView) findViewById(R.id.trackpad);
        final GestureDetector onSingleTap = new GestureDetector(this, new SingleTapConfirm());
        trackpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (onSingleTap.onTouchEvent(motionEvent)) {
                    result_output_view.setText("tap");
                    return false;
                } else {
                    scaleDetector.onTouchEvent(motionEvent);
                    result_output_view.setText("gesture");
                    return true;
                }
            }
        });

        // Numbers
        Button btn_num1 = (Button)findViewById(R.id.btn_num_1);
        btn_num1.setOnClickListener(this);
        Button btn_num2 = (Button)findViewById(R.id.btn_num_2);
        btn_num2.setOnClickListener(this);
        Button btn_num3 = (Button)findViewById(R.id.btn_num_3);
        btn_num3.setOnClickListener(this);
        Button btn_num4 = (Button)findViewById(R.id.btn_num_4);
        btn_num4.setOnClickListener(this);
        Button btn_num5 = (Button)findViewById(R.id.btn_num_5);
        btn_num5.setOnClickListener(this);
        Button btn_num6 = (Button)findViewById(R.id.btn_num_6);
        btn_num6.setOnClickListener(this);
        Button btn_num7 = (Button)findViewById(R.id.btn_num_7);
        btn_num7.setOnClickListener(this);
        Button btn_num8 = (Button)findViewById(R.id.btn_num_8);
        btn_num8.setOnClickListener(this);
        Button btn_num9 = (Button)findViewById(R.id.btn_num_9);
        btn_num9.setOnClickListener(this);
        Button btn_num0 = findViewById(R.id.btn_num_0);
        btn_num0.setOnClickListener(this);

        // Basic Operators
        Button btn_plus = (Button)findViewById(R.id.btn_result);
        btn_plus.setOnClickListener(this);
        Button btn_minus = (Button)findViewById(R.id.btn_op_plus);
        btn_minus.setOnClickListener(this);
        Button btn_multiply = (Button)findViewById(R.id.btn_op_subtract);
        btn_multiply.setOnClickListener(this);
        Button btn_divide = (Button)findViewById(R.id.btn_op_multiply);
        btn_divide.setOnClickListener(this);
        Button btn_equals = (Button)findViewById(R.id.btn_op_divide);
        btn_equals.setOnClickListener(this);

        ImageButton btn_delete = (ImageButton)findViewById(R.id.btn_delete);
        //btn_delete.setOnClickListener(new Button.OnClickListener() {
        //    public void onClick(View v) {
        //        RemoveLastInput();
        //    }
        //});
    }

    // CLICK
    @Override
    public void onClick(View v) {
        user_input_view.setText("1+1");
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
            factor = Math.max(1f, Math.min(factor, 1.5f));
            setNumPadScaleFactor(factor);
            user_input_view.setText("ON SCALE");

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            user_input_view.setText("BEGINN");
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector){
            if (factor <= 1.05f) {
                factor = 1f;
            } else if (factor <= 1.3f) {
                factor = 1.2f;
            } else {
                factor = 1.5f;
            }

            setNumPadScaleFactor(factor);
        }

        private void setNumPadScaleFactor(float factor) {
            numPad.setScaleX(factor);
            numPad.setScaleY(factor);
            numPad.setPivotX(0);
            numPad.setPivotY(numPad.getHeight());
        }
    }

    //private Boolean LastInputIsOperator(){
    //    if (calculus.endsWith("+") || calculus.endsWith("-") || calculus.endsWith("*") || calculus.endsWith("/")) {
    //        return true;
    //    } else {
    //        return false;
    //    }
    //}

    //private void RemoveLastInput() {
    //    if (calculus.length() > 0) {
    //        calculus = calculus.substring(0, calculus.length() - 1);
    //    }
    //    user_input_view.setText(calculus);
    //}
}