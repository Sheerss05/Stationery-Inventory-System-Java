package stationary.stationary;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class Sales{
    //Display Menu Method-----------------------------------------------------------------------------------------------------------------------
    public void displaySalesMenu(){
        System.out.println("       ______     ______     __         ______     ______    ");
        System.out.println("      /\\  ___\\   /\\  __ \\   /\\ \\       /\\  ___\\   /\\  ___\\   ");
        System.out.println("      \\ \\___  \\  \\ \\  __ \\  \\ \\ \\____  \\ \\  __\\   \\ \\___  \\  ");
        System.out.println("       \\/\\_____\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\_____\\  \\/\\_____\\ ");
        System.out.println("        \\/_____/   \\/_/\\/_/   \\/_____/   \\/_____/   \\/_____/ ");
        System.out.println("\n");
        System.out.println("        Welcome to Sales Management! ");
        System.out.println("        ===================================================");
        System.out.println("        |               Sales Management                  |");
        System.out.println("        ===================================================");
        System.out.println("        |               1. Purchase Order                 |");
        System.out.println("        |               2. Generate Invoice               |");
        System.out.println("        |               0. Exit                           |");
        System.out.println("        ---------------------------------------------------");
    }
    
    public void displayPOMenuAdmin(){ //staff placed order, administrator checked and approve
        System.out.println("                        ______   ______    ");
        System.out.println("                       /\\  == \\ /\\  __ \\   ");
        System.out.println("                       \\ \\  _-/ \\ \\ \\/\\ \\  ");
        System.out.println("                        \\ \\_\\    \\ \\_____\\ ");
        System.out.println("                         \\/_/     \\/_____/ ");
        System.out.println("");
        System.out.println("        ---------------------------------------------------");
        System.out.println("        |             Purchase Order - ADMIN              |");
        System.out.println("        ---------------------------------------------------");
        System.out.println("        |               1. Display Order                  |");
        System.out.println("        |               2. Add Order                      |");
        System.out.println("        |               3. Modify Order Details           |");   // modify pending PO that havent placed successfully
        System.out.println("        |               4. Modify Order Status            |");   
        System.out.println("        |               5. Cancel Order                   |");   //cancel pending PO that havent placed successfully 
        System.out.println("        |               0. Exit                           |");
        System.out.println("        ---------------------------------------------------");
    }
    
    public void displayPOMenuStaff(){ //staff placed order, administrator checked and approve
        System.out.println("                        ______   ______    ");
        System.out.println("                       /\\  == \\ /\\  __ \\   ");
        System.out.println("                       \\ \\  _-/ \\ \\ \\/\\ \\  ");
        System.out.println("                        \\ \\_\\    \\ \\_____\\ ");
        System.out.println("                         \\/_/     \\/_____/ ");
        System.out.println("");
        System.out.println("        ---------------------------------------------------");
        System.out.println("        |             Purchase Order - STAFF              |");
        System.out.println("        ---------------------------------------------------");
        System.out.println("        |               1. Display Order                  |");
        System.out.println("        |               2. Add Order                      |");
        System.out.println("        |               3. Modify Order Details           |");   // modify pending PO that havent placed successfully
        System.out.println("        |               0. Exit                           |");
        System.out.println("        ---------------------------------------------------");
    }
    
    public void salesAdminMain(Scanner scn){
        SalesMethod salesMethod = new SalesMethod();
        
        int userChoice;
        do {
            StationaryMain.clearScreen();
            List<PurchaseOrder> poArray = salesMethod.readSalesFile();
            displaySalesMenu();
            userChoice = StationaryMain.userChoice(scn, 0, 2);

            switch (userChoice){
                case 0:
                    System.out.print("Exiting Sales Management... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 1:
                    StationaryMain.clearScreen();
                    int userPOChoice;
                    do {
                        displayPOMenuAdmin();
                        userPOChoice = StationaryMain.userChoice(scn, 0, 5);
                        switch (userPOChoice){
                            case 0:
                                break;
                            case 1:
                                poArray = salesMethod.readSalesFile();
                                salesMethod.displayPO(poArray);
                                System.out.print("\n[Press enter to continue...]");
                                scn.nextLine(); // Clear the buffer
                                scn.nextLine();
                                StationaryMain.clearScreen();
                                System.out.print("\n\n");
                                break;
                            case 2:
                                salesMethod.addPO(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            case 3:
                                salesMethod.modifyPO(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            case 4:
                                salesMethod.modifyPOStatus(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            case 5:
                                salesMethod.cancelPO(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            default:
                                System.out.println("\nInvalid choice. Exiting... [Press enter to continue]");
                                scn.nextLine(); // Clear the buffer
                                scn.nextLine();
                                break;
                        }
                    }while (userPOChoice != 0);
                    break;
                case 2:
                    salesMethod.generateInvoice(poArray, scn);
                    System.out.print("Exiting Invoice Generation Function... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    System.out.print("\n\n");
                    break;

                default:
                    System.out.println("\nInvalid choices. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
            }
        }while(userChoice != 0);
    }
    
    public void salesStaffMain(Scanner scn){
        SalesMethod salesMethod = new SalesMethod();
        
        int userChoice;
        do {
            StationaryMain.clearScreen();
            List<PurchaseOrder> poArray = salesMethod.readSalesFile();
            displaySalesMenu();
            userChoice = StationaryMain.userChoice(scn, 0, 2);

            switch (userChoice){
                case 0:
                    System.out.print("Exiting Sales Management... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;

                case 1:
                    StationaryMain.clearScreen();
                    int userPOChoice;
                    do {
                        displayPOMenuStaff();
                        userPOChoice = StationaryMain.userChoice(scn, 0, 3);
                        switch (userPOChoice){
                            case 0:
                                break;
                            case 1:
                                poArray = salesMethod.readSalesFile();
                                salesMethod.displayPO(poArray);
                                System.out.print("\n[Press enter to continue...]");
                                scn.nextLine(); // Clear the buffer
                                scn.nextLine();
                                System.out.print("\n\n");
                                break;
                            case 2:
                                salesMethod.addPO(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            case 3:
                                salesMethod.modifyPO(scn, poArray);
                                System.out.print("\n\n");
                                break;
                            default:
                                System.out.println("\nInvalid choice. Exiting... [Press enter to continue]");
                                scn.nextLine(); // Clear the buffer
                                scn.nextLine();
                                break;
                        }
                    }while (userPOChoice != 0);
                    break;
                    
                case 2:
                    salesMethod.generateInvoice(poArray, scn);
                    System.out.print("Exiting Invoice Generation Function... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    System.out.print("\n\n");
                    break;

                default:
                    System.out.println("\nInvalid choices. Exiting... [Press enter to continue]");
                    scn.nextLine(); // Clear the buffer
                    scn.nextLine();
                    break;
            }
        }while(userChoice != 0);
    }
    
}
