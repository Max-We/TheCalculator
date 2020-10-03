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
        Button btn_multiply = (Button)findViewById(R.id.btn_op_multiply);
        btn_multiply.setOnClickListener(this);
        Button btn_divide = (Button)findViewById(R.id.btn_op_divide);
        btn_divide.setOnClickListener(this);

        Button btn_root = (Button)findViewById(R.id.btn_op_square_root);
        btn_root.setOnClickListener(this);
        Button btn_cube_root = (Button)findViewById(R.id.btn_op_cube_root);
        btn_cube_root.setOnClickListener(this);
        Button btn_square = (Button)findViewById(R.id.btn_op_square);
        btn_square.setOnClickListener(this);
        Button btn_cube = (Button)findViewById(R.id.btn_op_cube);
        btn_cube.setOnClickListener(this);
        Button btn_power_inverse = (Button)findViewById(R.id.btn_op_power_inverse);
        btn_power_inverse.setOnClickListener(this);
        Button btn_permille = (Button)findViewById(R.id.btn_op_permille);
        btn_permille.setOnClickListener(this);
        Button btn_percent = (Button)findViewById(R.id.btn_op_percent);
        btn_percent.setOnClickListener(this);

        // Serparators
        Button btn_openBraces = (Button)findViewById(R.id.btn_sep_openBraces);
        btn_openBraces.setOnClickListener(this);
        Button btn_closeBraces = (Button)findViewById(R.id.btn_sep_closeBraces);
        btn_closeBraces.setOnClickListener(this);
        Button btn_comma = (Button)findViewById(R.id.btn_sep_comma);
        btn_comma.setOnClickListener(this);

        // Functions
        Button btn_factorial = (Button)findViewById(R.id.btn_fun_factorial);
        btn_factorial.setOnClickListener(this);
        Button btn_sin = (Button)findViewById(R.id.btn_fun_sin);
        btn_sin.setOnClickListener(this);
        Button btn_sin_inverse = (Button)findViewById(R.id.btn_fun_sin_inverse);
        btn_sin_inverse.setOnClickListener(this);
        Button btn_cos = (Button)findViewById(R.id.btn_fun_cos);
        btn_cos.setOnClickListener(this);
        Button btn_cos_inverse = (Button)findViewById(R.id.btn_fun_cos_inverse);
        btn_cos_inverse.setOnClickListener(this);
        Button btn_tan = (Button)findViewById(R.id.btn_fun_tan);
        btn_tan.setOnClickListener(this);
        Button btn_tan_inverse = (Button)findViewById(R.id.btn_fun_tan_inverse);
        btn_tan_inverse.setOnClickListener(this);
        Button btn_log = (Button)findViewById(R.id.btn_fun_log);
        btn_log.setOnClickListener(this);
        Button btn_ln = (Button)findViewById(R.id.btn_fun_ln);
        btn_ln.setOnClickListener(this);

        // Constants
        Button btn_eulers = (Button)findViewById(R.id.btn_const_eulers);
        btn_eulers.setOnClickListener(this);
        Button btn_pi = (Button)findViewById(R.id.btn_const_pi);
        btn_pi.setOnClickListener(this);

        // Actions
        ImageButton btn_delete = (ImageButton)findViewById(R.id.btn_delete);
        btn_delete.setOnTouchListener(new HandleRemove());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (inputConnection == null) return;

        switch (v.getId()) {
            case R.id.btn_op_divide:
                inputConnection.commitText(getResources().getString(R.string.divide), 1);
                break;
            case R.id.btn_num_0:
                inputConnection.commitText("0", 1);
                break;
            case R.id.btn_num_1:
                inputConnection.commitText("1", 1);
                break;
            case R.id.btn_num_2:
                inputConnection.commitText("2", 1);
                break;
            case R.id.btn_num_3:
                inputConnection.commitText("3", 1);
                break;
            case R.id.btn_num_4:
                inputConnection.commitText("4", 1);
                break;
            case R.id.btn_num_5:
                inputConnection.commitText("5", 1);
                break;
            case R.id.btn_num_6:
                inputConnection.commitText("6", 1);
                break;
            case R.id.btn_num_7:
                inputConnection.commitText("7", 1);
                break;
            case R.id.btn_num_8:
                inputConnection.commitText("8", 1);
                break;
            case R.id.btn_num_9:
                inputConnection.commitText("9", 1);
                break;
            case R.id.btn_op_plus:
                inputConnection.commitText("+", 1);
                break;
            case R.id.btn_op_subtract:
                inputConnection.commitText(getResources().getString(R.string.minus), 1);
                break;
            case R.id.btn_op_multiply:
                inputConnection.commitText(getResources().getString(R.string.multiply), 1);
                break;
            case R.id.btn_sep_openBraces:
                inputConnection.commitText("(", 1);
                break;
            case R.id.btn_sep_closeBraces:
                inputConnection.commitText(")", 1);
                break;
            case R.id.btn_sep_comma:
                inputConnection.commitText(".", 1);
                break;
            case R.id.btn_op_square_root:
                inputConnection.commitText(getResources().getString(R.string.root_square) + "(", 1);
                break;
            case R.id.btn_op_cube_root:
                inputConnection.commitText(getResources().getString(R.string.root_cube) + "(", 1);
                break;
            case R.id.btn_op_square:
                inputConnection.commitText(getResources().getString(R.string.power_of_2), 1);
                break;
            case R.id.btn_op_cube:
                inputConnection.commitText(getResources().getString(R.string.power_of_3), 1);
                break;
            case R.id.btn_op_power_inverse:
                inputConnection.commitText(getResources().getString(R.string.power_of_inverse), 1);
                break;
            case R.id.btn_fun_factorial:
                inputConnection.commitText("!", 1);
                break;
            case R.id.btn_op_percent:
                inputConnection.commitText("%", 1);
                break;
            case R.id.btn_op_permille:
                inputConnection.commitText(getResources().getString(R.string.permille), 1);
                break;
            case R.id.btn_const_eulers:
                inputConnection.commitText("e", 1);
                break;
            case R.id.btn_const_pi:
                inputConnection.commitText(getResources().getString(R.string.pi), 1);
                break;
            case R.id.btn_fun_sin:
                inputConnection.commitText("sin(", 1);
                break;
            case R.id.btn_fun_sin_inverse:
                inputConnection.commitText(getResources().getString(R.string.sin_inverse) + "(", 1);
                break;
            case R.id.btn_fun_cos:
                inputConnection.commitText("cos(", 1);
                break;
            case R.id.btn_fun_cos_inverse:
                inputConnection.commitText(getResources().getString(R.string.cos_inverse) + "(", 1);
                break;
            case R.id.btn_fun_tan:
                inputConnection.commitText("tan(", 1);
                break;
            case R.id.btn_fun_tan_inverse:
                inputConnection.commitText(getResources().getString(R.string.tan_inverse) + "(", 1);
                break;
            case R.id.btn_fun_log:
                inputConnection.commitText("log(", 1);
                break;
            case R.id.btn_fun_ln:
                inputConnection.commitText("ln(", 1);
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
            Thread t = new Thread() {
                @Override
                public void run(){
                    boolean initialWaitOver = false;

                    try {
                        deleteThreadRunning = true;
                        while(!cancelDeleteThread){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    CharSequence selectedText = inputConnection.getSelectedText(0);
                                    if (TextUtils.isEmpty(selectedText)) {
                                        String beforeCursor = inputConnection.getTextBeforeCursor(6, 0).toString();
                                        if (beforeCursor.contains(getResources().getString(R.string.sin_inverse) + "(") || beforeCursor.contains(getResources().getString(R.string.cos_inverse) + "(") || beforeCursor.contains(getResources().getString(R.string.tan_inverse) + "(")) {
                                            inputConnection.deleteSurroundingText(6, 0);
                                        } else {
                                            beforeCursor = inputConnection.getTextBeforeCursor(4, 0).toString();
                                            if (beforeCursor.contains("sin(") || beforeCursor.contains("cos(") || beforeCursor.contains("tan(") || beforeCursor.contains("log(")) {
                                                inputConnection.deleteSurroundingText(4, 0);
                                            } else {
                                                beforeCursor = inputConnection.getTextBeforeCursor(3, 0).toString();
                                                if (beforeCursor.contains("ln(")) {
                                                    inputConnection.deleteSurroundingText(3, 0);
                                                }  else {
                                                    beforeCursor = inputConnection.getTextBeforeCursor(2, 0).toString();
                                                    if (beforeCursor.contains(getResources().getString(R.string.root_square) + "(") || beforeCursor.contains(getResources().getString(R.string.root_cube) + "(")) {
                                                        inputConnection.deleteSurroundingText(2, 0);
                                                    } else {
                                                        inputConnection.deleteSurroundingText(1, 0);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        inputConnection.commitText("", 1);
                                    }
                                    btn_result.setEnabled(true);
                                }
                            });

                            try {
                                if (!initialWaitOver) {
                                    Thread.sleep(250);
                                    initialWaitOver = true;
                                } else {
                                    Thread.sleep(100);
                                }
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
