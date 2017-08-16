package com.example.jrgen.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextSize extends BaseActivity implements View.OnClickListener {

    int minimalTextSize = 12;
    int maximalTextSize = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size);

        init();
    }

    @Override
    public void onRestart() {
        init();
        super.onRestart();
    }

    private void init() {
        initToolbar(getResources().getString(R.string.textSizeTitle));
        setTextInTextView(getResources().getString(R.string.instructionTextSize));
        initButton(getResources().getString(R.string.pContinue), 1);
        initButton(getResources().getString(R.string.shrink), 2);
        initButton(getResources().getString(R.string.enlarge), 3);
        loadTextSizes();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:      //shrink TextSize
                if(values.getMinTextSize() > minimalTextSize && values.getMinTextSize() <= maximalTextSize)
                    values.setMinTextSize(values.getMinTextSize() - 1);
                break;

            case R.id.button3:      //enlarge TextSize
                if(values.getMinTextSize() >= minimalTextSize && values.getMinTextSize() < maximalTextSize)
                    values.setMinTextSize(values.getMinTextSize() + 1);
                break;

            default:
                break;
        }
        loadTextSizes();
    }

    private void loadTextSizes() {
        // Update Button- and TextView-TextSize
        TextView tw = (TextView) findViewById(R.id.textView);
        tw.setTextSize(values.getMinTextSize());
        final Button b1 = (Button) findViewById(R.id.button1);
        final Button b2 = (Button) findViewById(R.id.button2);
        final Button b3 = (Button) findViewById(R.id.button3);
        b1.setTextSize(values.getTextSizeButton());
        b2.setTextSize(values.getTextSizeButton());
        b3.setTextSize(values.getTextSizeButton());
    }
}