package ch.fhnw.strombewusst;

/**
 * Represents a question for our quiz puzzle.
 *
 * @param question the question
 * @param answerOptions the possible answers
 * @param correctAnswer the index of the correct answer [0-2]
 */
public record QuizQuestion(
        String question,
        String[] answerOptions,
        int correctAnswer
) {
}
