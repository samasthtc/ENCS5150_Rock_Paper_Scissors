package edu.birzeit.rockPaperScissors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    final int[] score = {0};
    final int rock = 1;
    final int paper = 2;
    final int scissors = 3;
    final int timerDuration = 5;

    ImageButton rockButton;
    ImageButton paperButton;
    ImageButton scissorsButton;
    TextView timerNumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView opponentDecisionImage = findViewById(R.id.opDecImage);
        opponentDecisionImage.setVisibility(View.INVISIBLE);
        TextView scoreNumText = findViewById(R.id.scoreNumText);

        timerNumText = findViewById(R.id.timerNumText);

        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);


        rockButton.setClickable(true);
        paperButton.setClickable(true);
        scissorsButton.setClickable(true);

        rockButton.setOnClickListener(v -> handleButtonClick(rock, opponentDecisionImage, scoreNumText));
        paperButton.setOnClickListener(v -> handleButtonClick(paper, opponentDecisionImage, scoreNumText));
        scissorsButton.setOnClickListener(v -> handleButtonClick(scissors, opponentDecisionImage, scoreNumText));
    }

    private void handleButtonClick(int playerDecision, ImageView opponentDecisionImage, TextView scoreNumText) {
        rockButton.setClickable(false);
        paperButton.setClickable(false);
        scissorsButton.setClickable(false);

        int opponentDecision = getOpponentDecision();
        int opponentDecisionSrc = getOpponentDecisionImage(opponentDecision);
        opponentDecisionImage.setImageResource(opponentDecisionSrc);
        opponentDecisionImage.setVisibility(View.VISIBLE);

        int scoreChange = getScoreChange(opponentDecision, playerDecision);
        score[0] += scoreChange;
        scoreNumText.setText(String.valueOf(score[0]));

        String feedbackText;
        if (scoreChange > 1) feedbackText = "YOU WON!";
        else if (scoreChange < 1) feedbackText = "YOU LOST!";
        else feedbackText = "TIE!";


        timerNumText.setVisibility(View.VISIBLE);
        new CountDownTimer(timerDuration * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timerNumText.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerNumText.setVisibility(View.INVISIBLE);
                try {
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("SCORE", score[0]);
                    intent.putExtra("WIN_LOSE", feedbackText);
                    startActivity(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                opponentDecisionImage.setVisibility(View.INVISIBLE);
                rockButton.setClickable(true);
                paperButton.setClickable(true);
                scissorsButton.setClickable(true);
            }
        }.start();
    }

    protected int getScoreChange(int opponentDecision, int playerDecision) {

        switch (playerDecision) {
            case rock:
                switch (opponentDecision) {
                    case scissors:
                        return 3;
                    case paper:
                        return -4;
                    default:
                        return 1;
                }
            case paper:
                switch (opponentDecision) {
                    case rock:
                        return 3;
                    case scissors:
                        return -4;
                    default:
                        return 1;
                }
            case scissors:
                switch (opponentDecision) {
                    case paper:
                        return 3;
                    case rock:
                        return -4;
                    default:
                        return 1;
                }
        }
        return 1;
    }

    protected int getOpponentDecision() {
        return (int) (Math.random() * 3) + 1;
    }

    protected int getOpponentDecisionImage(int opponentDecision) {
        switch (opponentDecision) {
            case paper:
                return R.drawable.hand_paper_op;
            case scissors:
                return R.drawable.hand_scissors_op;
            default:
                return R.drawable.hand_rock_op;
        }
    }
}
