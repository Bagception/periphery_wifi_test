package de.uulm.mi.ubicom.proximity.wifi;

import com.example.proximity_periphery_wifi_test.R;

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

/*
You need to create WifiConfiguration instance like this:

String networkSSID = "test";
String networkPass = "pass";

WifiConfiguration conf = new WifiConfiguration();
conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes
Then, for WEP network you need to do this:

conf.wepKeys[0] = "\"" + networkPass + "\""; 
conf.wepTxKeyIndex = 0;
conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
For WPA network you need to add passphrase like this:

conf.preSharedKey = "\""+ networkPass +"\"";
For Open network you need to do this:

conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
Then, you need to add it to Android wifi manager settings:

WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
wifiManager.addNetwork(conf);
And finally, you might need to enable it, so Android conntects to it:

List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
for( WifiConfiguration i : list ) {
    if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
         wifiManager.disconnect();
         wifiManager.enableNetwork(i.networkId, true);
         wifiManager.reconnect();               

         break;
    }           
 }
UPD: In case of WEP, if your password is in hex, you do not need to surround it with quotes.

http://stackoverflow.com/questions/8818290/how-to-connect-to-a-specific-wifi-network-in-android-programmatically
*/

/*
String url = "http://www.example.com";
Intent i = new Intent(Intent.ACTION_VIEW);
i.setData(Uri.parse(url));
startActivity(i);
*/