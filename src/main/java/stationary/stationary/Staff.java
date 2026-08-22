package stationary.stationary;

public class Staff extends User {

        public Staff() {}
        public Staff(String name, String password) {
            super(name, password);
        }
        
    @Override
    public void displayRole() {
        System.out.println("\nLogin as Staff:");
    }
}
