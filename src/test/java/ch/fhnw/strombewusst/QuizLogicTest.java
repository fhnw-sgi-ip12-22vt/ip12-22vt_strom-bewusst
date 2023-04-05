package ch.fhnw.strombewusst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuizLogicTest {
    /*
    QuizLogic quiz;

    @BeforeEach
    public void setUp() {
        quiz = new QuizLogic(0);
    }

    @Test
    void checkAnswer() {
        //checkAnswer() returns only true if both choose the correct answer
        String[] correctAnswer = {"RED", "RED", "GREEN", "BLUE", "RED", "RED"};
        String[] wrongAnswer = {"BLUE", "BLUE", "BLUE", "GREEN", "BLUE", "GREEN"};

        for (int i = 0; i < 5; i++) {
            quiz.setAnswerP1(correctAnswer[i]);
            quiz.setAnswerP2(correctAnswer[i]);
            assertTrue(quiz.checkAnswer());

            quiz.setAnswerP1(correctAnswer[i]);
            quiz.setAnswerP2(wrongAnswer[i]);
            assertFalse(quiz.checkAnswer());

            quiz.incQuestNum();
        }
    }

    @Test
    void quizDone() {
        //We currently have 6 questions in total, hence we can increment the questionNum 5 times
        for (int i = 0; i < 5; i++) {
            assertFalse(quiz.quizDone());
            quiz.incQuestNum();
        }
        assertTrue(quiz.quizDone());
    }

    @Test
    void incQuestNum() {
        for (int i = 0; i < 5; i++) {
            assertEquals(quiz.getQustNum(), i);
            quiz.incQuestNum();
        }
    }

    @Test
    void resetAnswers() {
        quiz.setAnswerP1("RED");
        quiz.setAnswerP2("BLUE");
        assertEquals(quiz.getAnswerP1(), "RED");
        assertEquals(quiz.getAnswerP2(), "BLUE");

        quiz.resetAnswers();
        assertEquals(quiz.getAnswerP1(), "");
        assertEquals(quiz.getAnswerP2(), "");
    }

    @Test
    void getText() {
        String questionString = "Welche Option entspricht der Einheit Watt ?";
        String firstString = "(Newton * Meter) / Sekunde";
        String secondString = "(Sekunde * Meter) / Newton";
        String thirdString = "(Newton * Sekunde) / Meter";
        String answer = "RED";

        String[] firstQuestion = {questionString, firstString, secondString, thirdString, answer};
        for (int i = 0; i < 5; i++) {
            assertEquals(firstQuestion[i], quiz.getText(0, i));
        }
    }
     */
}