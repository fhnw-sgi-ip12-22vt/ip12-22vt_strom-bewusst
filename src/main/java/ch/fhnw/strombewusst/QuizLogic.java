package ch.fhnw.strombewusst;


public class QuizLogic {
    private String questionString1 = "Welche Option entspricht der Einheit Watt ?";
    private String firstString1 = "(Newton * Meter) / Sekunde";
    private String secondString1 = "(Sekunde * Meter) / Newton";
    private String thirdString1 = "(Newton * Sekunde) / Meter";
    private String answer1 = "RED";

    private String questionString2 = "Was verbraucht am meisten Strom von diesen 4 Haushaltsgeräten? ";
    private String firstString2 = "Wasserkocher";
    private String secondString2 = "Haarföhn";
    private String thirdString2 = "Handy laden";
    private String answer2 = "RED";

    private String questionString3 =
            "Warum ist der Stromverbrauch in einer Wohnung im Mehrfamilienhaus  niedriger als im Einfamilienhaus?";
    private String firstString3 = "Weil in Mehrfamilienhäuser die Dusche geteilt wird.";
    private String secondString3 = "Weil die Nebenkosten bei Mehrfamilienhäuser auf alle Mieter verteilt werden.";
    private String thirdString3 = "Weil Einfamilienhäuser im Durchschnitt mehr Haushältgeräte besitzen.";
    private String answer3 = "GREEN";
    private String questionString4 = "Wann in der Woche ist der Strom am günstigsten?";
    private String firstString4 = "Freitag um 19:00";
    private String secondString4 = "Samstag um 12:00";
    private String thirdString4 = "Es gibt günstigere Tage";
    private String answer4 = "BLUE";

    private String questionString5 =
            "Wieviel kostet der Strom in einem Jahr bei einer Familie, die 3’500kwH in diesem Jahr verbraucht hat?";
    private String firstString5 = "735 Franken";
    private String secondString5 = "835 Franken";
    private String thirdString5 = "635 Franken";
    private String answer5 = "RED";

    private String questionString6 = "Geschirrspülmaschine oder von Hand spülen: Was spart mehr Strom?";
    private String firstString6 = "Geschirrspülmaschine";
    private String secondString6 = "Von Hand spülen";
    private String thirdString6 = "Beide verbrauchen gleichviel Strom";
    private String answer6 = "RED";
    private String[][] quest = {
            {questionString1, firstString1, secondString1, thirdString1, answer1},
            {questionString2, firstString2, secondString2, thirdString2, answer2},
            {questionString3, firstString3, secondString3, thirdString3, answer3},
            {questionString4, firstString4, secondString4, thirdString4, answer4},
            {questionString5, firstString5, secondString5, thirdString5, answer5},
            {questionString6, firstString6, secondString6, thirdString6, answer6}
    };

    private String answerP1;
    private String answerP2;
    private int questionNum;

    public QuizLogic(int questionNum) {
        this.questionNum = questionNum;
    }

    public boolean checkAnswer() {
        return getAnswerP1().equals(quest[questionNum][4]) && getAnswerP2().equals(quest[questionNum][4]);
    }

    public boolean quizDone() {
        return questionNum > quest.length - 2;
    }

    public void incQuestNum() {
        this.questionNum++;
    }

    public void resetAnswers() {
        answerP2 = "";
        answerP1 = "";
    }

    public String getText(int question, int num) {
        return quest[question][num];
    }

    public void setAnswerP1(String answerP1) {
        this.answerP1 = answerP1;
    }

    public void setAnswerP2(String answerP2) {
        this.answerP2 = answerP2;
    }

    public String getAnswerP1() {
        return answerP1;
    }

    public String getAnswerP2() {
        return answerP2;
    }

    public int getQustNum() {
        return questionNum;
    }
}


