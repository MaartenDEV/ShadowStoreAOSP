package com.maartenxda.shadowaosp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;



public class Skins extends ListActivity {
	String name;
	
	static ListView lv;
	
	
	
	
	
	Context context = this;

	// All static variables
	static final String URL = "http://bitterrific.com/skins.xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_DATE = "date";
	static final String KEY_URL = "url";
	static final String KEY_DESC = "description";
	
	TwoStatePreference scrollbar;
	File outputfile;

	
	
	
	View.OnClickListener refresh = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new ParseXML().execute();
			
		}

	};
	
	PullToRefreshListView pullToRefreshView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		    @Override
		    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		        // Do work to refresh the list here.
		        new ParseXML().execute();
		    }
		});
		
		
	/*	((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
		    @Override
		    public void onRefresh() {
		        // Do work to refresh the list here.
		        new RefreshList().execute();
		    }
		});
		*/
		
		SharedPreferences prefs = PreferenceManager
			    .getDefaultSharedPreferences(Skins.this);
			 
			   String username = prefs.getString("username", "Shadow");
		
		setTitle(username + " News");

		 boolean skins = prefs.getBoolean("skins", true);
   		 
		 	if (String.valueOf(skins) == "true") {
				
				
				
				
		
		
		new ParseXML().execute();
		
		 	}
		 	
		 	
				
				
		
				
				// Starting new intent
				
				
				

			
	
	}
	
	public void refresh(View v) {
		
		new ParseXML().execute();
		
	}
	
	class DownloadMapAsync extends AsyncTask<String, String, String> {
		
		File outputfile;
		
		
		ProgressDialog mProgressDialog;
		
	    String result ="";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(context);			
			mProgressDialog.setTitle("Downloading...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			
		
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

		try {

			
			outputfile = new File("/sdcard/Shadow/Downloads/", name+".apk");
			   
		
		URL url = new URL(aurl[0]);
		URLConnection conexion = url.openConnection();
		conexion.connect();
		int lenghtOfFile = conexion.getContentLength();
		InputStream input = new BufferedInputStream(url.openStream());
		
		OutputStream output = new FileOutputStream(outputfile);

		byte data[] = new byte[1024];
		long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				publishProgress(""+(int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}
			output.close();
			input.close();
			result = "true";

		} catch (Exception e) {
			
			result = "false";
		}
		return null;

		}
		@Override
		protected void onProgressUpdate(String... progress) {
			 Log.d("ANDRO_ASYNC",progress[0]);
			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			mProgressDialog.dismiss();
			if(result.equalsIgnoreCase("true")){
				
				
				final File download = new File("/sdcard/Shadow/Downloads/", name+".apk");
	     	
				AlertDialog.Builder install = new AlertDialog.Builder(context);
				install.setTitle("Finished downloading");
				install.setMessage("The download is completed, would you like to install '"+name+"' now?");
				install.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						Intent intent = new Intent(Intent.ACTION_VIEW);
					 	  intent.setDataAndType(Uri.fromFile(download), "application/vnd.android.package-archive");
					 	  context.startActivity(intent);
					}
					
				});
				install.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						
					}
					
				});
				install.show();
			
			}
			else{
				
			}
		}
	}	
	
	
class Refresh extends AsyncTask<String, String, String> {
		
		File outputfile;
		
		
		ProgressDialog mProgressDialog;
		
	    String result ="";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(context);			
			mProgressDialog.setTitle("Downloading...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			
		
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			recreate();
			
		return null;

		}
		@Override
		protected void onProgressUpdate(String... progress) {
			 Log.d("ANDRO_ASYNC",progress[0]);
			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			mProgressDialog.dismiss();
			if(result.equalsIgnoreCase("true")){
				
				
				final File download = new File("/sdcard/Shadow/Downloads/", name+".apk");
	     	
				AlertDialog.Builder install = new AlertDialog.Builder(context);
				install.setTitle("Finished downloading");
				install.setMessage("The download is completed, would you like to install '"+name+"' now?");
				install.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						Intent intent = new Intent(Intent.ACTION_VIEW);
					 	  intent.setDataAndType(Uri.fromFile(download), "application/vnd.android.package-archive");
					 	  context.startActivity(intent);
					}
					
				});
				install.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						
					}
					
				});
				install.show();
			
			}
			else{
				
			}
		}
	}	




