package me.kimhieu.yummy.ecommerceproject.explore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;
import me.kimhieu.yummy.ecommerceproject.product_detail_view.ProductDetailActivity;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

/**
 * Created by Tri Nguyen on 6/9/2016.
 */
public class ProductListAdapter extends RecyclerView.Adapter{

    private List<Product> productList;
    private Context mContext;

    static final String DOLLAR = "$ ";

    public List<Product> getProductList() {
        return productList;
    }

    public ProductListAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.mContext = context;
    }

    public static class CategoryViewHolder extends  RecyclerView.ViewHolder {

        public TextView textViewProductName;
        public TextView textViewProductPrice;
        public ImageView imageViewProductImage;
        public CardView productView;
        public ImageView imageViewAddButton;

        public CategoryViewHolder(View itemView)
        {
            super(itemView);
            textViewProductName = (TextView) itemView.findViewById(R.id.cell_product_name);
            textViewProductPrice = (TextView) itemView.findViewById(R.id.cell_product_price);
            imageViewProductImage =  (ImageView) itemView.findViewById(R.id.cell_product_image);
            imageViewAddButton = (ImageView) itemView.findViewById(R.id.cell_product_add_button);
            productView = (CardView) itemView.findViewById(R.id.cell_product);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_product_list,parent,false);
        CategoryViewHolder vh = new CategoryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CategoryViewHolder)holder).textViewProductName.setText(productList.get(position).getTitle());
        ((CategoryViewHolder)holder).textViewProductPrice.setText(DOLLAR + String.valueOf(productList.get(position).getPrice()));
        Glide.with(mContext)
                .load(productList.get(position).getImages().get(0).getSrc())
                .into(((CategoryViewHolder)holder).imageViewProductImage);

        ((CategoryViewHolder)holder).productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra(YummySession.PRODUCT_ID, productList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        ((CategoryViewHolder)holder).imageViewAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YummySession.cart.add(productList.get(position));
                Toast.makeText(mContext,
                        productList.get(position).getTitle() + " is added to cart",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
