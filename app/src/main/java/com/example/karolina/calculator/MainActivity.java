package com.example.karolina.calculator;

import android.annotation.SuppressLint;
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

    private double firstOperand = 0;
    private double secoundOperand = 0;
    private double accumulator = 0;
    private char operator = ' ';

    private String firstOperandString = "";
    private String secoundOperandString = "";

    private boolean ifFirstOperand = true;
    private boolean ifBothOperandsSet = false;

    //###########   VIEW ACTIONS    ###############
    private void refresh(boolean ifDisplayResult){
        TextView operation = (TextView) findViewById(R.id.operationTextView);
        TextView result = (TextView) findViewById(R.id.resultTextView);

        operation.setText( firstOperandString + " " + String.valueOf(operator) + " " + secoundOperandString );

        if(ifDisplayResult) {
            result.setText( String.valueOf(accumulator) );
        }
        else{
            result.setText("");
        }
    }


    //###########   BUTTONS ACTIONS ###############
    public void allClean(View view){
        firstOperand = 0;
        secoundOperand = 0;
        accumulator = 0;
        operator = ' ';

        firstOperandString = "";
        secoundOperandString = "";

        ifFirstOperand = true;
        ifBothOperandsSet = false;

        refresh(false);
    }

    public void undo(View view){
        if(accumulator != 0) {
            accumulator = 0;
        }
        else if(ifBothOperandsSet){
            secoundOperandString = "";
            ifBothOperandsSet = false;
        }
        else if(operator != ' '){
            operator = ' ';
        }
        else{
            firstOperandString = "";
            ifBothOperandsSet = false;
        }
        refresh(false);
    }

    public void showResult(View view){
        if(ifBothOperandsSet) {
            firstOperand = Double.parseDouble(firstOperandString);
            secoundOperand = Double.parseDouble(secoundOperandString);

            switch (operator) {
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
            refresh(true);
            ifFirstOperand = true;
            ifBothOperandsSet = false;
        }
    }

    public void assignOperand(View view){
        if(ifFirstOperand){
            if(accumulator != 0){
                allClean(view);
            }
            if( !view.getTag().toString().contains(".") || (view.getTag().toString().contains(".") && !firstOperandString.contains(".")) ){
                firstOperandString += view.getTag().toString();
            }
        }
        else{
            if( !view.getTag().toString().contains(".") || (view.getTag().toString().contains(".") && !secoundOperandString.contains(".")) ){
                secoundOperandString += view.getTag().toString();
            }
            ifBothOperandsSet = true;
        }
        refresh(false);
    }

    public void assignOperator(View view){
        if (accumulator != 0){
            firstOperandString = String.valueOf(accumulator);
            secoundOperandString = " ";
            accumulator = 0;
        }
        operator = (char) view.getTag().toString().charAt(0);
        ifFirstOperand = false;
        refresh(false);
    }
}
