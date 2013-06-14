package com.maartenxda.shadowaosp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;







public class BootManager extends PreferenceActivity implements OnPreferenceClickListener {
	
	
	Preference reboot;
	Preference recovery;
	Preference bootloader;
	
	
	
	
	
	@Override
    public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar bar = getActionBar();
		
		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
		
		setTitle("Boot manager");
		
		addPreferencesFromResource(R.xml.boot_manager);
		
		reboot = findPreference("reboot");
		reboot.setOnPreferenceClickListener(this);
		
		bootloader = findPreference("bootloader");
		bootloader.setOnPreferenceClickListener(this);
		
		recovery = findPreference("recovery");
		recovery.setOnPreferenceClickListener(this);

    }
	
	
	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		
		if (pref == reboot) {
			
			AlertDialog.Builder reboot = new AlertDialog.Builder(this);
			reboot.setTitle("Are you sure?")
					.setMessage("You are now rebooting, are you sure you want to?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
							((PowerManager) getSystemService(Context.POWER_SERVICE)).reboot("reboot");
							
						}
													
					})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
						}
					})
					.show();
			
			
			return true;
		}
		
		if (pref == recovery) {
			
			
			AlertDialog.Builder recovery = new AlertDialog.Builder(this);
			recovery.setTitle("Are you sure?")
					.setMessage("You are now rebooting to recovery, are you sure you want to?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
							((PowerManager) getSystemService(Context.POWER_SERVICE)).reboot("recovery");
							
						}
													
					})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
						}
					})
					.show();
			
			
			return true;
		}
		
		if (pref == bootloader) {
			
			
			AlertDialog.Builder bootloader = new AlertDialog.Builder(this);
			bootloader.setTitle("Are you sure?")
					.setMessage("You are now rebooting to bootloader mode, are you sure you want to?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
							((PowerManager) getSystemService(Context.POWER_SERVICE)).reboot("bootloader");
							
						}
													
					})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
						}
					})
					.show();
			
			return true;
		}
		
		
		
		return false;
		
	}
	

}



