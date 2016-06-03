
package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {

    @SerializedName("product")
    @Expose
    private Product product;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductResponse() {
    }

    /**
     *
     * @param product
     */
    public ProductResponse(Product product) {
        this.product = product;
    }

    /**
     * 
     * @return
     *     The product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

}
