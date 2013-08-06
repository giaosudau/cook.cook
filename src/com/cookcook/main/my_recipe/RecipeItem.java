package com.cookcook.main.my_recipe;

import com.cookcook.main.my_recipe.Item;
import com.cookcook.main.socialfragment.SimpleTextArrayAdapter.RowType;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.cookcook.main.R;

public class RecipeItem implements Item {
    private final String str1;
    private final String str2;

    public RecipeItem(String text1, String text2) {
        this.str1 = text1;
        this.str2 = text2;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    public String getName()
    {
    	return str1;
    }
    public String getAmount()
    {
    	return str2;
    }
    
    public int getRecipe_id()
    {
    	return 0;
    }
    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        Log.v("====text listitem===", "getview:");
        if (convertView == null) {
            view = inflater.inflate(R.layout.simple_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.list_item_text);
        text1.setText(str1+str2);
        Log.v("====text listitem===", "getview1:");
        return view;
    }

}
