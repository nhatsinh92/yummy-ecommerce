
package me.kimhieu.yummy.ecommerceproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIErrorsResponse {

    @SerializedName("errors")
    @Expose
    private List<Error> errors = new ArrayList<Error>();

    /**
     * 
     * @return
     *     The errors
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * 
     * @param errors
     *     The errors
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

}
