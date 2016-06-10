package me.kimhieu.yummy.ecommerceproject.onsale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnSaleActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private OnSaleRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale);

        // Set toolbar title
        getSupportActionBar().setTitle("On Sale");

        // Recycler view for product list
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products_on_sale);
        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new OnSaleRecyclerViewAdapter(this, new ArrayList<Product>());
        recyclerView.setAdapter(recyclerViewAdapter);

        WooCommerceService service =  ServiceGenerator.createService(WooCommerceService.class);
        Call<ProductsResponse> productsResponseCall = service.getListProduct();

        productsResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                recyclerViewAdapter.setProductList(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
            }
        });
    }

}
