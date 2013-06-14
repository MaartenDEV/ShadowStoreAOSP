package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class SendChatMessage extends Activity {
	
	
    
    
    EditText nametext;
    EditText mailtext;
    EditText bugtext;
	
    
    Context context = this;
    
	
	
	@Override
	public void onCreate(Bundle SavedInstancestate) {
	super.onCreate(SavedInstancestate);
	
	ActionBar bar = getActionBar();
	
	
	bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
	setTitle("New message");
	
	
	
	ParseUser currentUser = ParseUser.getCurrentUser();
	if (currentUser != null) {
	  // do stuff with the user
		setContentView(R.layout.message);
		
		
		
		
		bugtext = (EditText) findViewById(R.id.messagetext);
		mailtext = (EditText) findViewById(R.id.mailtext);
		
		mailtext.setHint("Email, optional...");
		
		
	} else {
	  // show the signup or login screen
		AlertDialog.Builder login = new AlertDialog.Builder(this);
		
		login.setTitle("Not logged in");
		login.setMessage("You have to be logged in to post a message, would you like to login now?");
		login.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			
				Intent intent = new Intent(context, LogIn.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		login.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			
				Intent intent = new Intent(context, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		login.show();
		
	}
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	public void sendBug(View v) {
		
		
		
		
		
			
			if (bugtext.getText().toString().equalsIgnoreCase("")) {
				
				AppMsg.makeText(this, "Please enter a valid message", AppMsg.STYLE_ALERT).show();
				
			} else {
		
					
			
					
				ParseUser currentUser = ParseUser.getCurrentUser();
				
		ParseObject bugs = new ParseObject("Chat");
		bugs.put("message", bugtext.getText().toString());
		bugs.put("name", currentUser.getUsername());
		
		
		if (mailtext.getText().toString().equalsIgnoreCase("")) {
			
			bugs.put("mail", "No email");
			
		} else {
			
			bugs.put("mail", mailtext.getText().toString());
		}
		
		
		
		bugs.saveInBackground();
		AppMsg.makeText(this, "Message has been send", AppMsg.STYLE_CONFIRM).show();
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	
				
			
		}
		
		Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		vibrate.vibrate(30);
		
	}

	
        
}