package stationary.stationary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.*;

public class SupplierMethod {
    public static final List<Admin> AllAdmin = new ArrayList<>();
    public static final List<Supplier> AllSupplier = new ArrayList<>();
    public static final List<Staff> AllStaff = new ArrayList<>();
    public static final Scanner sc = new Scanner(in);
    public static User loginUser;

    
    // Login  --------------------------------------------------------------------------------------------------------------------------------------------
    static {
        Admin a = new Admin();
        a.setPassword("88888888");
        a.setName("admin");
        AllAdmin.add(a);
    }

    public static void loadData() {
        loadStaffData();
        loadSupplierData();
    }

    private static void loadStaffData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("staff.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming staff data is stored as "name,password"
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Staff staff = new Staff(parts[0], parts[1]); // Adjust constructor as needed
                    AllStaff.add(staff);
                } else {
                    out.println("Invalid staff data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadSupplierData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("supplier.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Trim the line to avoid issues with leading/trailing whitespace
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Assuming supplier data is stored as "companyName,phone,email,state,supplierId"
                String[] parts = line.split(",");

                // Check if the line has exactly 5 parts
                if (parts.length == 5) {
                    Supplier supplier = new Supplier(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    AllSupplier.add(supplier);
                } else {
                    System.err.println("Invalid supplier data: " + line + " (Expected 5 parts, got " + parts.length + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAdminMain(){
        while (true) {
            StationaryMain.clearScreen();
            out.println(" \nWelcome to Account Management! ");
            out.println("===================================================");
            out.println("|           Account Management - ADMIN            |");
            out.println("===================================================");
            out.println("|            1. Register new staff                |");
            out.println("|            2. Display all staff                 |");
            out.println("|            0. Exit                              |");
            out.println("----------------------------------------------------");
            out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch(choice) {
                case "1":
                    registerStaff();
                    break;
                case "2":
                    displayAllStaff();
                    break;
                case "0":
                    out.print("Exiting Account Management... [Press enter to continue]");
                    sc.nextLine(); // Clear the buffer
                    return;
                default:
                    out.println("Invalid choice.  Please enter a number between 0 and 2.");
            }
            out.println();
        }
    }

    public static void showMain() {
        while (true) {
            StationaryMain.clearScreen();
            StationaryMain.displayStationaryMenu();
            out.println();
            out.println("============ Welcome to the Stationary Inventory System ============");
            out.println("                        1. Staff");
            out.println("                        2. Administrator");
            out.println("                        0. Exit");
            out.println("--------------------------------------------------------------------");
            out.print("Please enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    stafflogin();
                    break;
                case "2":
                    adminlogin();
                    break;
                case "0":
                    out.println("Thank you for using the Stationary Inventory System. Goodbye!");
                    exit(0);
                default:
                    out.println("Invalid choice. Please enter a number between 0 and 2.");
            }
            out.println();
        }
    }

    private static void registerStaff() {
        StationaryMain.clearScreen();
        out.println("=============== Register Staff ===============");
        out.print("Enter Account Name: ");
        String name = sc.nextLine();

        if (isStaffNameExists(name)) {
            out.println(name + " is already in the system.");
            return;
        }

        String password = "";
        while (true) {
            out.print("Enter Password: ");
            password = sc.nextLine();
            out.print("Password confirmation: ");
            String passwordConfirmation = sc.nextLine();

            if (password.equals(passwordConfirmation)) {
                break;
            } else {
                out.println("Passwords do not match");
            }
        }
        Staff account = new Staff(name, password);
        AllStaff.add(account);
        // Write new staff data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staff.txt", true))) {
            writer.write(name + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Staff account registered successfully.");
        System.out.print("\n[Press enter to continue...]");
        sc.nextLine(); // Clear the buffer

    }

    public static void adminlogin() {
        while (true) {
            Admin admin = new Admin();
            admin.displayRole();
            out.print("Enter admin username: ");
            String name = sc.nextLine();
            out.print("Enter password: ");
            String password = sc.nextLine();
            User u = getAdminByLoginName(name);
            if (u != null) {
                if (u.getPassword().equals(password)) {
                    StationaryMain.adminMain();
                    return;
                } else {
                    out.println("Incorrect password");
                }
            } else {
                out.println("Incorrect username");
                return;
            }
        }
    }

    public static void stafflogin() {
        while (true) {
            Staff staff = new Staff();
            staff.displayRole();
            out.print("Enter staff username: ");
            String name = sc.nextLine();
            out.print("Enter password: ");
            String password = sc.nextLine();
            User u = getStaffByLoginName(name);
            if (u != null) {
                if (u.getPassword().equals(password)) {
                    loginUser = u;
                    StationaryMain.staffMain();
                    return;
                } else {
                    out.println("Incorrect password");
                }
            } else {
                out.println("Incorrect username");
                return;
            }
        }
    }
    
    public static void displayUserName(){
        out.println("\n [ Welcome back ! User " + loginUser.getName() + " ]");
    }

    
    // Supplier --------------------------------------------------------------------------------------------------------------------------------------------
    public void supplierAdminMenu() {
        while (true) {
            StationaryMain.clearScreen();
            out.println(" \nWelcome to Supplier Management System! ");
            out.println("====================================================");
            out.println("|           Supplier Management - ADMIN            |");
            out.println("====================================================");
            out.println("|           1. View all suppliers                  |");
            out.println("|           2. Add new supplier                    |");
            out.println("|           3. Edit supplier details               |");
            out.println("|           4. Remove supplier                     |");
            out.println("|           0. Exit                                |");
            out.println("----------------------------------------------------");
            out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    displaySupplier();
                    break;
                case "2":
                    addSupplier();
                    break;
                case "3":
                    editSupplier();
                    break;
                case "4":
                    removeSupplier();
                    break;
                case "0":
                    out.print("Exiting Supplier Management... [Press enter to continue]");
                    sc.nextLine(); // Clear the buffer
                    return;
                default:
                    out.println("Invalid choice. Please enter a number between 0 and 4.");
            }
            out.println();
        }
    }
    
    public void supplierStaffMenu() {
        while (true) {
            StationaryMain.clearScreen();
            out.println(" \nWelcome to Supplier Management System! ");
            out.println("====================================================");
            out.println("|           Supplier Management - STAFF            |");
            out.println("====================================================");
            out.println("|           1. View all suppliers                  |");
            out.println("|           2. Add new supplier                    |");
            out.println("|           3. Edit supplier details               |");
            out.println("|           4. Remove supplier                     |");
            out.println("|           0. Exit                                |");
            out.println("----------------------------------------------------");
            out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    displaySupplier();
                    break;
                case "2":
                    addSupplier();
                    break;
                case "0":
                    out.print("Exiting Supplier Management... [Press enter to continue]");
                    sc.nextLine(); // Clear the buffer
                    return;
                default:
                    out.println("Invalid choice. Please enter a number between 0 and 4.");
            }
            out.println();
        }
    }

    private static void saveAllSuppliersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("supplier.txt"))) {
            for (Supplier supplier : AllSupplier) {
                writer.write(supplier.getCompanyName() + "," + supplier.getPhone() + "," + supplier.getEmail() + "," +
                        supplier.getState() + "," + supplier.getSupplierId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void editSupplier() {
        if (AllSupplier.size() == 0) {
            out.println("There are no supplier in the system");
            return;
        }
        displaySupplier();
        out.println("=============== Edit Supplier ===============");
        while (true) {
            out.print("Enter Supplier ID: ");
            String id = sc.nextLine();
            Supplier s = getSupplierById(id);
            if (s != null) {
                out.print("Enter new Company Name: ");
                String companyName = sc.nextLine();
                out.print("Enter new Company Phone Number: ");
                String phone = sc.nextLine();
                out.print("Enter new Company Email: ");
                String email = sc.nextLine();
                out.print("Enter new Company State: ");
                String state = sc.nextLine();
                s.setCompanyName(companyName);
                s.setPhone(phone);
                s.setEmail(email);
                s.setState(state);
                out.println("Supplier '" + s.getSupplierId() + "' has been edit successfully.");
                saveAllSuppliersToFile();
                out.println("Supplier details updated successfully.");
                out.print("Do you have any more suppliers to edit? (y/n): ");
                String answer = sc.nextLine();
                switch (answer) {
                    case "y":
                        break;
                    default:
                        out.println("Exiting...");
                        return;
                }
            }else {
                out.println("Supplier '" + id + "' not found, please make sure you has enter right Supplier ID.");
                break;
            }
        }
    }

    private static void removeSupplier() {
        if (AllSupplier.size() == 0) {
            out.println("There are no supplier in the system");
            return;
        }
        displaySupplier();
        out.println("=============== Remove Supplier ===============");
        while (true) {
            out.print("Enter Supplier ID: ");
            String id = sc.nextLine();
            Supplier s = getSupplierById(id);
            if (s != null) {
                AllSupplier.remove(s);
                out.println("Supplier '" + s.getCompanyName() + "' has been removed successfully.");
                out.print("Do you have any more suppliers to remove? (y/n): ");
                String answer = sc.nextLine();
                switch (answer) {
                    case "y":
                        break;
                    default:
                        out.println("Exiting...");
                        return;
                }
            }else {
                out.println("Supplier '" + id + "' not found, please make sure you has enter right Supplier ID.");
                break;
            }
        }
    }

    public static Supplier getSupplierById(String id) {
    for (Supplier supplier : AllSupplier) {
        if(supplier.getSupplierId().equals(id)) {
            return supplier;
        }
    }
    return null;
}

    private static void addSupplier() {
    out.println("=============== Add New Supplier ===============");
    out.print("Enter Company Name: ");
    String companyName = sc.nextLine();


        if (isSupplierNameExists(companyName)) {
            out.println(companyName + " is already in the system.");
            return;
        }
    out.print("Enter Company Phone Number: ");
    String phone = sc.nextLine();
    out.print("Enter Company Email: ");
    String email = sc.nextLine();
    out.print("Enter Company State: ");
    String state = sc.nextLine();
    String supplierId = createSupplierId();
    out.println("Supplier ID is: "+supplierId);
    Supplier newSupplier = new Supplier(companyName, phone , email, state, supplierId);
    AllSupplier.add(newSupplier);
        // Write new supplier data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("supplier.txt", true))) {
            writer.write(companyName + "," + phone + "," + email + "," + state + "," + supplierId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    out.println("Supplier added successfully.");
}

    private static void displaySupplier() {
        StationaryMain.clearScreen();
        if(AllSupplier.size() == 0) {
            out.println("There are no supplier in the system");
            return;
        }
        out.println("====================================================================== Supplier List =======================================================================");
        out.println();
        out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        out.printf("%-30s %-15s %-45s %-25s %-15s%n", "Company Name", "Phone", "Email", "State", "Supplier ID");
        out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Supplier supplier : AllSupplier) {
            out.printf("%-30s %-15s %-45s %-25s %-15s%n",
                    supplier.getCompanyName(),
                    supplier.getPhone(),
                    supplier.getEmail(),
                    supplier.getState(),
                    supplier.getSupplierId());
        }

        out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        out.println();
        
        System.out.print("\n[Press enter to continue...]");
        sc.nextLine(); // Clear the buffer
    }

    public static void displayAllStaff() {
        StationaryMain.clearScreen();
        
        if(AllSupplier.size() == 0) {
            out.println("There are no staff in the system");
            return;
        }
        
        out.println("=============== List of Staff ===============");
        out.println();
        out.println("---------------------------------------------");
        out.printf("%-15s %-15s%n", "Staff Name", "Password");
        out.println("---------------------------------------------");

        for (Staff staff : AllStaff) {
            out.printf("%-15s %-15s%n", staff.getName(), staff.getPassword());
        }

        out.println("---------------------------------------------");
        out.println();
        
        System.out.print("\n[Press enter to continue...]");
        sc.nextLine(); // Clear the buffer
    }

    private static boolean isStaffNameExists(String name) {
        for (Staff staff : AllStaff) {
            if (staff.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSupplierNameExists(String companyName) {
        for (Supplier supplier : AllSupplier) {
            if (supplier.getCompanyName().equalsIgnoreCase(companyName)) {
                return true;
            }
        }
        return false;
    }

    public static Staff getStaffByLoginName(String name) {
        for(Staff staff : AllStaff) {
            if(staff.getName().equals(name)){
                return staff;
            }
        }
        return null;
    }

    public static Admin getAdminByLoginName(String name) {
        for(Admin admin  : AllAdmin) {
            if(admin.getName().equals(name)){
                return admin;
            }
        }
        return null;
    }

    public static String createSupplierId() {
        while (true) {
            String supplierId = "";
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                supplierId += r.nextInt(10);
            }

            Supplier supplier = getSupplierBySupplierId(supplierId);
            if (supplier == null) {
                return supplierId;
            }
        }
    }

    public static Supplier getSupplierBySupplierId(String supplierId) {
        for (int i = 0; i < AllSupplier.size(); i++) {
            Supplier supplier = AllSupplier.get(i);
            if (supplier.getSupplierId().equals(supplierId)) {
                return supplier;
            }
        }
        return null;
    }
}
