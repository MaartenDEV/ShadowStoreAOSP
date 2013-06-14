package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class SingleMenuItemActivity  extends Activity {
	
	Context context = this;
	

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        
        ActionBar bar = getActionBar();
	
        setTitle("News item");
		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
       
        
        
        
	}

}