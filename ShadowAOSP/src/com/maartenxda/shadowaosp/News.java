package com.maartenxda.shadowaosp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class News extends ListActivity {

	SharedPreferences prefs;

	Context context = this;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	// All static variables
	static final String URL = "http://bitterrific.com/news.xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_DATE = "date";
	static final String KEY_DESC = "description";
	static final String KEY_NEW = "new";

	public void refresh(View v) {

		new ParseXML().execute();

	}

	View.OnClickListener refresh = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new ParseXML().execute();

		}
	};

	private Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {

		}
		return bm;
	}

	PullToRefreshListView pullToRefreshView;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						new ParseXML().execute();
					}
				});

		prefs = PreferenceManager.getDefaultSharedPreferences(News.this);

		String username = prefs.getString("username", "Shadow");

		cd = new ConnectionDetector(this);
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {

			boolean news = prefs.getBoolean("news", true);

			if (String.valueOf(news) == "true") {

				new ParseXML().execute();

			}

		} else {

			AlertDialog.Builder nointernet = new AlertDialog.Builder(this);

			nointernet
					.setTitle("Not connected to internet")
					.setCancelable(false)
					.setMessage(
							"I'm sorry, this app needs to connect to internet. Please try again when you have an internet connection.")
					.setPositiveButton("Exit",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {

									finish();

								}

							});
			nointernet.show();

		}
	}

	ListAdapter adapter;

	static ListView lv;

	public class ParseXML extends AsyncTask<Void, Void, Void> {

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
			 * make connection & download XML here, use your XML parser class
			 * object to parse the xml from here
			 * 
			 * create ArrayList & etc. from here...
			 */

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

				// adding HashList to ArrayList
				menuItems.add(map);
			}

			

			// Adding menuItems to ListView
			adapter = new SimpleAdapter(context, menuItems, R.layout.list_item,
					new String[] { KEY_NAME, KEY_DESC, KEY_DATE }, new int[] {
							R.id.name, R.id.desciption, R.id.date });

			lv = getListView();

			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.name))
							.getText().toString();
					String date = ((TextView) view.findViewById(R.id.date))
							.getText().toString();
					String description = ((TextView) view
							.findViewById(R.id.desciption)).getText()
							.toString();

					// Starting new intent
					Intent in = new Intent(getApplicationContext(),
							SingleMenuItemActivity.class);
					in.putExtra(KEY_NAME, name);
					in.putExtra(KEY_DATE, date);
					in.putExtra(KEY_DESC, description);
					startActivity(in);

				}
			});

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// postexecute logic
			super.onPostExecute(result);

			loading.dismiss();

			pullToRefreshView.onRefreshComplete();

			// Adding menuItems to ListView

			boolean checkBox = prefs.getBoolean("scrollbars", false);

			if (String.valueOf(checkBox) == "false") {

				lv.setVerticalScrollBarEnabled(false);
				lv.setHorizontalScrollBarEnabled(false);

			}

			lv.setDividerHeight(0);

			// ((PullToRefreshListView) getListView()).onRefreshComplete();

			setListAdapter(adapter);

		}

	}

	public void scrollbars(String enabled) {

		if (enabled.equalsIgnoreCase("enable")) {

			lv.setVerticalScrollBarEnabled(false);
			lv.setHorizontalScrollBarEnabled(false);

		} else {

			lv.setVerticalScrollBarEnabled(true);
			lv.setHorizontalScrollBarEnabled(true);

		}

	}

}