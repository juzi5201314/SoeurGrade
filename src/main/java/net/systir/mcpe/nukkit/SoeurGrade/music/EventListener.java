package net.systir.mcpe.nukkit.SoeurGrade.music;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.scheduler.AsyncTask;
import net.systir.mcpe.nukkit.SoeurGrade.Main;
import net.systir.mcpe.nukkit.SoeurGrade.gui.CustomWindow;

import java.io.File;
import java.util.ArrayList;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onItemHeld(PlayerItemHeldEvent event) {
        event.getPlayer().sendMessage(String.valueOf(event.getSlot()));
        if (event.getSlot() == 0) {

            new CustomWindow()
                    .SetTitle("更换音乐")
                    .AddElement(new ElementDropdown("", new ArrayList<String>(){
                        {
                            for (ResourcePack resourcePack : Main.This().getServer().getResourcePackManager().getResourceStack()) {
                                add(resourcePack.getPackName().substring(6));
                            }
                        }
                    }, 0))
                    .OnResponded((response, player) -> {
                        player.sendMessage("正在为您播放 >>>> " + ((FormResponseCustom) response).getDropdownResponse(0).getElementContent());
                        PlaySoundPacket pk = new PlaySoundPacket();
                        pk.x = (int) player.x;
                        pk.y = (int) player.y;
                        pk.z = (int) player.z;
                        pk.name = "music." + ((FormResponseCustom) response).getDropdownResponse(0).getElementContent();
                        pk.volume = 400f;
                        pk.pitch = 1;
                        player.dataPacket(pk);
                    })
                    .Send(event.getPlayer());
        }
    }
}
