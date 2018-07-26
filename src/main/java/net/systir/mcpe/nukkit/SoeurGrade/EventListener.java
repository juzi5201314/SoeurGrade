package net.systir.mcpe.nukkit.SoeurGrade;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.scheduler.PluginTask;
import net.systir.mcpe.nukkit.SoeurGrade.gui.ModalWindow;
import net.systir.mcpe.nukkit.SoeurGrade.gui.Window;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onJoin(PlayerJoinEvent event) {
        Main.This().getServer().getScheduler().scheduleDelayedTask(new PluginTask<Main>(Main.This()) {
            @Override
            public void onRun(int i) {
                new ModalWindow()
                        .SetTitle("what?")
                        .SetContent("xxm is a sb?")
                        .SetTrueButtonText("yes")
                        .SetFalseButtonText("no")
                        .OnTrue((player) -> player.sendMessage("蛤蛤蛤xxm真丑"))
                        .OnFalse((player) -> {
                            player.sendMessage("答案错误，请重新选择");
                            this.onRun(i);
                        })
                        .Send(event.getPlayer());
            }
        }, 20);
    }

}
