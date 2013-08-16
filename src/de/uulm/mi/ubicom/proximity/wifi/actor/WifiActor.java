package de.uulm.mi.ubicom.proximity.wifi.actor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;
import de.uulm.mi.ubicom.proximity.wifi.reactor.WifiReactor;

public class WifiActor extends BroadcastActor<WifiReactor>{

	public WifiActor(WifiReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Activity a) {
		IntentFilter filter = new IntentFilter();
		//filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
		//filter.addAction("android.net.wifi.STATE_CHANGE");
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
		a.registerReceiver(this,filter);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		/*
		Log.d("wifi","=====================");
		
		if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			Log.d("wifi","nwstatechanged");
		}
		
		if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
			/*
			Log.d("wifi","wifistatechanged");
			int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,  WifiManager.WIFI_STATE_UNKNOWN);
			  switch(extraWifiState){
			  case WifiManager.WIFI_STATE_DISABLED:
			   //Log.d("wifi","WIFI STATE DISABLED");
			   reactor.disabled();
			   break;
			  case WifiManager.WIFI_STATE_DISABLING:
				  //Log.d("wifi","WIFI STATE DISABLING");
			   break;
			  case WifiManager.WIFI_STATE_ENABLED:
				  //Log.d("wifi","WIFI STATE ENABLED");#
				  reactor.enabled();
			   break;
			  case WifiManager.WIFI_STATE_ENABLING:
				  //Log.d("wifi","WIFI STATE ENABLING");
			   break;
			  case WifiManager.WIFI_STATE_UNKNOWN:
				 // Log.d("wifi","WIFI STATE UNKNOWN");
			   break;
		  }
		}
		/*
		if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)){
				Log.d("wifi","supplconn");
		}
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		WifiManager wifim = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		NetworkInfo info1 = cm.getActiveNetworkInfo();
		
		if (info1 != null){
			Log.d("wifi",info1.toString());	
			if (info1.getType() == ConnectivityManager.TYPE_WIFI){
				if (info1.isConnected()){
					//Log.d("wifi","wifi connected with "+wifim.getConnectionInfo().getSSID());
					reactor.connected(wifim.getConnectionInfo().getSSID());
				}else{
					Log.d("wifi","wifi disconnected");
					Log.d("wifi","dc bool");
					reactor.disconnected();
				}	
			}else{
				reactor.disconnected();
				Log.d("wifi","dc type");
			}
			
		}else{
			Log.d("wifi", "NF info is null");
			reactor.disconnected();
		}
		
		Log.d("wifi","________________________");
		*/
		poll(context);
	}
	
	public void poll(Context context){
		WifiManager wifim = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (wifim.isWifiEnabled()){
			reactor.enabled();
		}else{
			reactor.disabled();
		}
		NetworkInfo info1 = cm.getActiveNetworkInfo();
		if (info1 != null){
			Log.d("wifi",info1.toString());

			if (info1.getType() == ConnectivityManager.TYPE_WIFI){
				if (info1.isConnected()){
					reactor.connected(wifim.getConnectionInfo().getSSID());
				}else{
					reactor.disconnected();
				}	
			}else{
				reactor.disconnected();
			}
			
		}else{
			Log.d("wifi","info is null");

			reactor.disconnected();
		}
		
		
	}

}



	 

