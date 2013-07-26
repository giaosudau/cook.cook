package com.cookcook.main.socialfragment;
import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cookcook.main.R;
public class My_Recipe_Builder extends SherlockActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipe_builder);
        }

        //Button next = (Button) findViewById(R.id.Button02);
        //next.setOnClickListener(new View.OnClickListener() {
        /*    public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });*/
	
	@Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        //Used to put dark icons on light action bar
       // boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

        menu.add("Save")
            //.setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
        
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

       
        return true;
    }

}
