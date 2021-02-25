package com.example.myfirstapp.content.math;

public class Question {
    private String question;
    private String answer;
    private String a;
    private String b;
    private String c;
    private String d;

    public Question() {
    }

    public Question(String question, String answer, String a, String b, String c, String d) {
        this.question = question;
        this.answer = answer;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSuggestedA() {
        return a;
    }

    public void setSuggestedA(String suggestedA) {
        this.a = suggestedA;
    }

    public String getSuggestedB() {
        return b;
    }

    public void setSuggestedB(String suggestedB) {
        this.b = suggestedB;
    }

    public String getSuggestedC() {
        return c;
    }

    public void setSuggestedC(String suggestedC) {
        this.c = suggestedC;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
