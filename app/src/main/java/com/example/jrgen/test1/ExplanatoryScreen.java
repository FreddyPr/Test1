package com.example.jrgen.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExplanatoryScreen extends BaseActivity implements View.OnClickListener {

    int state = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBrown);
        setContentView(R.layout.activity_explanatory_screen);

        loadCurrentStateOfApp();
        init();
    }

    /*
    EXPLANATION OF THE DEMO VERSION:
    The word 'Demo' after some lines, states that these lines either have been excluded from the App or put into the App
    for demonstrating purposes only. By searching 'DEMO' those lines can quickly be identified.
     */

    public void init() {
        initButton(getResources().getString(R.string.pContinue), 1);
        initButton(getResources().getString(R.string.skip), 2);
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    private void loadCurrentStateOfApp() {
        // Define what happens when loading the App
        state = data.readFromInternalStorage(this);
        changeActivity.setCurrentActivity(state);

        if (state == 1){
            setTextInTextView(getResources().getString(R.string.description));
            initToolbar(getResources().getString(R.string.explanatoryScreenReloadTitle));
        } else {
            if (state == 0  || state == -1) {
                // Data gathered, or not wanted to gather data

                //loadAttachedApp();                                                                // DEMO
                setTextInTextView("Die App wurde bereits durchlaufen, jedoch kann die nachgestellte App nicht geladen werden, da dies bloß eine Demoversion ist. Durch Drücken auf Überspringen, wird die ursprüngliche Version noch einmal geladen.");   // DEMO
                initButton("Neustart", 2);                                                          // DEMO
                initToolbar("App durchlaufen");                                                     // DEMO
            } else {
                setTextInTextView(getResources().getString(R.string.descriptionReload));
                initToolbar(getResources().getString(R.string.explanatoryScreenReloadTitle));
                changeActivity.setCurrentActivity(changeActivity.getCurrentActivity() - 1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Next-Activity implemented in BaseActivity
            case R.id.button2:
                if( state == 0 || state == -1) {                                                    // DEMO
                    values.setOriginData();                                                         // DEMO
                    data.saveData(this, "1"+"\n"+values.getData());                                 // DEMO
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    //ChangeActivity.loadActivity(this,1);                                          // DEMO
                } else                                                                              // DEMO
                changeActivity.loadSkipScreen(this);
                break;
        }
    }
}

