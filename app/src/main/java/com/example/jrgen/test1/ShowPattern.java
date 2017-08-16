package com.example.jrgen.test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ShowPattern extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show_pattern);

        init();
    }

    @Override
    public void onRestart() {
        init();
        super.onRestart();
    }

    private void init() {
        initToolbar(getResources().getString(R.string.rememberPatternTitle));
        initButton(getResources().getString(R.string.pContinue), 1);

        loadPattern();
    }

    private void loadPattern() {
        // Define pattern
        boolean[] pattern = new boolean[]{false, true, false, false, false, true, true, false, true};

        // Display pattern
        for(int i = 0; i < 9; i++) {
            int resID = getResources().getIdentifier("imageView" + (i+1), "id", getPackageName());
            ImageView iV = (ImageView) findViewById(resID);
            if (pattern[i]) {
                iV.setImageResource(R.drawable.quadrat_rot);
            } else {
                iV.setImageResource(R.drawable.quadrat_weiss);
            }
        }
        saveArrayInSharedPreferences(pattern);
    }

    private void saveArrayInSharedPreferences(boolean[] pPattern) {
        // Save Array, so that it can easily be transfered to Activity 'TestPattern'
        SharedPreferences prefs = getSharedPreferences("patternArray", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(int i=0;i<pPattern.length;i++) {
            editor.putBoolean(String.valueOf(i),pPattern[i]);
        }
        editor.apply();
    }
}
