package stationary.stationary;

public class Admin extends User {
    public Admin() {}

    public Admin(String name, String password) {
        super(name, password);
    }
    
    @Override
    public void displayRole() {
        System.out.println("\nLogin as Admin:");
    }
}
