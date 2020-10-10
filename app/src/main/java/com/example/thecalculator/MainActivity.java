package com.example.thecalculator;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import org.javia.arity.Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    Symbols arity;

    Pad pad;
    Button btn_result;
    EditText formula_text;
    TextView result_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Disable System keyboard
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        // Startup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Code start
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
        pad.setBtnResult(btn_result);

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result;
                try {
                    if (formula_text.getText().length() > 0) {
                        Double c = arity.eval(formatExpression(formula_text.getText().toString()));
                        if(c.isNaN()) {
                            c = 0d;
                        }

                        result = Util.doubleToString(c, 12, 5);
                        NumberFormat formatter = new DecimalFormat("0.#####E0");
                        if (!c.isInfinite()) {
                            if (result.length() > 10) {
                                Log.d("Result Debug: ", "Result: " + c.toString() + " Length: " + c.toString().length());
                                result = formatter.format(Double.valueOf(result)) + "";
                            }
                        } else {
                            result = "u\221E";
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
    }

    private String formatExpression(String expression) {
        // Cube Root
        expression = expression.replace(getResources().getString(R.string.root_cube), "cbrt");

        // Permille
        expression = expression.replace(getResources().getString(R.string.permille), "*0.001");

        // Inverse sin, cos, tan
        expression = expression.replace(getResources().getString(R.string.sin_inverse), "asin");
        expression = expression.replace(getResources().getString(R.string.cos_inverse), "acos");
        expression = expression.replace(getResources().getString(R.string.tan_inverse), "atan");

        // Power
        expression = expression.replace(getResources().getString(R.string.power_of_2), "^2");
        expression = expression.replace(getResources().getString(R.string.power_of_3), "^3");
        expression = expression.replace(getResources().getString(R.string.power_of_inverse), "^-1");

        return expression;
    }
}