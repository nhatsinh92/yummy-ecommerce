package me.kimhieu.yummy.ecommerceproject.checkout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;

/**
 * Created by zyk on 6/12/2016.
 */
public class CheckoutRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Product> dataSet;
    private Context context;
    public CheckoutRecyclerViewAdapter(Context context, List<Product>productList){
        this.context = context;
        this.dataSet = productList;
    }

    public static class ProductsCheckoutViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewProductPicture;
        public TextView textViewProductName;
        public TextView textViewCategoryName;
        public TextView textViewPrice;
        public ImageView imageViewAddButton;

        public ProductsCheckoutViewHolder(View itemView) {
            super(itemView);
            imageViewProductPicture = (ImageView) itemView.findViewById(R.id.image_view_checkout_product);
            textViewProductName = (TextView) itemView.findViewById(R.id.text_view_checkout_product_name);
            textViewCategoryName = (TextView) itemView.findViewById(R.id.text_view_checkout_category_name);
            textViewPrice = (TextView) itemView.findViewById(R.id.text_view_checkout_price);
            imageViewAddButton = (ImageView) itemView.findViewById(R.id.image_view_checkout_add_button);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_product_checkout_list,parent,false);
        ProductsCheckoutViewHolder vh = new ProductsCheckoutViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(this.context)
                .load(dataSet.get(position).getImages().get(0).getSrc())
                .into(((ProductsCheckoutViewHolder)holder).imageViewProductPicture);
        ((ProductsCheckoutViewHolder)holder).textViewProductName.setText(dataSet.get(position).getTitle());
        ((ProductsCheckoutViewHolder)holder).textViewCategoryName.setText(dataSet.get(position).getCategories().get(0));
        String priceInDollar = "$" + dataSet.get(position).getPrice();
        ((ProductsCheckoutViewHolder)holder).textViewPrice.setText(priceInDollar);

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