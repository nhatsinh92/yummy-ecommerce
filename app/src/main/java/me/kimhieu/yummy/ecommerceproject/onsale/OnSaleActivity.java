package me.kimhieu.yummy.ecommerceproject.onsale;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnSaleActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private OnSaleRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton fabCart;
    private TextView textViewCartQuantity;

    WooCommerceService service =  ServiceGenerator.createService(WooCommerceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale);

        handleIntent(getIntent());

        // Set toolbar title
        getSupportActionBar().setTitle("On Sale");

        // Recycler view for product list
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products_on_sale);
        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new OnSaleRecyclerViewAdapter(this, new ArrayList<Product>());
        recyclerView.setAdapter(recyclerViewAdapter);

        displayOnSaleProduct();

        fabCart = (FloatingActionButton) findViewById(R.id.fab_cart);
        textViewCartQuantity = (TextView) findViewById(R.id.text_view_cart_quantity);

        if (YummySession.cart.size() == 0) {
            textViewCartQuantity.setVisibility(View.INVISIBLE);
        }else {
            textViewCartQuantity.setVisibility(View.VISIBLE);
            textViewCartQuantity.setText(String.valueOf(YummySession.cart.size()));
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchProductByNameAndDisplay(query);
        }
    }

    private void searchProductByNameAndDisplay(final String keyWord) {
        Call<ProductsResponse> productsResponseCall = service.getListProduct();
        productsResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                List<Product> productList = new ArrayList<>();
                for (Product product: response.body().getProducts()) {
                    if (product.getTitle().toLowerCase().contains(keyWord.toLowerCase())
                            && product.getOnSale()
                            ) {
                        productList.add(product);
                    }
                }
                recyclerViewAdapter.setProductList(productList);
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                recyclerViewAdapter.setProductList(new ArrayList<Product>());
            }
        });
    }

    private void displayOnSaleProduct() {
        Call<ProductsResponse> productsResponseCall = service.getListProduct();

        productsResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                List<Product> onSaleProductList = new ArrayList<Product>();
                for (Product product : response.body().getProducts()) {
                    if (product.getOnSale()) {
                        onSaleProductList.add(product);
                    }
                }
                recyclerViewAdapter.setProductList(onSaleProductList);
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                recyclerViewAdapter.setProductList(new ArrayList<Product>());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // reload data here
                displayOnSaleProduct();
                return false;
            }
        });

        return true;
    }

}
