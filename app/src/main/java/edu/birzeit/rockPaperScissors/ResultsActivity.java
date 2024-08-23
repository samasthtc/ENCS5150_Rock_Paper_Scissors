package edu.birzeit.rockPaperScissors;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.results), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView winLoseText = findViewById(R.id.winLoseText);
        TextView scoreNumText = findViewById(R.id.scoreNumText);
        Button resetButton = findViewById(R.id.resetButton);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        String winLose = intent.getStringExtra("WIN_LOSE");

        winLoseText.setText(winLose);
        scoreNumText.setText(String.valueOf(score));

        resetButton.setOnClickListener(v -> finish());
    }
}
