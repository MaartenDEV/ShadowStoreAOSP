package com.maartenxda.shadowaosp;


import android.app.ActionBar;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.parse.ParsePush;



public class SendMessage extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

	
	
	
	EditTextPreference message;
	Preference send;
	
	
	ParsePush push;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.send_prefs);
		
		setTitle("New message");
		ActionBar bar = getActionBar();
		
		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
		
		push = new ParsePush();
		
		message = (EditTextPreference) findPreference("message");
		message.setOnPreferenceChangeListener(this);
		
		send = (Preference) findPreference("send");
		send.setOnPreferenceClickListener(this);
		
		
		
		
		
		
		

	}

	@Override
	public boolean onPreferenceChange(Preference pref, Object newValue) {
		// TODO Auto-generated method stub
		
		if (pref == message) {
		    
			push.setMessage(newValue.toString());	
			
			
			
			
		}
		
		
		return false;
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		
		if (pref == send) {
			
			
			
	        push.setChannel("All");
	        
	        push.sendInBackground();
	        
			
		}
		
		
		
		return false;
	}

	

	

}