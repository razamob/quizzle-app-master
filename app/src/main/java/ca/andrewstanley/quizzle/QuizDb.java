package ca.andrewstanley.quizzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDb {

    // Instances for accessing the database
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    // Constants for the database
    public static final String DB_NAME = "quizzes.db";
    public static final int DB_VERSION = 1;

    // Constants for the table
    public static final String QUESTIONS_TABLE = "Questions";

    public static final String ID = "_id";
    public static final int ID_COLUMN = 0;

    public static final String QUESTION = "question";
    public static final int QUESTION_COLUMN = 1;

    public static final String CHOICE1 = "choice1";
    public static final int CHOICE1_COLUMN = 2;

    public static final String CHOICE2 = "choice2";
    public static final int CHOICE2_COLUMN = 3;

    public static final String CHOICE3 = "choice3";
    public static final int CHOICE3_COLUMN = 4;

    public static final String CHOICE4 = "choice4";
    public static final int CHOICE4_COLUMN = 5;

    public static final String ANSWER = "answer";
    public static final int ANSWER_COLUMN = 6;

    public static final String QUIZ_ID = "quizid";
    public static final int QUIZID_COLUMN = 7;

    // Create the table
    public static final String CREATE_QUESTIONS_TABLE =
            "CREATE TABLE " + QUESTIONS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QUESTION + " TEXT, " +
                    CHOICE1 + " TEXT, " +
                    CHOICE2 + " TEXT, " +
                    CHOICE3 + " TEXT, " +
                    CHOICE4 + " TEXT, " +
                    ANSWER + " TEXT, " +
                    QUIZ_ID + " TEXT)";

    public QuizDb(Context context) {
        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create the questions table
            db.execSQL(CREATE_QUESTIONS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS_TABLE);
            onCreate(db);
        }
    }

    public Question saveQuestion(Question question) {

        // Get a writeable version of the database
        database = openHelper.getWritableDatabase();

        // Values to be added to the database
        ContentValues values = new ContentValues();
        values.put(QUESTION, question.getQuestion());
        values.put(CHOICE1, question.getChoice(0));
        values.put(CHOICE2, question.getChoice(1));
        values.put(CHOICE3, question.getChoice(2));
        values.put(CHOICE4, question.getChoice(3));
        values.put(ANSWER, question.getAnswer());
        values.put(QUIZ_ID, question.getQuizId());

        // Prepare to insert record
        long dbId = database.insert(QUESTIONS_TABLE, null, values);

        // Insert record
        question.setDbId(dbId);

        // Close the database
        database.close();

        // Return the question
        return question;
    }

    public ArrayList<Question> getQuestions(String quizId) {

        // Create an ArrayList that will hold the questions
        ArrayList<Question> questions = new ArrayList<>();

        // Get readable database
        database = openHelper.getReadableDatabase();

        String select = "quizid=?";

        String[] selectArgs = new String[] {quizId};

        // Run a SELECT query WHERE quizid = quizId
        Cursor result = database.query(QUESTIONS_TABLE, null, select, selectArgs, null, null, null);

        // Loop through the results and fill up the arrayList
        while (result.moveToNext()) {
            // Get the values from the results
            String question = result.getString(QUESTION_COLUMN);
            String choice1 = result.getString(CHOICE1_COLUMN);
            String choice2 = result.getString(CHOICE2_COLUMN);
            String choice3 = result.getString(CHOICE3_COLUMN);
            String choice4 = result.getString(CHOICE4_COLUMN);
            String answer = result.getString(ANSWER_COLUMN);
            String quizid = result.getString(QUIZID_COLUMN);

            String[] choices = new String[4];
            choices[0] = choice1;
            choices[1] = choice2;
            choices[2] = choice3;
            choices[3] = choice4;

            // Add the questions to the array
            questions.add(new Question(question, choices, answer, quizid));
        }

        result.close();
        database.close();

        return questions;
    }

    public String [] getCreatedQuizzes() {
        List<String> createdQuizzes = new ArrayList<String>();

        // Get readable database
        database = openHelper.getReadableDatabase();

        // Where the quizId does not equal the premade quizzes
        String select = "quizid NOT IN(?,?,?,?,?)";

        String[] selectArgs = new String[] {"Android", "C#", "C++", "Canada", "Osmosis"};

        Cursor result = database.query(QUESTIONS_TABLE, null, select, selectArgs, null, null, null);

        while (result.moveToNext()) {
            String quizid = result.getString(QUIZID_COLUMN);

            if (!createdQuizzes.contains(quizid)) {
                // Add it to the array
                createdQuizzes.add(quizid);
            }
        }

        result.close();
        database.close();

        String[] cq = createdQuizzes.toArray(new String[0]);

        // Return the array
        return cq;
    }

    public String [] getAllQuizzes() {
        List<String> createdQuizzes = new ArrayList<String>();

        // Get readable database
        database = openHelper.getReadableDatabase();

        // Where the quizId does not equal the premade quizzes
        String select = "quizid NOT IN(?)";

        String[] selectArgs = new String[] {""};

        Cursor result = database.query(QUESTIONS_TABLE, null, select, selectArgs, null, null, null);

        while (result.moveToNext()) {
            String quizid = result.getString(QUIZID_COLUMN);

            if (!createdQuizzes.contains(quizid)) {
                // Add it to the array
                createdQuizzes.add(quizid);
            }
        }

        result.close();
        database.close();

        String[] cq = createdQuizzes.toArray(new String[0]);

        // Return the array
        return cq;
    }

    public boolean addQuestion(String question, String choice1, String choice2, String choice3, String choice4, String answer, String quizId){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question);
        contentValues.put(CHOICE1, choice1);
        contentValues.put(CHOICE2, choice2);
        contentValues.put(CHOICE3, choice3);
        contentValues.put(CHOICE4, choice4);
        contentValues.put(ANSWER, answer);
        contentValues.put(QUIZ_ID, quizId);

        long result = db.insert(QUESTIONS_TABLE, null, contentValues);
            if(result == -1){
                return false;
            }
            else
                return true;
    }
}

