package Data;

import java.util.ArrayList;

public class AdminCollection implements java.io.Serializable{

    private ArrayList<Admin> admins = new ArrayList<Admin>();

    public Admin [] getAdmins(){
        return admins.toArray(new Admin[0]);
    }

    public void addAdmin(String email, String password){
        admins.add(new Admin(email,password));
    }

    public Boolean isValid(String email, String password){

        for (int i = 0; i < admins.size(); i++) {

            if (admins.get(i).isValid(email,password)){
                return true;

            }
        }
        return false;
    }

    public Admin getAdmin(String email, String password){
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).isValid(email, password)){
                return admins.get(i);
            }
        }
        return null;
    }
}
