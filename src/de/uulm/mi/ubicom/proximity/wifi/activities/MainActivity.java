package de.uulm.mi.ubicom.proximity.wifi.activities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.uulm.mi.proximity_periphery_wifi_test.R;

import de.uulm.mi.ubicom.proximity.action.UI;
import de.uulm.mi.ubicom.proximity.wifi.WifiInitiation;
import de.uulm.mi.ubicom.proximity.wifi.actor.WifiActor;
import de.uulm.mi.ubicom.proximity.wifi.reactor.WifiReactor;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements WifiReactor{

	private static final String WIFISSID = "defaultulm";
	private boolean wifiOnStart;
	private WifiActor wifiActor;
	private boolean wifiLastEnabled;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		wifiActor = new WifiActor(this);
		WifiInitiation.enableWifi(this);
		Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
		WifiManager wifim = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		wifiOnStart =  wifim.isWifiEnabled();
		wifiStatusSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("wifi","wifi");
				Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
				Log.d("wifi","switch is "+wifiStatusSwitch.isChecked());
				if (wifiStatusSwitch.isChecked()){
					//active
					WifiInitiation.enableWifi(MainActivity.this);
				}else{
					//deac
					WifiInitiation.disableWifi(MainActivity.this);
					
				}
				wifiStatusSwitch.setChecked(!wifiStatusSwitch.isChecked());
				wifiStatusSwitch.setClickable(false);
				
			}
		});	
	}
	@Override
	protected void onResume() {
		Log.d("state","onResume");
		super.onResume();
		wifiActor.register(this);
		wifiActor.poll(this);
	}
	
	@Override
	protected void onPause() {
		Log.d("state","onPause");
		super.onPause();
		wifiActor.unregister(this);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("state","onSaveInstaceState");
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("state","onRestoreInstaceState");
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onDestroy() {
		Log.d("state","onDestroy");
		super.onDestroy();
	}
	@Override
	protected void onStart() {
		Log.d("state","onStart");
		super.onStart();
	}
	@Override
	protected void onStop() {
		Log.d("state","onStop");
		super.onStop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	public void openUrl(View v){
		//UI.openURL("http://192.168.1.2:8080", this);

	}

	@Override
	public void enabled() {

    	Log.d("wifi","enabled, connecting...");
 		Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
 		wifiStatusSwitch.setChecked(true);
 		wifiStatusSwitch.setClickable(true);
 		if (wifiLastEnabled) return;
 		if (!WifiInitiation.connectToWifi(WIFISSID, this)){
 			promptPassword(WIFISSID);
 		}
 
		wifiLastEnabled = true;
	}


	private void promptPassword(final String ssid){
		final EditText edit = new EditText(this);
		new AlertDialog.Builder(MainActivity.this)
	    .setTitle("Enter Password")
	    .setMessage("Enter password for "+ssid)
	    .setView(edit)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            Editable value = edit.getText(); 
	            WifiInitiation.connectToWifi(ssid, value.toString(),MainActivity.this);
	        }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            // Do nothing.
	        }
	    }).show();
	}
	
	@Override
	public void disabled() {
		Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
		wifiStatusSwitch.setChecked(false);		
		wifiStatusSwitch.setClickable(true);
		wifiLastEnabled = false;
		
	}


	@Override
	public void connected(String ssid) {
		TextView wifiStatusView = (TextView) findViewById(R.id.wificonstatus);
		wifiStatusView.setText("connected with " + ssid);
		if (WIFISSID.equals(ssid)){
			wifiStatusView.setTextColor(Color.GREEN);
			UI.openURL("http://192.168.1.2:8080", this);	
		}else{
			wifiStatusView.setTextColor(Color.RED);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Unable to connect ")
			       .setTitle("WIFI");
			AlertDialog dialog = builder.create();
			if (!wifiOnStart){
				WifiInitiation.disableWifi(this);
			}
		}
		

	}

	

	@Override
	public void disconnected() {
		TextView wifiStatusView = (TextView) findViewById(R.id.wificonstatus);
		wifiStatusView.setText("not connected");		
	}

}
