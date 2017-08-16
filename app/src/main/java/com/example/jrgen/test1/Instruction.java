package com.example.jrgen.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Instruction extends BaseActivity implements View.OnClickListener {

    long startTime = 0;
    long numberOfWords = 0;
    long delayTime = -2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        // Initialize after Activity loaded
        super.onStart();
        init();
    }

    @Override
    public void onRestart() {
        // If Activity reloads, don't calculate readingSpeed
        startTime = 0;
        super.onRestart();
    }

    private void init() {
        loadTexts();
        numberOfWords = wordCountInstruction();
        initButton(getResources().getString(R.string.pContinue), 1);
    }

    private void loadTexts() {
        // Load the current instruction
        switch (changeActivity.getCurrentActivity()) {
            case 3:
                initToolbar(getResources().getString(R.string.rememberPatternTitle));
                setTextInTextView(getResources().getString(R.string.instructionRememberPattern));
                break;
            case 5:
                initToolbar(getResources().getString(R.string.colourTitle));
                setTextInTextView(getResources().getString((R.string.instructionColour)));
                break;
            case 7:
                initToolbar(getResources().getString(R.string.volumeTitle));
                setTextInTextView(getResources().getString(R.string.instructionVolume));
                break;
            case 9:
                initToolbar(getResources().getString(R.string.pushPointsTitle));
                setTextInTextView(getResources().getString(R.string.instructionPushPoints));
                break;
            case 11:
                initToolbar(getResources().getString(R.string.enterPatternTitle));
                setTextInTextView(getResources().getString(R.string.instructionEnterPattern));
                break;
            case 13:
                initToolbar(getResources().getString(R.string.endingTitle));
                setTextInTextView(getResources().getString(R.string.instructionEnding));
                break;
            default:
                break;
        }
    }

    public int wordCountInstruction(){
        // Count the words of the instruction
        String message = "";
        int resID = getResources().getIdentifier("textView1", "id", getPackageName());
        TextView textView = (TextView) findViewById(resID);
        if(textView != null) {
            message = textView.getText().toString();
        }
        return message.trim().split("\\s+").length;
    }

    private void calculateReadingSpeed() {
        // Calculate readingSpeed
        double duration = (System.currentTimeMillis() - (startTime + delayTime + values.getDurationInput()));
        double readingSpeed = (60000/duration)*numberOfWords;

        if(startTime != 0)
            values.setReadingSpeedArray((int) readingSpeed, changeActivity.getCurrentActivity());

    }

    @Override
    protected void nextActivity() {
        if(com.example.jrgen.test1.ChangeActivity.currentActivity != 13 && startTime != 0)
            // Not the Ending-Text && not restarted the Activty via BackButton
            calculateReadingSpeed();
        super.nextActivity();
    }
}


