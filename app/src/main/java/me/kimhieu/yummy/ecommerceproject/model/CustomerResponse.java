package me.kimhieu.yummy.ecommerceproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerResponse {

    @SerializedName("customer")
    @Expose
    private Customer customer;

    public CustomerResponse(Customer customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     *     The customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     *     The customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
