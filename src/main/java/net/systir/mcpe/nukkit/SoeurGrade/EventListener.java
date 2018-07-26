package net.systir.mcpe.nukkit.SoeurGrade;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindowCustom;
import net.systir.mcpe.nukkit.SoeurGrade.gui.ModalWindow;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onJoin(PlayerJoinEvent event) {
        FormWindowCustom formWindowModal = new FormWindowCustom("1");
        //System.out.println(formWindowModal.getJSONData());
        event.getPlayer().showFormWindow(formWindowModal);
        new ModalWindow()
                .SetTitle("what?")
                .SetContent("xxm is a sb?")
                .SetTrueButtonText("yes")
                .SetFalseButtonText("no")
                .OnResponded((response, player) -> {
                    player.sendMessage("you chose " + ((FormResponseModal) response).getClickedButtonText());
                })
                .OnClose((player) -> {
                    player.sendMessage("天杀的, 你居然关闭了窗口");
                })
                .Send(event.getPlayer());
    }

}
