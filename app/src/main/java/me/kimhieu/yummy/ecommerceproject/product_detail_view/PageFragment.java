package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.kimhieu.yummy.ecommerceproject.R;

public class PageFragment extends android.support.v4.app.Fragment{

    private String imageResource;
    private int productId;
    private String productName;

    public static final PageFragment getInstance(String imageUrl, Integer id, String productName) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", imageUrl);
        args.putInt("product_id", id);
        args.putString("product_name", productName);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageResource = getArguments().getString("image_source");
        productId = getArguments().getInt("product_id");
        productName = getArguments().getString("product_name");

        final View view =  inflater.inflate(R.layout.fragment_page, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductDetailActivity)v.getContext()).reloadData(productId);
            }
        });

        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_view_pager);
        TextView textViewRelatedProductName = (TextView) view.findViewById(R.id.text_view_related_product_name);
        Glide.with(view.getContext())
                .load(imageResource)
                .placeholder(R.drawable.place_holder)
                .error(R.mipmap.error)
                .dontAnimate()
                .into(imageView);
        if (this.productName.equals("-1")) {
            textViewRelatedProductName.setVisibility(View.INVISIBLE);
        }else {
            textViewRelatedProductName.setVisibility(View.VISIBLE);
            textViewRelatedProductName.setText(productName);
        }
        return view;
    }
}
