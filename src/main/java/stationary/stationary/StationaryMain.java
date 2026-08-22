package stationary.stationary;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author shirl
 */

public class StationaryMain {
    
    // Main Menu ----------------------------------------------------------------------------------
    public static void displayStationaryMenu(){
        System.out.println("       ___|   |           |   _)                                   ");
        System.out.println("     \\___ \\   __|   _` |  __|  |   _ \\   __ \\    _` |   __|  |   | ");
        System.out.println("           |  |    (   |  |    |  (   |  |   |  (   |  |     |   | ");
        System.out.println("     _____/  \\__| \\__,_| \\__| _| \\___/  _|  _| \\__,_| _|    \\__, | ");
        System.out.println("                                                            ____/  ");
    }
    
    public static void displayAdminMenu(){
        displayStationaryMenu();
        System.out.println(" ======================================================================");
        System.out.println(" |                STATIONARY INVENTORY SYSTEM - ADMIN                 |");
        System.out.println(" ======================================================================");
        System.out.println(" |                        1. Product                                  |");
        System.out.println(" |                        2. Sales                                    |");
        System.out.println(" |                        3. Supplier                                 |");
        System.out.println(" |                        4. Staff Account                            |");
        System.out.println(" |                        0. Exit                                     |");
        System.out.println(" ----------------------------------------------------------------------");
    }
    
    public static void displayStaffMenu(){
        displayStationaryMenu();
        SupplierMethod.displayUserName();
        System.out.println(" ======================================================================");
        System.out.println(" |                STATIONARY INVENTORY SYSTEM - STAFF                 |");
        System.out.println(" ======================================================================");
        System.out.println(" |                        1. Product                                  |");
        System.out.println(" |                        2. Sales                                    |");
        System.out.println(" |                        3. Supplier                                 |");
        System.out.println(" |                        0. Exit                                     |");
        System.out.println(" ----------------------------------------------------------------------");
    }
    
    
    // Staff & Admin main --------------------------------------------------------------------------
    public static void adminMain(){
        Scanner scn = new Scanner (System.in);
        
        int userChoice;
        
        do {
            clearScreen();
            displayAdminMenu();
            userChoice = userChoice(scn, 0, 4);
            
            switch (userChoice){
                case 0:
                    System.out.print("Exiting System... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
                case 1:
                    clearScreen();
                    ProductMain productEntity = new ProductMain();
                    productEntity.productAdminMain(scn);
                    clearScreen();
                    break;
                case 2:
                    Sales sales = new Sales();
                    sales.salesAdminMain(scn);
                    clearScreen();
                    break;
                case 3:
                    SupplierMethod supplierMethod = new SupplierMethod();
                    supplierMethod.supplierAdminMenu();
                    clearScreen();
                    break;
                case 4:
                    SupplierMethod.showAdminMain();
                    clearScreen();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
            }
            
        }while (userChoice != 0);
    }
    
    public static void staffMain(){
        Scanner scn = new Scanner (System.in);
        
        int userChoice;
        
        do {
            clearScreen();
            displayStaffMenu();
            userChoice = userChoice(scn, 0, 3);
            
            switch (userChoice){
                case 0:
                    System.out.print("Exiting System... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
                case 1:
                    clearScreen();
                    ProductMain productEntity = new ProductMain();
                    productEntity.productStaffMain(scn);
                    clearScreen();
                    break;
                case 2:
                    Sales sales = new Sales();
                    sales.salesStaffMain(scn);
                    clearScreen();
                    break;
                case 3:
                    SupplierMethod supplierMethod = new SupplierMethod();
                    supplierMethod.supplierStaffMenu();
                    clearScreen();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
            }
            
        }while (userChoice != 0);
    }
    
    
    // Assist Method --------------------------------------------------------------------------------------------
    public static int userChoice(Scanner scn, int min, int max){
        int choice = 0;
        boolean flag = false;
        
        do {
            try{
                System.out.printf("\nEnter your choice (%d-%d) > ", min, max);
                choice = scn.nextInt();
                if (choice >= min && choice <= max){
                    flag = true;
                }
                else {
                    throw new InputMismatchException();
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter number between " + min + " and " + max + " only.");
            }
        }while(!flag);
        
        return choice;
    }
    
    public static void clearScreen() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
    
    
    // Void Main ==================================================================================================
    public static void main(String[] args) {
        SupplierMethod.loadData();
        
        SupplierMethod.showMain();
    }
}
