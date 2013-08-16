package de.uulm.mi.ubicom.proximity.wifi;

import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiInitiation {
	
	public static void connectToWifi(String ssid, String password,Context context){

			WifiConfiguration conf = new WifiConfiguration();
			conf.SSID = "\"" + ssid + "\"";   
			conf.preSharedKey = "\""+ password +"\"";
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
			wifiManager.addNetwork(conf);

			List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
			for( WifiConfiguration i : list ) {
				Log.d("wifi",i.toString());
			}
			for( WifiConfiguration i : list ) {
			    if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
			         wifiManager.disconnect();
			         wifiManager.enableNetwork(i.networkId, true);
			         wifiManager.reconnect();               

			         break;
			    }           
			 }
		
	}
}
