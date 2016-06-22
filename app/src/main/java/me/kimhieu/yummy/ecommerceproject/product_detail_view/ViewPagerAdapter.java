package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
//    private Context context;
//    private List<Image> images;
//    private List<Integer> relatedProductIds;
//
//    public ViewPagerAdapter(Context context, FragmentManager fm, List<Image> imagesList, List<Integer> idsList) {
//        super(fm);
//        this.context = context;
//        this.images = imagesList;
//        this.relatedProductIds = idsList;
//    }
//
//    public ViewPagerAdapter(Context context, FragmentManager fm, List<Image> imagesList) {
//        super(fm);
//        this.context = context;
//        this.images = imagesList;
//    }
//
//    @Override
//    public Fragment getItem(final int position) {
//        String s = images.get(position).getSrc();
//        int productId;
//        try{
//            productId = relatedProductIds.get(position);
//        }
//        catch (Exception e){
//            productId = -1;
//        }
//
//        Fragment f =  PageFragment.getInstance(s, productId);
//        return f;
//    }
//
//    @Override
//    public int getCount() {
//        return images.size();
//    }
//
//    public void updatePageViewer(Image image, int id) {
//        this.images.add(image);
//        this.relatedProductIds.add(id);
//        notifyDataSetChanged();
//    }
    private List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    public void updatePageViewer(Fragment fragment) {
        this.fragmentList.add(fragment);
        notifyDataSetChanged();
    }
}
