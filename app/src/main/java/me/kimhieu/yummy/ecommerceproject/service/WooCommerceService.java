package me.kimhieu.yummy.ecommerceproject.service;

import me.kimhieu.yummy.ecommerceproject.model.CustomerResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategoriesResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategory;
import me.kimhieu.yummy.ecommerceproject.model.ProductResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductReviewsResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WooCommerceService {
    @GET("products/{id}")
    Call<ProductResponse> getProductById(@Path("id") int productId);

    @GET("products?filter[limit]=-1")
    Call<ProductsResponse> getListProduct();

    @GET("products/categories")
    Call<ProductCategoriesResponse> getListCategories();

    @GET("products/categories/{id}")
    Call<ProductCategory> getCategoryByID(@Path("id") int id);

    @GET("products")
    Call<ProductsResponse> getListProductByCategory(
            @Query("filter[category]") String categoryName
    );

    @GET("products/{id}/reviews")
    Call<ProductReviewsResponse> getProductReviewsById(@Path("id") int id);

    @POST("customers")
    Call<CustomerResponse> addCustomer(@Body CustomerResponse customerResponse);
}

