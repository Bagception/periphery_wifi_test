package de.uulm.mi.ubicom.proximity.wifi.activities;

import com.example.proximity_periphery_wifi_test.R;

import de.uulm.mi.ubicom.proximity.wifi.WifiInitiation;
import de.uulm.mi.ubicom.proximity.wifi.actor.WifiActor;
import de.uulm.mi.ubicom.proximity.wifi.reactor.WifiReactor;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements WifiReactor{

	private WifiActor wifiActor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		wifiActor = new WifiActor(this);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		wifiActor.register(this);
		wifiActor.poll(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		wifiActor.unregister(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void wifiSwitch(View v){
		Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
		Log.d("wifi","switch is "+wifiStatusSwitch.isChecked());
		if (wifiStatusSwitch.isChecked()){
			//active
			WifiInitiation.enableWifi(this);
		}else{
			//deac
			WifiInitiation.disableWifi(this);
			
		}
		wifiStatusSwitch.setChecked(!wifiStatusSwitch.isChecked());
		wifiStatusSwitch.setClickable(false);
	}


	@Override
	public void enabled() {
		Log.d("wifi","enabled, connecting...");
		Switch wifiStatusSwitch = (Switch) findViewById(R.id.switch1);
		wifiStatusSwitch.setChecked(true);
		wifiStatusSwitch.setClickable(true);
		if (!WifiInitiation.connectToWifi("defaultulm", this)){
			promptPassword("defaultulm");
		}
		
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
	}


	@Override
	public void connected(String ssid) {
		TextView wifiStatusView = (TextView) findViewById(R.id.wificonstatus);
		wifiStatusView.setText("connected with " + ssid);
	}


	@Override
	public void disconnected() {
		TextView wifiStatusView = (TextView) findViewById(R.id.wificonstatus);
		wifiStatusView.setText("not connected");		
	}

}
