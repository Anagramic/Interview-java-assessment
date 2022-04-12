package Data;

public class Admin implements java.io.Serializable{
    private String email;
    private String password;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isValid(String email, String password){
        if (email.equals(this.email) & (password.equals(this.password))){
            return true;
        }
        return false;
    }
}
