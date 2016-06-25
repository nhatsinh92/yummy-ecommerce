package me.kimhieu.yummy.ecommerceproject.checkout;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

public class CheckoutActivity extends BaseActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    TextView textViewTotalPrice;
    TextView textViewPurchase;
    ImageView imageViewAddPaymentMethod;
    LinearLayout paymentMethodsHolder;
    List<View> paymentMethods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getSupportActionBar().setTitle("Checkout");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_products_on_sale);
        adapter = new CartRecyclerViewAdapter(this, new ArrayList<>(YummySession.cart));
        layoutManager = new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        textViewTotalPrice = (TextView) findViewById(R.id.text_view_total_price);
        textViewTotalPrice.setText("$" + CalculateTotalPrice());

        paymentMethodsHolder = (LinearLayout) findViewById(R.id.linear_layout_payment_method);

        textViewPurchase = (TextView) findViewById(R.id.text_view_complete_purchase);
        textViewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageViewAddPaymentMethod = (ImageView) findViewById(R.id.image_view_add_payment_method);
        imageViewAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View method = LayoutInflater.from(CheckoutActivity.this).inflate(R.layout.payment_method_item, null);
                paymentMethods.add(method);
                paymentMethodsHolder.addView(method);
                method.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (View view : paymentMethods) {
                            ImageView unChecked = (ImageView) view.findViewById(R.id.payment_method_checked);
                            unChecked.setVisibility(View.INVISIBLE);
                        }
                        ImageView checked = (ImageView) v.findViewById(R.id.payment_method_checked);
                        checked.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }

    public static int CalculateTotalPrice() {
        int totalPrice = 0;
        for (Product p: YummySession.cart) {
            boolean isNumber = Pattern.matches("[0-9]+", p.getPrice());
            if (isNumber) {
                totalPrice += Integer.parseInt(p.getPrice());
            }
        }
        return totalPrice;
    }
}
