package me.kimhieu.yummy.ecommerceproject.event;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Product;

/**
 * Created by Tri Nguyen on 6/16/2016.
 */
public class ProductLoadingEvent {
    private boolean isCompleted;
    List<Product> productListByCategory;
    String categoryName;
    public ProductLoadingEvent(boolean isCompleted, List<Product> productListByCategory, String categoryName)
    {
        this.isCompleted = isCompleted;
        if (isCompleted == true)
        {
            this.productListByCategory = productListByCategory;
            this.categoryName = categoryName;
        }
        else
        {
               this.productListByCategory = null;
               this.categoryName = null;
        }
    }

    public boolean getStatus()
    {
        return isCompleted;
    }

    public List<Product> getProductList()
    {
        return productListByCategory;
    }

    public String getCategoryName()
    {
        return categoryName;
    }
}
