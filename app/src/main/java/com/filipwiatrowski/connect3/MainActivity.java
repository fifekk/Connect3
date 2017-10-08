package com.filipwiatrowski.connect3;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; //0 = yellow 1 = red
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2}; //2 means unplayed
    int[][] winningPositions = {{0,1,2}, {3,4,5},{6,7,8}, {0,3,6}, {1,4,7},{2,5,8}, {0,4,8}, {3,5,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter] == 2 && gameIsActive == true){
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if(activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }
            else{
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;

            }
            counter.animate().translationYBy(1000f).rotation(3600f).setDuration(700);
            for(int[] winningPosition : winningPositions)
            {


                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                    String winnerStr = "Red";
                    if(gameState[winningPosition[0]] == 0){
                        winnerStr = "Yellow";
                    }
                    //someone has win!
                    TextView winner = (TextView) findViewById(R.id.winnerMsg);
                    winner.setText(winnerStr + " has won!");

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                            playAgainLayout.setVisibility(View.VISIBLE);
                        }
                    }, 800);

                    gameIsActive = false;
                }else {
                    boolean gameIsOver = true;
                    for(int counterState : gameState){
                        if(counterState == 2){
                            gameIsOver = false;
                        }

                    }
                    if(gameIsOver){
                        TextView winner = (TextView) findViewById(R.id.winnerMsg);
                        winner.setText("It's a Draw!");
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                                playAgainLayout.setVisibility(View.VISIBLE);
                            }
                        }, 800);
                    }
                }
            }
        }

    }
    public void playAgain(View view){
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);
        activePlayer = 0; //0 = yellow 1 = red
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
        gameIsActive = true;
    }

}
