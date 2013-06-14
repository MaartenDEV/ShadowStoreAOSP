package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends Activity {
	
	
    
    EditText signupname;
    EditText signuppassword;
    EditText signuppasswordconfirm;
    
    
    
    Context context = this;
    
    Activity context1 = this;
	
	
	
	@Override
	public void onCreate(Bundle SavedInstancestate) {
	super.onCreate(SavedInstancestate);
	
	
	
		setContentView(R.layout.signup);
		// show the signup or login screen
	
		ActionBar bar = getActionBar();
		setTitle("Sing up");
		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
	  
	
	
	
		
	
	signupname = (EditText) findViewById(R.id.signupname1);
	signuppassword = (EditText) findViewById(R.id.signuppassword1);
	signuppasswordconfirm = (EditText) findViewById(R.id.signuppasswordconfirm1);
	
	
	
	
	}
	
	
	
	public void signUp (View v) {
		
		if (signuppassword.getText().toString().equalsIgnoreCase(signuppasswordconfirm.getText().toString())) {
		
		try {	
		ParseUser user = new ParseUser();
		
		if (signuppassword.getText().toString().equalsIgnoreCase("")) {
			
			AppMsg.makeText(this, "Please type in a valid password", AppMsg.STYLE_ALERT);
			
		} else {
		
		user.setUsername(signupname.getText().toString());
		user.setPassword(signuppassword.getText().toString());
		
		}
		
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    	AppMsg.makeText(context1, "Your account has been signed up and logged in", AppMsg.STYLE_CONFIRM).show();
			    	Intent intent = new Intent(context, MainActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        context.startActivity(intent);
			    } else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    AppMsg.makeText(context1, "This username has already been taken", AppMsg.STYLE_ALERT).show();	
			    }
			  }
			});
		} catch (IllegalArgumentException IO ) {
			
			
			AppMsg.makeText(context1, "Please enter a valid name or password", AppMsg.STYLE_ALERT).show();
			
		}
		
		
		
		} else {
			
			AppMsg.makeText(context1, "Passwords don't match", AppMsg.STYLE_ALERT).show();
			
		}
		
		
		
	}
	
	
	
	
        
}