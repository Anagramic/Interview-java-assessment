package Data;

public class User implements java.io.Serializable{
    private String email;
    private String address;
    private String phoneNumber;
    private boolean canDrive;

    public User (String name, String address, String phoneNumber, boolean canDrive) {
        this.email = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.canDrive = canDrive;
    }

    public String getName () {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getCanDrive() {
        return canDrive;
    }
}
