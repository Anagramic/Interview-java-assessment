package Data;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiQuestion extends Question implements java.io.Serializable{
    private ArrayList<String> answers = new ArrayList<String>();
    private ArrayList<Boolean> isCorrect = new ArrayList<Boolean>();

    public MultiQuestion(String questionText) {
        super(questionText);
    }

    public void addAnswer (String text, boolean isCorrect) {
        answers.add(text);
        this.isCorrect.add(isCorrect);
    }

    @Override
    public boolean hasAnswers() {
        return true;
    }

    //Writes the question and the multiple answers
    @Override
    public String questionText(){
        ArrayList<String> letters = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"));
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < this.answers().length; i++) {
            output.append(letters.get(i)).append(" ").append(this.answers.get(i)).append("\n");
        }
        return "\n\n"+output;
    }

    @Override
    public String [] answers () {
        return answers.toArray(new String[0]);
    }

    public Boolean [] wasCorrect () {
        return isCorrect.toArray(new Boolean[0]);
    }

}
