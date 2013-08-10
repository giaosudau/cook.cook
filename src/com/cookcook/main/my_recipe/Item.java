package com.cookcook.main.my_recipe;

import android.view.LayoutInflater;
import android.view.View;

public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
    public String getName();
    public String getRecipe_id();
    public String getAmount();
}