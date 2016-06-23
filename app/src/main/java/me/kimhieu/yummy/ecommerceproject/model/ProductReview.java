
package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReview {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("reviewer_name")
    @Expose
    private String reviewerName;
    @SerializedName("reviewer_email")
    @Expose
    private String reviewerEmail;
    @SerializedName("verified")
    @Expose
    private Boolean verified;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The review
     */
    public String getReview() {
        return review;
    }

    /**
     * 
     * @param review
     *     The review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The reviewerName
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * 
     * @param reviewerName
     *     The reviewer_name
     */
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    /**
     * 
     * @return
     *     The reviewerEmail
     */
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    /**
     * 
     * @param reviewerEmail
     *     The reviewer_email
     */
    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    /**
     * 
     * @return
     *     The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * 
     * @param verified
     *     The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
