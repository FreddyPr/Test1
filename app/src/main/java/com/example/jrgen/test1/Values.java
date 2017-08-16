package com.example.jrgen.test1;

import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

public class Values extends AppCompatActivity {

    static int buttonHeight=0;
    static String firstColour = "#757575", secondColour = "#E0E0E0";
    static String appTheme = "AppThemeGrey";
    static int volume = 6;
    static double textHeightInButton = 0.5;
    static int minTextSize = 25;
    static double shortTermMemory = 0.0;
    static double hardwareButtonProblems = 0.0;
    static int precision = 0;
    static double shakyFingers = 0.0;
    static int readingSpeed = 0;                         // Words / Minute
    static int screenTimeout = 60000;
    static long durationInput = 0;

    private int textSizeButton = 25;
    private int[] readingSpeedArray = {0,0,0,0,0,0,0,0,0,0,0,0,0};

    public void setOriginData() {
        buttonHeight=0;
        firstColour = "#757575";
        secondColour = "#E0E0E0";
        appTheme = "AppThemeGrey";
        volume = 6;
        textHeightInButton = 0.5;
        minTextSize = 25;
        shortTermMemory = 0.0;
        hardwareButtonProblems = 0.0;
        precision = 0;
        shakyFingers = 0.0;
        readingSpeed = 0;                         // Words / Minute
        screenTimeout = 60000;
        durationInput = 0;
        textSizeButton = 25;
        for(int i=0; i<12; i++){
            readingSpeedArray[i] = 0;
        }
    }

    public String getData() {
        // return gathered/default data
        calculateReadingSpeed();
        String data = "";
        data += getButtonHeight()+"\n";
        data += getAppTheme()+"\n";
        data += getVolume()+"\n";
        data += getTextHeightInButton()+"\n";
        data += getMinTextSize()+"\n";
        data += getShortTermMemory()+"\n";
        data += getHardwareButtonProblems()+"\n";
        data += getPrecision()+"\n";
        data += getShakyFingers()+"\n";
        data += getReadingSpeed()+"\n";
        data += getScreenTimeout()+"\n";
        data += getDurationInput()+"\n";
        return data;
    }

    public void calculateReadingSpeed() {
        Arrays.sort(readingSpeedArray);
        setReadingSpeed(readingSpeedArray[10]);     //Median of the 5 tracked values (Position 8-12)
    }


    // GETTER-Functions

    public int getReadingSpeed() {
        return readingSpeed;
    }

    public double getShakyFingers() {
        return shakyFingers;
    }

    public int getPrecision() {
        return precision;
    }

    public double getHardwareButtonProblems() {
        return hardwareButtonProblems;
    }

    public int getScreenTimeout() {
        return screenTimeout;
    }

    public double getShortTermMemory() {
        return shortTermMemory;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public int getTextSizeButton() {
        return textSizeButton;
    }

    public int getMinTextSize() {
        return minTextSize;
    }

    public double getTextHeightInButton() {
        return textHeightInButton;
    }

    public String getFirstColour() {
        return firstColour;
    }

    public String getSecondColour() {
        return secondColour;
    }

    public int getVolume() {
        return volume;
    }

    public long getDurationInput() {
        return durationInput;
    }

    public String getAppTheme() { return appTheme; }

    // SETTER-Functions

    public void setHardwareButtonProblems(double pProblem) {
        if(pProblem <= 1 && pProblem >= 0)
            hardwareButtonProblems = pProblem;
    }

    public void setAppTheme(String pAppTheme) {
        appTheme = pAppTheme;
    }

    public void setShakyFingers(double pProblem) {
        if(pProblem <= 1 && pProblem >= 0)
            shakyFingers = pProblem;
    }

    public void setPrecision(int pPrecision) {
        precision = pPrecision;
    }

    public void setReadingSpeedArray(int pReadingSpeed, int pActivityNumber) {
        readingSpeedArray[pActivityNumber] = pReadingSpeed;
    }

    public void setReadingSpeed(int pReadingSpeed) {
        readingSpeed = pReadingSpeed;
    }

    public void setScreenTimeout(int pScreenTimeout) {
        screenTimeout = pScreenTimeout;
    }

    public void setButtonHeight(int bH) {
        buttonHeight = bH;
    }

    public void setFirstColour(String colour1) {
        firstColour = colour1;
    }

    public void setSecondColour(String colour2) {
        secondColour = colour2;
    }

    public void setShortTermMemory(double pShortTermMemory) {
        shortTermMemory = pShortTermMemory;
    }

    public void setTextSizeButton(int pTextSize) {
        if( pTextSize >= 20 )
            textSizeButton = pTextSize;
    }

    public void setMinTextSize(int pTextSize) {
        minTextSize = pTextSize;
        setTextSizeButton(minTextSize);
    }

    public void setDurationInput(long pDurationInput) {
        if(durationInput == 0)
            durationInput = pDurationInput;
        else
            durationInput = (long) ((durationInput+pDurationInput)*0.5);
    }

    public void setTextHeightInButton(double pRelativeHeight) {
        textHeightInButton = pRelativeHeight;
    }

    public void setVolume(int pVolume) {
        volume = pVolume;
    }

}