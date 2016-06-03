package me.kimhieu.yummy.ecommerceproject.service;

import me.kimhieu.yummy.ecommerceproject.model.CategoriesResponse;
import me.kimhieu.yummy.ecommerceproject.model.Category;
import me.kimhieu.yummy.ecommerceproject.model.ProductResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WooCommerceService {
    @GET("products/{id}")
    Call<ProductResponse> getProductById(@Path("id") int productId);

    @GET("products?filter[limit]=-1")
    Call<ProductsResponse> getListProduct();

    @GET("products/categories")
    Call<CategoriesResponse> getListCategories();

    @GET("products/categories/{id}")
    Call<Category> getCategoryByID(@Path("id") int id);

    @GET("products")
    Call<ProductsResponse> getListProductByCategory(
            @Query("filter[category]") String categoryName
    );
}

