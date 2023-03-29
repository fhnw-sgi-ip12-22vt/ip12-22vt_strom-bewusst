package ch.fhnw.strombewusst;

public record QuizQuestion(
        String question,
        String[] answerOptions,
        int correctAnswer
) {}
