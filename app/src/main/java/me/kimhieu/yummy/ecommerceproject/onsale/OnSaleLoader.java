package me.kimhieu.yummy.ecommerceproject.onsale;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnSaleLoader extends AsyncTaskLoader<List<Product>> {

    List<Product> productList = new ArrayList<>();

    public OnSaleLoader(Context context) {
        super(context);
    }

    @Override
    public List<Product> loadInBackground() {
        WooCommerceService service =  ServiceGenerator.createService(WooCommerceService.class);
        Call<ProductsResponse> productsResponseCall = service.getListProduct();

        productsResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                productList =  response.body().getProducts();
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                productList = new ArrayList<>();
            }
        });
        return this.productList;
    }
}
