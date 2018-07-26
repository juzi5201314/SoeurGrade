package net.systir.mcpe.nukkit.SoeurGrade.gui;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onResponded(PlayerFormRespondedEvent event) {
        if (event.getWindow() instanceof Window) {
            if (event.wasClosed())
                ((Window) event.getWindow()).GetCloseFunc().accept(event.getPlayer());
            else
                ((Window) event.getWindow()).GetRespondedFunc().accept(event.getResponse(), event.getPlayer());
        }
    }

}
