package ca.andrewstanley.quizzle;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;

public class HighScoreActivity extends Activity {

    TextView txtScore;
    TextView txtHighScore;
    Button btnMenu;
    Button btnRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // Set up views
        txtScore = findViewById(R.id.txt_score);
        txtHighScore = findViewById(R.id.txt_highscore);
        btnMenu = findViewById(R.id.btn_menu);
        btnRepeat = findViewById(R.id.btn_repeat);

        // Get the quizid and score from the Quiz Activity
        Intent intent = getIntent();
        final int score = intent.getIntExtra("score",0);
        final String quizId = intent.getStringExtra("quizId");

        // Set the textview to the user's score
        txtScore.setText("Your Score: " + score);

        // Get the high score from shared preferences
        getHighScore(score, quizId);

        // Save the users score to shared preferences
        saveScore(score, quizId);

        // If the Repeat Quiz button is clicked start the quiz activity again and pass the quizId into it
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizActivity = new Intent(getApplicationContext(), QuizActivity.class);
                quizActivity.putExtra("quizId", quizId);
                startActivity(quizActivity);
            }
        });

        // If the Menu button is clicked start to Main Activity
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    public void saveScore(int score, String quizId) {
        SharedPreferences scores = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor scoresEditor = scores.edit();

        // Add the score and the quiz name to the scoresEditor
        scoresEditor.putInt("score", score)
                    .putString("quiz", quizId);

        // Save the changes
        scoresEditor.commit();
    }

    public void getHighScore(int score, String quizId) {
        SharedPreferences highScore = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor highScoreEditor = highScore.edit();

        // Retrieve the highscore
        int hs = highScore.getInt(quizId + "highscore",0);

        if(hs >= score) {
            // Set the high score textview to the existing highscore
            txtHighScore.setText("High score: "+ hs);
        }
        else {
            // Set the high score textview to the new highscore
            txtHighScore.setText("New highscore: "+ score);

            // Save the highscore to shared preferences
            highScoreEditor.putInt(quizId + "highscore", score);

            // Save the changes
            highScoreEditor.commit();
        }
    }
}

