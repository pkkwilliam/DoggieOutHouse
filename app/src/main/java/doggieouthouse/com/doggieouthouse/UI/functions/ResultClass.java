package doggieouthouse.com.doggieouthouse.UI.functions;

/**
 * Created by pkkwilliam on 4/6/18.
 */

public class ResultClass {
    private String description;
    private float score;
    public ResultClass(String description, float score){
        this.description = description;
        this.score = score;
    }
    public float getScore(){
        return score;
    }
    public String getDescription(){
        return description;
    }
}
