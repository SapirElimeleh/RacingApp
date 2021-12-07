package com.example.racingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.racingapp.Models.MapSpot;
import com.example.racingapp.Models.Record;
import com.example.racingapp.Utils.MyDB;
import com.google.gson.Gson;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {

    private static final int DELAY = 1000;
    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    private ImageView[] main_IMG_hearts;
    private int[][] num_IMV_rocks;
    private ImageView[][] main_IMG_obs;
    private ImageView[] main_IMG_Jhon;
    private ImageButton main_IMG_leftArrow;
    private ImageButton main_IMG_rightArrow;
    private String[] main_img_names;
    private TextView main_LBL_score;
    private Timer timer;
    private final int obsRow = 8;
    private final int obsCol = 5;
    private int countLives = 0;
    private int currentJhonPlace = 0;
    private int score = 0;
    private static MyDB myDB = new MyDB();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double lat;
    private double lon;
    private MSP msp;
    private Gson gson;


    private SensorManager sensorManager;
    private Sensor sensor;

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];

            if (x < -6) {
                moveJhonRight();
            } else if (x > 6)
                moveJhonLeft();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        gson = new Gson();
        msp = MSP.getInstance();
        locationPermission();

        findViews();

        Intent intent = getIntent();
        initSensor(intent.getStringExtra(SENSOR_TYPE));

        moveObs();


    }


    //set all views
    private void findViews() {

        //set all hearts pictures
        main_IMG_hearts = new ImageView[]
                {findViewById(R.id.main_IMG_heart1)
                        , findViewById(R.id.main_IMG_heart2)
                        , findViewById(R.id.main_IMG_heart3)};

        //set all obstacles pictures
        main_IMG_obs = new ImageView[][]{
                {findViewById(R.id.main_IMG_obs00)
                        , findViewById(R.id.main_IMG_obs01)
                        , findViewById(R.id.main_IMG_obs02)
                        , findViewById(R.id.main_IMG_obs03)
                        , findViewById(R.id.main_IMG_obs04)},
                {findViewById(R.id.main_IMG_obs10)
                        , findViewById(R.id.main_IMG_obs11)
                        , findViewById(R.id.main_IMG_obs12)
                        , findViewById(R.id.main_IMG_obs13)
                        , findViewById(R.id.main_IMG_obs14)},
                {findViewById(R.id.main_IMG_obs20)
                        , findViewById(R.id.main_IMG_obs21)
                        , findViewById(R.id.main_IMG_obs22)
                        , findViewById(R.id.main_IMG_obs23)
                        , findViewById(R.id.main_IMG_obs24)},
                {findViewById(R.id.main_IMG_obs30)
                        , findViewById(R.id.main_IMG_obs31)
                        , findViewById(R.id.main_IMG_obs32)
                        , findViewById(R.id.main_IMG_obs33)
                        , findViewById(R.id.main_IMG_obs34)},
                {findViewById(R.id.main_IMG_obs40)
                        , findViewById(R.id.main_IMG_obs41)
                        , findViewById(R.id.main_IMG_obs42)
                        , findViewById(R.id.main_IMG_obs43)
                        , findViewById(R.id.main_IMG_obs44)},
                {findViewById(R.id.main_IMG_obs50)
                        , findViewById(R.id.main_IMG_obs51)
                        , findViewById(R.id.main_IMG_obs52)
                        , findViewById(R.id.main_IMG_obs53)
                        , findViewById(R.id.main_IMG_obs54)},
                {findViewById(R.id.main_IMG_obs60)
                        , findViewById(R.id.main_IMG_obs61)
                        , findViewById(R.id.main_IMG_obs62)
                        , findViewById(R.id.main_IMG_obs63)
                        , findViewById(R.id.main_IMG_obs64)},
                {findViewById(R.id.main_IMG_obs70)
                        , findViewById(R.id.main_IMG_obs71)
                        , findViewById(R.id.main_IMG_obs72)
                        , findViewById(R.id.main_IMG_obs73)
                        , findViewById(R.id.main_IMG_obs74)},
        };


        //set all pictures to invisible
        for (ImageView[] main_img_obs : main_IMG_obs) {
            for (ImageView imageView : main_img_obs) {
                imageView.setVisibility(View.INVISIBLE);
            }
        }


        //set all jhon pictures
        main_IMG_Jhon = new ImageView[]
                {findViewById(R.id.main_IMG_jhon0)
                        , findViewById(R.id.main_IMG_jhon1)
                        , findViewById(R.id.main_IMG_jhon2)
                        , findViewById(R.id.main_IMG_jhon3)
                        , findViewById(R.id.main_IMG_jhon4)};

        //set jhon first visibility
        main_IMG_Jhon[0].setVisibility(View.INVISIBLE);
        main_IMG_Jhon[1].setVisibility(View.INVISIBLE);
        main_IMG_Jhon[3].setVisibility(View.INVISIBLE);
        main_IMG_Jhon[4].setVisibility(View.INVISIBLE);
        currentJhonPlace = 2;


        //find the arrows pictures
        main_IMG_leftArrow = findViewById(R.id.main_IMG_leftArrow);
        main_IMG_rightArrow = findViewById(R.id.main_IMG_rightArrow);

        //set array of image names
        main_img_names = new String[]{"chair", "sword", "dead", "blue_dragon", "brown_dragon"};

        //get the score text view label
        main_LBL_score = findViewById(R.id.main_LBL_score);

        //set the numbers matrix
        num_IMV_rocks = new int[][]{
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1}
        };

    }

    //move jhon
    private void moveJhon() {

        main_IMG_leftArrow.setOnClickListener(v -> {
            if (currentJhonPlace != 0) {
                currentJhonPlace -= 1;
                setJhonVisibility(currentJhonPlace);
            }
        });

        main_IMG_rightArrow.setOnClickListener(view -> {
            if (currentJhonPlace != obsCol - 1) {
                currentJhonPlace += 1;
                setJhonVisibility(currentJhonPlace);
            }
        });

    }

    //move jhon left
    private void moveJhonLeft() {

        if (currentJhonPlace != 0) {
            currentJhonPlace -= 1;
            setJhonVisibility(currentJhonPlace);
        }
    }

    //move jhon right
    private void moveJhonRight() {

            if (currentJhonPlace != obsCol - 1) {
                currentJhonPlace += 1;
                setJhonVisibility(currentJhonPlace);
            }

    }


    //set visibility of jhon for arrow clicking
    private void setJhonVisibility(int nextIndex) {
        for (int i = 0; i < main_IMG_Jhon.length; i++) {
            if (i == nextIndex)
                main_IMG_Jhon[i].setVisibility(View.VISIBLE);
            else {
                main_IMG_Jhon[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    //make a toast
    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //make a vibrate
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

    //choose random number for column and image type
    private int chooseRandomWithMax(int max) {

        int randNum = 0;
        Random rand = new Random();

        do {
            randNum = rand.nextInt((max + 3));
        } while (randNum >= max);

        return randNum;
    }

    //move int row down
    private void moveRowDown() {
        for (int i = obsRow - 1; i > 0; i--) {
            for (int j = obsCol - 1; j >= 0; j--) {
                if (i == obsRow - 1 && num_IMV_rocks[i][j] != -1) {
                    checkJhon(j, num_IMV_rocks[i][j]);
                }
                num_IMV_rocks[i][j] = num_IMV_rocks[i - 1][j];
                num_IMV_rocks[i - 1][j] = -1;

            }
        }
    }


    //image has reached to the last row so we check if jhon hit an obstacle or prise
    private void checkJhon(int col, int imgType) {

        if (currentJhonPlace == col) {
            if (imgType == 0 || imgType == 1) {
                updateScore(imgType);
            } else {
                if (countLives == main_IMG_hearts.length - 1) {
                    gameOver();
                } else {
                    final MediaPlayer crashSound = MediaPlayer.create(GameActivity.this, R.raw.scream_sound);
                    crashSound.setVolume(100, 100);
                    crashSound.start();
                    vibrate();
                    toast("Watch Out!..");
                    main_IMG_hearts[countLives].setVisibility(View.INVISIBLE);
                    countLives++;
                }
            }
        }

    }

    //update score by image type
    private void updateScore(int imgType) {
        if (imgType == 0) {
            score += 20;
        } else {
            score += 40;
        }

        main_LBL_score.setText("Score: " + score);

    }

    //get random column and random image type and set on number matrix
    private void setImageTypeInFirstRow() {
        int randomCol = chooseRandomWithMax(obsCol);
        int randomType = chooseRandomWithMax(main_img_names.length);

        for (int i = 0; i < obsCol; i++) {
            num_IMV_rocks[0][i] = -1;
        }

        num_IMV_rocks[0][randomCol] = randomType;

    }

    //set image view in img matrix by numbers matrix
    private void setImageVIewInMatrix() {
        for (int i = 0; i < obsRow; i++) {
            for (int j = 0; j < obsCol; j++) {

                if (num_IMV_rocks[i][j] != -1) {
                    int imgID = getResources().getIdentifier(main_img_names[num_IMV_rocks[i][j]], "drawable", getPackageName());
                    main_IMG_obs[i][j].setImageResource(imgID);
                    main_IMG_obs[i][j].setVisibility(View.VISIBLE);
                } else {
                    main_IMG_obs[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    private void gameOver() {

        timer.cancel();

        if (sensorManager != null)
            sensorManager.unregisterListener(accSensorEventListener);

        String js = MSP.getInstance().getString("MY_DB", "");
        if (js != null) {
            myDB = new Gson().fromJson(js, MyDB.class);
        }


        myDB.addRecord(new Record().setDate(10122021).setScore(score).setMySpot(
                new MapSpot(lat, lon)));

        String json = new Gson().toJson(myDB);
        MSP.getInstance().putString("MY_DB", json);


        Intent intent = new Intent(GameActivity.this, RecordsActivity.class);
        startActivity(intent);
        finish();
    }


    //create loop of obstacles getting down
    private void moveObs() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    moveRowDown();
                    setImageTypeInFirstRow();
                    setImageVIewInMatrix();
                });
            }
        }, 0, DELAY);
    }


    private void locationPermission() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
        }
    }

    private void initSensor(String typeSen) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        if (intent.getStringExtra(SENSOR_TYPE).equals("SENS")) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            main_IMG_rightArrow.setVisibility(View.INVISIBLE);
            main_IMG_leftArrow.setVisibility(View.INVISIBLE);
        } else //SENSOR_TYPE == "BTN"
        {
           // sensorManager.unregisterListener(accSensorEventListener);
            moveJhon();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener , sensor , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
    }




}










