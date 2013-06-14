package com.maartenxda.shadowaosp;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Chat extends ListActivity {
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	public static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private List<ParseObject> todos;
	private Dialog progressDialog;

	
	PullToRefreshListView pullToRefreshView;
	

	Context context = this;

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of todos in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Chat");
			query.orderByDescending("_created_at");

			try {
				todos = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		ProgressDialog progressDialog;

		ListView lv;
		
		

		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Loading... Hang on...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.show();

			
			
			
			
			
			super.onPreExecute();

			lv = getListView();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			
			
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chat.this,
					R.layout.todo_row);
			for (ParseObject todo : todos) {
				adapter.add("By: " + ((String) todo.get("name")) + "\n" + "\n"
						+ (String) todo.get("message"));
				
				
				
				
			}
			
			
			
			setListAdapter(adapter);

			TextView empty = (TextView) findViewById(android.R.id.empty);
			empty.setVisibility(View.GONE);

			progressDialog.dismiss();

			lv.setDividerHeight(1);
			
			lv.setHorizontalScrollBarEnabled(false);
			lv.setVerticalScrollBarEnabled(false);
			
			pullToRefreshView.onRefreshComplete();
    		
    		
    		
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.chat);
		
		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		    @Override
		    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		        // Do work to refresh the list here.
		        new RemoteDataTask().execute();
		    }
		});
		
		
		
		

	//	TextView empty = (TextView) findViewById(android.R.id.empty);
	//	empty.setVisibility(View.INVISIBLE);

		new RemoteDataTask().execute();

	}

	
	public void newMessage(View v) {
		
		Intent intent = new Intent(this, SendChatMessage.class);
		
		startActivity(new Intent(this, SendChatMessage.class));
		overridePendingTransition(R.anim.push_right_in,R.anim.push_right_in);
		
		
	}
	
	
	
	public void refreshChat(View v) {
		
		new RemoteDataTask().execute();
		
		
	}
	
	
	
	

	

}