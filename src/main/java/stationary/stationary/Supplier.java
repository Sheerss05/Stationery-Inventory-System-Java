package stationary.stationary;


public class Supplier {
    private String companyName;
    private String phone;
    private String email;
    private String state;
    private String supplierId;

    public Supplier() {
    }

    public Supplier(String companyName, String phone, String email, String state, String supplierId) {
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.supplierId = supplierId;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}


