package net.ttfl.code;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PingListener implements Listener {
    private TiaCustomOnline plugin;

    public PingListener(TiaCustomOnline plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        int realOnline = plugin.getProxy().getOnlineCount();
        int realMax = plugin.getProxy().getConfig().getPlayerLimit();

        int fakeOnline = plugin.getCount().getOnline(realOnline, realMax);
        int fakeMax = plugin.getCount().getMax(realOnline, realMax);

        event.getResponse().getPlayers().setOnline(fakeOnline);
        event.getResponse().getPlayers().setMax(fakeMax);
    }
}
