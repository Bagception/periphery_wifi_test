package de.uulm.mi.ubicom.proximity.wifi;

import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiInitiation {
	
	public static void enableWifi(Context context){
		WifiManager wifim = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifim.setWifiEnabled(true);
	}
	public static void disableWifi(Context context){
		WifiManager wifim = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifim.setWifiEnabled(false);
	}
	
	public static boolean connectToWifi(String ssid,Context context){
		return connectToWifi(ssid, null, context);
	}
	
	/**
	 * 
	 * @param ssid
	 * @param password
	 * @param context
	 * @return true if the ssid is found
	 */
	public static boolean connectToWifi(String ssid, String password,Context context){

			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
			WifiConfiguration conf = new WifiConfiguration();
			
			if (
					wifiManager.getConnectionInfo() != null && 
					wifiManager.getConnectionInfo().getSSID() != null && 
					wifiManager.getConnectionInfo().getSSID().equals(ssid)){
				
					return true; 
			}
			conf.SSID = "\"" + ssid + "\"";   
			
			if (password != null){
				conf.preSharedKey = "\""+ password +"\"";				
			}
			
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);


			WifiConfiguration confNetwork = getWifiConfiguration(ssid, context);
			if (confNetwork == null){
				if (password == null){
					Log.d("wifiInit","no password for no preconf network "+ssid);
					return false;
				}
				Log.d("wifiInit","no preconf network for "+ssid);
				
				wifiManager.addNetwork(conf);

			}else{
				Log.d("wifiInit","preconf network for "+ssid);
			}
			confNetwork = getWifiConfiguration(ssid, context);
			
			if (confNetwork == null) return false;
			
			//wifiManager.disconnect();
	        wifiManager.enableNetwork(confNetwork.networkId, true);
	        
	        wifiManager.reconnect();               
	        wifiManager.saveConfiguration();
		 
	        return true;

	}
	
	private static WifiConfiguration getWifiConfiguration(String ssid, Context context){
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 

		List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
		for( WifiConfiguration i : list ) {
		    if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
		        return i; 
		    }           
		 }
		return null;
	}
	

}
