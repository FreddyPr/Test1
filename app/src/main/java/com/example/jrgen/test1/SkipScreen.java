package com.example.jrgen.test1;

import android.os.Bundle;
import android.view.View;

public class SkipScreen extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip_screen);

        init();
    }

    void init() {
        initToolbar(getResources().getString(R.string.skipScreenTitle));
        initButton(getResources().getString(R.string.yes),2);
        initButton(getResources().getString(R.string.no),3);
    }

    @Override
    public void actionAfterTouch() {
        // Override this function, so that 'button1' does not behave like a Continue-Button
        TouchBehaviour object = new TouchBehaviour();
        object.setCustomObjectListener(new TouchBehaviour.InputOver() {
            @Override
            public void next() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:              // Ask next time
                data.saveData(this, changeActivity.getCurrentActivity()+"\n"+values.getData());
                break;

            case R.id.button3:              // Don't ask again
                data.saveData(this, "-1"+"\n"+values.getData());
                break;
        }
        loadAttachedApp();
    }
}
