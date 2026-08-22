package stationary.stationary;


import java.util.Scanner;

public class ProductMain extends ProductManage {
    public void displayAdminProductMenu() {
        System.out.println(" \nWelcome to Product Management! ");
        System.out.println("===================================");
        System.out.println("|   Product Management - ADMIN    |");
        System.out.println("===================================");
        System.out.println("|      1. Add New Product         |");
        System.out.println("|      2. View All Products       |");
        System.out.println("|      3. Modify Product          |");
        System.out.println("|      4. Delete Product          |");
        System.out.println("|      0. Exit                    |");
        System.out.println("-----------------------------------\n");
    }
    
    public void displayStaffProductMenu() {
        System.out.println(" \nWelcome to Product Management! ");
        System.out.println("===================================");
        System.out.println("|   Product Management - STAFF    |");
        System.out.println("===================================");
        System.out.println("|      1. Add New Product         |");
        System.out.println("|      2. View All Products       |");
        System.out.println("|      3. Modify Product          |");
        System.out.println("|      0. Exit                    |");
        System.out.println("-----------------------------------\n");
    }

    public void productAdminMain(Scanner scn) {
        ProductManage productManage = new ProductManage(); // This should be an instance of a concrete class
        int userChoice;

        do {
            StationaryMain.clearScreen();
            displayAdminProductMenu();
            userChoice = StationaryMain.userChoice(scn, 0, 4);

            switch (userChoice) {
                case 0:
                    System.out.print("Exiting Product Management... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 1:
                    StationaryMain.clearScreen();
                    productManage.addNewProduct(scn);
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 2:
                    StationaryMain.clearScreen();
                    productManage.viewAllProducts();
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 3:
                    StationaryMain.clearScreen();
                    productManage.modifyProduct(scn);
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 4:
                    StationaryMain.clearScreen();
                    productManage.deleteProduct(scn);
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                default:
                    System.out.println("\nInvalid choice. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
            }
        } while (userChoice != 0);
    }

    public void productStaffMain(Scanner scn) {
        ProductManage productManage = new ProductManage(); // This should be an instance of a concrete class
        int userChoice;

        do {
            StationaryMain.clearScreen();
            displayStaffProductMenu();
            userChoice = StationaryMain.userChoice(scn, 0, 3); // Adjust choices for staff

            switch (userChoice) {
                case 0:
                    System.out.print("Exiting Product Management... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 1:
                    StationaryMain.clearScreen();
                    productManage.addNewProduct(scn);
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    break;

                case 2:
                    StationaryMain.clearScreen();
                    productManage.viewAllProducts();
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    break;

                case 3:
                    StationaryMain.clearScreen();
                    productManage.modifyProduct(scn);
                    System.out.print("\n[Press enter to continue...]");
                    scn.nextLine(); // Clear the buffer
                    break;

                default:
                    System.out.println("\nInvalid choice. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    break;
            }
        } while (userChoice != 0);
    }
}
