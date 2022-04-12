package Data;
import java.io.*;

public class Application {
    private JobCollection collection = new JobCollection();

    public Application () {

        User user = new User("Simon Andrews", "10 Downing Street", "0123423432", true);

        Role role = new Role("Head of Bioinformatics");
        collection.addJob(role);

        role.addQuestion(new Question("What is your favourite colour?"));
        role.addQuestion(new Question("What is your quest?"));
        MultiQuestion mq = new MultiQuestion("What does PSU stand for?");
        mq.addAnswer("Pseudo science unit",false);
        mq.addAnswer("Power supply unit",true);
        mq.addAnswer("Partially supported unit",false);
        role.addQuestion(mq);

        Response response = new Response(user);

        response.addAnswer("Blue");
        response.addAnswer("I seek the grail");
        response.addAnswer("Power supply unit");

        role.addResponse(response);

        save(collection);
        useCollection();
    }

    private void save(JobCollection collection){
        try {
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"/Data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(collection);
            out.close();
            fileOut.close();
            System.out.print("Serialized data is saved in "+System.getProperty("user.dir")+"/Data.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private JobCollection load(){
        try {
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir")+"/Data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            collection = (JobCollection) in.readObject();
            in.close();
            fileIn.close();
            return collection;

        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return null;
    }


    public void useCollection() {

        Role[] roles = collection.getRoles();

        for (Role role: roles) {
            System.out.println(role.getRoleName());

            Response [] responses = role.getResponses();

            for (Response r: responses) {
                System.out.println(r.getWhoAnswered().getName());

                Question [] questions = role.getQuestions();
                for (int q=0; q<questions.length-1; q++) {
                    System.out.println(questions[q].questionText());
                    System.out.println(r.getAnswer(q));
                }
            }
        }
    }

    public static void main(String[] args) {
        new Application();
    }
}
