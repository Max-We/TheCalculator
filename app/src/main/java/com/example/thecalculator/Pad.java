package com.example.thecalculator;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Pad extends LinearLayout implements View.OnClickListener {
    InputConnection inputConnection;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pad(Context context) {
        super(context);
        init(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    Button btn_result;
    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.pad, this, true);

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

        // Operators
        Button btn_plus = (Button)findViewById(R.id.btn_op_plus);
        btn_plus.setOnClickListener(this);
        Button btn_minus = (Button)findViewById(R.id.btn_op_subtract);
        btn_minus.setOnClickListener(this);
        Button btn_multiply = (Button)findViewById(R.id.btn_op_subtract);
        btn_multiply.setOnClickListener(this);
        Button btn_divide = (Button)findViewById(R.id.btn_op_multiply);
        btn_divide.setOnClickListener(this);

        // Serparators
        Button btn_openBraces = (Button)findViewById(R.id.btn_sep_openBraces);
        btn_openBraces.setOnClickListener(this);
        Button btn_closeBraces = (Button)findViewById(R.id.btn_sep_closeBraces);
        btn_closeBraces.setOnClickListener(this);
        Button btn_comma = (Button)findViewById(R.id.btn_sep_comma);
        btn_comma.setOnClickListener(this);

        // Actions
        ImageButton btn_delete = (ImageButton)findViewById(R.id.btn_delete);
        btn_delete.setOnTouchListener(new HandleRemove());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (inputConnection == null) return;

        switch (v.getId()) {
            case R.id.btn_num_0:
                inputConnection.commitText("0", 1);
//                    formula = formula.concat("0");
                break;
            case R.id.btn_num_1:
                inputConnection.commitText("1", 1);
//                    formula = formula.concat("1");
                break;
            case R.id.btn_num_2:
                inputConnection.commitText("2", 1);
//                    formula = formula.concat("2");
                break;
            case R.id.btn_num_3:
                inputConnection.commitText("3", 1);
//                    formula = formula.concat("3");
                break;
            case R.id.btn_num_4:
                inputConnection.commitText("4", 1);
//                    formula = formula.concat("4");
                break;
            case R.id.btn_num_5:
                inputConnection.commitText("5", 1);
//                    formula = formula.concat("5");
                break;
            case R.id.btn_num_6:
                inputConnection.commitText("6", 1);
//                    formula = formula.concat("6");
                break;
            case R.id.btn_num_7:
                inputConnection.commitText("7", 1);
                //formula = formula.concat("7");
                break;
            case R.id.btn_num_8:
                inputConnection.commitText("8", 1);
                //formula = formula.concat("8");
                break;
            case R.id.btn_num_9:
                inputConnection.commitText("9", 1);
                //formula = formula.concat("9");
                break;
            case R.id.btn_op_plus:
                inputConnection.commitText("+", 1);
                //formula = formula.concat("+");
                break;
            case R.id.btn_op_subtract:
                inputConnection.commitText("-", 1);
                //formula = formula.concat("-");
                break;
            case R.id.btn_op_multiply:
                inputConnection.commitText("*", 1);
                //formula = formula.concat("*");
                break;
            case R.id.btn_op_divide:
                inputConnection.commitText("/", 1);
                //formula = formula.concat("/");
                break;
            case R.id.btn_sep_openBraces:
                inputConnection.commitText("(", 1);
                //formula = formula.concat("(");
                break;
            case R.id.btn_sep_closeBraces:
                inputConnection.commitText(")", 1);
                //formula = formula.concat(")");
                break;
            case R.id.btn_sep_comma:
                inputConnection.commitText(".", 1);
                //formula = formula.concat(".");
                break;
        }

        btn_result.setEnabled(true);
    }

    private class HandleRemove implements OnTouchListener {
        boolean deleteThreadRunning = false;
        boolean cancelDeleteThread = false;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    startDeleteThread();
                    return true;
                }

                case MotionEvent.ACTION_UP:
                {
                    stopDeleteThread();
                }

                default:
                    return false;
            }
        }

        private void startDeleteThread() {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }

            Thread t = new Thread() {
                @Override
                public void run(){
                    try {
                        deleteThreadRunning = true;
                        while(!cancelDeleteThread){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    CharSequence selectedText = inputConnection.getSelectedText(0);
                                    if (TextUtils.isEmpty(selectedText)) {
                                        inputConnection.deleteSurroundingText(1, 0);
                                    } else {
                                        inputConnection.commitText("", 1);
                                    }
                                }
                            });

                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(
                                        "Could not wait between char delete.", e);
                            };
                        }
                    } finally {
                        deleteThreadRunning = false;
                        cancelDeleteThread = false;
                    }
                }
            };

            t.start();
        }

        private void stopDeleteThread() {
            cancelDeleteThread = true;
        }
    }



    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }

    public void setBtn_result(Button btn_result_reference) { this.btn_result = btn_result_reference; }
}
