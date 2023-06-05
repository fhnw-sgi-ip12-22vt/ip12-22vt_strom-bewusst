package ch.fhnw.strombewusst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizLogicTest {
    QuizLogic quiz;
    List<QuizQuestion> questions = List.of(
            new QuizQuestion("Q1", new String[]{"A1.1", "A1.2", "A1.3"}, 0),
            new QuizQuestion("Q2", new String[]{"A2.1", "A2.2", "A2.3"}, 1),
            new QuizQuestion("Q3", new String[]{"A3.1", "A3.2", "A3.3"}, 2),
            new QuizQuestion("Q4", new String[]{"A4.1", "A4.2", "A4.3"}, 0),
            new QuizQuestion("Q5", new String[]{"A5.1", "A5.2", "A5.3"}, 1)
    );

    @BeforeEach
    public void setUp() {
        quiz = new QuizLogic(4);
        quiz.setQuestions(questions);

        quiz.buildSet();
    }

    @Test
    void checkAnswerCorrect() {
        int correctAnswer = quiz.getQuestion().correctAnswer();

        quiz.setAnswerP1(correctAnswer);
        quiz.setAnswerP2(correctAnswer);

        assertTrue(quiz.checkAnswer());
    }

    @Test
    void checkAnswerIncorrect() {
        int incorrectAnswer = quiz.getQuestion().correctAnswer() == 0 ? 1 : 0;

        quiz.setAnswerP1(incorrectAnswer);
        quiz.setAnswerP2(incorrectAnswer);

        assertFalse(quiz.checkAnswer());
    }

    @Test
    void quizDone() {
        assertFalse(quiz.quizDone());
        quiz.nextQuestion();
        assertFalse(quiz.quizDone());
        quiz.nextQuestion();
        assertFalse(quiz.quizDone());
        quiz.nextQuestion();
        assertFalse(quiz.quizDone());
        quiz.nextQuestion();
        assertTrue(quiz.quizDone());
    }

    @Test
    void resetAnswers() {
        quiz.setAnswerP1(1);
        quiz.setAnswerP2(1);
        assertEquals(quiz.getAnswerP1(), 1);
        assertEquals(quiz.getAnswerP2(), 1);

        quiz.resetAnswers();
        assertEquals(quiz.getAnswerP1(), -1);
        assertEquals(quiz.getAnswerP2(), -1);
    }
}
