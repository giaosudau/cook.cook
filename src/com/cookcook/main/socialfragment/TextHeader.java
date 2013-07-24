package com.cookcook.main.socialfragment;

import com.cookcook.main.socialfragment.SimpleTextArrayAdapter.RowType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cookcook.main.R;

public class TextHeader implements Item {
    private final String  name;
    private final Integer  icon;

    public TextHeader(String name) {
        this.name = name;
        this.icon = R.drawable.btn_radio_on_focused_holo_light;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }
    
    public String getName()
    {
    	return name;
    }
    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.simple_list_header_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.list_header_item_text);
        text.setText(name);
        ImageView image1 = (ImageView) view.findViewById(R.id.list_header_item_img);
        image1.setImageResource(icon);

        return view;
    }

}