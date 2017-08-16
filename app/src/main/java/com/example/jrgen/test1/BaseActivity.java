package com.example.jrgen.test1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;


abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    final static Values values = new Values();
    static HardwareButtonPress hardwareButtonPress = new HardwareButtonPress();
    static TouchBehaviour touchBehaviour = new TouchBehaviour();
    static ChangeActivity changeActivity = new ChangeActivity();
    static int inputTime = 300;                            // Time that has to pass till Continue-Button causes action
    SaveReadData data = new SaveReadData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenTimeout();
    }

    protected void setScreenTimeout() {
        // Set the time that passes till screen turns off
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, values.getScreenTimeout());
    }

    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        // If a HardwareButton is pressed
        HardwareButtonPress warning = new HardwareButtonPress();
        warning.setCustomObjectListener(new HardwareButtonPress.MyListener() {
            @Override
            public void yes() {
                if(keyCode == KeyEvent.KEYCODE_BACK && ChangeActivity.currentActivity > 1) {
                    // If 'Back'-Button -> load previous Activity
                    changeActivity.setCurrentActivity(changeActivity.getCurrentActivity()-2);
                    nextActivity();
                } else {
                    BaseActivity.super.onKeyUp(keyCode, event);
                }
            }
            public void no() {
                values.setHardwareButtonProblems(values.getHardwareButtonProblems()+0.25);
            }
        });
        return hardwareButtonPress.onKeyUp(keyCode,this);
    }

    protected void initToolbar(String message) {
        // Initialization of Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        int resID = getResources().getIdentifier("toolbar_title", "id", getPackageName());
        TextView textView = (TextView) findViewById(resID);
        textView.setText(message);

        myToolbar.setBackgroundColor(Color.parseColor(values.getFirstColour()));
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        // Necessary to highlight words in a bold font
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    protected void setTextInTextView(String message) {
        int resID = getResources().getIdentifier("textView1", "id", getPackageName());
        TextView textView = (TextView) findViewById(resID);

        if(textView != null) {
            textView.setTextSize(values.getMinTextSize());
            textView.setText(fromHtml(message));
        }
    }

    protected void setTextHeightInButton(Button b) {
        // Shift text in button vertically
        if(values.getTextHeightInButton() > 0.5) {
            b.setPadding(0,(int) (b.getHeight()*(values.getTextHeightInButton()-0.5)),0,0);
        } else {
            b.setPadding(0,0,0,(int) (b.getHeight()*(0.5- values.getTextHeightInButton())));
        }
    }

    protected void initButton(String title, int buttonNumber) {
        // Initialize button 'buttonNumber'
        int resID = getResources().getIdentifier("button" + (buttonNumber), "id", getPackageName());
        final Button button = (Button) findViewById(resID);

        // Button Background-Colour
        Drawable buttonBackground = button.getBackground();
        if (buttonBackground != null) {
            buttonBackground.setColorFilter(Color.parseColor(values.getSecondColour()), PorterDuff.Mode.MULTIPLY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // API > 16
                button.setBackground(buttonBackground);
            } else {
                // API < 16
                button.setBackgroundDrawable(buttonBackground);
            }
        }

        // Change buttonHeight if buttonHeight has already been calculated
        if (values.getButtonHeight() != 0) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) button.getLayoutParams();
            ll.gravity = Gravity.CENTER;
            ll.height = values.getButtonHeight();
            button.setLayoutParams(ll);
        }

        // Wait till Button fully loaded. Else getHeight() returns 0
        button.post(new Runnable() {
            @Override
            public void run() {
                setTextHeightInButton(button);
            }
        });
        button.setTextSize(values.getTextSizeButton());
        button.setOnClickListener(this);
        button.setText(title);

        // OnTouchListener to track the Touch-Behaviour (only for Continue-Button)
        if (buttonNumber == 1) {
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    actionAfterTouch();
                    touchBehaviour.calculateDurationPushed(event, inputTime);
                    touchBehaviour.calculateTextHeightInButton(v, event);
                    return false;
                }
            });
        }
    }

    protected void actionAfterTouch() {
        // What happens after the TouchBehaviour was tracked, and the Listener is loaded
        TouchBehaviour object = new TouchBehaviour();
        object.setCustomObjectListener(new TouchBehaviour.InputOver() {
            @Override
            public void next() {
                if(changeActivity.getCurrentActivity() != 13) {
                    values.setDurationInput(touchBehaviour.getDurationInput());
                    values.setTextHeightInButton(touchBehaviour.getTextHeightInButton());
                    if(touchBehaviour.moreThanOnePush())
                        values.setShakyFingers(values.getShakyFingers()+0.25);
                }
                nextActivity();
            }
        });
    }

    protected void initImageButtons(int numberOfButtons) {
        // Initialize all ImageButtons till 'numberOfButtons'
        for(int i=1; i<=numberOfButtons; i++) {
            int resID = getResources().getIdentifier("imageButton"+(i), "id", getPackageName());
            ImageButton button = (ImageButton) findViewById(resID);
            button.setOnClickListener(this);
        }
    }

    protected int randomNumber(int lowerBound, int upperBound) {
        // Calculate a random number n such that lowerBound <= n < upperBound
        if(lowerBound != upperBound) {
            Random r = new Random();
            return r.nextInt(upperBound - lowerBound) + lowerBound;
        } else {
            return lowerBound;
        }
    }

    protected double calculateDistance(float x1, float y1, float x2, float y2) {
        // Calculate distance between point x and point y
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    @Override
    public void onClick(View v) {
        // Empty Method has to be implemented
    }

    protected void nextActivity() {
        // Load the next Activity
        if(changeActivity.getCurrentActivity() == 13) {
            data.saveData(this, "0"+"\n"+values.getData());
            loadAttachedApp();
        } else {
            data.saveData(this, changeActivity.getCurrentActivity()+1+"\n"+values.getData());
            changeActivity.loadNextActivity(this);
        }
    }

    protected void loadAttachedApp() {
        // Load the attached App

        changeActivity.loadActivity(this,1);                                                        // DEMO
    }
}



/*
Buttons:
Buttons should be named "button1", "button2", "button3", etc.
"button1": Continue-Button. Next Activity loads after 'inputTime' passes.
 */

