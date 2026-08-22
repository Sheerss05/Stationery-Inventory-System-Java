package stationary.stationary;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author shirl
 */
public class SalesMethod{
    //Main Method-----------------------------------------------------------------------------------------------------------------------
    public void displayPO(List<PurchaseOrder> poArray){
        StationaryMain.clearScreen();
        System.out.println("PURCHASE ORDER");
        printDash();
        System.out.print("\n");
        System.out.printf("| %-10s| %-20s| %-11s| %-30s| %-30s| %-20s| %-20s| %-15s| %-20s| %-20s| %-20s| %-20s| %-15s| %-15s| %-15s |\n",
                            "PO Number", "PO Date", "Product ID", 
                            "Product Name", "Product Category", "Product Brand",
                            "Product Color", "Product Price", "Quantity in Stock", 
                            "Product Supplier", "Quantity Purchased", "Product Branch", 
                            "Total Price", "PO Status", "Payment Status");
        printDash();
        System.out.print("\n");
        for (PurchaseOrder po : poArray) {
            System.out.printf("| %-10s| %-20s| %-11d| %-30s| %-30s| %-20s| %-20s| %-15.2f| %-20d| %-20s| %-20d| %-20s| %-15.2f| %-15s| %-15s |\n", 
                                po.getPONumber(), po.getPODate(), po.getProdID(), 
                                po.getProdName(), po.getProdCategory(), po.getProdBrand(), 
                                po.getProdColor(), po.getProdPrice(), po.getProdQIS(), 
                                po.getProdSupplierInfo(), po.getQuantity(), po.getItemBranch(), 
                                po.getTotalPrice(), po.getPOStatus(), po.getPaymentStatus());
            
        }
        printDash();
        System.out.print("\n");
    }
    
    
    public void addPO(Scanner scn, List<PurchaseOrder> poArray){
        List<String[]> productList = readProductFile();
        List<String> branchList = readBranchFile();

        // Data read from file
        String addProdName = "";
        String addProdCategory = "";
        String addProdBrand = "";
        String addProdColor = "";
        String addProdPrice = "0";
        String addProdQIS = "0";
        String addProdSupplierInfo = "";

        // Data fields (input from user)
        int addProductCode = 0;
        int addQuantity;
        String addProductBranch = "";

        // Auto-set fields
        String addPONumber;
        String addPOStatus = "Pending";
        String addPaymentStatus = "Unpaid";
        String addPODate;
        double addTotalPrice;

        // Convert data types for calculation
        double newProdPrice;
        int newProdQIS;

        // Header
        StationaryMain.clearScreen();
        System.out.println("ADD PURCHASE ORDER");
        
        // Product Code --------------------------------------------
        boolean found = false;
        while (!found) {  // Continue looping until a product is found
            System.out.print("Product Code (e.g. 1)                 > ");
            if (scn.hasNextInt()) {
                addProductCode = scn.nextInt();
                scn.nextLine();
                for (String[] productDetails : productList) {
                    if (String.valueOf(addProductCode).equals(productDetails[1])) {
                        addProdName = productDetails[2];
                        addProdCategory = productDetails[3];
                        addProdBrand = productDetails[4];
                        addProdColor = productDetails[5];
                        addProdPrice = productDetails[6];
                        addProdQIS = productDetails[7];
                        addProdSupplierInfo = productDetails[9];

                        found = true;  // Exit the loop when product is found
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Product code not found. Please enter a valid product code.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.\n");
                scn.next(); // discard the invalid input
            }
        }

        // Now the rest of your code will be reachable
        // Purchase Order ID  --------------------------------------
        if (poArray.isEmpty()) {
            addPONumber = "PO001";  // If no previous orders exist
        } else {
            String lastPONumber = poArray.get(poArray.size() - 1).getPONumber();
            // Extract the numeric part
            String numericPart = lastPONumber.substring(2); // Removes "PO"
            int nextPONumber = Integer.parseInt(numericPart) + 1; // Increment

            // Format the new purchase order number
            addPONumber = String.format("PO%03d", nextPONumber);
        }


        // Quantity  ------------------------------------------------
        while (true) {
            System.out.print("Quantity purchased (e.g. 15)          > ");
            if (scn.hasNextInt()) {
                addQuantity = scn.nextInt();
                scn.nextLine();
                if (addQuantity > 0) {
                    break; // Exit loop if valid
                } else {
                    System.out.println("Quantity must be greater than zero. Please enter a valid quantity.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.\n");
                scn.next(); // discard the invalid input
            }
        }

        // Branch  --------------------------------------------------
        while (true) {
            System.out.println();
            System.out.println("    -----------------------");
            System.out.println("    |       BRANCH        |");
            System.out.println("    -----------------------");
            for (int i = 0; i < branchList.size(); i++) {
                String branch = branchList.get(i);
                System.out.printf("    | %-2d. %-15s |\n", i + 1, branch);
            }
            System.out.println("    -----------------------");

            // Prompt user to select a branch
            System.out.print("Select a branch                       > ");
            if (scn.hasNextInt()) {
                int branchChoice = scn.nextInt();
                scn.nextLine(); // Clear the buffer

                if (branchChoice > 0 && branchChoice <= branchList.size()) {
                    addProductBranch = branchList.get(branchChoice - 1);
                    break;
                } else {
                    System.out.println("Invalid choice. Please select a valid branch.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.\n");
                scn.next(); // Clear invalid input
            }
        }

        // PO Date & Time -------------------------------------------
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        addPODate = now.format(formatter);

        // Total Price ----------------------------------------------
        try {
            newProdPrice = Double.parseDouble(addProdPrice);
        } catch (NumberFormatException e) {
            newProdPrice = 0.0;
            System.out.println("Invalid product price. Defaulting to 0.\n");
        }
        addTotalPrice = calculateTotalPrice(newProdPrice, addQuantity);

        // QIS ------------------------------------------------------
        try {
            newProdQIS = Integer.parseInt(addProdQIS);
        } catch (NumberFormatException e) {
            newProdQIS = 0;
            System.out.println("Invalid product QIS. Defaulting to 0.\n");
        }

        // Store all data inside PurchaseOrder
        PurchaseOrder addNewData = new PurchaseOrder(addProductCode, addProdName, addProdCategory, addProdBrand, addProdColor,
                newProdPrice, newProdQIS, addProdSupplierInfo, addPONumber, addPODate,
                addQuantity, addProductBranch, addTotalPrice, addPOStatus, addPaymentStatus);

        appendFile(scn, addNewData);
    }

    
    public void modifyPO(Scanner scn, List<PurchaseOrder> poArray){
        List<String> branchList = readBranchFile();
        
        StationaryMain.clearScreen();
        System.out.println("MODIFY PURCHASE ORDER");
        boolean flag = false;
        do{
            System.out.print("Enter PO number to be modify > ");
            String modifyPONum = scn.next();
            
            if (modifyPONum.matches("PO\\d{3}")){
                boolean found = false;
                for (int i = 0; i < poArray.size();i++){
                    PurchaseOrder po = poArray.get(i); // Use get(i) to access elements
                    if (po.getPONumber().equals(modifyPONum)){
                        if(po.getPOStatus().equals("Pending")){
                            // Display all details
                            System.out.println("     ----------------------------------------------------");
                            System.out.println("     |               Current PO Details                 |");
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26s |\n", "Purchase Order No", po.getPONumber());
                            System.out.printf("     | %-20s: %-26s |\n", "Date", po.getPODate());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Product ID", po.getProdID());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Name", po.getProdName());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Branch", po.getItemBranch());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Quantity Purchased", po.getQuantity());
                            System.out.printf("     | %-20s: %-26.2f |\n", "Total Price", po.getTotalPrice());
                            System.out.printf("     | %-20s: %-26s |\n", "Order Status", po.getPOStatus());
                            System.out.printf("     | %-20s: %-26s |\n", "Payment Status", po.getPaymentStatus());
                            System.out.println("     ----------------------------------------------------\n");

                            System.out.print("Enter the modified quantity purchased > ");
                            int modifiedQuantity = scn.nextInt();
                            scn.nextLine();

                            // Branch  --------------------------------------------------
                            String modifyProductBranch = "";
                            
                            while (true) {
                                System.out.println();
                                System.out.println("-----------------------");
                                System.out.println("|       BRANCH        |");
                                System.out.println("-----------------------");
                                for (int j = 0; j < branchList.size(); j++) {
                                    String branch = branchList.get(j);
                                    System.out.printf("| %-2d. %-15s |\n", j + 1, branch);
                                }
                                System.out.println("-----------------------");

                                // Prompt user to select a branch
                                System.out.print("Select a branch  > ");
                                if (scn.hasNextInt()) {
                                    int branchChoice = scn.nextInt();
                                    scn.nextLine(); // Clear the buffer

                                    if (branchChoice > 0 && branchChoice <= branchList.size()) {
                                        modifyProductBranch = branchList.get(branchChoice - 1);
                                        break;
                                    } else {
                                        System.out.println("Invalid choice. Please select a valid branch.\n");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a number.\n");
                                    scn.next(); // Clear invalid input
                                }
                            }

                            //Double confirm
                            char choicesYN;
                            boolean flagYN;
                            do {
                                System.out.print("\nSure to modify (Y = Yes / N = No)? ");
                                choicesYN = scn.next().charAt(0); // Read a single character
                                switch (choicesYN) {
                                    case 'Y':
                                    case 'y':
                                        po.setQuantity(modifiedQuantity);
                                        po.setItemBranch(modifyProductBranch);
                                        double newTotalPrice = calculateTotalPrice(po.getProdPrice(), modifiedQuantity);
                                        po.setTotalPrice(newTotalPrice);
                                        overwriteFile(poArray, "Modify successfully");
                                        flagYN = true;
                                        break;
                                    case 'N':
                                    case 'n':
                                        System.out.println("Modification Cancelled.\n");
                                        flag = true;  // Set flag to true to break out of the loop
                                        flagYN = true;
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please enter 'Y' or 'N' only.\n");
                                        flagYN = false;
                                        break;
                                }
                            }while(!flagYN);

                            found = true;
                            break;
                        }
                        else{
                            System.out.println("Purchase order [" + modifyPONum + "] are not in pending status. Please try again.\n");
                            found = true; // Stop further searching
                            break;
                        }
                    }
                    
                }
                if (!found) {
                    System.out.println("PO number not found. Please try again.\n");
                } 
                else {
                    flag = continueAction(scn, "Do you want to continue modifying PO", flag);
                }
            }
            else{
                System.out.println("Invalid PO number format. Please enter a valid format(e.g. PO001).\n");
            }
        }while(!flag); //loop while flag is false
    }
    
    
    public void modifyPOStatus(Scanner scn, List<PurchaseOrder> poArray){
        List<String> productList = new ArrayList<>();
        AppendResult result = new AppendResult(false, null);
        boolean flag = false;
        
        StationaryMain.clearScreen();
        System.out.println("MODIFY PURCHASE ORDER STATUS");
        do{
            System.out.print("Enter PO number to be modify > ");
            String modifyPONum = scn.next();
            
            if (modifyPONum.matches("PO\\d{3}")){
                boolean found = false;
                for (int i = 0; i < poArray.size();i++){
                    PurchaseOrder po = poArray.get(i); // Use get(i) to access elements
                    if (po.getPONumber().equals(modifyPONum)){
                        if(po.getPOStatus().equals("Complete")){
                            System.out.println("Purchase order are completed. Please try another.\n");
                            found = true;
                            break;
                        }
                        else{
                            // Display all details
                            System.out.println("     ----------------------------------------------------");
                            System.out.println("     |                Current PO Status                 |");
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26s |\n", "Purchase Order No", po.getPONumber());
                            System.out.printf("     | %-20s: %-26s |\n", "Date", po.getPODate());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Product ID", po.getProdID());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Name", po.getProdName());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Branch", po.getItemBranch());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Quantity Purchased", po.getQuantity());
                            System.out.printf("     | %-20s: %-26.2f |\n", "Total Price", po.getTotalPrice());
                            System.out.printf("     | %-20s: %-26s |\n", "Order Status", po.getPOStatus());
                            System.out.printf("     | %-20s: %-26s |\n", "Payment Status", po.getPaymentStatus());
                            System.out.println("     ----------------------------------------------------\n");
                            
                            //Chose modify field
                            System.out.println("Choose which status to be modify :");
                            System.out.println("1. Modify Order Status");
                            System.out.println("2. Modify Payment Status");
                            System.out.println("3. Modify Both Status");
                            System.out.print("0. Exit");
                            int userChoiceModify = StationaryMain.userChoice(scn, 0, 3);
                            
                            boolean modify = false;
                            boolean exit = false;
                            switch (userChoiceModify){
                                case 1:
                                    System.out.println("\n\nMODIFYING ORDER STATUS");
                                    System.out.println("Choose Status:");
                                    System.out.println("1. Pending");
                                    System.out.println("2. Approve");
                                    System.out.println("3. Complete");
                                    System.out.print("0. Cancel Modify");
                                    int userOrderS = StationaryMain.userChoice(scn, 0, 3);
                                    switch(userOrderS){
                                        case 1:
                                            if (po.getPOStatus().equals("Pending")){
                                                System.out.println("Purchase Order is already on pending status. No modification needed.\n");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Pending");
                                                modify = true;
                                            }
                                            break;
                                        case 2:
                                            if (po.getPOStatus().equals("Approve")){
                                                System.out.println("Purchase Order is already on approve status. No modification needed.\n");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Approve");
                                                modify = true;
                                            }
                                            break;
                                        case 3:
                                            if (po.getPOStatus().equals("Complete")){
                                                System.out.println("Purchase Order is already on complete status. No modification needed.\n");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Complete");
                                                po.setPaymentStatus("Paid");
                                                
                                                result = updateProdQIS(productList, po.getProdID(), po.getQuantity(), po.getItemBranch());
                                                
                                                modify = true;
                                            }
                                            break;
                                        default:
                                            exit = true;
                                            break;
                                    }
                                    break;
                                    
                                case 2:
                                    System.out.println("\n\nMODIFYING PAYMENT STATUS");
                                    System.out.println("Choose Status:");
                                    System.out.println("1. Paid");
                                    System.out.println("2. Unpaid");
                                    System.out.print("0. Cancel Modify");
                                    int userPaymentS = StationaryMain.userChoice(scn, 0, 2);
                                    switch(userPaymentS){
                                        case 1:
                                            if (po.getPaymentStatus().equals("Paid")){
                                                System.out.println("Order payment is already on paid status. No modification needed.\n");
                                                break;
                                            }
                                            else{
                                                po.setPaymentStatus("Paid");
                                                modify = true;
                                            }
                                            break;
                                        case 2:
                                            if (po.getPaymentStatus().equals("Unpaid")){
                                                System.out.println("Order payment is already on unpaid status. No modification needed.\n");
                                                break;
                                            }
                                            else{
                                                po.setPaymentStatus("Unpaid");
                                                modify = true;
                                            }
                                            break;
                                        default:
                                            exit = true;
                                            break;
                                    }
                                    break;
                                    
                                case 3:
                                    System.out.println("\n\nMODIFYING BOTH STATUS");
                                    System.out.println("Choose Order Status:");
                                    System.out.println("1. Pending");
                                    System.out.println("2. Approve");
                                    System.out.println("3. Complete");
                                    System.out.print("0. Cancel Modify");
                                    int userBOrderS = StationaryMain.userChoice(scn, 0, 3);
                                    switch(userBOrderS){
                                        case 1:
                                            if (po.getPOStatus().equals("Pending")){
                                                System.out.println("Purchase Order is already on pending status. No modification needed.");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Pending");
                                                modify = true;
                                            }
                                            break;
                                        case 2:
                                            if (po.getPOStatus().equals("Approve")){
                                                System.out.println("Purchase Order is already on approve status. No modification needed.");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Approve");
                                                modify = true;
                                            }
                                            break;
                                        case 3:
                                            if (po.getPOStatus().equals("Complete")){
                                                System.out.println("Purchase Order is already on complete status. No modification needed.");
                                                break;
                                            }
                                            else{
                                                po.setPOStatus("Complete");
                                                
                                                 result = updateProdQIS(productList, po.getProdID(), po.getQuantity(), po.getItemBranch());
                                                
                                                modify = true;
                                            }
                                            break;
                                        default:
                                            exit = true;
                                            break;
                                    }
                                    if (!exit){
                                        System.out.println("\nChoose Payment Status:");
                                        System.out.println("1. Paid");
                                        System.out.println("2. Unpaid");
                                        System.out.print("0. Cancel Modify");
                                        int userBPaymentS = StationaryMain.userChoice(scn, 0, 2);
                                        switch(userBPaymentS){
                                            case 1:
                                                if (po.getPaymentStatus().equals("Paid")){
                                                    System.out.println("Order payment is already on paid status. No modification needed.\n");
                                                    break;
                                                }
                                                else{
                                                    po.setPaymentStatus("Paid");
                                                    modify = true;
                                                }
                                                break;
                                            case 2:
                                                if (po.getPaymentStatus().equals("Unpaid")){
                                                    System.out.println("Order payment is already on unpaid status. No modification needed.\n");
                                                    break;
                                                }
                                                else{
                                                    po.setPaymentStatus("Unpaid");
                                                    modify = true;
                                                }
                                                break;
                                            default:
                                                exit = true;
                                                break;
                                        }
                                    }
                                    
                                    break;
                                    
                                default:
                                    exit = true;
                                    break;
                            }
                            
                            if (exit){
                                System.out.println("\nModification Cancelled.\n");
                            }
                            else {
                                if (modify){
                                    //Double confirm
                                    char choicesYN;
                                    boolean flagYN;
                                    do {
                                        System.out.print("\nSure to modify (Y = Yes / N = No)? ");
                                        choicesYN = scn.next().charAt(0); // Read a single character
                                        switch (choicesYN) {
                                            case 'Y':
                                            case 'y':
                                                overwriteFile(poArray, "Modify successfully");
                                                    
                                                if (result.isAppendNeeded()) {
                                                    // Use the appendProduct string from the result
                                                    //String message = "New product added at [" + po.getItemBranch() + "] branch.\n\n";
                                                    ProductManage.appendProductFile(result.getAppendProduct());
                                                    System.out.printf("New product added at [%s] branch.\n\n", po.getItemBranch());
                                                }
                                                else{
                                                    // After the loop, overwrite the product details if any changes were made
                                                    overwriteProductDetails(productList);
                                                    System.out.println("Product quantity in stock will be updated automatically if PO status is complete.\n");
                                                }
                                                
                                                flagYN = true;
                                                break;
                                            case 'N':
                                            case 'n':
                                                System.out.println("Modification Cancelled.\n");
                                                flag = true;  // Set flag to true to break out of the loop
                                                flagYN = true;
                                                break;
                                            default:
                                                System.out.println("Invalid input. Please enter 'Y' or 'N' only.\n");
                                                flagYN = false;
                                                break;
                                        }
                                    }while(!flagYN);
                                }
//                                else{
//                                    System.out.println("Status remain the same. No modification needed.\n");
//                                }
                            }
                            
                            found = true;
                            break;
                        }
                    }
                    
                }
                if (!found) {
                    System.out.println("PO number not found. Please try again.\n");
                }
                else {
                    flag = continueAction(scn, "Do you want to continue modify PO status", flag);
                    StationaryMain.clearScreen();
                }
            }
            else{
                System.out.println("Invalid PO number format. Please enter a valid format(e.g. PO001).\n");
            }
        }while(!flag);
    } //if complete add on product QIS
    
    public AppendResult updateProdQIS(List<String> productList, int prodID, int quantity, String branch){
        boolean append = false;
        String appendProduct = "";
        
        String prodName;
        String prodCategory;
        String prodBrand;
        String prodColor;
        String prodPrice;
        String prodDesc;
        String prodSupplierInfo;
        String prodDateAdded;
        int prodQIS;
        
                                                
        try (BufferedReader br = new BufferedReader(new FileReader("ProductDetails.txt"))) {
            String line;
            boolean firstLine = true; // Flag to skip the first line (header)

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                String[] productDetails = line.split("\\|");
                if (productDetails.length == 12) {
                    if (prodID == Integer.parseInt(productDetails[1])) {
                        if (branch.equals(productDetails[11])) {
                            prodQIS = Integer.parseInt(productDetails[7]) + quantity;
                            productDetails[7] = String.valueOf(prodQIS);

                            productList.add(String.join("|", productDetails));
                            
                            append = false;
                        } else {
                            prodName = productDetails[2];
                            prodCategory = productDetails[3];
                            prodBrand = productDetails[4];
                            prodColor = productDetails[5];
                            prodPrice = productDetails[6];
                            prodDesc = productDetails[8];
                            prodSupplierInfo = productDetails[9];
                            prodDateAdded = productDetails[10];

                            prodQIS = quantity;

//                            appendProduct = prodID + "|" + prodName + "|" + prodCategory + "|" + prodBrand + "|"
//                                    + prodColor + "|" + prodPrice + "|" + prodQIS + "|" + prodDesc + "|"
//                                    + prodSupplierInfo + "|" + prodDateAdded + "|" + branch;
                            
                            appendProduct = "[" + ProductManage.getNextProductIndex() + "]" + "|" + prodID + "|" + prodName + "|" + prodCategory + "|" + prodBrand + "|"
                             + prodColor + "|" + prodPrice + "|" + prodQIS + "|" + prodDesc + "|"
                             + prodSupplierInfo + "|" + prodDateAdded + "|" + branch;
                            
                            append = true;
                        }
                    } else {
                        productList.add(line);
                    }
                } 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        
        // Return the append flag and the product string
        return new AppendResult(append, appendProduct);
    }
    
    public void cancelPO(Scanner scn, List<PurchaseOrder> poArray){
        StationaryMain.clearScreen();
        System.out.println("DELETING PURCHASE ORDER");
        boolean flag = false;
        do{
            System.out.print("Enter PO Number to be delete > ");
            String deletePONum = scn.next();
            
            if (deletePONum.matches("PO\\d{3}")){
                boolean found = false;
                for (int i = 0; i < poArray.size(); i++){
                    PurchaseOrder po = poArray.get(i);
                    if (po.getPONumber().equals(deletePONum)){
                        if(po.getPOStatus().equals("Pending")){
                            // Display all details
                            System.out.println("     ----------------------------------------------------");
                            System.out.println("     |              Purchase Order Details              |");
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26s |\n", "Purchase Order No", po.getPONumber());
                            System.out.printf("     | %-20s: %-26s |\n", "Date", po.getPODate());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Product ID", po.getProdID());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Name", po.getProdName());
                            System.out.printf("     | %-20s: %-26s |\n", "Product Branch", po.getItemBranch());
                            System.out.println("     ----------------------------------------------------");
                            System.out.printf("     | %-20s: %-26d |\n", "Quantity Purchased", po.getQuantity());
                            System.out.printf("     | %-20s: %-26.2f |\n", "Total Price", po.getTotalPrice());
                            System.out.printf("     | %-20s: %-26s |\n", "Order Status", po.getPOStatus());
                            System.out.printf("     | %-20s: %-26s |\n", "Payment Status", po.getPaymentStatus());
                            System.out.println("     ----------------------------------------------------\n");
                            
                            //Double confirm
                            char choicesYN;
                            boolean flagYN;
                            do {
                                System.out.print("\nAre you sure you want to delete [" + deletePONum + "] (Y = Yes / N = No)? ");
                                choicesYN = scn.next().charAt(0); // Read a single character
                                switch (choicesYN) {
                                    case 'Y':
                                    case 'y':
                                        poArray.remove(i); // Remove the matching PO
                                        overwriteFile(poArray, "Deletion successful\n");
                                        flagYN = true;
                                        break;
                                    case 'N':
                                    case 'n':
                                        System.out.println("Deletion Cancelled.\n");
                                        flag = true;  // Set flag to true to break out of the loop
                                        flagYN = true;
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please enter 'Y' or 'N' only.\n");
                                        flagYN = false;
                                        break;
                                }
                            }while(!flagYN);
                            
                            found = true;
                            break;
                        }
                        else{
                            System.out.println("Purchase order [" + deletePONum + "] are not in pending status. Please try again.\n");
                            found = true; // Stop further searching
                            break;
                        }
                    }
                }
                if (!found) {
                    System.out.println("PO number not found. Please try again.\n");
                } 
                else {
                    flag = continueAction(scn, "Do you want to continue deleting PO", flag);
                }
            }
            else{
                System.out.println("Invalid PO number format. Please enter a valid format(e.g. PO001).\n");
            }
        }while(!flag); //loop while flag is false
    }
    
    public void generateInvoice(List<PurchaseOrder> poArray, Scanner scn){
        StationaryMain.clearScreen();
        System.out.println("GENERATE INVOICE");
        boolean flag = false;
        do {
            System.out.print("Enter PO number to generate invoice > ");
            String invoicePONum = scn.next();

            // Validate the format of the PO number
            if (invoicePONum.matches("PO\\d{3}")) {
                boolean found = false; // Track if the PO is found
                for (PurchaseOrder po : poArray) {
                    if (po.getPONumber().equals(invoicePONum)) {
                        System.out.println("\nGenerating invoice for: [" + invoicePONum + "]");
                        System.out.println("     ----------------------------------------------------");
                        System.out.println("     |               Stationery Invoice                 |");
                        System.out.println("     ----------------------------------------------------");
                        System.out.printf("     | %-20s: %-26s |\n", "Purchase Order No", po.getPONumber());
                        System.out.printf("     | %-20s: %-26s |\n", "Date", po.getPODate());
                        System.out.println("     ----------------------------------------------------");
                        System.out.printf("     | %-20s: %-26d |\n", "Product ID", po.getProdID());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Name", po.getProdName());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Category", po.getProdCategory());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Brand", po.getProdBrand());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Color", po.getProdColor());
                        System.out.printf("     | %-20s: %-26.2f |\n", "Product Price", po.getProdPrice());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Supplier", po.getProdSupplierInfo());
                        System.out.printf("     | %-20s: %-26s |\n", "Product Branch", po.getItemBranch());
                        System.out.println("     ----------------------------------------------------");
                        System.out.printf("     | %-20s: %-26d |\n", "Quantity Purchased", po.getQuantity());
                        System.out.printf("     | %-20s: %-26.2f |\n", "Total Price", po.getTotalPrice());
                        System.out.printf("     | %-20s: %-26s |\n", "Order Status", po.getPOStatus());
                        System.out.printf("     | %-20s: %-26s |\n", "Payment Status", po.getPaymentStatus());
                        System.out.println("     ----------------------------------------------------\n");
                        found = true; // PO found, set found to true
                        break;
                    }
                }
                if (!found) {
                    System.out.println("PO number not found. Please try again.\n");
                }
                else {
                    flag = continueAction(scn, "Do you want to continue generate invoice", flag);
                }
            } else {
                System.out.println("Invalid PO number format. Please enter a valid format(e.g. PO001).\n");
            }
            
        } while (!flag); // Continue looping until flag is true
        
    }
    
    // File Method -----------------------------------------------------------------------------------------------------------------------------
    //List<PurchaseOrder> poArray pass after read file
    public List<PurchaseOrder> readSalesFile(){
        List<PurchaseOrder> poArray = new ArrayList<>();
        
        try{
            File salesFile = new File("sales.txt");
            Scanner scnRead = new Scanner(salesFile);
            while (scnRead.hasNextLine()){
                String line = scnRead.nextLine();
                if (!line.isEmpty()){
                    try{
                        Scanner scnLine = new Scanner(line);
                        scnLine.useDelimiter("\\|");

                        //Parse field using Scanner
                        String poNumber = scnLine.next();
                        String poDate = scnLine.next();
                        int prodID = scnLine.nextInt();
                        String prodName = scnLine.next();
                        String prodCategory = scnLine.next();
                        String prodBrand = scnLine.next();
                        String prodColor = scnLine.next();
                        double prodPrice = scnLine.nextDouble();
                        int prodQIS = scnLine.nextInt();
                        String prodSupplierInfo = scnLine.next();
                        int quantity = scnLine.nextInt();
                        String itemBranch = scnLine.next();
                        double totalPrice = scnLine.nextDouble();
                        String poStatus = scnLine.next();
                        String paymentStatus = scnLine.next();

                        // Create a new PurchaseOrder object
                        PurchaseOrder po = new PurchaseOrder(prodID, prodName, prodCategory, prodBrand, prodColor,
                                                                 prodPrice, prodQIS, prodSupplierInfo, poNumber, poDate,
                                                                 quantity, itemBranch, totalPrice, poStatus, paymentStatus);

                        // Add the object to the list
                        poArray.add(po);
                    }
                    catch (Exception e){
                    System.out.println("An error occurred while parsing the file.");
                    e.printStackTrace();
                    }
                }
                else{
                    System.out.println(" No Record Found !");
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("An error occurred when reading sales.txt .");
            e.printStackTrace();
        }
        return poArray;
    }
    
    public static List<String> readBranchFile() {
        List<String> branchList = new ArrayList<>();
        try {
            File branchFile = new File("branch.txt");
            Scanner scnRead = new Scanner(branchFile);

            while (scnRead.hasNextLine()) {
                String line = scnRead.nextLine();
                if (!line.isEmpty()) {
                    branchList.add(line);  // Add the entire line since it's a single field
                } else {
                    System.out.println("No Record Found!");
                }
            }

            scnRead.close();  // Always close the Scanner after use
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when reading branch.txt.");
            e.printStackTrace();
        }
        return branchList;
    }
    
    public static List<String[]> readProductFile() {
        List<String[]> productList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("ProductDetails.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                if (productDetails.length == 12) {
                    productList.add(productDetails); // Add valid product details to the list
                } else {
                    System.out.println("Invalid Product Entry: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        return productList; // Return the list of product details
    }
    
    private static void overwriteFile(List<PurchaseOrder> poArray, String message){
        try(FileWriter salesFile = new FileWriter("sales.txt")){ //if file exists, means flag=true (append mode). If flag=false, create new file
            for (PurchaseOrder po : poArray) {
                salesFile.write(po.toString());
            }
            System.out.println(message + "!");
        }
        catch(IOException e){
            System.out.println("Action failed. An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void overwriteProductDetails(List<String> productList) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ProductDetails.txt"))) {
//            for (String product : productList) {
//                bw.write(product + "\n");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred while writing the file.");
//            e.printStackTrace();
//        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProductDetails.txt"))) {
            for (String product : productList) {
                writer.write(product);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating product details: " + e.getMessage());
        }
    }
    
    private static void appendFile(Scanner scn, PurchaseOrder po){
        try(FileWriter salesFile = new FileWriter("sales.txt", true)){ //if file exists, means flag=true (append mode). If flag=false, create new file
            salesFile.write(po.toString());
            System.out.println("\nOrder Placed! [Press enter to continue]");
            scn.nextLine(); // Clear the buffer
            StationaryMain.clearScreen();
        }
        catch(IOException e){
            System.out.println("\nFailed to place order. An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void appendProductFile(String productDetails, String message){
        try {
            FileWriter myWriter = new FileWriter("ProductDetails.txt", true);
            myWriter.write(productDetails + "\n");
            myWriter.close();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    
    //Arithmetic Method ------------------------------------------------------------------------------------------------------------------------
    private static double calculateTotalPrice(double price, int quantity){
        return price * quantity ;
    }
    
    
    // Assists Method --------------------------------------------------------------------------------------------------------------------------
    private boolean continueAction(Scanner scn, String message, boolean flag){
        char choicesYN;
        boolean flagYN;
        do {
            System.out.print(message + " (Y = Yes / N = No)? ");
            choicesYN = scn.next().charAt(0); // Read a single character
            switch (choicesYN) {
                case 'Y':
                case 'y':
                    System.out.print("\n");
                    StationaryMain.clearScreen();
                    flag = false; 
                    flagYN = true;
                    break;
                case 'N':
                case 'n':
                    System.out.print("\n");
                    StationaryMain.clearScreen();
                    flag = true;  // Set flag to true to break out of the loop
                    flagYN = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter 'Y' or 'N' only.\n");
                    flag = false; 
                    flagYN = false;
                    break;
            }
        }while(!flagYN);
        
        return flag;
    }
    
    private static void printDash(){
        for (int i = 0; i < 313; i++){
            System.out.print("-");
        }
    }
}
