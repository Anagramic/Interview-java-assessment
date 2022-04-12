package Data;

import java.util.ArrayList;

public class Role implements java.io.Serializable{

    private String roleName = "";
    private ArrayList<Question> questions = new ArrayList<Question>();
    private ArrayList<Response> responses = new ArrayList<Response>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public Question [] getQuestions() {
        return questions.toArray(new Question[0]);
    }

    public void addQuestion (Question q) {
        questions.add(q);
    }

    public void addResponse(Response r) {
        responses.add(r);
    }

    public Response [] getResponses () {
        return responses.toArray(new Response[0]);
    }
}
