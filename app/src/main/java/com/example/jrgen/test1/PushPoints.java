package com.example.jrgen.test1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PushPoints extends BaseActivity {

    int numberOfQuerys = 4;
    int[] buttonClass = {0,0,0,0,0,0};
    int currentNumberOfQuery = 1;
    double precision=0;

    static TouchBehaviour touchBehaviour = new TouchBehaviour();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_points);

        init();
    }

    public void onConfigurationChanged(Configuration configuration) {
        // Tilt of Screen in landscape, or back to normal
        super.onConfigurationChanged(configuration);
        setPosition((int) (calculateScreenWidth()*0.5), (int) (calculateScreenHeight()*0.5));
    }

    @Override
    public void onRestart() {
        init();
        super.onRestart();
    }

    private void init() {
        initToolbar(getResources().getString(R.string.pushPointsTitle));

        currentNumberOfQuery = 1;
        for(int i = 0; i<6; i++) {
            buttonClass[i] = 0;
        }
        setPosition((int) (calculateScreenWidth()*0.5), (int) (calculateScreenHeight()*0.5));
    }

    private void calculatePrecisionClass(MotionEvent event) {
        //Calculate precision
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_push_points);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        int upperBar = (int) calculateScreenHeight()-(layout.getHeight());
        int toolBar = myToolbar.getHeight();
        double distance = calculateDistance(event.getRawX(), event.getRawY()-upperBar-toolBar, iw.getX() + (iw.getWidth() / 2), iw.getY() + (iw.getHeight() / 2));

        precision = (precision+distance)*0.5;

        if(distance <= iw.getWidth()/4) {
            buttonClass[0] = buttonClass[0]+1;
        } else {
            if(distance <= iw.getWidth()/2) {
                buttonClass[1] = buttonClass[1]+1;
            } else {
                if(distance <= iw.getWidth()) {
                    buttonClass[2] = buttonClass[2]+1;
                } else {
                    if(distance <= 2*iw.getWidth()) {
                        buttonClass[3] = buttonClass[3]+1;
                    } else {
                        if(distance <= 4*iw.getWidth()) {
                            buttonClass[4] = buttonClass[4]+1;
                        } else {
                            buttonClass[5] = buttonClass[5]+1;
                        }
                    }
                }
            }
        }
    }

    private int calculateButtonHeight() {
        // calculate the corresponding buttonHeight
        int pKlasse = 0;
        for(int i=0; i <= 5; i++) {
            pKlasse = pKlasse+(i+1)* buttonClass[i];
        }
        double test = (double) pKlasse / numberOfQuerys;
        pKlasse = (int) Math.round(test);
        switch (pKlasse) {
            case 1: return 75;
            case 2: return 100;
            case 3: return 125;
            case 4: return 150;
            case 5: return 175;
            case 6: return 200;
            default: return 150;
        }
    }

    private void storeValues() {
        // store the calculated values
        values.setButtonHeight(calculateButtonHeight());
        values.setPrecision((int) precision);
    }

    @Override
    public void actionAfterTouch() {
        //Callback when Waiting-Time after Input is over
        TouchBehaviour object = new TouchBehaviour();
        object.setCustomObjectListener(new TouchBehaviour.InputOver() {
            @Override
            public void next() {
                if (currentNumberOfQuery != numberOfQuerys) {
                    newSpawn();
                } else {
                    // Activity done
                    storeValues();
                    nextActivity();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        actionAfterTouch();

        touchBehaviour.calculateDurationPushed(event, inputTime);
        if (event.getAction() == MotionEvent.ACTION_DOWN && !touchBehaviour.moreThanOnePush()) {
            calculatePrecisionClass(event);
        }
        return true;
    }

    private void newSpawn() {
        // Load new red cross
        currentNumberOfQuery++;
        setRandomPosition();
    }

    private void setPosition(int posX, int posY) {
        // Set redCross to position (posX, posY)
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        iw.setX(posX);
        iw.setY(posY);
    }

    private void setRandomPosition() {
        // Reset the position of the cross randomly
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_push_points);
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        int upperBar = (int) calculateScreenHeight()-(layout.getHeight());
        int toolBar = myToolbar.getHeight();
        int puffer = 20;    // distance from screen

        int positionWidth = randomNumber(puffer, (int) (calculateScreenWidth()-layout.getPaddingLeft()-layout.getPaddingRight()-iw.getWidth()-puffer));
        int positionHeight = randomNumber(puffer, (int) (calculateScreenHeight()-layout.getPaddingBottom()-layout.getPaddingTop()-iw.getHeight()-toolBar-upperBar-puffer));

        while (calculateDistance(positionWidth, positionHeight, iw.getX(), iw.getY()) < 200) {          //Distance of Cross should be over 200
            positionWidth = randomNumber(puffer, (int) (calculateScreenWidth()-layout.getPaddingLeft()-layout.getPaddingRight()-iw.getWidth()-puffer));
            positionHeight = randomNumber(puffer, (int) (calculateScreenHeight()-layout.getPaddingBottom()-layout.getPaddingTop()-iw.getHeight()-toolBar-upperBar-puffer));
        }

        setPosition(positionWidth, positionHeight);
    }



    //Calculate Height and Width of the Screen
    private double calculateScreenWidth() {
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
    private double calculateScreenHeight() {
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}