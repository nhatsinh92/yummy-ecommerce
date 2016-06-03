
package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductsResponse {

    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<Product>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductsResponse() {
    }

    /**
     * 
     * @param products
     */
    public ProductsResponse(List<Product> products) {
        this.products = products;
    }

    /**
     * 
     * @return
     *     The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * 
     * @param products
     *     The products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
