package com.cookcook.main.navi_drawer;

import com.cookcook.main.navi_drawer.TwoTextArrayAdapter.RowType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cookcook.main.R;

public class ListItem implements Item {
    private final String str1;
    private final Integer  icon;

    public ListItem(String text1, Integer image1) {
        this.str1 = text1;
        this.icon = image1;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.drawer_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.navi_menu_text);
        ImageView image1 = (ImageView) view.findViewById(R.id.navi_menu_icon);
        text1.setText(str1);
        image1.setImageResource(icon);

        return view;
    }

}
