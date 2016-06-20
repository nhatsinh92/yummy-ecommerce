package me.kimhieu.yummy.ecommerceproject.utils;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.model.ProductCategory;

/**
 * Created by Tri Nguyen on 6/9/2016.
 */
public class ExploreLibrary {

    public static List<ProductCategory> tranformToHierarchy(List<ProductCategory> list)
    {

        List<ProductCategory> nested = ExploreLibrary.getParent(list);

        for (ProductCategory item : list) {
            if (item.getParent() != 0) {
                int parentPosition = ExploreLibrary.searchItemById(nested, item.getParent());
                if (parentPosition != -1)
                {
                    nested.get(parentPosition).addChild(item);
                }
            }
        }

        for (ProductCategory item : nested) {
            if (item.getChildNumber() ==0)
            {
                item.addChild(item);
            }
        }

        return nested;

    }

    public static List<ProductCategory> getParent(List<ProductCategory> initialList)
    {
        List<ProductCategory> nested = new ArrayList<ProductCategory>();
        for (ProductCategory item : initialList)
        {
            if (item.getParent() == 0)
            {
                nested.add(item);
            }
        }
        return nested;
    }

    public static int searchItemById (List<ProductCategory> items, int id)
    {
        boolean found = false;
        int position = 0;
        for (ProductCategory item : items)
        {
            if (item.getId() == id)
            {
                found = true;
                break;
            }
            position++;
        }
        if (found == false)
        {
            position = -1;
        }
        return position;
    }

    public static  String removeHtmlTag (String inputString)
    {
        StringBuffer newString = new StringBuffer(inputString);
        while(newString.indexOf("<") != -1)
        {
            int beginTag = newString.indexOf("<");
            int endTag = newString.indexOf(">",beginTag)+1;
            newString = newString.delete(beginTag,endTag);
        }
        return newString.toString();
    }
}
