package Data;

public class Question implements java.io.Serializable{
    private String questionText = "";

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String questionText () {
        return questionText;
    }

    public boolean hasAnswers() {
        return false;
    }

    public String [] answers () {
        return null;
    }
}
