package ch.fhnw.strombewusst;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Score {
    private int score;

    public Score(){
        this.score = 0;
    }

    public int getScore(){return score;}

    public void increaseScore(int points){this.score+=points;}

    public HBox pushScore(){
        Text title = new Text(toString());
        title.getStyleClass().add("message");

        HBox messageHBox1 = new HBox(title);
        messageHBox1.setTranslateX(750);
        messageHBox1.setTranslateY(25);
        return messageHBox1;
    }

    @Override
    public String toString() {
        return "Score: " + this.score;
    }
}
