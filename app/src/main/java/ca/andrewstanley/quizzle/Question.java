package ca.andrewstanley.quizzle;

import android.os.Bundle;

public class Question {
    private String question;
    private String[] choice = new String[4];
    private String answer;
    private String quizId;
    private long dbId;

    public Question() {

    }

    public Question(String question, String[] choice, String answer, String quizId) {
        this.question = question;
        this.choice[0] = choice[0];
        this.choice[1] = choice[1];
        this.choice[2] = choice[2];
        this.choice[3] = choice[3];
        this.answer = answer;
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoice(int i) {
        return choice[i];
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuizId() { return quizId; }

    public long getDbId() { return dbId; }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setChoice(int i, String choice) {
        this.choice[i] = choice;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuizId(String quizId) { this.quizId = quizId; }

    public void setDbId(long dbId) { this.dbId = dbId; }
}
