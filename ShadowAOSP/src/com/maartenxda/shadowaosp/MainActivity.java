package com.maartenxda.shadowaosp;
	
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Shell;
	

	public class MainActivity extends TabActivity {
	
		Context context = this;
		
		private static String tempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp";
		
		ActionBar bar;
		
		static TabHost host;
		
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			getMenuInflater().inflate(R.menu.main, menu);

			// send = menu.getItem(6);

			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle item selection
			switch (item.getItemId()) {

			case R.id.about:

				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);

				String username = prefs.getString("username", "Shadow");

				AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
						context);

				dlgAlert.setMessage(R.string.about_shadow);
				dlgAlert.setTitle("Hey there " + username + "!");
				dlgAlert.setPositiveButton("Go to the ROM's thread",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Intent browserIntent = new Intent(
										Intent.ACTION_VIEW, Uri.parse(""));
								startActivity(browserIntent);
							}
						});
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();

				return true;

			case R.id.appreciation:

				AlertDialog.Builder dlgAlert1 = new AlertDialog.Builder(this);

				dlgAlert1.setMessage(R.string.appreciation);
				dlgAlert1.setTitle("Appreciation");
				dlgAlert1.setPositiveButton("Donate",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Intent browserIntent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=MQHE27S38LEV2&lc=NL&item_name=XDA%20Developers&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_LG%2egif%3aNonHosted"));
								startActivity(browserIntent);
							}
						});
				dlgAlert1.setCancelable(true);
				dlgAlert1.create().show();

				return true;

			case R.id.devs:

				intent(DevelopersActivity.class);

				return true;

			case R.id.bugs:

				intent(ReportBugs.class);

				return true;

			case R.id.signup:

				Intent intent2 = new Intent(this, LogIn.class);
				startActivity(intent2);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_in);

				return true;

			case R.id.send:

				intent(SendMessage.class);

				return true;

			case R.id.files:

				intent(FilesActivity.class);
				return true;

			case R.id.personalize:

				Intent i = new Intent();
				i.setAction(Intent.ACTION_VIEW);
				i.setClassName("com.htc.home.personalize",
						"com.htc.home.personalize.main.PersonalizeCarouselMainActivity");

				startActivity(i);
				return true;

			case R.id.prefs:

				intent(Preferences.class);
				return true;

			case R.id.bootmanager:
				intent(BootManager.class);
				return true;

			default:
				return super.onOptionsItemSelected(item);
			}
		}

		public void intent(Class activity) {

			Intent intent = new Intent(this, activity);
			startActivity(intent);

		}
		
		private static void createTempFile(){
            Process process = null;
            DataOutputStream os = null;

            try{
                    RootTools.remount("/system/", "rw");
                    RootTools.copyFile("/system/build.prop", Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp", false, true);
                    process = Runtime.getRuntime().exec("su");
                    os = new DataOutputStream(process.getOutputStream());
                    os.writeBytes("chmod 777 " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp\n");
                    RootTools.remount("/system/", "ro");
                    os.writeBytes("exit\n");
                    os.flush();
                    process.waitFor();
            }catch(Exception e){
                    e.printStackTrace();
            }finally{
                    try{
                            if(os != null){
                                    os.close();
                            }
                            process.destroy();
                    }catch(Exception e){
                            e.printStackTrace();
                    }
            }
            tempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp";
    }

		
		
		public static String getProp(String key){
            final Properties prop = new Properties();
            Process process = null;
            DataOutputStream os = null;
            createTempFile();

            try{
                    FileInputStream in = new FileInputStream(new File(tempFile));
                    prop.load(in);
                    in.close();
            }catch(IOException e){
                    e.printStackTrace();
            }

            if(prop.getProperty(key) != null){
                    String value = prop.getProperty(key);
                    new File(tempFile).delete();
                    return value;
            }
            return "";
    }
		
		
		
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	
	Parse.initialize(this, "TM89BHpQB2QLRuBzo4He9QGrB9CiDWk7xxwYwBRm",
			"S7dY3jHa0x9doKp6wddsJLJi5xnp36sM0KFvBmq9");
	
	PushService.setDefaultPushCallback(this, MainActivity.class);
	PushService.subscribe(context, "All", MainActivity.class);
	ParseInstallation.getCurrentInstallation().saveInBackground();

	if (RootTools.isAccessGiven() == true) {
	
	} else {
		
		RootTools.offerSuperUser(this);
	}
	
	
	bar = getActionBar();
	
	setTitle("");
	bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
	
	host = getTabHost();
	
	
	
			host.addTab(host.newTabSpec("One")
						.setIndicator("News")
						.setContent(new Intent(this, News.class)));
			host.addTab(host.newTabSpec("Two")
						.setIndicator("Chat")
						.setContent(new Intent(this, Chat.class)));
			host.addTab(host.newTabSpec("Three")
					.setIndicator("Skins")
					.setContent(new Intent(this, Skins.class)));
			host.addTab(host.newTabSpec("Four")
					.setIndicator("Animations")
					.setContent(new Intent(this, BootAnims.class)));
		
			for(int i=0;i<host.getTabWidget().getChildCount();i++)
	        {
	            host.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.custom_tab_unselected); //unselected
	        }
	        host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundResource(R.drawable.custom_tab_selected); // selected
			
			host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

				@Override
				public void onTabChanged(String tabId) {
					// TODO Auto-generated method stub
					
						for(int i=0;i<host.getTabWidget().getChildCount();i++)
				        {
				            host.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.custom_tab_unselected); //unselected
				        }
				        host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundResource(R.drawable.custom_tab_selected); // selected
				
					
				}
							
				
			});
			
		}
	
	
}