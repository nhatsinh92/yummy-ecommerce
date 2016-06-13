package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.kimhieu.yummy.ecommerceproject.R;

public class PageFragment extends android.support.v4.app.Fragment{

    private String imageResource;
    private int id;

    public static PageFragment getInstance(String imageUrl, Integer id) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", imageUrl);
        args.putInt("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getString("image_source");
        id = getArguments().getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_page, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductDetailActivity)v.getContext()).reloadData(id);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_view_pager);
        Glide.with(view.getContext()).load(imageResource).into(imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
