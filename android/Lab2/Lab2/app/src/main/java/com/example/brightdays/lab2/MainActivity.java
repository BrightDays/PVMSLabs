package com.example.brightdays.lab2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textField;
    private Button tryButton;
    private EditText botsNumbersTextField;

    private double average;
    private ArrayList<Integer> numbers;
    private int winnerNumber;
    private int upperBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setView();
        this.setModel();
    }

    private void setView()
    {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textField = (EditText)findViewById(R.id.editText);
        botsNumbersTextField = (EditText)findViewById(R.id.numberOfBotsTextField);
        tryButton = (Button)findViewById(R.id.button);
        tryButton.setOnClickListener(this);
    }

    private void setModel()
    {
        numbers = new ArrayList<>();
        average = -1;
        winnerNumber = -1;
        upperBound = 50;
    }

    private void showAlert(String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showAlert(String title, String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    private void generateBotsNumbers(int countOfBots)
    {

        if (average != -1)
        if (average > 25 && upperBound > 40)
            upperBound = 40;
        else
        if (average > 10 && upperBound > 20)
            upperBound = 20;
        else
        if (average > 5 && upperBound > 10)
            upperBound = 10;
        else
            upperBound = 5;

        numbers.clear();
        for(int i = 0; i < countOfBots; i++)
        {
            Random generator = new Random();
            int number = generator.nextInt(upperBound) + 1;
            numbers.add(number);
        }
    }

    private void findWinner(int number)
    {
        double sum = number;
        for(int i = 0; i < numbers.size(); i++)
            sum += numbers.get(i);
        average = (sum / (1.f + numbers.size())) * 2.f/3;
        int count = 1;
        double difference = Math.abs(average - number);
        int num = -1;
        for(int i = 0; i < numbers.size(); i++)
        {
            if (difference > Math.abs(average - numbers.get(i)))
            {
                difference = Math.abs(average - numbers.get(i));
                count = 1;
                num = i + 1;
            } else
            if (difference == Math.abs(average - numbers.get(i)))
            {
                count++;
            }
        }
        if (difference < Math.abs(average - number))
        {
            winnerNumber = num;
            return;
        }
        if (difference == Math.abs(average - number))
        {
            if (count == 1)
            {
                winnerNumber = 0;
                return;
            }
            Random generator = new Random();
            int winNumber = generator.nextInt(count);
            if (winNumber == 0)
            {
                winnerNumber = 0;
                return;
            }
            int temp = 0;
            for(int i = 0; i < numbers.size(); i++)
            {
                if (difference == Math.abs(average - numbers.get(i)))
                    temp++;
                if (temp == winNumber)
                {
                    winnerNumber = i + 1;
                    return;
                }
            }

        }
    }

    private void showAlertWithNumbers(int number)
    {
        String title = this.getString(R.string.you_lose);
        if (winnerNumber == 0)
            title = this.getString(R.string.you_win);
        String text = this.getString(R.string.average) + " : " + String.valueOf(average) + "\n";
        text += this.getString(R.string.your_number) + " : " + String.valueOf(number) + "\n";
        text += this.getString(R.string.bots_integers) + " : ";

        for(int i = 0; i < numbers.size() - 1; i++)
        {
            text += String.valueOf(numbers.get(i)) + ", ";
        }
        text += String.valueOf(numbers.get(numbers.size() - 1)) + "\n";
        if (winnerNumber != 0)
            text += this.getString(R.string.winner_bot) + String.valueOf(winnerNumber);
        showAlert(title, text);
    }

    public void onClick(View view)
    {

        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        if (textField.getText().toString().length() == 0)
        {
            showAlert(this.getString(R.string.enter_number));
            return;
        }
        int number = Integer.parseInt(textField.getText().toString());
        if (number < 1 || number > 100)
        {
            showAlert(this.getString(R.string.incorrect_number));
            return;
        }

        int botsNumber = 5;
        if (botsNumbersTextField.getText().toString().length() != 0)
        {
            int countOfBots = Integer.parseInt(botsNumbersTextField.getText().toString());
            if (countOfBots >= 2 && countOfBots <= 20)
                botsNumber = countOfBots;
        }

        generateBotsNumbers(botsNumber);
        findWinner(number);
        showAlertWithNumbers(number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            this.finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
}
