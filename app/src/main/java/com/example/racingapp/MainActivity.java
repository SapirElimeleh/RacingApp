package com.example.racingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int DELAY = 1000;
    ImageView[] main_IMG_hearts;
    ImageView[][] main_IMG_rocks;
    ImageView[] main_IMG_cars;
    ImageButton main_IMG_leftArrow;
    ImageButton main_IMG_rightArrow;
    int rockRow = 5;
    int countLives = 0;
    int tempRock1;
    int tempRock2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        moveCar();
        moveRocks();

    }


    private void moveRocks() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    moveRocksDown();
                    chooseRandomRock();
                    checkLives();
                });
            }
        }, 0, DELAY);
    }

    private void chooseRandomRock() {

        Random rand = new Random();

        int randCol = rand.nextInt(3);

        main_IMG_rocks[0][randCol].setVisibility(View.VISIBLE);

    }

    private void moveRocksDown() {

        for (int i = main_IMG_rocks.length - 1; i >= 0; i--) {
            for (int j = main_IMG_rocks[i].length - 1; j >= 0; j--) {

                if (main_IMG_rocks[i][j].getVisibility() == View.VISIBLE) {

                    if (i == main_IMG_rocks.length - 1) {
                        main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);

                    } else {
                        main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                        main_IMG_rocks[i + 1][j].setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    private void checkLives() {

        if (countLives == main_IMG_hearts.length) {
            for (ImageView main_img_heart : main_IMG_hearts) {
                main_img_heart.setVisibility(View.VISIBLE);
            }
            countLives = 0;
        }

        if ((main_IMG_rocks[rockRow - 1][0].getVisibility() == View.VISIBLE && main_IMG_cars[0].getVisibility() == View.VISIBLE)
                || (main_IMG_rocks[rockRow - 1][1].getVisibility() == View.VISIBLE && main_IMG_cars[1].getVisibility() == View.VISIBLE)
                || (main_IMG_rocks[rockRow - 1][2].getVisibility() == View.VISIBLE && main_IMG_cars[2].getVisibility() == View.VISIBLE)) {

            toast("Oops..");
            vibrate();
            main_IMG_hearts[countLives].setVisibility(View.INVISIBLE);
            countLives++;
        }

    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }


    private void moveCar() {

        main_IMG_leftArrow.setOnClickListener(v -> {
            if (main_IMG_cars[1].getVisibility() == View.VISIBLE
                    && main_IMG_cars[2].getVisibility() == View.INVISIBLE
                    && main_IMG_cars[0].getVisibility() == View.INVISIBLE) {

                main_IMG_cars[1].setVisibility(View.INVISIBLE);
                main_IMG_cars[0].setVisibility(View.VISIBLE);
            } else if (main_IMG_cars[2].getVisibility() == View.VISIBLE
                    && main_IMG_cars[1].getVisibility() == View.INVISIBLE
                    && main_IMG_cars[0].getVisibility() == View.INVISIBLE) {

                main_IMG_cars[2].setVisibility(View.INVISIBLE);
                main_IMG_cars[1].setVisibility(View.VISIBLE);
            }
        });


        main_IMG_rightArrow.setOnClickListener(view -> {
            if (main_IMG_cars[0].getVisibility() == View.VISIBLE
                    && main_IMG_cars[1].getVisibility() == View.INVISIBLE
                    && main_IMG_cars[2].getVisibility() == View.INVISIBLE) {

                main_IMG_cars[0].setVisibility(View.INVISIBLE);
                main_IMG_cars[1].setVisibility(View.VISIBLE);
            } else if (main_IMG_cars[1].getVisibility() == View.VISIBLE
                    && main_IMG_cars[0].getVisibility() == View.INVISIBLE
                    && main_IMG_cars[2].getVisibility() == View.INVISIBLE) {

                main_IMG_cars[1].setVisibility(View.INVISIBLE);
                main_IMG_cars[2].setVisibility(View.VISIBLE);
            }
        });


    }


    private void findViews() {

        main_IMG_hearts = new ImageView[]
                {findViewById(R.id.main_IMG_heart1)
                        , findViewById(R.id.main_IMG_heart2)
                        , findViewById(R.id.main_IMG_heart3)};


        main_IMG_rocks = new ImageView[][]{
                {findViewById(R.id.main_IMG_leftRock1)
                        , findViewById(R.id.main_IMG_middleRock1)
                        , findViewById(R.id.main_IMG_rightRock1)},
                {findViewById(R.id.main_IMG_leftRock2)
                        , findViewById(R.id.main_IMG_middleRock2)
                        , findViewById(R.id.main_IMG_rightRock2)},
                {findViewById(R.id.main_IMG_leftRock3)
                        , findViewById(R.id.main_IMG_middleRock3)
                        , findViewById(R.id.main_IMG_rightRock3)},
                {findViewById(R.id.main_IMG_leftRock4)
                        , findViewById(R.id.main_IMG_middleRock4)
                        , findViewById(R.id.main_IMG_rightRock4)},
                {findViewById(R.id.main_IMG_leftRock5)
                        , findViewById(R.id.main_IMG_middleRock5)
                        , findViewById(R.id.main_IMG_rightRock5)},
        };


        for (ImageView[] main_img_rock : main_IMG_rocks) {
            for (ImageView imageView : main_img_rock) {
                imageView.setVisibility(View.INVISIBLE);
            }
        }


        main_IMG_cars = new ImageView[]
                {findViewById(R.id.main_IMG_carLeft)
                        , findViewById(R.id.main_IMG_carMiddle)
                        , findViewById(R.id.main_IMG_carRight)};

        main_IMG_cars[0].setVisibility(View.INVISIBLE);
        main_IMG_cars[1].setVisibility(View.INVISIBLE);


        main_IMG_leftArrow = findViewById(R.id.main_IMG_leftArrow);
        main_IMG_rightArrow = findViewById(R.id.main_IMG_rightArrow);


    }
}