ListAdapter adapter;


private class ParseXML extends AsyncTask<Void, Void, Void> {
	
	
	ProgressDialog loading;
	
	
	
	@Override
    protected void onPreExecute() {
        // pre execute logic
        super.onPreExecute();
        
        loading = new ProgressDialog(context);
        
        
        
			
        loading.setMessage("Loading... Hang on...");
			loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loading.setCancelable(false);
        
        
        
        
        loading.show();
        
        
    }

    @Override
    protected Void doInBackground(Void... params) {
        /*
                    make connection & download XML here, 
                    use your XML parser class object to parse the xml from here

                    create ArrayList & etc. from here...

                */
    	
    	SharedPreferences prefs = PreferenceManager
			    .getDefaultSharedPreferences(Skins.this);
			 
			   String username = prefs.getString("username", "Shadow");
			   
			   
			   
    	ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_DATE, parser.getValue(e, KEY_DATE));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
			map.put(KEY_URL, parser.getValue(e, KEY_URL));

			// adding HashList to ArrayList
			menuItems.add(map);
			
		}
		 adapter = new SimpleAdapter(context, menuItems,
				R.layout.skins_item,
				new String[] { KEY_NAME, KEY_DESC, KEY_DATE, KEY_URL }, new int[] {
						R.id.name, R.id.desciption1, R.id.date, R.id.url });
    	
		 lv = getListView();
		 
		 
		 
		 boolean checkBox = prefs.getBoolean("scrollbars", false);
		 
		 if (String.valueOf(checkBox) == "false") {
				
				lv.setVerticalScrollBarEnabled(false);
				lv.setHorizontalScrollBarEnabled(false);
				
				}
    	
		 
		 lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
					String description = ((TextView) view.findViewById(R.id.desciption1)).getText().toString();
					
				   
					
					
					AlertDialog.Builder longclick = new AlertDialog.Builder(context);
					longclick.setTitle(name);
					longclick.setMessage(description+"\n"+"\n"+date);
					longclick.show();
					
					outputfile = new File("/sdcard/Shadow/Downloads/", name+"");
					
					return false;
					
					
					
				}
				
				
				
			});

			
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				
				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					final String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
					final String url = ((TextView) view.findViewById(R.id.url)).getText().toString();
					final String description = ((TextView) view.findViewById(R.id.desciption1)).getText().toString();
					
					File file = new File(Environment.getExternalStorageDirectory()+"/Shadow/Downloads/", name+".apk");
					
					if (file.exists()) {
						
						AlertDialog.Builder downloaded = new AlertDialog.Builder(context);
						downloaded.setTitle("Already downloaded");
						downloaded.setMessage("The file '"+name+"' has already been downloaded, do you want to install it now?");
						downloaded.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								
							}
							
							
							
						});
						
						final File download = new File("/sdcard/Shadow/Downloads/", name+".apk");
						
						downloaded.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								Intent intent = new Intent(Intent.ACTION_VIEW);
							 	  intent.setDataAndType(Uri.fromFile(download), "application/vnd.android.package-archive");
							 	  context.startActivity(intent);
								
							}
							
						});
						
						
						downloaded.show();
					}
				
					else {
						
						AlertDialog.Builder download = new AlertDialog.Builder(context);
						
						download.setTitle(name);
						download.setMessage("Do you want to download '"+name+"' now?");
						download.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								new DownloadMapAsync().execute(url);
								
								
							}
							
							
							
							
						});
						download.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								
								
							}
							
							
							
						});
						download.show();
						
						
						
					}
				
				
				}});
			
			
    	
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        // postexecute logic
        super.onPostExecute(result);
        
        
        
     // Adding menuItems to ListView
        lv.setDividerHeight(0);
		
        pullToRefreshView.onRefreshComplete();

        
     //   ((PullToRefreshListView) getListView()).onRefreshComplete();
        
        
        
		setListAdapter(adapter);
		scrollbars("enable");
		
		loading.dismiss();
    }

    
    

}

private class RefreshList extends AsyncTask<Void, Void, Void> {
	
	
	ProgressDialog loading;
	
	
	
