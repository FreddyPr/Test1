package com.example.jrgen.test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TestPattern extends BaseActivity implements View.OnClickListener {

    boolean[] pattern = {false,false,false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pattern);

        init();
    }

    private void init() {
        initToolbar(getResources().getString(R.string.enterPatternTitle));
        initButton(getResources().getString(R.string.pContinue), 1);
        initImageButtons(9);
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    private boolean[] loadArray() {
        //Load the generated pattern in 'ShowPattern' out of the SharedPreferences
        boolean[] array = {false,false,false,false,false,false,false,false,false};
        SharedPreferences prefs = getSharedPreferences("patternArray", Context.MODE_PRIVATE);
        for(int i=0; i<array.length; i++) {
            array[i] = prefs.getBoolean(String.valueOf(i), false);
        }
        return array;
    }

    private void changeButton(int i) {
        // Change the colour of a selected ImageButton
        int resID = getResources().getIdentifier("imageButton"+(i+1), "id", getPackageName());
        ImageButton button = (ImageButton) findViewById(resID);

        if(!pattern[i]) {
            pattern[i] = true;
            button.setImageResource(R.drawable.quadrat_rot);
        } else {
            pattern[i] = false;
            button.setImageResource(R.drawable.quadrat_weiss);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                changeButton(0);
                break;

            case R.id.imageButton2:
                changeButton(1);
                break;

            case R.id.imageButton3:
                changeButton(2);
                break;

            case R.id.imageButton4:
                changeButton(3);
                break;

            case R.id.imageButton5:
                changeButton(4);
                break;

            case R.id.imageButton6:
                changeButton(5);
                break;

            case R.id.imageButton7:
                changeButton(6);
                break;

            case R.id.imageButton8:
                changeButton(7);
                break;

            case R.id.imageButton9:
                changeButton(8);
                break;

            case R.id.button1:
                values.setShortTermMemory(calculateShortTimeMemory());
                break;
        }
    }

    private double calculateShortTimeMemory() {
        // Calculate the number of rightly selected squares; based on that calculate the value for 'shortTermMemory'
        int numberOfCorrespondingSquares=0;
        int numberOfSelectedSquares=0;
        boolean[] correctPattern = loadArray();
        for(int i=0; i<9; i++) {
            if(pattern[i]) {
                if(pattern[i] == correctPattern[i]) {
                    numberOfCorrespondingSquares++;
                }
                numberOfSelectedSquares++;
            }
        }
        double value = numberOfCorrespondingSquares*0.25;
        if(numberOfSelectedSquares != 4) {
            // if number of selected squares wrong
            value = value-0.25;
        }
        if(value < 0) value = 0;


        if(value == 1)
            value = 0;
        else
            if(value == 0.75)
                value = 0.25;
            else
                if(value == 0.25)
                    value = 0.75;
                else
                    if(value == 0)
                         value = 1;

        return value;
    }
}
