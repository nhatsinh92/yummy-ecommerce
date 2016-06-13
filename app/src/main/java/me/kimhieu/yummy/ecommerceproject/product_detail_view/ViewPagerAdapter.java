package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Image;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Image> images;
    private List<Integer> relatedProductIds;

    public ViewPagerAdapter(Context context, FragmentManager fm, List<Image> imagesList, List<Integer> idsList) {
        super(fm);
        this.context = context;
        this.images = imagesList;
        this.relatedProductIds = idsList;

    }

    @Override
    public Fragment getItem(final int position) {
        String s = images.get(position).getSrc();
        final Integer i = relatedProductIds.get(position);
        Fragment f =  PageFragment.getInstance(s, i);
        return f;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public void updatePageViewer(Image image, int id) {
        this.images.add(image);
        this.relatedProductIds.add(id);
        notifyDataSetChanged();
    }
}
