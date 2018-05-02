package ca.andrewstanley.quizzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnStartQuiz;
    Button btnAddQuestions;
    TextView txtScores;

    private QuestionBank questionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up views
        btnStartQuiz = findViewById(R.id.btn_start_quiz);
        btnAddQuestions = findViewById(R.id.btn_add_questions);
        txtScores = findViewById(R.id.txt_scores);

        // Initiliaze the default questions
        questionBank = new QuestionBank();
        questionBank.initDefaultQuestions(getApplicationContext());

        // Display the recent quizzes
        displayRecentQuizzes();

        // When Start Quiz is clicked display the categories
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCategories();
            }
        });

        // Open the Add Questions Activity when the Add Questions button is clicked
        btnAddQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addQuestionsActivity = new Intent(getApplicationContext(), AddQuestionsActivity.class);
                // Pass the selected quiz to the QuizActivity
                startActivity(addQuestionsActivity);
            }
        });
    }

    private void displayCategories() {
        // Create and display an AlertDialog with all the categories
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose a Category")
                .setIcon(R.mipmap.ic_launcher_round)
                .setItems(categories, categoryListener)
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void displayQuizzes(String [] quizzes) {
        // Create and display an AlertDialog with all the quizzes
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose a Quiz")
                .setIcon(R.mipmap.ic_launcher_round)
                .setItems(quizzes, quizListener)
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    // Create a String Array that holds all the categories
    private String[] categories = {"Computer Science", "Geography", "Biology", "Custom Quizzes"};

    // Create a String Array that will hold all the quizzes
    private String[] quizzes;

    // Listener for when a category is selected
    DialogInterface.OnClickListener categoryListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // Computer Science
                    quizzes = new String[]{"Android", "C#", "C++"};
                    break;
                case 1: // Geography
                    quizzes = new String[]{"Canada"};
                    break;
                case 2: // Biology
                    quizzes = new String[]{"Osmosis"};
                    break;
                case 3: // Created Quizzes
                    // Get the created quizzes from the database
                    QuizDb database = new QuizDb(getApplicationContext());
                    quizzes = database.getCreatedQuizzes();
                    break;
            }

            displayQuizzes(quizzes);
        }
    };

    // Listener for when a quiz is clicked
    DialogInterface.OnClickListener quizListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // Open and pass the selected quiz to the QuizActivity
            Intent quizActivity = new Intent(getApplicationContext(), QuizActivity.class);
            quizActivity.putExtra("quizId", quizzes[which]);
            startActivity(quizActivity);
        }
    };

    public void displayRecentQuizzes() {
        // Get Shared Preferences
        SharedPreferences scores = PreferenceManager.getDefaultSharedPreferences(this);

        // Create a new string builder
        StringBuilder scoresBuilder = new StringBuilder("");

        // Fill the string builder with the most recent quiz
        scoresBuilder.append("Quiz: ");
        scoresBuilder.append(scores.getString("quiz", null));
        scoresBuilder.append("   Score: ");
        scoresBuilder.append(scores.getInt("score", 0));

        String quiz = scores.getString("quiz", null);

        // Check if there is a recent quiz to display
        if (quiz == null) {
            // Display nothing
            txtScores.setText("");
        }
        else {
            // Display the recent quiz
            txtScores.setText(scoresBuilder);
        }
    }
}
