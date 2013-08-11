package com.cookcook.main.my_recipe;

import java.util.ArrayList;
import java.util.List;

import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class CommentListAdapter extends ArrayAdapter<Model_Comments>{
	
	private final Activity context;
	private  ArrayList<Model_Comments> list;
	DBAdapter mDb;
	static class ViewHolder {
	    public TextView text;
	    public TextView info;
	}
	public CommentListAdapter(Activity context, ArrayList<Model_Comments> list) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.shopping_list_item, list);
		this.context = context;
		this.list = list;
		mDb = new DBAdapter(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		return super.getView(position, convertView, parent);
		View rowView=convertView;
		if(rowView == null)
		{
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView=inflater.inflate(R.layout.comment_list_item, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.txt_body_comment_list);
			viewHolder.info = (TextView) rowView.findViewById(R.id.txt_info_comment_list);
			rowView.setTag(viewHolder);
		}
		
//		rowView.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ViewHolder viewHold=(ViewHolder)v.getTag();
//				Model_Comments element = (Model_Comments)viewHold.check.getTag();
//				element.setSelected(!((Model_Comments)viewHold.check.getTag()).getSelected());
//				viewHold.check.setChecked(element.getSelected());
//				
//				mDb.open();
//				mDb.ModifyShoppingList(element.getName(), element.getSelected()== true?1:0);
////				Toast.makeText(context, "You choose:"+((Model)viewHold.check.getTag()).getName()+";isChecked:"+((Model)viewHold.check.getTag()).getSelected(), Toast.LENGTH_SHORT).show();
//				mDb.close();
//			}
//		});
		ViewHolder viewHolder=(ViewHolder)rowView.getTag();
		viewHolder.text.setText(list.get(position).getComment());
		viewHolder.info.setText("at: "+list.get(position).getTime()+ "; by:"+list.get(position).getName());
		
		//Make strike through on text if checkbox is selected
		return rowView;
	}
	
}