package com.example.karolina.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public:
        double firstOperand = 0;
        double secoundOperand = 0;
        double accumulator = 0;
        char operator = '+';

        String firstOperandString = "";
        String secoundOperandString = "";

    //###########   VIEW ACTIONS    ###############
    public void refresh(View view){
        TextView operation = (TextView) findViewById(R.id.operationTextView);
        TextView result = (TextView) findViewById(R.id.resultTextView);
        TextView sign = (TextView) findViewById(R.id.signTextView);

        operation.setText( String.valueOf(firstOperand) + " " + String.valueOf(operator) + " " + String.valueOf(secoundOperand) );
        result.setText( String.valueOf(accumulator) );
        sign.setText( String.valueOf(operator) );
    }


    //###########   BUTTONS ACTIONS ###############
    public void allClean(View view){
        firstOperand = 0;
        secoundOperand = 0;
        accumulator = 0;
        operator = ' ';

        firstOperandString = "";
        secoundOperandString = "";

        refresh(view);
    }

    public void undo(View view){
        if(accumulator != 0) {
            accumulator = 0;
        }
        else if(secoundOperand != 0){
            secoundOperand = 0;
        }
        else{
            firstOperand = 0;
        }
        refresh(view);
    }

    public void showResult(View view){
        firstOperand = Double.parseDouble(firstOperandString);
        secoundOperand = Double.parseDouble(secoundOperandString);

        switch(operator){
            case '+':
                accumulator = firstOperand + secoundOperand;
                break;

            case '-':
                accumulator = firstOperand - secoundOperand;
                break;

            case '*':
                accumulator = firstOperand * secoundOperand;
                break;

            case '/':
                accumulator = firstOperand / secoundOperand;
                break;

            case '^':
                accumulator = pow(firstOperand, secoundOperand);
                break;

            default:
                accumulator = 0;

        }
        refresh(view);
    }

    public void assignOperand(View view){
        if(firstOperand != 0){
            secoundOperandString += view.getTag().toString();
        }
        else{
            firstOperandString += view.getTag().toString();
        }
        refresh(view);
    }

    public void assignOperator(View view){
        operator = (char) view.getTag().toString().charAt(0);
        refresh(view);
    }
}
