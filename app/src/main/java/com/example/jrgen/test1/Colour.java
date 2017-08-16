package com.example.jrgen.test1;

import android.os.Bundle;
import android.view.View;

public class Colour extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour);

        init();
    }

    public void refreshColours() {
        // Set AppLayout in selected colour
        initToolbar(getResources().getString(R.string.colourTitle));
        initButton(getResources().getString(R.string.pContinue), 1);
    }

    private void init() {
        initToolbar(getResources().getString(R.string.colourTitle));
        initButton(getResources().getString(R.string.pContinue), 1);
        initImageButtons(6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1: //Red
                values.setFirstColour("#E53935");
                values.setSecondColour("#EF9A9A");
                values.setAppTheme("AppThemeRed");
                break;

            case R.id.imageButton2: //Yellow
                values.setFirstColour("#FDD835");
                values.setSecondColour("#FFF59D");
                values.setAppTheme("AppThemeYellow");
                break;

            case R.id.imageButton3: //Green
                values.setFirstColour("#7CB342");
                values.setSecondColour("#C5E1A5");
                values.setAppTheme("AppThemeGreen");
                break;

            case R.id.imageButton4: //Blue
                values.setFirstColour("#1E88E5");
                values.setSecondColour("#90CAF9");
                values.setAppTheme("AppThemeBlue");
                break;

            case R.id.imageButton5: //Brown
                values.setFirstColour("#6D4C41");
                values.setSecondColour("#BCAAA4");
                values.setAppTheme("AppThemeBrown");
                break;

            case R.id.imageButton6: //Grey
                values.setFirstColour("#757575");
                values.setSecondColour("#E0E0E0");
                values.setAppTheme("AppThemeGrey");
                break;
        }
        refreshColours();
    }
}