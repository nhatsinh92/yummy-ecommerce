package me.kimhieu.yummy.ecommerceproject.onsale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public TextView textViewProductName;

        public ProductsOnSaleViewHolder(View itemView) {
            super(itemView);
            textViewProductName = (TextView) itemView.findViewById(R.id.text_view_on_sale_product_name);
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
        ((ProductsOnSaleViewHolder)holder).textViewProductName.setText(dataSet.get(position).getTitle());
        Random ran = new Random();
        int size = ran.nextInt(50);
        ((ProductsOnSaleViewHolder)holder).textViewProductName.setTextSize(size);
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
