/*
-----------------
| James Andrews |
| 2110221       |
| Alpha Squad   |
-----------------
 */


import Data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Home {
    private JobCollection collection = new JobCollection();
    private AdminCollection adminCollection = new AdminCollection();

    public static void main(String[] args) {
        new Home();
    }

    public Home(){

        //load data-------
        if(loadCollection()!=null){
            collection = loadCollection();
        }

        if(loadAdmins()!=null){
            adminCollection = loadAdmins();
        }
        //----------------

        //Get what sign in type
        Scanner input = new Scanner(System.in);
        System.out.print("Are you a User or an Administrator?");
        String userType = input.nextLine();

        String email = null;
        String password = null;
        if (userType.equalsIgnoreCase("user")){

            //takes an email and gets a pre-existing object if the user is in the system
            System.out.print("Please enter your email:");
            email = input.nextLine();
            User user = collection.getUser(email);

            //if no user was found to match with the email then it will have returned null
            if (user == null){

                System.out.println("Please enter your current address:");
                String address = input.nextLine();

                System.out.println("Please enter your current phone number:");
                String phoneNumber = input.nextLine();

                System.out.println("Can you drive?");
                Boolean canDrive = input.nextLine().equalsIgnoreCase("yes");
                user = collection.createUser(email, address, phoneNumber, canDrive);
            }


            //-----------------User Homepage-----------------------//

            System.out.println("Welcome "+user.getName()+"\n\n");

            ArrayList<Role> Available = collection.getAvailableRoles();

            if (Available.size() == 0){
                System.out.println("Sorry there are no available positions at the moment.");
            } else {
                System.out.println("Our Available positions \n");

                for (int i = 0; i < Available.size(); i++) {
                    System.out.println((i + 1) + " | " + Available.get(i).getRoleName());
                }

                //Ask which role they want to apply for, and apply them for it
                System.out.println("What number role would you like to apply to?");
                int roleNumber = input.nextInt();
                applyToRole(Available.get(roleNumber - 1), user);
            }
            safeExit(collection,adminCollection);
            //----------------------END----------------------------//


        }else if (userType.equalsIgnoreCase("admin") || userType.equalsIgnoreCase("administrator")){
            boolean exists = false;

            System.out.println("Create new account?");
            if (input.nextLine().equalsIgnoreCase("yes")){
                System.out.println("What is your email");
                email = input.nextLine();

                System.out.println("What is your password");
                password = input.nextLine();

                if (adminCollection.isValid(email, password)){
                    System.out.println("Account already exists");
                }else {
                    adminCollection.addAdmin(email, password);
                }
            } else {
                while (!exists) {
                    System.out.print("Please enter your email:");
                    email = input.nextLine();
                    System.out.print("Please enter your password:");
                    password = input.nextLine();

                    exists = adminCollection.isValid(email, password);

                    if (!exists) {
                        System.out.println("Wrong credentials");
                    }

                }
            }
            Admin admin = adminCollection.getAdmin(email,password);

            //-----------------Admin Homepage-----------------------//
            System.out.println("Welcome "+admin.getEmail()+"\n\n");
            ArrayList<Role> roles = collection.getAvailableRoles();
            while (true){
                System.out.println("Would you like to view responses or create a new role? Type 0 to exit");
                String action = input.nextLine();

                if (action.equalsIgnoreCase("view")) {

                    for (Role role : roles) {
                        System.out.println(role.getRoleName());

                        Question[] questions = role.getQuestions();
                        Response[] responses = role.getResponses();

                        for (int i = 0; i < responses.length; i++) {
                            //iterates through all the questions and prints the answer given
                            for (int j = 0; j < questions.length; j++) {
                                System.out.println(questions[j].questionText() + "\n" + responses[i].getAnswer(j));
                            }
                        }
                    }

                } else if(action.equalsIgnoreCase("create")) {
                    System.out.println("\n\n\nWhat is the new role title?");
                    String title = input.nextLine();
                    Role role = new Role(title);
                    collection.addJob(role);

                    while (true) {
                        System.out.println("Add a question or type 0 to finish:");
                        String question = input.nextLine();

                        if (question.equals("0")) {
                            break;

                        } else {
                            System.out.println("Make it multiple choice?");
                            String multipleYN = input.nextLine();

                            if (multipleYN.equalsIgnoreCase("yes") || (multipleYN.equalsIgnoreCase("y"))) {
                                MultiQuestion mq = new MultiQuestion(question);

                                String option;
                                String correct;

                                while (true) {
                                    System.out.println("Add option or type 0 to end:");
                                    option = input.nextLine();

                                    if (option.equals("0")) {
                                        break;
                                    }
                                    System.out.println("Is this the correct answer?");
                                    correct = input.nextLine();
                                    mq.addAnswer(option, correct.equalsIgnoreCase("yes") || (correct.equalsIgnoreCase("y")));
                                }
                                role.addQuestion(mq);
                            } else {
                                role.addQuestion(new Question(question));
                            }
                        }
                    }
                } else if(action.equals("0")){
                    safeExit(collection,adminCollection);
                    break;
                }
            }
        }
        safeExit(collection,adminCollection);
            //-----------------------END----------------------------//
    }


    //Role application page
    private void applyToRole(Role role, User user){
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n" + user.getName() + " Application to " + role.getRoleName());
        Question[] questions = role.getQuestions();
        Response response = new Response(user);

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i].questionText());
            String userResponse = input.nextLine();
            response.addAnswer(userResponse);
            role.addResponse(response);
        }
        input.close();
    }

    private void saveCollection(JobCollection collection){
        try {
            File f = new File(System.getProperty("user.dir")+"Data1.txt");
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(collection);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in "+System.getProperty("user.dir")+"\\Data1.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private JobCollection loadCollection(){
        try {
            File f = new File(System.getProperty("user.dir")+"Data1.txt");
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            JobCollection collection = (JobCollection) in.readObject();
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
    private void saveAdmins(AdminCollection adminCollection){
        try {
            File f = new File(System.getProperty("user.dir")+"Data2.txt");
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(adminCollection);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in "+System.getProperty("user.dir")+"\\Data2.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private AdminCollection loadAdmins(){
        try {
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir")+"\\Data2.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            adminCollection = (AdminCollection) in.readObject();
            in.close();
            fileIn.close();
            return adminCollection;

        } catch (IOException i) {
            i.printStackTrace();
            //return new AdminCollection();

        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return null;
    }
    private void safeExit(JobCollection collection, AdminCollection adminCollection){
        saveAdmins(adminCollection);
        saveCollection(collection);
        System.exit(2);

    }
}

