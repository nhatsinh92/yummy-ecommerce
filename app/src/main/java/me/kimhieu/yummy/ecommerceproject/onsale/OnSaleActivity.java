package me.kimhieu.yummy.ecommerceproject.onsale;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;

public class OnSaleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Product>>{

    RecyclerView recyclerView;
    OnSaleRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products_on_sale);
        recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewAdapter = new OnSaleRecyclerViewAdapter(this, new ArrayList<Product>());
        recyclerView.setAdapter(recyclerViewAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        return new OnSaleLoader(OnSaleActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        recyclerViewAdapter.setProductList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {
        recyclerViewAdapter.setProductList(new ArrayList<Product>());
    }
}
