package com.example.brightdays.lab1buttons;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button switchToGreen;
    private Button switchToRed;
    private Button switchToBlue;
    private Button switchToImage;
    private LinearLayout screenLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init buttons
        switchToBlue = (Button) findViewById(R.id.switchBlue);
        switchToImage = (Button) findViewById(R.id.switchImage);
        switchToGreen = (Button) findViewById(R.id.switchGreen);
        switchToRed = (Button) findViewById(R.id.switchRed);
        screenLayout = (LinearLayout) findViewById(R.id.screenLayout);

        // setup listeners
        switchToBlue.setOnClickListener(this);
        switchToRed.setOnClickListener(this);
        switchToGreen.setOnClickListener(this);
        switchToImage.setOnClickListener(this);

    }

    public void onClick(View view) {
        if (switchToBlue.equals(view)) {
            screenLayout.setBackgroundColor(Color.BLUE);
            showToast("Hello blue");
        } else if (switchToRed.equals(view)) {
            screenLayout.setBackgroundColor(Color.RED);
            showToast("Hello red");
        } else if (switchToGreen.equals(view)) {
            screenLayout.setBackgroundColor(Color.GREEN);
            showToast("Hello green");
        } else if (switchToImage.equals(view))
        {
            screenLayout.setBackground(getResources().getDrawable(R.drawable.back));
            showToast("Image");
        }

    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}