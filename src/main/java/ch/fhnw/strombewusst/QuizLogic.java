package ch.fhnw.strombewusst;


import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuizLogic {
    private int answerP1;
    private int answerP2;

    //private boolean doorOpen = false;
    private boolean doorOpen = true; //for development of room 2
    private final int size;

    private final Deque<QuizQuestion> questionSet = new LinkedList<>();

    private List<QuizQuestion> questions = new ArrayList<>();

    public QuizLogic(int size) {
        this.size = size;
    }

    public void initQuestions() {
        questions = Arrays.stream(FXGL.getAssetLoader().loadJSON("json/questions.json", QuizQuestion[].class).get())
                .toList();

        buildSet();
    }

    public void unlockDoor() {
        this.doorOpen = true;
    }

    public boolean checkAnswer() {
        return getAnswerP1() == getQuestion().correctAnswer()
                && getAnswerP2() == getQuestion().correctAnswer();
    }

    public boolean quizDone() {
        return questionSet.isEmpty();
    }

    public void nextQuestion() {
        questionSet.pop();
    }

    public QuizQuestion getQuestion() {
        return questionSet.peekFirst();
    }

    public void resetAnswers() {
        answerP2 = -1;
        answerP1 = -1;
    }

    public void setAnswerP1(int answerP1) {
        this.answerP1 = answerP1;
    }

    public void setAnswerP2(int answerP2) {
        this.answerP2 = answerP2;
    }

    public int getAnswerP1() {
        return answerP1;
    }

    public int getAnswerP2() {
        return answerP2;
    }

    public int getSize() {
        return size;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public void buildSet() {
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomNum = random.nextInt(questions.size());
            while (questionSet.contains(questions.get(randomNum))) {
                randomNum = random.nextInt(questions.size());
            }

            questionSet.add(questions.get(randomNum));
        }
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }
}


