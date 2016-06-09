package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.kimhieu.yummy.ecommerceproject.R;

public class PageFragment extends android.support.v4.app.Fragment{

    private String imageResource;

    public static PageFragment getInstance(String imageUrl) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getString("image_source");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_view_pager);
        Picasso.with(view.getContext()).load(imageResource).into(imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
