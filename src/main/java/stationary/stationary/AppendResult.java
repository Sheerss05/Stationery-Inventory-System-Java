/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stationary.stationary;

/**
 *
 * @author shirl
 */
public class AppendResult {
    private boolean appendNeeded;
    private String appendProduct;

    public AppendResult(boolean appendNeeded, String appendProduct) {
        this.appendNeeded = appendNeeded;
        this.appendProduct = appendProduct;
    }

    public boolean isAppendNeeded() {
        return appendNeeded;
    }

    public String getAppendProduct() {
        return appendProduct;
    }
}

