package com.cookcook.main.navi_drawer;

import com.cookcook.main.navi_drawer.TwoTextArrayAdapter.RowType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.cookcook.main.R;

public class Header implements Item {
    private final String  name;

    public Header(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.drawer_list_header_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.navi_menu_text2);
        text.setText(name);

        return view;
    }

}