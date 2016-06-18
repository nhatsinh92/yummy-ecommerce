package me.kimhieu.yummy.ecommerceproject.utils;

import com.auth0.core.UserProfile;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.Product;

public class YummySession {

    public static List<Product> cart = new ArrayList<>();
    public static UserProfile userProfile;

}
