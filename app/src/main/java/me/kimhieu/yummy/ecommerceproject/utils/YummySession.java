package me.kimhieu.yummy.ecommerceproject.utils;

import com.auth0.core.UserProfile;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Product;

public class YummySession {

    public static List<Product> cart = new ArrayList<>();
    public static UserProfile userProfile;
    public static int selectedItemPosition = -1;
    public static final String PRODUCT_ID = "me.kimhieu.yummy.ecommerceproject.utils.PRODUCT_ID";
    public static int notificationId = 000;
    public static boolean pushNotification = false;
    public static boolean emailNotification = false;
}
