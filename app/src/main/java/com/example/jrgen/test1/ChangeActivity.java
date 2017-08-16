package com.example.jrgen.test1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class ChangeActivity extends AppCompatActivity {

    public static int currentActivity = 1;
    Intent nextActivity;

    protected void loadActivity(final Activity activity, int activityNumber) {
        // Load the current Activity
        switch (activityNumber) {
            case 1:
                nextActivity = new Intent(activity, ExplanatoryScreen.class);
                break;
            case 2:
                nextActivity = new Intent(activity, TextSize.class);
                break;
            case 4:
                nextActivity = new Intent(activity, ShowPattern.class);
                break;
            case 6:
                nextActivity = new Intent(activity, Colour.class);
                break;
            case 8:
                nextActivity = new Intent(activity, Volume.class);
                break;
            case 10:
                nextActivity = new Intent(activity, PushPoints.class);
                break;
            case 12:
                nextActivity = new Intent(activity, TestPattern.class);
                break;

            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13: nextActivity = new Intent(activity, Instruction.class);
                break;

            default:
                nextActivity = new Intent(activity, ExplanatoryScreen.class);
                break;
        }

        activity.startActivity(nextActivity);
    }

    protected void loadNextActivity(final Activity activity) {
        // Load the next Activity. Increase current Activity.
        currentActivity += 1;
        loadActivity(activity, currentActivity);
    }

    protected void loadSkipScreen(final Activity activity) {
        // Load the Skip-Screen (Exit of App)
        nextActivity = new Intent(activity, SkipScreen.class);
        activity.startActivity(nextActivity);
    }

    public void setCurrentActivity(int pActivityNumber) {
        currentActivity = pActivityNumber;
    }

    public int getCurrentActivity() {
        return currentActivity;
    }
}

/*
-2: Data not gathered, Don't ask again
-1: Data not gathered, Ask next time
0: Data gathered and can be used
1: ExplanatoryScreen
2: TextSize
3: Instruction
4: ShowPattern
5: Instruction
6: Colour
7: Instruction
8: Volume
9: Instruction
10: PushPoints
11: Instruction
12: TestPattern
13: Ending-Instruction
 */