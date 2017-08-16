package com.example.jrgen.test1;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;


public class TouchBehaviour extends AppCompatActivity {

    /*
    Push : Event, that user pushes the screen
    Input: Can contain several pushes (if user releases and pushes the screen several times)
     */

    // -1 : default-Value
    long firstTimeDown = -1;        // the first 'time' pushed
    long timeDown = -1;             // the 'time' pushed        (after every new push, overwritten)
    long timeUp = -1;               // the 'time' released      (after every new release, overwritten)
    int numberOfPushes = 0;         // How often pushed during one input
    long durationInput = 0;

    // Height of Text in Buttons
    static double textHeightInButton = 0.5;     // Relative Height of Text in Button
    static int middleOfButton = 0;          // Number of times, a Button was touched in the horizontal center

    private static InputOver listener;

    public TouchBehaviour() {
        listener = null;
    }

    public interface InputOver {
        void next();
    }

    public void setCustomObjectListener(InputOver pListener) {
        listener = pListener;
    }

    public void increaseNumberOfPushes() {
        numberOfPushes++;
    }

    public void resetTimer() {
        // New Input
        timeDown = -1;
        timeUp = -1;
        firstTimeDown = -1;
        numberOfPushes = 0;
        durationInput = 0;
    }

    public boolean calculateDurationPushed(MotionEvent event, final int waitingTime) {
        // waitingTime: time that goes by after a push, till next inputData can be gathered
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Button pushed down
            timeDown = System.currentTimeMillis();
            timeUp = 0;
            increaseNumberOfPushes();

            if(firstTimeDown == -1) {   // First Push during this Input?
                firstTimeDown = timeDown;
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // Button released
            timeUp = System.currentTimeMillis();

            final int goOn = numberOfPushes;
            new CountDownTimer(waitingTime, 100) {
                public void onTick(long millisUntilFinished) { }

                public void onFinish() {
                    if (numberOfPushes == goOn) {           // Has there been another Push since?
                        setDurationInput(timeUp-firstTimeDown);
                        if (listener != null)
                            listener.next();
                        resetTimer();
                    }
                }
            }.start();
        }
        return true;
    }

    public long getDurationInput() {
        return durationInput;
    }
    public void setDurationInput(long pDurationInput) {
        durationInput = pDurationInput;
    }

    public void calculateTextHeightInButton(View v, MotionEvent event) {
        // Calculate the new position of text in buttons
        if (event.getAction() == MotionEvent.ACTION_DOWN && middleOfButton < 4) {
            // Where is the Button touched in the vertical (relatively)
            double relativePush = (event.getY() - v.getY()) / v.getHeight();

            if (relativePush > 0.7 || relativePush < 0.3) {
                // Touched NOT in the Middle
                setTextHeightInButton(getTextHeightInButton() - (relativePush - 0.5) / 5);
                middleOfButton = 0;
            } else {
                middleOfButton++;
            }

            if (getTextHeightInButton() > 0.75)
                setTextHeightInButton(0.75);

            if (getTextHeightInButton() < 0.25)
                setTextHeightInButton(0.25);
        }
    }

    /*
    GETTER-Functions
     */

    public boolean moreThanOnePush() {
        return numberOfPushes > 1;
    }

    public void setTextHeightInButton(double pTextHeightInButton) {
        textHeightInButton = pTextHeightInButton;
    }

    public double getTextHeightInButton() {
        return textHeightInButton;
    }

}
