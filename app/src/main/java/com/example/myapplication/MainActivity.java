package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0 :yellow, 1: red, 2: empty

    TextView mTv;
    Button mButton;
    GridLayout mGridLayout;

    public int counter = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0;

    public void dropIn(View view) {
        Log.d("dropIn", view.getTag().toString());
        ImageView imageView = (ImageView) view;
        int tagCounter = Integer.parseInt(view.getTag().toString());

        if (isSomeoneWin()) {
            return;
        }

        if (gameState[tagCounter] != 2) {
            Toast.makeText(this, "Choose a empty one", Toast.LENGTH_SHORT).show();
        } else {
            gameState[tagCounter] = activePlayer;
            imageView.setTranslationY(-1500);
            if (counter % 2 == 0) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
                activePlayer = 1;
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.red));
                activePlayer = 0;
            }
            imageView.animate().translationYBy(1500).setDuration(500);
            counter++;
            if (isSomeoneWin()) {
                mTv.setVisibility(View.VISIBLE);
                if (activePlayer == 1) {
                    mTv.setText("Yellow Win!!");
                } else {
                    mTv.setText("Red Win!!");
                }
            }

            if (counter == 9 && !isSomeoneWin()) {
                mTv.setVisibility(View.VISIBLE);
                mTv.setText("Tie!!!!");
            }
        }
    }

    private boolean isSomeoneWin() {
        for (int[] winStatePosition : winState) {
            if (gameState[winStatePosition[0]] == gameState[winStatePosition[1]] && gameState[winStatePosition[1]] == gameState[winStatePosition[2]]
                    && gameState[winStatePosition[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mGridLayout = (GridLayout) findViewById(R.id.gridLayout);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initGameStatus();
            }
        });
    }

    private void initGameStatus() {
        for (int i = 0; i < gameState.length; i++)
            gameState[i] = 2;
        activePlayer = 0;
        counter = 0;
        mTv.setVisibility(View.INVISIBLE);
        for (int i = 0; i < mGridLayout.getChildCount(); i++) {
            ImageView mImageView = (ImageView) mGridLayout.getChildAt(i);
            mImageView.setImageResource(0);
        }
    }
}
