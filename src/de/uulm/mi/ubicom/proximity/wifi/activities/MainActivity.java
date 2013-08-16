package de.uulm.mi.ubicom.proximity.wifi.activities;

import com.example.proximity_periphery_wifi_test.R;

import de.uulm.mi.ubicom.proximity.wifi.actor.WifiActor;
import de.uulm.mi.ubicom.proximity.wifi.reactor.WifiReactor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

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
		
	}

}
