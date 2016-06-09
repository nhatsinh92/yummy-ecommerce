package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductCategory;
import me.kimhieu.yummy.ecommerceproject.model.ProductResponse;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout thumbnailsContainer;

    private TextView textViewProductName;
    private RatingBar ratingBar;
    private TextView textViewNumberOfComment;
    private TextView textViewRegularPrice;
    private TextView textViewPrice;
    private TextView textViewDescription;
    private ImageView iv_product_image;
    private CircleImageView civ_category_image_in_product_detail;
    private String productCategory;
    private ProductCategory cate;
    private TextView textViewCategoryName;
    private ImageView imageViewLike;
    private ImageView imageViewComment;
    private ImageView imageViewMore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_in_product_detail);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.view_pager_product_detail);

        civ_category_image_in_product_detail = (CircleImageView) findViewById(R.id.circle_image_view_category_in_product_detail);
        textViewCategoryName = (TextView) findViewById(R.id.text_view_category_name_in_product_detail) ;
        textViewProductName = (TextView) findViewById(R.id.text_view_product_name_in_product_detail);
        textViewPrice = (TextView) findViewById(R.id.text_view_price_in_product_detail);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);

        imageViewLike = (ImageView) findViewById(R.id.image_view_like);
        textViewDescription = (TextView) findViewById(R.id.text_view_description_in_product_detail);


        Intent intent = getIntent();
        //int productId = intent.getIntExtra(ProductListByCategoryActivity.PRODUCT_ID, -1);
        int productId = 99;

        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);

        // Call woo commerce service to get product details
        Call<ProductResponse> productsResponseCall = service.getProductById(productId);
        productsResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    Product product = productResponse.getProduct();
                    productCategory = product.getCategories().get(0);
                    myToolbar.setTitle("Product Detail");

                    WooCommerceService service1 = ServiceGenerator.createService(WooCommerceService.class);
//                    // Call woo commerce service to get all categories
//                    Call<ProductCategoriesResponse> productCategoryResponseCall = service1.getListCategories();
//                    productCategoryResponseCall.enqueue(new Callback<ProductCategoriesResponse>() {
//                        @Override
//                        public void onResponse(Call<ProductCategoriesResponse> call, Response<ProductCategoriesResponse> response) {
//                            ProductCategoriesResponse productCategoriesResponse = response.body();
//                            List<ProductCategory> categoryList = productCategoriesResponse.getProductCategories();
//                            for (ProductCategory category:categoryList) {
//                                if (category.getName().equals(productCategory)) {
//                                    cate = category;
//                                    System.out.println("asdasdasdasdasd" + cate.getImage());
//                                    break;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ProductCategoriesResponse> call, Throwable t) {
//
//                        }
//                    });

                    displayProduct(product);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

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
        // init viewpager adapter and attach
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), product.getImages());
        viewPager.setAdapter(adapter);

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

}
