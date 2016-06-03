
package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoriesResponse {

    @SerializedName("product_categories")
    @Expose
    private List<Category> productCategories = new ArrayList<Category>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CategoriesResponse() {
    }

    /**
     * 
     * @param productCategories
     */
    public CategoriesResponse(List<Category> productCategories) {
        this.productCategories = productCategories;
    }

    /**
     * 
     * @return
     *     The productCategories
     */
    public List<Category> getProductCategories() {
        return productCategories;
    }

    /**
     * 
     * @param productCategories
     *     The product_categories
     */
    public void setProductCategories(List<Category> productCategories) {
        this.productCategories = productCategories;
    }

}
