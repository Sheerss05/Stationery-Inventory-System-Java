package stationary.stationary;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductManage {
    
    private static List<String> branchList = List.of(
        "Kuala Lumpur", 
        "Alor Setar", 
        "Setapak", 
        "Cheras", 
        "Petaling Jaya", 
        "Pulau Pinang", 
        "Perak"
    );
    
    public static void regenerateProductIndices(List<String> productList) {
        for (int i = 0; i < productList.size(); i++) {
            String[] productDetails = productList.get(i).split("\\|");
            productDetails[0] = "[" + (i + 1) + "]";  
            productList.set(i, String.join("|", productDetails));
        }
    }

    
    public static String selectBranch(Scanner scn) {
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("|       BRANCH        |");
        System.out.println("-----------------------");
        for (int i = 0; i < branchList.size(); i++) {
            System.out.printf("| %-2d. %-15s |\n", i + 1, branchList.get(i));
        }
        System.out.println("-----------------------");

        while (true) {
            System.out.print("Select a branch by number (1-" + branchList.size() + ") > ");
            String input = scn.nextLine().trim();

            try {
                int branchChoice = Integer.parseInt(input);
                if (branchChoice > 0 && branchChoice <= branchList.size()) {
                    return branchList.get(branchChoice - 1);
                } else {
                    System.out.println("Invalid choice. Please select a valid branch number.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n");
            }
        }
    }
    
    
    public static int getNextProductIndex() {
        List<String> productList = readProductDetailsFromFile();
        return productList.size() + 1;
    }
    
    
    public static void addNewProduct(Scanner scn) {
        System.out.println("Enter the information of the product you would like to add");
        System.out.print("============================================================\n\n");
        
        int prodID = 0;
        while (true) {
            System.out.print("Product ID                        : ");
            if (scn.hasNextInt()) {
                prodID = scn.nextInt();
                scn.nextLine();
                if (prodID > 0) {
                    break;
                } else {
                    System.out.println("Product ID must be a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scn.nextLine();
            }
        }

        String prodName;
        do {
            System.out.print("Product Name                      : ");
            prodName = scn.nextLine().trim();
            if (prodName.isEmpty()) {
                System.out.println("Product Name cannot be empty.");
            }
        } while (prodName.isEmpty());

        String prodCategory;
        do {
            System.out.print("Product Category                  : ");
            prodCategory = scn.nextLine().trim();
            if (prodCategory.isEmpty()) {
                System.out.println("Product Category cannot be empty.");
            }
        } while (prodCategory.isEmpty());

        String prodBrand;
        do {
            System.out.print("Product Brand                     : ");
            prodBrand = scn.nextLine().trim();
            if (prodBrand.isEmpty()) {
                System.out.println("Product Brand cannot be empty.");
            }
        } while (prodBrand.isEmpty());

        String prodColor;
        do {
            System.out.print("Product Color                     : ");
            prodColor = scn.nextLine().trim();
            if (prodColor.isEmpty()) {
                System.out.println("Product Color cannot be empty.");
            }
        } while (prodColor.isEmpty());

        double prodPrice = 0.0;
        while (true) {
            System.out.print("Product Price                     : ");
            if (scn.hasNextDouble()) {
                prodPrice = scn.nextDouble();
                scn.nextLine();
                if (prodPrice > 0) {
                    break;
                } else {
                    System.out.println("Product Price must be a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scn.nextLine();
            }
        }

        int prodQIS = 0;
        while (true) {
            System.out.print("Product Quantity in Stock         : ");
            if (scn.hasNextInt()) {
                prodQIS = scn.nextInt();
                scn.nextLine();
                if (prodQIS >= 0) {
                    break;
                } else {
                    System.out.println("Product Quantity in Stock cannot be negative.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scn.nextLine();
            }
        }

        String prodDesc;
        while (true) {
            System.out.print("Product Description (Max 60 chars): ");
            prodDesc = scn.nextLine().trim();
            if (prodDesc.length() <= 60) {
                break;
            } else {
                System.out.println("Product Description must be 60 characters or less.");
            }
        }

        String prodSupplierInfo;
        do {
            System.out.print("Supplier Information              : ");
            prodSupplierInfo = scn.nextLine().trim();
            if (prodSupplierInfo.isEmpty()) {
                System.out.println("Supplier Information cannot be empty.");
            }
        } while (prodSupplierInfo.isEmpty());

        String prodDateAdded;
        while (true) {
            System.out.print("Product Date Added (dd/mmm/yyyy)  : ");
            prodDateAdded = scn.nextLine().trim();
            if (prodDateAdded.matches("\\d{2}/[A-Za-z]{3}/\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter the date in dd/mmm/yyyy format.");
            }
        }

        String addProductBranch = selectBranch(scn);

        String productDetails = "[" + getNextProductIndex() + "]" + "|" + prodID + "|" + prodName + "|" + prodCategory + "|" + prodBrand + "|"
                             + prodColor + "|" + prodPrice + "|" + prodQIS + "|" + prodDesc + "|"
                             + prodSupplierInfo + "|" + prodDateAdded + "|" + addProductBranch;

        appendProductFile(productDetails); 
    }
    

    public static void viewAllProducts() {
        System.out.println("LIST OF PRODUCTS");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.printf("| %-8s| %-10s| %-20s| %-25s| %-15s| %-17s| %-13s| %-23s| %-60s| %-30s| %-19s| %-15s |\n",
                        "Idx", "Product ID", "Product Name", "Category", 
                        "Brand", "Color", "Price (RM)", "Quantity in Stock", 
                        "Description", "Supplier Information", "Date Added", "Branch");
        System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        List<String> productList = readProductDetailsFromFile();
        for (String line : productList) {
            String[] productDetails = line.split("\\|");
            if (productDetails.length == 12) {  // 12 fields now, including index
                System.out.printf("| %-8s| %-10s| %-20s| %-25s| %-15s| %-17s| %-13s| %-23s| %-60s| %-30s| %-19s| %-15s |\n", 
                                  productDetails[0], productDetails[1], productDetails[2], 
                                  productDetails[3], productDetails[4], productDetails[5], 
                                  productDetails[6], productDetails[7], productDetails[8], 
                                  productDetails[9], productDetails[10], productDetails[11]);
            } else {
                System.out.println("Invalid Product Entry: " + line);
            }
        }
        System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    

    public static void modifyProduct(Scanner scn) {
        System.out.print("\nEnter the Product ID to modify: ");
        int prodID = validateProductId(scn);

        List<String> productList = new ArrayList<>();
        List<String[]> matchingProducts = new ArrayList<>();

        List<String> allProducts = readProductDetailsFromFile();

        
        for (String line : allProducts) {
            String[] productDetails = line.split("\\|");
            if (productDetails.length == 12 && Integer.parseInt(productDetails[1]) == prodID) {
                matchingProducts.add(productDetails);
            }
            productList.add(line);  
        }

        
        if (matchingProducts.isEmpty()) {
            System.out.println("No products found with ID " + prodID);
            return;
        }

        
        System.out.println("\n Matching products");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < matchingProducts.size(); i++) {
            System.out.printf("| %d) %s | %s | %s | %s | %s |\n", 
                              i + 1, matchingProducts.get(i)[1], matchingProducts.get(i)[2], matchingProducts.get(i)[3],
                              matchingProducts.get(i)[4], matchingProducts.get(i)[11]);
        }
        System.out.println("----------------------------------------------------------------");

        System.out.print("\nSelect a product to modify by number: ");
        int productIndex = scn.nextInt();
        scn.nextLine();  

        if (productIndex < 1 || productIndex > matchingProducts.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String[] selectedProduct = matchingProducts.get(productIndex - 1);

        System.out.println("\nModify the product details or press Enter to keep the current value:");

        
        System.out.print("Product Name [" + selectedProduct[2] + "]: ");
        String newProdName = scn.nextLine().trim();
        if (!newProdName.isEmpty()) selectedProduct[2] = newProdName;

        System.out.print("Product Category [" + selectedProduct[3] + "]: ");
        String newProdCategory = scn.nextLine().trim();
        if (!newProdCategory.isEmpty()) selectedProduct[3] = newProdCategory;

        System.out.print("Product Brand [" + selectedProduct[4] + "]: ");
        String newProdBrand = scn.nextLine().trim();
        if (!newProdBrand.isEmpty()) selectedProduct[4] = newProdBrand;

        System.out.print("Product Color [" + selectedProduct[5] + "]: ");
        String newProdColor = scn.nextLine().trim();
        if (!newProdColor.isEmpty()) selectedProduct[5] = newProdColor;

        System.out.print("Product Price [" + selectedProduct[6] + "]: ");
        String newProdPrice = scn.nextLine().trim();
        if (!newProdPrice.isEmpty() && newProdPrice.matches("\\d+(\\.\\d{1,2})?")) selectedProduct[6] = newProdPrice;

        System.out.print("Product Quantity in Stock [" + selectedProduct[7] + "]: ");
        String newProdQIS = scn.nextLine().trim();
        if (!newProdQIS.isEmpty() && newProdQIS.matches("\\d+")) selectedProduct[7] = newProdQIS;

        System.out.print("Product Description [" + selectedProduct[8] + "]: ");
        String newProdDesc = scn.nextLine().trim();
        if (!newProdDesc.isEmpty() && newProdDesc.length() <= 30) selectedProduct[8] = newProdDesc;

        System.out.print("Supplier Information [" + selectedProduct[9] + "]: ");
        String newProdSupplierInfo = scn.nextLine().trim();
        if (!newProdSupplierInfo.isEmpty()) selectedProduct[9] = newProdSupplierInfo;

        System.out.print("Product Date Added [" + selectedProduct[10] + "]: ");
        String newProdDateAdded = scn.nextLine().trim();
        if (!newProdDateAdded.isEmpty() && newProdDateAdded.matches("\\d{2}/[A-Za-z]{3}/\\d{4}")) 
            selectedProduct[10] = newProdDateAdded;

        String newProdBranch = selectBranch(scn);
        if (!newProdBranch.isEmpty()) selectedProduct[11] = newProdBranch;

        
        String modifiedProductDetails = String.join("|", selectedProduct);

        
        for (int i = 0; i < productList.size(); i++) {
            String[] productDetails = productList.get(i).split("\\|");
            if (productDetails[0].equals(selectedProduct[0])) {
                productList.set(i, modifiedProductDetails);
            }
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProductDetails.txt"))) {
            for (String product : productList) {
                writer.write(product);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating product details: " + e.getMessage());
        }

        System.out.println("Product details updated successfully.");
    }
    
    
    public static void deleteProduct(Scanner scn) {
        System.out.print("\nEnter the Product ID to delete: ");
        int prodID = validateProductId(scn);

        List<String> productList = new ArrayList<>();
        List<String[]> matchingProducts = new ArrayList<>();

        List<String> allProducts = readProductDetailsFromFile();

        
        for (String line : allProducts) {
            String[] productDetails = line.split("\\|");
            if (productDetails.length == 12 && Integer.parseInt(productDetails[1]) == prodID) {
                matchingProducts.add(productDetails);
            }
            productList.add(line);  
        }

       
        if (matchingProducts.isEmpty()) {
            System.out.println("No products found with ID " + prodID);
            return;
        }

        
        System.out.println("\nMatching products");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < matchingProducts.size(); i++) {
            System.out.printf("| %d) %s | %s | %s | %s | %s |\n", 
                              i + 1, matchingProducts.get(i)[1], matchingProducts.get(i)[2], matchingProducts.get(i)[3],
                              matchingProducts.get(i)[4], matchingProducts.get(i)[11]);
        }
        System.out.println("---------------------------------------------------------------");

        System.out.print("\nSelect a product to delete by number: ");
        int productIndex = scn.nextInt();
        scn.nextLine(); 

        if (productIndex < 1 || productIndex > matchingProducts.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String[] selectedProduct = matchingProducts.get(productIndex - 1);

       
        System.out.print("Are you sure you want to delete this product? (yes/no): ");
        String confirmation = scn.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        
        productList.removeIf(product -> product.startsWith(selectedProduct[0]));

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProductDetails.txt"))) {
            regenerateProductIndices(productList);  
            for (String product : productList) {
                writer.write(product);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }

        System.out.println("Product deleted successfully.");
    }
    

    public static List<String> readProductDetailsFromFile() {
        List<String> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductDetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                productList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading product file: " + e.getMessage());
        }
        return productList;
    }


    public static void appendProductFile(String productDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProductDetails.txt", true))) {
            writer.write(productDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to product file: " + e.getMessage());
        }
    }


    public static int validateProductId(Scanner scn) {
        while (true) {
            if (scn.hasNextInt()) {
                int prodID = scn.nextInt();
                scn.nextLine();
                if (prodID > 0) {
                    return prodID;
                } else {
                    System.out.print("Invalid Product ID. Please enter a positive number: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a numeric value: ");
                scn.nextLine();
            }
        }
    }
}
