package me.kimhieu.yummy.ecommerceproject.onsale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.Product;

public class OnSaleRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Product> dataSet;
    private Context context;

    public OnSaleRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.dataSet = productList;
    }

    public static class ProductsOnSaleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProductPicture;
        public TextView textViewProductName;
        public TextView textViewCategoryName;
        public TextView textViewPrice;
        public ImageView imageViewAddButton;

        public ProductsOnSaleViewHolder(View itemView) {
            super(itemView);
            imageViewProductPicture = (ImageView) itemView.findViewById(R.id.image_view_on_sale_product);
            textViewProductName = (TextView) itemView.findViewById(R.id.text_view_on_sale_product_name);
            textViewCategoryName = (TextView) itemView.findViewById(R.id.text_view_on_sale_category_name);
            textViewPrice = (TextView) itemView.findViewById(R.id.text_view_on_sale_price);
            imageViewAddButton = (ImageView) itemView.findViewById(R.id.image_view_on_sale_add_button);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_products_on_sale_list,parent,false);
        ProductsOnSaleViewHolder vh = new ProductsOnSaleViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(this.context)
                .load(dataSet.get(position).getImages().get(0).getSrc())
                .into(((ProductsOnSaleViewHolder)holder).imageViewProductPicture);
        ((ProductsOnSaleViewHolder)holder).textViewProductName.setText(dataSet.get(position).getTitle());
        ((ProductsOnSaleViewHolder)holder).textViewCategoryName.setText(dataSet.get(position).getCategories().get(0));
        ((ProductsOnSaleViewHolder)holder).textViewPrice.setText("$" + dataSet.get(position).getPrice());
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
