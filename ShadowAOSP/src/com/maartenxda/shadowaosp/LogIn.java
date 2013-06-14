package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogIn extends Activity {
	
	
    EditText signupname;
    EditText signuppassword;
    EditText signuppasswordconfirm;
    
    EditText loginname;
    EditText loginpassword;
    
    Context context = this;
    Activity context1 = this;
	
	
	@Override
	public void onCreate(Bundle SavedInstancestate) {
	super.onCreate(SavedInstancestate);
	
	
	ActionBar bar = getActionBar();
	setTitle("Log in");
	
	bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
	
	
	ParseUser currentUser = ParseUser.getCurrentUser();
	if (currentUser != null) {
	  // do stuff with the user
		AlertDialog.Builder logged = new AlertDialog.Builder(this);
		logged.setTitle("Already logged in")
			.setMessage("You've already been logged in, do you want to log out now?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					ParseUser.logOut();
					AppMsg.makeText(context1, "You've been logged out", AppMsg.STYLE_CONFIRM).show();
					Intent intent = new Intent(context, MainActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        context.startActivity(intent);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(context, MainActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    startActivity(intent);
				    overridePendingTransition(R.anim.push_right_out,R.anim.push_right_out);
					
				}
				
			})
			.show();
	} else {
	  
		setContentView(R.layout.login);
		// show the signup or login screen
	}
	
	  
	
	
	
		
	
	
	
	loginname = (EditText) findViewById(R.id.loginname);
	loginpassword = (EditText) findViewById(R.id.loginpassword);
	
	
	}
	
	@Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
        overridePendingTransition(R.anim.push_left_out, R.anim.push_left_out);
        super.onPause();
    }
	
	public void goToSignUp(View v) {
		
		
		Intent intent = new Intent(this, SignUp.class);
		startActivity(intent);
		
	}
	
	
		
		
		
	
	
	public void logIn(View v) {
		
		
		ParseUser.logInInBackground(loginname.getText().toString(), loginpassword.getText().toString(), new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
			    	
			    	AppMsg.makeText(context1, "Logged in", AppMsg.STYLE_CONFIRM).show();
			    	Intent intent = new Intent(context, MainActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        context.startActivity(intent);
			    	
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    	AppMsg.makeText(context1, "Either your name or password isn't correct", AppMsg.STYLE_ALERT).show();
			    	
			    }
			  }
			});
		
		
	}
	
	
        
}