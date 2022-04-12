package Data;

import java.util.ArrayList;

public class Response implements java.io.Serializable{
    private ArrayList<String> answers = new ArrayList<String>();
    private User whoAnswered;

    public Response(User person) {
        this.whoAnswered = person;
    }

    public User getWhoAnswered () {
        return whoAnswered;
    }

    public void addAnswer(String text) {
        answers.add(text);
    }

    public String getAnswer(int questionNumber) {
        return answers.get(questionNumber);
    }
}
