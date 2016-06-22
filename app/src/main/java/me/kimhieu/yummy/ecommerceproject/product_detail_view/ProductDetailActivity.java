package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Image;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.model.ProductResponse;
import me.kimhieu.yummy.ecommerceproject.model.ProductReview;
import me.kimhieu.yummy.ecommerceproject.model.ProductReviewsResponse;
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
    private TextView textViewProductName;
    private TextView textViewPrice;
    private TextView textViewDescription;
    private CircleImageView civ_category_image_in_product_detail;
    private TextView textViewCategoryName;
    private ImageView imageViewComment;
    private TextView textViewRelatedProductName;
    private PopupWindow popWindow;

    int mDeviceHeight;
    private String productCategory;
    private int productId = -1;

    // declare recycler view
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Toolbar myToolbar;
    Toolbar reviewToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // setup toolbar for product detail view
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
        imageViewComment = (ImageView) findViewById(R.id.image_view_comment);
        textViewDescription = (TextView) findViewById(R.id.text_view_description_in_product_detail);


        Intent intent = getIntent();
        productId = intent.getIntExtra("PRODUCT_ID", -1);

        // Call woo commerce service to get product details
        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);
        Call<ProductResponse> productsResponseCall = service.getProductById(productId);
        productsResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    final Product product = productResponse.getProduct();
                    productCategory = product.getCategories().get(0);
                    displayProduct(product);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());

            }
        });

        imageViewComment.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view) {
                View v = new View(ProductDetailActivity.this);
                v.findViewById(R.id.relative_layout_popup1);
                displayProductComment();
                onShowPopup(v);
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

        List<Fragment> fragments = new ArrayList<>();
        for (Image image: product.getImages()) {
            Fragment f = PageFragment.getInstance(image.getSrc(), -1, "-1");
            fragments.add(f);
        }
        // init product image viewpager adapter and attach
        adapterProductImage = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPagerProductImages.setAdapter(adapterProductImage);

                // init related product image viewpager adapter and attach
        adapterRelatedProductImage = new ViewPagerAdapter(getSupportFragmentManager(), new ArrayList<Fragment>());
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
                    Fragment f = PageFragment.getInstance(product.getImages().get(0).getSrc(),
                            product.getId(),
                            product.getTitle());
                    adapterRelatedProductImage.updatePageViewer(f);
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

    private void displayProductComment(){

        // Call woo commerce service to get product reviews by id
        WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);
        final Call<ProductReviewsResponse> productReviewsResponseCall = service.getProductReviewsById(productId);
        productReviewsResponseCall.enqueue(new Callback<ProductReviewsResponse>() {
            @Override
            public void onResponse(Call<ProductReviewsResponse> call, Response<ProductReviewsResponse> response) {
                ProductReviewsResponse productReviewsResponse = response.body();
                List<ProductReview> productReviews = productReviewsResponse.getProductReviews();
                recyclerViewAdapter = new CommentAdapter(productReviews);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ProductReviewsResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void reloadData (int id) {
        productId = id;

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

    // call this method when required to show popup
    public void onShowPopup(View v){

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.popup_product_detail_comment, null,false);

        // find the ListView in the popup layout
        recyclerView = (RecyclerView) inflatedView.findViewById(R.id.recycler_view_comment);
        recyclerViewLayoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        mDeviceHeight = size.y;

        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,mDeviceHeight/2, true );
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at TOP ie,
        popWindow.showAtLocation(v, Gravity.TOP, 0,500);
    }
}
