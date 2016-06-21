package me.kimhieu.yummy.ecommerceproject.explore;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.event.CategoryLoadingEvent;
import me.kimhieu.yummy.ecommerceproject.event.ProductLoadingEvent;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategoriesResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategory;
import me.kimhieu.yummy.ecommerceproject.model.ProductsResponse;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import me.kimhieu.yummy.ecommerceproject.utils.ExploreLibrary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tri Nguyen on 6/9/2016.
 */
public class ExploreActivity extends BaseActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Spinner spinner;
    private WooCommerceService service;
    private ArrayAdapter<String> spinnerAdapter;
    private List<ProductCategory> nestedCategory;
    private List<ProductCategory> categoriList;
    private PagerViewAdapter pageViewerAdapter;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        manager = getSupportFragmentManager();
        pageViewerAdapter = new PagerViewAdapter(manager);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        spinner = (Spinner) findViewById(R.id.explore_spinner_nav);

        service =  ServiceGenerator.createService(WooCommerceService.class);
        setSpinnerCategoryList(service);

        // Spinner menu Action listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = parent.getSelectedItemPosition();
                setupViewPager(nestedCategory.get(itemPosition).getChild());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // handle nothing selected event
            }
        });
    }

    //load categorylist from server
    public void setSpinnerCategoryList(WooCommerceService service )
    {
        Call<ProductCategoriesResponse> categoriesResponseCall = service.getListCategories();
        categoriesResponseCall.enqueue(new Callback<ProductCategoriesResponse>() {

            @Override
            public void onResponse(Call<ProductCategoriesResponse> call, Response<ProductCategoriesResponse> response) {
                ProductCategoriesResponse categoriesResponse = response.body();
                categoriList = categoriesResponse.getProductCategories();
                nestedCategory = ExploreLibrary.tranformToHierarchy(categoriList);
                EventBus.getDefault().post(new CategoryLoadingEvent(true));
            }
            @Override
            public void onFailure(Call<ProductCategoriesResponse> call, Throwable t) {
                Log.e("Error",t.getMessage());
                EventBus.getDefault().post(new CategoryLoadingEvent(false));
            }
        });
    }

    //load product list by a single category selected from server
    public void setupViewPager(final List<ProductCategory> categories ) {
        // clear all tabs and viewpagers
        if (manager.getFragments() != null)
        {
            manager.getFragments().clear();
            tabLayout.removeAllTabs();
            pageViewerAdapter = new PagerViewAdapter(manager);
            viewPager.setAdapter(pageViewerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        for (final ProductCategory singleCategory : categories)
        {
            Call<ProductsResponse> productsResponseCall = service.getListProductByCategory(singleCategory.getName());
            productsResponseCall.enqueue(new Callback<ProductsResponse>() {

                @Override
                public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                    ProductsResponse productsResponse = response.body();
                    List<Product> productListByCategory = productsResponse.getProducts();
                    EventBus.getDefault().post(new ProductLoadingEvent(true, productListByCategory, singleCategory.getName()));
            }
                @Override
                public void onFailure(Call<ProductsResponse> call, Throwable t) {
                    //Notifice that callback was fall
                }
            });
        }
    }

    @Subscribe
    public void onProductCategoryLoading(CategoryLoadingEvent event)
    {
        if (event.getStatus() == true)
        {
            ArrayList<String> listItemSpinner = new ArrayList<>();
            for (ProductCategory singleCategory : nestedCategory)
            {
                listItemSpinner.add(singleCategory.getName());
            }
            spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.sprinner_item, listItemSpinner);
            spinnerAdapter.setDropDownViewResource(R.layout.sprinner_item);
            spinner.setAdapter(spinnerAdapter);
        }
    }

    @Subscribe
    public void onProductLoadingEvent(ProductLoadingEvent event)
    {
        pageViewerAdapter.addFrag(new ProductListFragment(event.getProductList()),event.getCategoryName());
        viewPager.setAdapter(pageViewerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected  void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop()
    {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.explore_menu, menu);
        return true;
    }

}

