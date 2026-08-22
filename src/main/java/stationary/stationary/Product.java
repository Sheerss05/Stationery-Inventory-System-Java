package stationary.stationary;

class Product{
    
    private String index;
    private int prodID;
    private String prodName;
    private String prodCategory;
    private String prodBrand;
    private String prodColor;
    private double prodPrice;
    private int prodQIS;
    private String prodDesc;
    private String prodSupplierInfo;
    private String prodDateAdded;
    private String addProductBranch;
    
    //constructor
    public Product(String index, int prodID, String prodName, String prodCategory, String prodBrand, String prodColor, double prodPrice, int prodQIS, String prodDesc, String prodSupplierInfo, String prodDateAdded, String addProductBranch){
        this.index = index;
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodBrand = prodBrand;
        this.prodColor = prodColor;
        this.prodPrice = prodPrice;
        this.prodQIS = prodQIS;
        this.prodDesc = prodDesc;
        this.prodSupplierInfo = prodSupplierInfo;
        this.prodDateAdded = prodDateAdded;
        this.addProductBranch = addProductBranch;
    }
    
    
    //setter
    
    public void setIndex(String index) {
        this.index = index;
    }
    
    public void setProdID(int prodID) {
        this.prodID = prodID;
    }
    
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand;
    }

    public void setProdColor(String prodColor) {
        this.prodColor = prodColor;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public void setProdQIS(int prodQIS) {
        this.prodQIS = prodQIS;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public void setProdSupplierInfo(String prodSupplierInfo) {
        this.prodSupplierInfo = prodSupplierInfo;
    }

    public void setProdDateAdded(String prodDateAdded) {
        this.prodDateAdded = prodDateAdded;
    }

    public void setAddProductBranch(String addProductBranch){
        this.addProductBranch = addProductBranch;
    }
    
    //getter
    public int getProdID() {
        return prodID;
    }
    
    public String getIndex() {
        return this.index;
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
    
    public String getProdDesc(){
        return this.prodDesc;
    }
    
    public String getProdSupplierInfo(){
        return this.prodSupplierInfo;
    }
    
    public String getProdDateAdded(){
        return this.prodDateAdded;
    }

    public String getAddProductBranch(){
        return this.addProductBranch;
    }
    
    public String toString(){
        return  index + "|" + prodID + "|" + prodName + "|" + prodCategory + "|" + prodBrand + "|" + prodColor + "|" +  prodPrice + "|" + prodQIS + "|" + prodDesc + "|"
                      + prodSupplierInfo + "|" + prodDateAdded + "|" + addProductBranch;
    }
    
    

}

