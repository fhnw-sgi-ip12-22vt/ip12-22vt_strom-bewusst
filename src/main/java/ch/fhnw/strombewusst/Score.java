package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Score {
    private int score;
    private int answerSolved;

    public Score(){
        this.score = 0;
        this.answerSolved = 0;
    }

    public int getScore(){return score;}

    public int getAnswerSolved(){return answerSolved;}

    public void increaseScore(int points){
        this.score+=points;
        FXGL.inc("score",+points);
        this.answerSolved++;
    }

    public HBox pushScore(int x, int y){
        Text title = new Text(toString());
        title.setStyle("-fx-font-size: 44px;");
        HBox titleHBox1 = new HBox(title);
        titleHBox1.setTranslateX(x);
        titleHBox1.setTranslateY(y);
        return titleHBox1;
    }

    @Override
    public String toString() {
        return "Score: " + this.score;
    }
}
