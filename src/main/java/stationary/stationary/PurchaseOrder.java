package stationary.stationary;

/**
 *
 * @author shirl
 */

public class PurchaseOrder {
    //data read from file
    private String prodName;
    private String prodCategory;
    private String prodBrand;
    private String prodColor;
    private double prodPrice;
    private int prodQIS;
    private String prodSupplierInfo;
    
    //data filled by user
    private String poNumber;
    private int prodID;
    private String poDate;
    private int quantity;
    private String itemBranch;
    private double totalPrice;
    private String poStatus;
    private String paymentStatus;
    
    PurchaseOrder(){
        
    }
    
    PurchaseOrder(int prodID, String prodName, String prodCategory, 
            String prodBrand, String prodColor, double prodPrice, int prodQIS,  
            String prodSupplierInfo, String poNumber, String poDate,
            int quantity, String itemBranch, double totalPrice, 
            String poStatus, String paymentStatus){
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodBrand = prodBrand;
        this.prodColor = prodColor;
        this.prodPrice = prodPrice;
        this.prodQIS = prodQIS;
        this.prodSupplierInfo = prodSupplierInfo;
        this.poNumber = poNumber;
         this.poDate = poDate;
        this.quantity = quantity;
        this.itemBranch = itemBranch;
        this.totalPrice = totalPrice;
        this.poStatus = poStatus;
        this.paymentStatus = paymentStatus;
        
    }
    
    //Getter
    public String getPONumber(){
        return this.poNumber;
    }
    public String getPODate(){
        return this.poDate;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getItemBranch(){
        return this.itemBranch;
    }
    public double getTotalPrice(){
        return this.totalPrice;
    }
    public String getPOStatus(){
        return this.poStatus;
    }
    public String getPaymentStatus(){
        return this.paymentStatus;
    }
    
    public int getProdID() {
        return prodID;
    }
    public String getProdName(){
        return this.prodName;
    }
    public String getProdCategory(){
        return this.prodCategory;
    }
    public String getProdBrand(){
        return this.prodBrand;
    }
    public String getProdColor(){
        return this.prodColor;
    }
    public double getProdPrice(){
        return this.prodPrice;
    }
    public int getProdQIS(){
        return this.prodQIS;
    }
    public String getProdSupplierInfo(){
        return this.prodSupplierInfo;
    }
    
    //Setter
    public void setPONumber(String poNumber){
        this.poNumber = poNumber;
    }
    public void setPODate(String poDate){
        this.poDate = poDate;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setItemBranch(String itemBranch){
        this.itemBranch = itemBranch;
    }
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
    public void setPOStatus(String poStatus){
        this.poStatus = poStatus;
    }
    public void setPaymentStatus(String paymentStatus){
        this.paymentStatus = paymentStatus;
    }
    
    public String toString(){
        return String.format("%s|%s|%d|%s|%s|%s|%s|%.2f|%d|%s|%d|%s|%.2f|%s|%s\n", 
                poNumber, poDate, prodID, 
                prodName, prodCategory, prodBrand, prodColor, prodPrice, prodQIS, prodSupplierInfo, 
                quantity, itemBranch, totalPrice, poStatus, paymentStatus);
        // PO001|01-09-2024 19:43:30|1|Ballpoint Pen|Writing Instruments|Pilot|Blue|0.5|100|ABC Supplies|370|Kuala Lumpur|185|Pending|Unpaid
    }
}
