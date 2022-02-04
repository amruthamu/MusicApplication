
package com.example.foodapplication;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodItemsNew {

    @SerializedName("f_items")
    @Expose
    private List<FItem> fItems = null;

    public List<FItem> getfItems() {
        return fItems;
    }

    public void setfItems(List<FItem> fItems) {
        this.fItems = fItems;
    }

    public FoodItemsNew withfItems(List<FItem> fItems) {
        this.fItems = fItems;
        return this;
    }

}
