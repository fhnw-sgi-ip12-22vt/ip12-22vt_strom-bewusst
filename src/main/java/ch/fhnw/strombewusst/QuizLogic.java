package ch.fhnw.strombewusst;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class QuizLogic {
    private String questionString1 = "Wie viel Energie kann man mit \nFahrrad fahren ca. herstellen?";
    private String firstString1 = "1kwh";
    private String secondString1 = "0.2kwh";
    private String thirdString1 = "5kwh";
    private String answer1 = "GREEN";

    private String questionString2 = "Wie viel denkst du kostet der \nStrom deines Handys pro Jahr? ";
    private String firstString2 = "40 – 50 CHF";
    private String secondString2 = "2 – 2.50 CHF";
    private String thirdString2 = "ca. 150 CHF";
    private String answer2 = "GREEN";

    private String questionString3 = "Wie viel Strom denkst du \nverbraucht ein Haushalt?";
    private String firstString3 = "4000 kwh";
    private String secondString3 = "10000 kwh";
    private String thirdString3 = "99999 kwh";
    private String answer3 = "RED";

    private String questionString4 = "Wo wird alles Strom benutzt?";
    private String firstString4 = "Wasserhahn, Warm Wasser, duschen";
    private String secondString4 = "Google, Netflix, Titkok, Sony";
    private String thirdString4 = "Alle Sachen sind von Strom abhängig";
    private String answer4 = "BLUE";

    private String questionString5 = "Mit welchen mitteln kann \nStrom hergestellt werden?";
    private String firstString5 = "Gas, Öl Kohle";
    private String secondString5 = "Sonne, Wind und Wasser";
    private String thirdString5 = "Alle ressourcen";
    private String answer5 = "BLUE";

    private String questionString6 = "Wer hat bewiesen das Blitze \nelektrisch geladen sind?";
    private String firstString6 = "Benjamin Franklin";
    private String secondString6 = "Thomas Edison";
    private String thirdString6 = "Thales von Milet";
    private String answer6 = "RED";

    private String questionString7 = "Welcher dieser Sachen \nnutzt elektrische Impulse?";
    private String firstString7 = "Gehirn";
    private String secondString7 = "Besen";
    private String thirdString7 = "Feuerzeug";
    private String answer7 = "RED";

    private String questionString8 = "Welches Gerät verbraucht \nam meisten Strom?";
    private String firstString8 = "Laptop";
    private String secondString8 = "Fernseher";
    private String thirdString8 = "Alte Heizungen";
    private String answer8 = "BLUE";

    private String questionString9 = "Welches Wort, das mit \nStrom zu tun hat, gibt es nicht?";
    private String firstString9 = "Strommast";
    private String secondString9 = "Stromgewebe";
    private String thirdString9 = "Stromnetz";
    private String answer9 = "GREEN";

    private String questionString10 = "Wieso sind Fossile Energien \nwie Öl, Gas und Kohle Umweltschädlich?";
    private String firstString10 = "Weil sie verbrannt werden, \nwodurch Treibhausgase entstehen";
    private String secondString10 = "Weil sie nach der Benutzung \nin die Umwelt geworfen werden";
    private String thirdString10 = "Weil Fossile Energien zusammen \nmit dem Sauerstoff aus der Luft gefiltert werden \n und das schlecht für die Umwelt ist";
    private String answer10 = "RED";

    private String questionString11 = "Wie wird Strom erzeugt?";
    private String firstString11 = "Durch Schmutz";
    private String secondString11 = "Es wird in der Steckdose erzeugt";
    private String thirdString11 = "Durch Turbinen";
    private String answer11 = "BLUE";

    private String questionString12 = "Was ist das wichtigste \nKraftwerk der Schweiz?";
    private String firstString12 = "Kernkraftwerk";
    private String secondString12 = "Windkraftwerk";
    private String thirdString12 = "Wasserkraftwerk";
    private String answer12 = "BLUE";

    private String questionString13 = "Wie viele Wasserkraftwerke \nhat die Schweiz?";
    private String firstString13 = "20’000";
    private String secondString13 = "1’753";
    private String thirdString13 = "1’300";
    private String answer13 = "BLUE";

    private String questionString14 = "Wie entsteht Strom?";
    private String firstString14 = "Durch Bewegung";
    private String secondString14 = "Durch einen Blitz";
    private String thirdString14 = "Durch Öl, Gas, Wasser, Luft";
    private String answer14 = "RED";

    private String questionString15 = "Mit was kann man elektrische \nStrom gut vergleichen?";
    private String firstString15 = "Durch einen Fluss";
    private String secondString15 = "Mit einem Blitz";
    private String thirdString15 = "Mit einer Graphik";
    private String answer15 = "RED";

    private String questionString16 = "Aus was besteht Strom?";
    private String firstString16 = "Atomen";
    private String secondString16 = "Elektronen";
    private String thirdString16 = "Photonen";
    private String answer16 = "GREEN";

    private String questionString17 = "Welches Kraftwerk erzeugt kein Strom?";
    private String firstString17 = "Gaskraftwerk";
    private String secondString17 = "Holzkraftwerk";
    private String thirdString17 = "Luftkraftwerk";
    private String answer17 = "BLUE";

    private String questionString18 = "Welche Fähigkeit hat Strom?";
    private String firstString18 = "Laufen";
    private String secondString18 = "Leiten";
    private String thirdString18 = "Fliegen";
    private String answer18 = "GREEN";

    private String questionString19 = "Welcher Gegenstand leitet Strom?";
    private String firstString19 = "Plastik";
    private String secondString19 = "Luft";
    private String thirdString19 = "Salzwasser";
    private String answer19 = "BLUE";

    private String questionString20 = "Welche Aussage trifft zu?";
    private String firstString20 = "Früher wurde mit Windmühlen elektrischer Strom produziert";
    private String secondString20 = "Ein Wasserkraftwerk kann mit Wasser steuern wie viel Strom produziert wird";
    private String thirdString20 = "Solaranlagen sind die wichtigsten Energieträger der Schweiz ";
    private String answer20 = "GREEN";

    private String questionString21 = "Was ist der effektivste Weg, \num Strom in der Küche zu sparen?";
    private String firstString21 = "Kühlschrank schnell schliessen";
    private String secondString21 = "Kochen mit Deckel";
    private String thirdString21 = "Kühlschrank und Gefriertruhe abtauen";
    private String answer21 = "GREEN";

    private String questionString22 = "Welches Gerät nutzt am \nwenigsten Strom?";
    private String firstString22 = "Laptop";
    private String secondString22 = "Kühlschrank";
    private String thirdString22 = "Fernseher";
    private String answer22 = "RED";

    private String questionString23 = "Wieso ist Strom sparen wichtig? ";
    private String firstString23 = "Weil ich die Umwelt schone";
    private String secondString23 = "Weil ich dadurch mehr Geld ausgebe";
    private String thirdString23 = "Damit ich später mehr habe";
    private String answer23 = "RED";

    private String questionString24 = "Zu welcher Tageszeit verbraucht \ndie Schweiz am meisten Storm?";
    private String firstString24 = "Morgens";
    private String secondString24 = "Mittags";
    private String thirdString24 = "Abends";
    private String answer24 = "BLUE";

    private String questionString25 = "Wie viel Strom verbraucht \neine Person im Jahr?";
    private String firstString25 = "12.76 Megawattstuden";
    private String secondString25 = "4.93 Megawattstunden";
    private String thirdString25 = "6.8 Megawattstunden";
    private String answer25 = "BLUE";

    private String[][] quest = {
            {questionString1, firstString1, secondString1, thirdString1, answer1},
            {questionString2, firstString2, secondString2, thirdString2, answer2},
            {questionString3, firstString3, secondString3, thirdString3, answer3},
            {questionString4, firstString4, secondString4, thirdString4, answer4},
            {questionString5, firstString5, secondString5, thirdString5, answer5},
            {questionString6, firstString6, secondString6, thirdString6, answer6},
            {questionString7, firstString7, secondString7, thirdString7, answer7},
            {questionString8, firstString8, secondString8, thirdString8, answer8},
            {questionString9, firstString9, secondString9, thirdString9, answer9},
            {questionString10, firstString10, secondString10, thirdString10, answer10},
            {questionString11, firstString11, secondString11, thirdString11, answer11},
            {questionString12, firstString12, secondString12, thirdString12, answer12},
            {questionString13, firstString13, secondString13, thirdString13, answer13},
            {questionString14, firstString14, secondString14, thirdString14, answer14},
            {questionString15, firstString15, secondString15, thirdString15, answer15},
            {questionString16, firstString16, secondString16, thirdString16, answer16},
            {questionString17, firstString17, secondString17, thirdString17, answer17},
            {questionString18, firstString18, secondString18, thirdString18, answer18},
            {questionString19, firstString19, secondString19, thirdString19, answer19},
            {questionString20, firstString20, secondString20, thirdString20, answer20},
            {questionString21, firstString21, secondString21, thirdString21, answer21},
            {questionString22, firstString22, secondString22, thirdString22, answer22},
            {questionString23, firstString23, secondString23, thirdString23, answer23},
            {questionString24, firstString24, secondString24, thirdString24, answer24},
            {questionString25, firstString25, secondString25, thirdString25, answer25}
    };

    private String answerP1;
    private String answerP2;
    private int questionNum;

    //private boolean doorOpen = false;
    private boolean doorOpen = true; //for development of room 2
    private int size;

    private Set<Integer> questSet = new HashSet<>();

    public QuizLogic(int size) {
        this.size = size;
        buildSet();
        nextQuestion();
    }

    public void unlockDoor(){this.doorOpen=true;}

    public boolean checkAnswer() {
        if (!(getAnswerP1() == null) || !(getAnswerP2() == null)) {
            return getAnswerP1().equals(quest[questionNum][4]) && getAnswerP2().equals(quest[questionNum][4]);
        }
        return false;
    }

    public boolean quizDone() {return questSet.isEmpty();}

    public void nextQuestion() {
        for(int i = 0; i < quest.length; i++){
            if(questSet.remove(i)){
                questionNum = i;
                break;
            }
        }
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

    public void buildSet(){
        Random random = new Random();
        int x;
        for(int i = 0; i < size; i++){
            x = random.nextInt(quest.length);
            while(questSet.contains(x)){x=random.nextInt(quest.length);}
            questSet.add(x);
        }
    }

    public boolean getDoorOpen(){return doorOpen;}
}


