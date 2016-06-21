package me.kimhieu.yummy.ecommerceproject.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.kimhieu.yummy.ecommerceproject.R;

public class PageFragment extends Fragment{

    public static final String IMAGE_ID = "me.kimhieu.yummy.ecommerceproject.login.IMAGE_ID";

    public static final PageFragment createInstance(int imageResId) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE_ID, imageResId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int imageResId = getArguments().getInt(IMAGE_ID);
        View v = inflater.inflate(R.layout.sign_in_pager_item, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.image_view_sign_in_pager_item);
        imageView.setImageResource(imageResId);

        return v;
    }
}
