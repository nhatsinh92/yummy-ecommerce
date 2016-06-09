package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Image;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Image> images;

    public ViewPagerAdapter(FragmentManager fm, List<Image> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images.get(position).getSrc());
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
