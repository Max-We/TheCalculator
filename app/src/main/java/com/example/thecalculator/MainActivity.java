package com.example.thecalculator;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import org.javia.arity.Util;

public class MainActivity extends AppCompatActivity {
    ScaleGestureDetector scaleDetector;

    ScriptEngine js_engine;
    Symbols arity;


    Pad pad;
    Button btn_result;
    EditText formula_text;
    TextView result_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        ScriptEngineManager mgr = new ScriptEngineManager();
        js_engine = mgr.getEngineByName("javascript");
        arity = new Symbols();

        // Output
        formula_text = (EditText)findViewById(R.id.formula_input);
        formula_text.setMovementMethod(new ScrollingMovementMethod());

        formula_text.setRawInputType(InputType.TYPE_CLASS_TEXT);
        formula_text.setTextIsSelectable(true);

        btn_result = (Button)findViewById(R.id.btn_result);
        result_text = (TextView)findViewById(R.id.result_text);

        // Connect pad to formula input
        pad = (Pad)findViewById(R.id.Pad);
        InputConnection ic = formula_text.onCreateInputConnection(new EditorInfo());
        pad.setInputConnection(ic);
        pad.setBtn_result(btn_result);



        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result;
                try {
                    if (formula_text.getText().length() > 0) {
//                        BigDecimal c = new BigDecimal(js_engine.eval(formula_text.getText().toString()).toString()).setScale(2, RoundingMode.DOWN);
                        Log.i("String to Calculate", formula_text.getText().toString());
                        BigDecimal c = new BigDecimal(arity.eval(formatExpression(formula_text.getText().toString()))).setScale(2, RoundingMode.DOWN);
                        NumberFormat formatter = new DecimalFormat("0.####E0");
                        if (c.toString().length() > 10) {
                            result = formatter.format(c.longValue()) + "";
                        } else {
                            result = c.longValue() + "";
                        }
                    } else {
                        result = "";
                    }
                } catch (SyntaxException e) {
                    result = "ERROR";
                }

                result_text.setText(result);
                btn_result.setEnabled(false);
            }
        });

        ImageView trackpad = (ImageView) findViewById(R.id.trackpad);
        final GestureDetector onSingleTap = new GestureDetector(this, new SingleTapConfirm());
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

    private String formatExpression(String expression) {
        // Power
        expression = expression.replace(getResources().getString(R.string.power_of_2), "^2");

        // Cube Root
        expression = expression.replace(getResources().getString(R.string.root_cube), "cbrt");

        // Permille
        expression = expression.replace(getResources().getString(R.string.permille), "*0.001");

        // Inverse sin, cos, tan
        expression = expression.replace(getResources().getString(R.string.sin_inverse), "asin");
        expression = expression.replace(getResources().getString(R.string.cos_inverse), "acos");
        expression = expression.replace(getResources().getString(R.string.tan_inverse), "atan");

        return expression;
    }

    private void setPadScaleFactor(float factor) {
        pad.setScaleX(factor);
        pad.setScaleY(factor);
        pad.setPivotX(0);
        pad.setPivotY(pad.getHeight());
    }
}