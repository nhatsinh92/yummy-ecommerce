
package me.kimhieu.yummy.ecommerceproject.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CustomersResponse {

    @SerializedName("customers")
    @Expose
    private List<Customer> customers = new ArrayList<Customer>();

    /**
     * 
     * @return
     *     The customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * 
     * @param customers
     *     The customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
