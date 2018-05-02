package ca.andrewstanley.quizzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQuestionsActivity extends Activity {

    EditText quizQuestion, answerOne, answerTwo, answerThree,answerFour, correctAnswer, addSubject;
    Button btnSaveQuestion,doneButton;
    RadioButton existingCat, newCat;

    QuizDb dbId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        // Initialize views
        quizQuestion = findViewById(R.id.quiz_question);
        answerOne = findViewById(R.id.answer_one);
        answerTwo = findViewById(R.id.answer_two);
        answerThree = findViewById(R.id.answer_three);
        answerFour = findViewById(R.id.answer_four);
        correctAnswer = findViewById(R.id.correct_answer);
        addSubject = findViewById(R.id.add_subject);
        btnSaveQuestion = findViewById(R.id.save_question);
        doneButton = findViewById(R.id.done_button);
        final Spinner addCat = findViewById(R.id.newCatSpinner);
        existingCat = findViewById(R.id.existingCat);
        newCat = findViewById(R.id.newCat);

        // Initialize field variable dbId
        dbId = new QuizDb(this);

        // Set OnClickListeners for the Radio Buttons
        existingCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (existingCat.isChecked()){
                    // Disable the addSubject EditText
                    addSubject.setEnabled(false);
                    // Enable the Spinner
                    addCat.setEnabled(true);
                }
            }
        });

        newCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newCat.isChecked()) {
                    // Disable the spinner
                    addCat.setEnabled(false);
                    // Enable the EditText
                    addSubject.setEnabled(true);
                }
            }
        });

        // Set OnClickListener for the save button
        btnSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (existingCat.isChecked()){
                    // Save the selected quiz
                    saveQuestion(addCat.getSelectedItem().toString());
                }
                else {
                    // Save the entered quiz
                    saveQuestion(addSubject.getText().toString());
                }

            }
        });

        // Set OnClickListener for the save button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main activity
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        // Open the quiz database
        QuizDb database = new QuizDb(getApplicationContext());

        // Get all the quizzes
        String[] quizzes = database.getAllQuizzes();

        //create instance of array adapter and fill it with quizzes
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                //pass categories array
                quizzes
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        addCat.setAdapter(adapter);
    }

    // Save the question to the appropriate quiz
    public void saveQuestion(String quiz) {
        // If fields are missing display an error
        if (quizQuestion.getText().toString().matches("") ||
                answerOne.getText().toString().matches("") ||
                answerTwo.getText().toString().matches("") ||
                answerThree.getText().toString().matches("") ||
                answerFour.getText().toString().matches("") ||
                correctAnswer.getText().toString().matches("")) {
            Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        }
        // If a quiz name has not been added display an erro
        else if (quiz.matches("")) {
            Toast.makeText(this, "To create a new quiz, please enter a name!", Toast.LENGTH_SHORT).show();
        }
        // If the correct answer does not match one of the answers display an error
        else if (!correctAnswer.getText().toString().matches(answerOne.getText().toString()) &&
                !correctAnswer.getText().toString().matches(answerTwo.getText().toString()) &&
                !correctAnswer.getText().toString().matches(answerThree.getText().toString()) &&
                !correctAnswer.getText().toString().matches(answerFour.getText().toString())) {
            Toast.makeText(this, "The correct answer must be one of the answers!", Toast.LENGTH_SHORT).show();
        }
        // Attempt to add the quiz to the database
        else {
            // Returns true if quiz is added to the database
            boolean quizAdded = dbId.addQuestion(
                    quizQuestion.getText().toString(),
                    answerOne.getText().toString(),
                    answerTwo.getText().toString(),
                    answerThree.getText().toString(),
                    answerFour.getText().toString(),
                    correctAnswer.getText().toString(),
                    quiz);

            if (quizAdded == true) {
                Toast.makeText(this, "Question saved to " + quiz, Toast.LENGTH_SHORT).show();

                // Clear all the text views
                quizQuestion.setText("");
                answerOne.setText("");
                answerTwo.setText("");
                answerThree.setText("");
                answerFour.setText("");
                correctAnswer.setText("");
                addSubject.setText("");
            } else {
                Toast.makeText(this, "Question Not Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
