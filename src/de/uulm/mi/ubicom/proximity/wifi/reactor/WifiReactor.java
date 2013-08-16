package de.uulm.mi.ubicom.proximity.wifi.reactor;

import de.uulm.mi.ubicom.proximity.lib.Reactor;

public interface WifiReactor extends Reactor{
	public void enabled();
	public void disabled();
	
	public void connected(String ssid);
	public void disconnected();
	
}
