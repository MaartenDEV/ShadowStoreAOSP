package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;


public class Preferences extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {
	
	
	
	Context context = this;
	Activity context1 = this;
	

	EditTextPreference username;
	CheckBoxPreference scrollbars;
	
	

	
	
	
	
		
	
	
	
	
	
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		
		ActionBar bar = getActionBar();
		setTitle("Preferences");
		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
				
		
		
				username = (EditTextPreference) findPreference("username");
				
				scrollbars = (CheckBoxPreference) findPreference("scrollbars");
				scrollbars.setOnPreferenceClickListener(this);
				
				
				
				
		
		
	}
	
	
	@Override
	public boolean onPreferenceChange(Preference pref, Object newvalue) {
		// TODO Auto-generated method stub
                /*
                * we can't use the case statement here, but we can get the "clicked" item using the IF statement
                */
		
		
		if (pref == username) {
			
			
			
		}
		
		
		
			
			

		
		

		return false;
	}
	
	
	@Override
	public boolean onPreferenceClick(Preference pref) {
		
		boolean scrollbarv = (scrollbars.isChecked());
		
		if (pref == scrollbars) {
			
			if (scrollbarv == true) {
				
				News MainActivity = new News();
				MainActivity.scrollbars("disable");
				Skins Skins = new Skins();
				Skins.scrollbars("disable");
				
			}
			
			if (scrollbarv == false) {
				
				News MainActivity = new News();
				MainActivity.scrollbars("enable");
				Skins Skins = new Skins();
				Skins.scrollbars("enable");
				
			}
			
			
			
			
			
		}
		

		
		// TODO Auto-generated method stub
		return false;
		

}
	
	
	
	
	
	
	private void displaySharedPreferences() {
		   SharedPreferences prefs = PreferenceManager
		    .getDefaultSharedPreferences(Preferences.this);
		 
		   String username = prefs.getString("username", "Shadow");
		   String passw = prefs.getString("password", "Default Password");
		   boolean checkBox = prefs.getBoolean("checkBox", false);
		   String listPrefs = prefs.getString("listpref", "Default list prefs");
		 
		  
		 
		   
		   
		   
		   		
			   
			   Toast.makeText(getApplicationContext(), "Checkbox: "+String.valueOf(checkBox), Toast.LENGTH_SHORT).show();
			   
		   		
		   		
		   		
		 		   
		 		   Toast.makeText(getApplicationContext(), "Your password is: "+ passw, Toast.LENGTH_SHORT).show();
		 		   
		 		  Toast.makeText(getApplicationContext(), "List preference:  "+ listPrefs, Toast.LENGTH_SHORT).show();
		 		   
		 		 Toast.makeText(getApplicationContext(), "Your username is:  "+ username, Toast.LENGTH_SHORT).show();
		 		   
		 	   		
			   
			   
		   
		}
	
	
	
	
	
	
	
	public void runRosie5 () {
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox mount -o remount,rw /system"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox cp /sdcard/Shadow/Launcher/Rosie5.apk /system/app/Rosie.apk"}); } 
		catch (Exception ex) { ex.printStackTrace(); }

		AppMsg.makeText(context1, "Installing 5 screen Rosie, please wait...", AppMsg.STYLE_INFO).show();
		
		
	}
	
	public void runRosie7 () {
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox mount -o remount,rw /system"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox cp /sdcard/Shadow/Launcher/Rosie7.apk /system/app/Rosie.apk"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		AppMsg.makeText(context1, "Installing 7 screen Rosie, please wait...", AppMsg.STYLE_INFO).show();
	}
	
	
	public void runRosie4x5 () {
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox mount -o remount,rw /system"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox cp /sdcard/Shadow/Launcher/Rosie4x5.apk /system/app/Rosie.apk"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		AppMsg.makeText(context1, "Installing 4x5 Rosie, please wait...", AppMsg.STYLE_INFO).show();
	}
	
	public void runRosie5x4 () {
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox mount -o remount,rw /system"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox cp /sdcard/Shadow/Launcher/Rosie5x4.apk /system/app/Rosie.apk"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		AppMsg.makeText(context1, "Installing 5x4 Rosie, please wait...", AppMsg.STYLE_INFO).show();
	}
	
	public void runRosie5x5 () {
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox mount -o remount,rw /system"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		try { Process process = Runtime.getRuntime().exec(new String[]{ "su", "-c", "busybox cp /sdcard/Shadow/Launcher/Rosie5x5.apk /system/app/Rosie.apk"}); } 
		catch (Exception ex) { ex.printStackTrace(); }
		AppMsg.makeText(context1, "Installing 5x5 Rosie, please wait...", AppMsg.STYLE_INFO).show();
	}


	
	
	
	
	
	 
}
