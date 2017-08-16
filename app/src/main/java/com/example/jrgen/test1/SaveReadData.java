package com.example.jrgen.test1;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

class SaveReadData {

    final static Values values = new Values();
    private String fileName = "Personalizationdata.txt";

    // SAVE Data

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    void saveData(Activity activity, String data) {
        // Save 'data' in according text files of the internal and external storage
        saveToExternalStorage(data);
        saveToInternalStorage(activity, data);
    }

    private void saveToInternalStorage(final Activity activity, String data) {
        // Save 'data' to internal storage
        try {
            FileOutputStream outputStream = activity.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToExternalStorage(String data) {
        // Save 'data' to external storage
        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            try {
                File myFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName);
                myFile.createNewFile(); // if file already exists, it will do nothing
                FileOutputStream oFile = new FileOutputStream(myFile, false);
                oFile.write(data.getBytes());
                oFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // READ Data

    //TODO: Saving to external storage if reading not possible.

    int readFromInternalStorage(Activity activity) {
        // read data, if possible, out of the internal storage
        try {
            FileInputStream fis = activity.getApplicationContext().openFileInput(fileName);
            InputStreamReader i = new InputStreamReader(fis);
            BufferedReader b = new BufferedReader(i);

            int state = Integer.parseInt(b.readLine());

            if(state <= 13 && state >= 4) {
                // 1,2,3 remain the same. Activity 4 has to load the Instruction first (3), 6 has to load Instruction (5) etc.
                if(state % 2 == 0) {
                    state -= 1;
                }
            }

            values.setTextSizeButton(20);
            values.setButtonHeight(Integer.parseInt(b.readLine()));
            String colour = b.readLine();
            // Set Colour in own App
            if(colour.equals("AppThemeGrey")) {
                values.setFirstColour("#757575");
                values.setSecondColour("#E0E0E0");
            }
            if(colour.equals("AppThemeRed")) {
                values.setFirstColour("#E53935");
                values.setSecondColour("#EF9A9A");
            }
            if(colour.equals("AppThemeGreen")) {
                values.setFirstColour("#7CB342");
                values.setSecondColour("#C5E1A5");
            }
            if(colour.equals("AppThemeBlue")) {
                values.setFirstColour("#1E88E5");
                values.setSecondColour("#90CAF9");
            }
            if(colour.equals("AppThemeYellow")) {
                values.setFirstColour("#FDD835");
                values.setSecondColour("#FFF59D");
            }
            if(colour.equals("AppThemeBrown")) {
                values.setFirstColour("#6D4C41");
                values.setSecondColour("#BCAAA4");
            }
            values.setAppTheme(colour);
            values.setVolume(Integer.parseInt(b.readLine()));
            values.setTextHeightInButton(Double.parseDouble(b.readLine()));
            values.setMinTextSize(Integer.parseInt(b.readLine()));
            values.setShortTermMemory(Double.parseDouble(b.readLine()));
            values.setHardwareButtonProblems(Double.parseDouble(b.readLine()));
            values.setPrecision(Integer.parseInt(b.readLine()));
            values.setShakyFingers(Double.parseDouble(b.readLine()));
            values.setReadingSpeed(Integer.parseInt(b.readLine()));
            values.setScreenTimeout(Integer.parseInt(b.readLine()));
            values.setDurationInput(Integer.parseInt(b.readLine()));

            return state;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;   // Error Case: Do test again
    }
}
