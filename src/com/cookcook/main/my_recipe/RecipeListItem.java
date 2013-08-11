package com.cookcook.main.my_recipe;

import com.cookcook.main.http.RestClient;
import com.cookcook.main.my_recipe.Item;
import com.cookcook.main.socialfragment.SimpleTextArrayAdapter.RowType;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cookcook.main.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RecipeListItem implements Item {
    private final String str1;
    private final String  icon;
    private final String id;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    
    DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showStubImage(R.drawable.ic_stub)
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.ic_empty)
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.build();

    public RecipeListItem(String text1, String image1, String id) {
        this.str1 = text1;
        this.icon = image1;
        this.id = id;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    public String getName()
    {
    	return str1;
    }
    public String getRecipe_id()
    {
    	return id;
    }
    public String getAmount()
    {
    	return str1;
    }
    
    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
//        Log.v("====text listitem===", "getview:");
        if (convertView == null) {
            view = inflater.inflate(R.layout.recipe_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.list_recipe_title_dish);
        ImageView image1 = (ImageView) view.findViewById(R.id.list_recipe_avatar_dish);
        text1.setText(str1);
        imageLoader.displayImage(RestClient.getAbsoluteUrl(icon), image1, options);
//        image1.setImageResource(icon);
//        Log.v("====text listitem===", "getview1:");
        return view;
    }

}
