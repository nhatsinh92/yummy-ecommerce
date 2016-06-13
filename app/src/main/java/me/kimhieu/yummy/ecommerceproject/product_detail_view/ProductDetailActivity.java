package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Image;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategory;
import me.kimhieu.yummy.ecommerceproject.model.ProductResponse;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager viewPagerProductImages;
    private ViewPager viewPagerRelatedProductImages;
    private ViewPagerAdapter adapterProductImage;
    private ViewPagerAdapter adapterRelatedProductImage;
    private List<Image> relatedImagesList;

    private TextView textViewProductName;
    private TextView textViewPrice;
    private TextView textViewDescription;
    private CircleImageView civ_category_image_in_product_detail;
    private String productCategory;
    private ProductCategory cate;
    private TextView textViewCategoryName;
    private ImageView imageViewLike;
    private ImageView imageViewComment;
    private ImageView imageViewMore;

    Toolbar myToolbar;

    private int productId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        myToolbar = (Toolbar) findViewById(R.id.toolbar_in_product_detail);
        myToolbar.setTitle("Product Detail");
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        viewPagerProductImages = (ViewPager) findViewById(R.id.view_pager_product_detail);
        viewPagerRelatedProductImages = (ViewPager) findViewById(R.id.view_pager_related_products);

        civ_category_image_in_product_detail = (CircleImageView) findViewById(R.id.circle_image_view_category_in_product_detail);
        textViewCategoryName = (TextView) findViewById(R.id.text_view_category_name_in_product_detail) ;
        textViewProductName = (TextView) findViewById(R.id.text_view_product_name_in_product_detail);
        textViewPrice = (TextView) findViewById(R.id.text_view_price_in_product_detail);

        imageViewLike = (ImageView) findViewById(R.id.image_view_like);
        textViewDescription = (TextView) findViewById(R.id.text_view_description_in_product_detail);


        Intent intent = getIntent();
        //int productId = intent.getIntExtra(ProductListByCategoryActivity.PRODUCT_ID, -1);
        if (productId == -1){
            productId = 99;
        }

        // Call woo commerce service to get product details
        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);
        Call<ProductResponse> productsResponseCall = service.getProductById(productId);
        productsResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    Product product = productResponse.getProduct();
                    productCategory = product.getCategories().get(0);

                    displayProduct(product);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.product_detail_action_bar_cart_button, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    private void displayProduct(Product product) {

        // init product image viewpager adapter and attach
        adapterProductImage = new ViewPagerAdapter(this, getSupportFragmentManager(), product.getImages(), product.getRelatedIds());
        viewPagerProductImages.setAdapter(adapterProductImage);

        // init related product image viewpager adapter and attach
        adapterRelatedProductImage = new ViewPagerAdapter(this, getSupportFragmentManager(), new ArrayList<Image>(), product.getRelatedIds());
        viewPagerRelatedProductImages.setAdapter(adapterRelatedProductImage);

        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);

        // Get related product images
        List<Integer> relatedProductIds =  product.getRelatedIds();
        for (final Integer id:relatedProductIds) {
            Call<ProductResponse> productsResponseCall = service.getProductById(id);
            productsResponseCall.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    ProductResponse productResponse = response.body();
                    Product product = productResponse.getProduct();
                    adapterRelatedProductImage.updatePageViewer(product.getImages().get(0), id);
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {

                }
            });
        }

        // Category Image
        Glide.with(this.getApplicationContext())
                //.load(cate.getImage())
                .load(product.getImages().get(0).getSrc())
                .centerCrop()
                .into((civ_category_image_in_product_detail));

        textViewCategoryName.setText(product.getCategories().get(0));
        textViewProductName.setText(product.getTitle() + " | " + product.getCategories().get(0));

        textViewPrice.setText("$" + product.getPrice());
        textViewDescription.setText(product.getDescription());
    }

    public void reloadData (int id) {
        // Call woo commerce service to get product details
        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);
        Call<ProductResponse> productsResponseCall = service.getProductById(id);
        productsResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    Product product = productResponse.getProduct();
                    productCategory = product.getCategories().get(0);
                    displayProduct(product);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());

            }
        });
    }
}
