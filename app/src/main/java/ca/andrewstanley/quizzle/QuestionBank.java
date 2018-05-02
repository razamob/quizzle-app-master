package ca.andrewstanley.quizzle;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    //Declare list of question Objects
    List<Question> questionList = new ArrayList<>();
    QuizDb database;

    //returns number of questions in list
    public int getLength() { return questionList.size(); }

    //method returns question from list based on list index
    public String getQuestion(int a)
    { return questionList.get(a).getQuestion();}

    //method returns single mc item based on list index
    //based on number of multiple choice items in list (4)
    //1,2,3,4 as argument
    public String getChoice(int index , int num)
    { return questionList.get(index).getChoice(num - 1);}

    //returns correct answer
    public String getCorrectAnswer(int a)
    { return questionList.get(a).getAnswer();}

    public void initQuestions(Context context, String quizId){
        // Create an instance of the Quiz Database
        database = new QuizDb(context);
        //get questions from database
        questionList = database.getQuestions(quizId);
    }

    public void initDefaultQuestions(Context context) {
        // Create an instance of the Quiz Database
        database = new QuizDb(context);

        // Pull a list of Android questions from the database to test
        List<Question> allQuestions = database.getQuestions("Android");//get questions/choices/answers from db

        // If the there is no questions in the database, fill it with questions
        if(allQuestions.isEmpty()) {
            database.saveQuestion(new Question("1. How are integers declared?", new String[]{"integer", "float", "int", "double"}, "int", "Android"));
            database.saveQuestion(new Question("2. What is an Activity in Android?", new String[]{"Performs the actions on screen", "Manage Application Content", "Screen UI", "None of the above"}, "Performs the actions on screen", "Android"));
            database.saveQuestion(new Question("3. What is ADB in android?", new String[]{"Image Tool", "Development Tool", "Android Debug Bridge", "None of the above"}, "Android Debug Bridge", "Android"));
            database.saveQuestion(new Question("4. In which technique, can we refresh the dynamic content in android?", new String[]{"Java", "Android", "Ajax", "None of the above"}, "Ajax", "Android"));
            database.saveQuestion(new Question("4. Which company developed android?", new String[]{"Apple", "Android Inc", "Google", "Nokia"}, "Android Inc", "Android"));

            // Create C# Questions
            database.saveQuestion(new Question("1. Which of the following converts a type to a 32bit integer?", new String[]{"ToDecimal", "ToDouble", "ToInt16", "ToInt32"}, "ToInt32", "C#"));
            database.saveQuestion(new Question("2. Which of the following operator creates a pointer to a variable in C#?", new String[]{"sizeof", "typeof", "&", "*"}, "*", "C#"));
            database.saveQuestion(new Question("3. Which of the following method helps in returning more than one value?", new String[]{"Value parameters", "Reference parameters", "Output parameters", "None of the above"}, "Output parameters", "C#"));
            database.saveQuestion(new Question("4. Which of the following preprocessor directive allows testing a symbol or symbols to see if they evaluate to true in C#?", new String[]{"Define", "Undef", "If", "Elif"}, "if", "C#"));
            database.saveQuestion(new Question("5. Which of the followings is not allowed in C# as access modifier?", new String[]{"Public", "Friend", "Internal", "Protected"}, "Friend", "C#"));

            // Create C++ Questions
            database.saveQuestion(new Question("1. Which of the following converts a type to a 64bit integer?", new String[]{"ToInt64", "ToDouble", "ToDecimal", "ToInt32"}, "ToInt64", "C++"));
            database.saveQuestion(new Question("2. Which data type can be used to hold a wide character in C++?", new String[]{"Unsigned Char", "Int", "Wchar_t", "None of the above"}, "Wchar_t", "C++"));
            database.saveQuestion(new Question("3. Which feature of the OOPS gives the concept of reusability?", new String[]{"Encapsulation", "Inheritance", "Abstraction", "None of the above"}, "Inheritance", "C++"));
            database.saveQuestion(new Question("4. What is a generic class?", new String[]{"Function Template", "Class Template", "Inherited Class", "None of the above"}, "Class Template", "C++"));
            database.saveQuestion(new Question("5. C++ does not support the following", new String[]{"Multilevel inheritance", "Hierarchical inheritance", "Hybrid inheritance", "None of the above"}, "None of the above", "C++"));

            // Create Canadian Geography Questions
            database.saveQuestion(new Question("1. What is the capital of Ontario?", new String[]{"London","Brampton","Ajax","Toronto"}, "Toronto", "Canada"));
            database.saveQuestion(new Question("2. Name the only Canadian province or territory with no natural features defining its borders.", new String[]{"Saskatchewan", "Manitoba", "Prince Edward Island", "The Yukon"}, "Saskatchewan", "Canada"));
            database.saveQuestion(new Question("3. The worldís longest covered bridge is in Canada. Where?", new String[]{"North Vancouver, B.C.", "West Montrose, Ont.", "Wakefield, Que.", "Hartland, N.B."}, "Hartland, N.B.", "Canada"));
            database.saveQuestion(new Question("4. Canada has the worldís longest coastline. In fact, it has about 25 per cent of all coastline on Earth. How long is it in kilometres? ", new String[]{"40,075", "54,716", "243,792", " 384,403"}, "243,792", "Canada"));
            database.saveQuestion(new Question("5. About 85 per cent of Canadaís population lives within 300 kilometres of its southern border. Roughly how much of the countryís freshwater is available to this vast majority of Canadians?", new String[]{"40%", "60%", "80%", "22%"}, "40%", "Canada"));

            // Create Chemistry Questions
            database.saveQuestion(new Question("1. What is movement in diffusion based on?", new String[]{"Speed of molecules","No movement","Kinetic energy, charge and mass of molecules","None of the above"}, "Kinetic energy, charge and mass of molecules", "Osmosis"));
            database.saveQuestion(new Question("2. What does permeable mean?", new String[]{"Things can pass through", "Different concentration levels", "Permanent", "Things are stuck"}, "Things can pass through", "Osmosis"));
            database.saveQuestion(new Question("3. What is diffusion?", new String[]{"When molecules move", "Molecules move from high concentration to low", "No movement", "Molecules move everywhere"}, "Molecules move from high concentration to low", "Osmosis"));
            database.saveQuestion(new Question("4. Osmosis is the movement of _____ across a membrane.", new String[]{"Food", "Energy", "Oxygen", "Water"}, "Water", "Osmosis"));
            database.saveQuestion(new Question("5. The diffusion of water through a semi-permeable membrane is called?", new String[]{"Osmosis", "Cell Wall", "Diffusion", "Respiration"}, "Osmosis", "Osmosis"));
        }
    }
}
