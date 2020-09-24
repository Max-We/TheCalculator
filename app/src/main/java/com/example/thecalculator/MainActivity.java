package com.example.thecalculator;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MotionEventCompat;

import org.w3c.dom.Text;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    ScaleGestureDetector scaleDetector;

    ScriptEngine js_engine;

    EditText formula_text;
    TextView result_text;

    Button btn_result;

    Pad pad;
    ConstraintLayout container;

    String formula = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        ScriptEngineManager mgr = new ScriptEngineManager();
        js_engine = mgr.getEngineByName("javascript");

        // Output
        formula_text = (EditText)findViewById(R.id.formula_input);
        formula_text.setMovementMethod(new ScrollingMovementMethod());

        formula_text.setRawInputType(InputType.TYPE_CLASS_TEXT);
        formula_text.setTextIsSelectable(true);

        // Connect pad to formula input
        pad = (Pad) findViewById(R.id.Pad);
        InputConnection ic = formula_text.onCreateInputConnection(new EditorInfo());
        pad.setInputConnection(ic);

        result_text = (TextView)findViewById(R.id.result_text);


        // Layout

        // Launch with Pad zoomed in
        //setPadScaleFactor(1.49f);

        ImageView trackpad = (ImageView) findViewById(R.id.trackpad);
        final GestureDetector onSingleTap = new GestureDetector(this, new SingleTapConfirm());
        // Numbers

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
        int startIndex = formula_text.getSelectionStart();
        if (formula.length() > 0) {
            if (formula_text.getSelectionStart() != 0) {
                formula = formula.substring(0, formula_text.getSelectionStart()-1) + formula.substring(formula_text.getSelectionStart()+1, formula.length());
            } else {
                formula = formula.substring(0, formula.length() - 1);
            }
        }
        Log.d("Cursor", "Index: " + startIndex);
        formula_text.setText(formula);
    }
}