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

    //########### DEFINE VARIABLES ###############
    private double firstOperand = 0;
    private double secoundOperand = 0;
    private double accumulator = 0;
    private char operator = ' ';

    private String firstOperandString = "";
    private String secoundOperandString = "";

    private boolean ifFirstOperandSet = false;
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

        ifFirstOperandSet = false;
        ifBothOperandsSet = false;

        refresh(false);
    }

    public void undo(View view){
        if(accumulator != 0) {
            // clear accumulator
            accumulator = 0;
            ifBothOperandsSet = true;
            ifFirstOperandSet = true;
        }
        else if(ifBothOperandsSet){
            // clear secound operand
            secoundOperandString = "";
            secoundOperand = 0;
            ifBothOperandsSet = false;
        }
        else if(operator != ' '){
            // clear sign
            operator = ' ';
            ifFirstOperandSet = false;
        }
        else{
            // clear first operand
            firstOperandString = "";
            firstOperand = 0;
            ifFirstOperandSet = false;
        }
        refresh(false);
    }

    public void showResult(View view){
        // calculate and show result
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
            ifFirstOperandSet = false;  // to take result as a first operand
            ifBothOperandsSet = true;
        }
    }

    public void assignOperand(View view){
        if(ifFirstOperandSet == false){
            // assign to the first operand
            if(accumulator != 0){
                allClean(view);
            }
            if( !view.getTag().toString().contains(".") || (view.getTag().toString().contains(".") && !firstOperandString.contains(".")) ) {
                firstOperandString += view.getTag().toString();
            }
        }
        else if(operator != ' '){
            // only if sign is set, assign to the secound operand
            if( !view.getTag().toString().contains(".") || (view.getTag().toString().contains(".") && !secoundOperandString.contains(".")) ){
                secoundOperandString += view.getTag().toString();
            }
            ifBothOperandsSet = true;
        }
        refresh(false);
    }

    public void assignOperator(View view){
        if (accumulator != 0){
            // assign accumulator to the first operand
            firstOperandString = String.valueOf(accumulator);
            ifFirstOperandSet = true;
            secoundOperandString = "";
            accumulator = 0;
            ifBothOperandsSet = false;
        }
        else if(!firstOperandString.isEmpty()){
            // if anything is in the first operand allow assign a sign
            operator = (char) view.getTag().toString().charAt(0);
            ifFirstOperandSet = true;
        }
        else if(firstOperandString.isEmpty() && (char) view.getTag().toString().charAt(0) == '-'){
            // make first operand negative number
            firstOperandString += '-';
        }
        refresh(false);
    }
}
