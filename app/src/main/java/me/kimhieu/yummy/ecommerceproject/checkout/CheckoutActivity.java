package me.kimhieu.yummy.ecommerceproject.checkout;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    List<PaymentMethod> paymentMethodList = new ArrayList<>();

    private class PaymentMethod {
        View view;
        boolean isChecked;

        public PaymentMethod(View view, boolean isChecked) {
            this.view = view;
            this.isChecked = isChecked;
            setMethod(this.isChecked);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (PaymentMethod method : paymentMethodList) {
                        method.setMethod(false);
                    }
                    PaymentMethod.this.setMethod(true);
                }
            });
        }

        public void setMethod(boolean isChecked) {
            ImageView checkedIcon = (ImageView) view.findViewById(R.id.payment_method_checked);
            if (isChecked) {
                checkedIcon.setVisibility(View.VISIBLE);
            }else {
                checkedIcon.setVisibility(View.INVISIBLE);
            }
        }
    }

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
                if (YummySession.cart.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this,
                            "Your cart is empty. Please add at least one product to your cart",
                            Toast.LENGTH_SHORT).show();
                }else {
                    if (paymentMethodList.isEmpty()) {
                        Toast.makeText(CheckoutActivity.this,
                                "No payment method found. Please add at least one payment method",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        YummySession.cart = new ArrayList<>(); // reset cart
                        ((CartRecyclerViewAdapter)adapter).setProductList(new ArrayList<Product>(YummySession.cart)); // update recycler view
                        Toast.makeText(CheckoutActivity.this, "Your order is confirmed", Toast.LENGTH_SHORT).show();
                        textViewTotalPrice.setText("$" + CalculateTotalPrice());
                        // TODO: send order detail to customer email
                    }
                }
            }
        });

        imageViewAddPaymentMethod = (ImageView) findViewById(R.id.image_view_add_payment_method);
        imageViewAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View methodView = LayoutInflater.from(CheckoutActivity.this).inflate(R.layout.payment_method_item, null);
                if (paymentMethodList.isEmpty()) {
                    paymentMethodList.add(new PaymentMethod(methodView, true));
                }else {
                    paymentMethodList.add(new PaymentMethod(methodView, false));
                }
                // Add payment method to screen
                paymentMethodsHolder.addView(methodView);
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
