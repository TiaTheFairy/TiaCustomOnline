package net.ttfl.code;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PingListener implements Listener {
    private TiaCustomOnline plugin;

    public PingListener(TiaCustomOnline plugin){
        this.plugin = plugin;
    }

    @EventHandler
    private void onPing(PaperServerListPingEvent event){
        int realOnline = event.getNumPlayers();
        int realMax = event.getMaxPlayers();

        int fakeOnline = plugin.getCount().getOnline(realOnline, realMax);
        int fakeMax = plugin.getCount().getMax(realOnline, realMax);

        event.setNumPlayers(fakeOnline);
        event.setMaxPlayers(fakeMax);
    }
}
