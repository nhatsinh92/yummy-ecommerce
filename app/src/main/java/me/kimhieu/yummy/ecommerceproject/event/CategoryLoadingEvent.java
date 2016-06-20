package me.kimhieu.yummy.ecommerceproject.event;

/**
 * Created by Tri Nguyen on 6/14/2016.
 */
public class CategoryLoadingEvent {

    private boolean isCompleted;

    public CategoryLoadingEvent(boolean isCompleted)
    {
       this.isCompleted = isCompleted;
    }

    public boolean getStatus()
    {
        return isCompleted;
    }
}
