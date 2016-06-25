package me.kimhieu.yummy.ecommerceproject.product_detail_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.ProductReview;

/**
 * Created by baok2n on 6/13/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter {

    List<ProductReview> productReviews;

    public CommentAdapter(List<ProductReview> productReviews){
        this.productReviews = productReviews;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_user;
        public TextView tv_comment;
        public RatingBar rb_product_rating;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_user = (TextView) itemView.findViewById(R.id.text_view_user);
            tv_comment = (TextView) itemView.findViewById(R.id.text_view_comment);
            rb_product_rating = (RatingBar) itemView.findViewById(R.id.rating_bar_product_rating);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_product_comment,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String reviewerName = productReviews.get(position).getReviewerName();
        String comment = productReviews.get(position).getReview();
        String productRating = productReviews.get(position).getRating();

        ((ViewHolder)holder).tv_user.setText(reviewerName + ":");
        ((ViewHolder)holder).tv_comment.setText(comment);
        ((ViewHolder)holder).rb_product_rating.setRating(Float.parseFloat(productRating));

    }

    @Override
    public int getItemCount() {
        return productReviews.size();
    }
}
