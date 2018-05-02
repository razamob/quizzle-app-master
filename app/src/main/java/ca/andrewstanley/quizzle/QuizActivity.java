package ca.andrewstanley.quizzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends Activity {

    private TextView txtScore;
    private TextView txtQuestion;
    private Button btnChoice1;
    private Button btnChoice2;
    private Button btnChoice3;
    private Button btnChoice4;
    private Button btnNext;

    private QuestionBank questionBank;
    String quizId;
    private String answer;
    private int score;
    private int questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //setup views
        txtScore = findViewById(R.id.txt_score);
        txtQuestion = findViewById(R.id.txt_question);
        btnChoice1 = findViewById(R.id.btn_choice1);
        btnChoice2 = findViewById(R.id.btn_choice2);
        btnChoice3 = findViewById(R.id.btn_choice3);
        btnChoice4 = findViewById(R.id.btn_choice4);
        btnNext = findViewById(R.id.btn_next);

        // Initialize field variables
        questionBank = new QuestionBank();
        score = 0;
        questionNumber = 0;

        // Get and store the quizId passed from the main activity
        Intent mainActivity = getIntent();
        if (mainActivity != null) {
            quizId = mainActivity.getStringExtra("quizId");
        }

        // Initialize all the questions
        questionBank.initQuestions(getApplicationContext(), quizId);

        // Move to the next question
        nextQuestion();

        // Update the score
        updateScore(score);

        // Set the OnClickListeners for the button choices
        btnChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });

        btnChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });

        btnChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });

        btnChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });

        // Listener for the Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();

                // Enable the buttons
                btnChoice1.setClickable(true);
                btnChoice2.setClickable(true);
                btnChoice3.setClickable(true);
                btnChoice4.setClickable(true);

                // Reset the buttons to their default color and appearence
                btnChoice1.setBackgroundResource(android.R.drawable.btn_default);
                btnChoice2.setBackgroundResource(android.R.drawable.btn_default);
                btnChoice3.setBackgroundResource(android.R.drawable.btn_default);
                btnChoice4.setBackgroundResource(android.R.drawable.btn_default);
            }
        });
    }

    private void nextQuestion() {
        // Make sure their is another question
        if (questionNumber < questionBank.getLength()){
            // Set the Text Views for new question
            txtQuestion.setText(questionBank.getQuestion(questionNumber));
            btnChoice1.setText(questionBank.getChoice(questionNumber,1));
            btnChoice2.setText(questionBank.getChoice(questionNumber,2));
            btnChoice3.setText(questionBank.getChoice(questionNumber,3));
            btnChoice4.setText(questionBank.getChoice(questionNumber,4));

            // Set the correct answer
            answer = questionBank.getCorrectAnswer(questionNumber);

            questionNumber++;
        }

        // If there is not another question start the High Score Activity and pass the score and quizId to it
        else {
            Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("quizId", quizId);
            startActivity(intent);
        }
    }

    private void updateScore(int point) {
        txtScore.setText(""+score+"/"+questionBank.getLength());
    }

    // Check if the answer is correct
    public void checkAnswer(View view){
        // The selected button
        Button btnSelected = (Button) view;

        int colorCorrect;
        int colorIncorrect;

        // Fill the color variables based on the SDK version
        if (Build.VERSION.SDK_INT < 23) {
            colorCorrect = getResources().getColor(R.color.colorGreen);
            colorIncorrect = getResources().getColor(R.color.colorRed);
        }
        else {
            colorCorrect = getResources().getColor(R.color.colorGreen);
            colorIncorrect = getResources().getColor(R.color.colorRed);
        }

        // If the answer is correct increase the score
        if(btnSelected.getText().equals(answer)){
            btnSelected.setBackgroundColor(colorCorrect);
            score = score + 1;
        }
        else {
            btnSelected.setBackgroundColor(colorIncorrect);
        }

        // Disable the buttons clickability
        btnChoice1.setClickable(false);
        btnChoice2.setClickable(false);
        btnChoice3.setClickable(false);
        btnChoice4.setClickable(false);

        //show total score for user
        updateScore(score);
    }
}
