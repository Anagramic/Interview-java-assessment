package Data;

import java.io.*;
import java.util.ArrayList;

public class JobCollection implements java.io.Serializable {

    private ArrayList<Role> roles = new ArrayList<Role>();

    public Role [] getRoles() {
        return roles.toArray(new Role[0]);
    }

    public void addJob (Role r) {
        roles.add(r);
    }

    public User getUser(String email){
        Role[] roles = this.getRoles();

        for (Role role: roles) {
            Response[] responses = role.getResponses();
            for (Response r : responses) {
                if (r.getWhoAnswered().getName().equalsIgnoreCase(email)){
                    return r.getWhoAnswered();
                }
            }
        }
        return null;
    }

    public User createUser(String email, String address, String phoneNumber, Boolean canDrive){
        return new User(email, address, phoneNumber, canDrive);
    }

    public ArrayList<Role> getAvailableRoles(){
        return roles;
    }
}
