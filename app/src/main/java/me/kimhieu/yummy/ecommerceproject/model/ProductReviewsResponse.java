
package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductReviewsResponse {

    @SerializedName("product_reviews")
    @Expose
    private List<ProductReview> productReviews = new ArrayList<ProductReview>();

    /**
     * 
     * @return
     *     The productReviews
     */
    public List<ProductReview> getProductReviews() {
        return productReviews;
    }

    /**
     * 
     * @param productReviews
     *     The product_reviews
     */
    public void setProductReviews(List<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

}
