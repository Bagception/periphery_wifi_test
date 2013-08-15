package de.uulm.mi.ubicom.proximity.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;

public class WifiActor extends BroadcastActor<WifiReactor>{

	public WifiActor(WifiReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Activity a) {
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
		filter.addAction("android.net.wifi.STATE_CHANGE");
		a.registerReceiver(this,filter);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		/*
		ConnectivityManager conMngr = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = conMngr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo mobile = conMngr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
        boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

        NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

        if(currentNetworkInfo.isConnected()){
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
        }
        */
		Log.d("wifi",intent.getAction());
	}

}
