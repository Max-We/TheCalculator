package com.example.thecalculator;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GestureDetector gestureDetector;
    ScriptEngine js_engine;

    TextView user_input_view;
    TextView result_output_view;

    String calculus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScriptEngineManager mgr = new ScriptEngineManager();
        js_engine = mgr.getEngineByName("javascript");

        // Layout
        LinearLayout numPad = (LinearLayout)findViewById(R.id.NumPad);

        // Output
        user_input_view = (TextView)findViewById(R.id.text_input);
        result_output_view =  (TextView)findViewById(R.id.text_result);

        // Numbers
        Button btn_num1 = (Button)findViewById(R.id.btn_1_row_3);
        btn_num1.setOnClickListener(this);
        Button btn_num2 = (Button)findViewById(R.id.btn_2_row_3);
        btn_num2.setOnClickListener(this);
        Button btn_num3 = (Button)findViewById(R.id.btn_3_row_3);
        btn_num3.setOnClickListener(this);
        Button btn_num4 = (Button)findViewById(R.id.btn_1_row_4);
        btn_num4.setOnClickListener(this);
        Button btn_num5 = (Button)findViewById(R.id.btn_2_row_4);
        btn_num5.setOnClickListener(this);
        Button btn_num6 = (Button)findViewById(R.id.btn_3_row_4);
        btn_num6.setOnClickListener(this);
        Button btn_num7 = (Button)findViewById(R.id.btn_1_row_5);
        btn_num7.setOnClickListener(this);
        Button btn_num8 = (Button)findViewById(R.id.btn_2_row_5);
        btn_num8.setOnClickListener(this);
        Button btn_num9 = (Button)findViewById(R.id.btn_3_row_5);
        btn_num9.setOnClickListener(this);
        Button btn_num0 = findViewById(R.id.btn_2_row_6);
        btn_num0.setOnClickListener(this);

        // Basic Operators
        Button btn_plus = (Button)findViewById(R.id.btn_equals);
        btn_plus.setOnClickListener(this);
        Button btn_minus = (Button)findViewById(R.id.btn_4_row_3);
        btn_minus.setOnClickListener(this);
        Button btn_multiply = (Button)findViewById(R.id.btn_4_row_4);
        btn_multiply.setOnClickListener(this);
        Button btn_divide = (Button)findViewById(R.id.btn_4_row_5);
        btn_divide.setOnClickListener(this);
        Button btn_equals = (Button)findViewById(R.id.btn_4_row_6);
        btn_equals.setOnClickListener(this);

        Button btn_delete = (Button)findViewById(R.id.btn_1_row_6);
        //btn_delete.setOnClickListener(new Button.OnClickListener() {
        //    public void onClick(View v) {
        //        RemoveLastInput();
        //    }
        //});
    }

    // CLICK HANDLER
    @Override
    public void onClick(View v) {
        // This should be executed if Android recognised a Button click
        switch (v.getId()) {
            case R.id.btn_1_row_1:
                // Do stuff
                break;
        }
    }

    // HOW TO HANDLE ZOOM GESTURE?

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