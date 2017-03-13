package com.example.game2048;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private int score = 0;
    private static MainActivity mainActivity = null;


    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public MainActivity() {
        mainActivity = this;
    }

    public void showScore() {
        tvScore.setText(score + "");
    }
    public void clearScore() {
        score = 0;
        showScore();
    }
    public void addScore(int s) {
        score+=s;
        showScore();
    }

    /*public static int windowWidth;
    public static int windowHeight;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.score);
        clearScore();
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView gameView = (GameView) findViewById(R.id.gameView);
                gameView.startGame();
                clearScore();
            }
        });
    }


}
