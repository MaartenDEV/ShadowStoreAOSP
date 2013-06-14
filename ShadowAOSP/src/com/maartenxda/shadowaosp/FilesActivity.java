package com.maartenxda.shadowaosp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



public class FilesActivity extends ListActivity {

	private List<String> item = null;

	 private List<String> path = null;

	 private String root="/sdcard/Shadow/Downloads/";

	 private TextView myPath;

	 
	
	    
	 
	 
	 
	 
	 
	 	
	 	
	 


	    /** Called when the activity is first created. */

	 LinearLayout layout1;
	 LinearLayout layout2;
	 
	    @Override

	    public void onCreate(Bundle savedInstanceState) {

	        super.onCreate(savedInstanceState);

	        
	        setTitle("Downloaded files");
	         setContentView(R.layout.activity_files);
	          	
	    			
	         ActionBar bar = getActionBar();
	 		
	 		
	 		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
	    			

	        myPath = (TextView)findViewById(R.id.path);
	        
	        

	        getDir(root);

	    }

	    

	    private void getDir(String dirPath)

	    {

	     myPath.setText("Location: " + dirPath);

	     

	     item = new ArrayList<String>();

	     path = new ArrayList<String>();

	     

	     File f = new File(dirPath);

	     File[] files = f.listFiles();

	     

	     if(!dirPath.equals(root))

	     {



	      item.add(root);

	      path.add(root);

	      

	      item.add("../");

	      path.add(f.getParent());

	            

	     }

	     

	     for(int i=0; i < files.length; i++)

	     {

	       File file = files[i];

	       path.add(file.getPath());

	       if(file.isDirectory())

	        item.add(file.getName() + "/");

	       else

	        item.add(file.getName());

	     }



	     ArrayAdapter<String> fileList =

	      new ArrayAdapter<String>(this, R.layout.row, item);

	     setListAdapter(fileList);

	    }



	 @Override

	 protected void onListItemClick(ListView l, View v, int position, long id) {

	  

	  File file = new File(path.get(position));
	  
	  
   
   if (file.toString().endsWith("apk")) {

 	  Intent intent = new Intent(Intent.ACTION_VIEW);
 	  intent.setDataAndType(Uri.fromFile(new File(path.get(position))), "application/vnd.android.package-archive");
 	  this.startActivity(intent);
   
   }
   
   if (file.toString().endsWith("zip")) {

	   Intent intent = new Intent();
       intent.setAction(android.content.Intent.ACTION_VIEW);
       
      
       MimeTypeMap mime = MimeTypeMap.getSingleton();
       String ext=file.getName().substring(file.getName().indexOf(".")+1);
       String type = mime.getMimeTypeFromExtension(ext);
     
       intent.setDataAndType(Uri.fromFile(file),type);
      
       startActivity(intent);
	   
	   
   }
	  
	  if (file.isDirectory())

	  {

	   if(file.canRead())

	    getDir(path.get(position));

	   else

	   {

	    
	   }

	  }

	  else

	  {

	   

	  }

	 }

	}