	@Override
    protected void onPreExecute() {
        // pre execute logic
        super.onPreExecute();
        
        loading = new ProgressDialog(context);
        
        
        
			
        loading.setMessage("Loading... Hang on...");
			loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loading.setCancelable(false);
        
        
        
        
     //   loading.show();
        
        
    }

    @Override
    protected Void doInBackground(Void... params) {
        /*
                    make connection & download XML here, 
                    use your XML parser class object to parse the xml from here

                    create ArrayList & etc. from here...

                */
    	
    	SharedPreferences prefs = PreferenceManager
			    .getDefaultSharedPreferences(Skins.this);
			 
			   String username = prefs.getString("username", "Shadow");
			   
			   
			   
    	ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_DATE, parser.getValue(e, KEY_DATE));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
			map.put(KEY_URL, parser.getValue(e, KEY_URL));

			// adding HashList to ArrayList
			menuItems.add(map);
			
		}
		 adapter = new SimpleAdapter(context, menuItems,
				R.layout.list_item,
				new String[] { KEY_NAME, KEY_DESC, KEY_DATE, KEY_URL }, new int[] {
						R.id.name, R.id.desciption1, R.id.date, R.id.url });
    	
		 lv = getListView();
		 
		 
		 
		 boolean checkBox = prefs.getBoolean("scrollbars", false);
		 
		 if (String.valueOf(checkBox) == "false") {
				
				lv.setVerticalScrollBarEnabled(false);
				lv.setHorizontalScrollBarEnabled(false);
				
				}
    	
		 
		 lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
					String description = ((TextView) view.findViewById(R.id.desciption1)).getText().toString();
					
				   
					
					
					AlertDialog.Builder longclick = new AlertDialog.Builder(context);
					longclick.setTitle(name);
					longclick.setMessage(description+"\n"+"\n"+date);
					longclick.show();
					
					outputfile = new File("/sdcard/Shadow/Downloads/", name+"");
					
					return false;
					
					
					
				}
				
				
				
			});

			
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				
				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					final String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
					final String url = ((TextView) view.findViewById(R.id.url)).getText().toString();
					final String description = ((TextView) view.findViewById(R.id.desciption1)).getText().toString();
					
					File file = new File(Environment.getExternalStorageDirectory()+"/Shadow/Downloads/", name+".apk");
					
					if (file.exists()) {
						
						AlertDialog.Builder downloaded = new AlertDialog.Builder(context);
						downloaded.setTitle("Already downloaded");
						downloaded.setMessage("The file '"+name+"' has already been downloaded, do you want to install it now?");
						downloaded.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								
							}
							
							
							
						});
						
						final File download = new File("/sdcard/Shadow/Downloads/", name+".apk");
						
						downloaded.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								Intent intent = new Intent(Intent.ACTION_VIEW);
							 	  intent.setDataAndType(Uri.fromFile(download), "application/vnd.android.package-archive");
							 	  context.startActivity(intent);
								
							}
							
						});
						
						
						downloaded.show();
					}
				
					else {
						
						AlertDialog.Builder download = new AlertDialog.Builder(context);
						
						download.setTitle(name);
						download.setMessage("Do you want to download '"+name+"' now?");
						download.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								new DownloadMapAsync().execute(url);
								
								
							}
							
							
							
							
						});
						download.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								
								
							}
							
							
							
						});
						download.show();
						
						
						
					}
				
				
				}});
			
			
    	
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        // postexecute logic
        super.onPostExecute(result);
        
        loading.dismiss();
        
     // Adding menuItems to ListView
        lv.setDividerHeight(0);
		
        

        
      //  ((PullToRefreshListView) getListView()).onRefreshComplete();
        
        
        
		setListAdapter(adapter);
		scrollbars("enable");
		
		
    }

    
    

}
	

	public void scrollbars(String enabled) {
		
	//	if (enabled.equalsIgnoreCase("enable")) {

//			lv.setVerticalScrollBarEnabled(false);
//			lv.setHorizontalScrollBarEnabled(false);

//		} else {
//		
//			lv.setVerticalScrollBarEnabled(true);
//			lv.setHorizontalScrollBarEnabled(true);
		
		
//		}




	}



}

