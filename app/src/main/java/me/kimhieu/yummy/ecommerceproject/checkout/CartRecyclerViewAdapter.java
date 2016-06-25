package me.kimhieu.yummy.ecommerceproject.checkout;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.product_detail_view.ProductDetailActivity;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Product> dataSet;
    private Context context;

    public CartRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.dataSet = productList;
    }

    public static class ProductsCartViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProductPicture;
        public TextView textViewProductName;
        public TextView textViewCategoryName;
        public TextView textViewPrice;
        public ImageView imageViewRemoveButton;

        public ProductsCartViewHolder(View itemView) {
            super(itemView);
            imageViewProductPicture = (ImageView) itemView.findViewById(R.id.image_view_cart_product);
            textViewProductName = (TextView) itemView.findViewById(R.id.text_view_cart_product_name);
            textViewCategoryName = (TextView) itemView.findViewById(R.id.text_view_cart_category_name);
            textViewPrice = (TextView) itemView.findViewById(R.id.text_view_cart_price);
            imageViewRemoveButton = (ImageView) itemView.findViewById(R.id.image_view_cart_remove_button);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_product_cart_list,parent,false);
        ProductsCartViewHolder vh = new ProductsCartViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(this.context)
                .load(dataSet.get(position).getImages().get(0).getSrc())
                .into(((ProductsCartViewHolder)holder).imageViewProductPicture);
        ((ProductsCartViewHolder)holder).textViewProductName.setText(dataSet.get(position).getTitle());
        ((ProductsCartViewHolder)holder).textViewCategoryName.setText(dataSet.get(position).getCategories().get(0));
        String priceInDollar = "$" + dataSet.get(position).getPrice();
        ((ProductsCartViewHolder)holder).textViewPrice.setText(priceInDollar);

        // Click on any item in product view. Change to ProductDetailActivity
        ProductClickListener productClickListener = new ProductClickListener(position);
        ((ProductsCartViewHolder)holder).imageViewProductPicture.setOnClickListener(productClickListener);
        ((ProductsCartViewHolder)holder).textViewProductName.setOnClickListener(productClickListener);
        ((ProductsCartViewHolder)holder).textViewCategoryName.setOnClickListener(productClickListener);
        ((ProductsCartViewHolder)holder).textViewPrice.setOnClickListener(productClickListener);

        // Click on add button. Add to that product to Cart
        ((ProductsCartViewHolder)holder).imageViewRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        dataSet.get(position).getTitle() + " is removed from cart",
                        Toast.LENGTH_SHORT).show();
                YummySession.cart.remove(position);
                setProductList(new ArrayList<>(YummySession.cart));
                TextView textViewTotalPrice = (TextView) ((BaseActivity)context).findViewById(R.id.text_view_total_price);
                textViewTotalPrice.setText("$" + CheckoutActivity.CalculateTotalPrice());
            }
        });

    }

    private class ProductClickListener implements View.OnClickListener {

        int position;

        public ProductClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(YummySession.PRODUCT_ID, dataSet.get(position).getId());
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setProductList (List<Product> list) {
        this.dataSet = list;
        notifyDataSetChanged();
    }
}
