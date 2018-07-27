package net.systir.mcpe.nukkit.SoeurGrade.music;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.StopSoundPacket;
import cn.nukkit.resourcepacks.ResourcePack;
import net.systir.mcpe.nukkit.SoeurGrade.Main;
import net.systir.mcpe.nukkit.SoeurGrade.gui.CustomWindow;
import net.systir.mcpe.nukkit.SoeurGrade.gui.SimpleWindow;
import org.gagravarr.ogg.audio.OggAudioStatistics;
import org.gagravarr.vorbis.VorbisFile;


import java.io.File;
import java.util.*;

public class EventListener implements Listener {

    private static Map<Player, String> playlist = new HashMap<>();
    private static Map<String, String> musicdata = new HashMap();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onItemHeld(PlayerItemHeldEvent event) {
        event.getPlayer().sendMessage(String.valueOf(event.getSlot()));
        if (event.getSlot() == 0) {
            StringBuilder content = new StringBuilder("");
            if (playlist.containsKey(event.getPlayer())) {
                File music = new File(Main.This().getDataFolder(), "/music/" + playlist.get(event.getPlayer()) + ".ogg");
                if (music.exists()) {
                    try {
                        if (musicdata.containsKey(playlist.get(event.getPlayer()))) {
                            content.append(musicdata.get(playlist.get(event.getPlayer())));
                        } else {
                            VorbisFile vf = new VorbisFile(music);
                            OggAudioStatistics stats = new OggAudioStatistics(vf, vf);
                            stats.calculate();
                            content.append("正在播放: " + playlist.get(event.getPlayer()));
                            content.append("\n");
                            content.append("大小: " + music.length() / 1024.0 / 1024.0 + "MB");
                            content.append("\n");
                            content.append("时长: " + stats.getDuration());
                            content.append("\n");
                            content.append("Rate: " + vf.getInfo().getRate());
                            content.append("\n");
                            content.append("Nominal/Lower/Upper Bitrate: " + vf.getInfo().getBitrateNominal() + "/" + vf.getInfo().getBitrateLower() + "/" + vf.getInfo().getBitrateUpper());
                            content.append("\n");
                            musicdata.put(playlist.get(event.getPlayer()), content.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    content.append("本歌曲暂无信息");
            } else
                content.append("快去选一首歌来听吧");
            new SimpleWindow()
                    .SetTitle("音乐")
                    .SetContent(content.toString())
                    .AddButton(new ElementButton("停止"))
                    .AddButton(new ElementButton("选歌"))
                    .OnResponded((response, player) -> {
                        if (((FormResponseSimple) response).getClickedButtonId() == 0) {
                            stopMusic(player);
                        } else {
                            new CustomWindow()
                                    .SetTitle("选择音乐")
                                    .AddElement(new ElementDropdown("", new ArrayList<String>() {
                                        {
                                            for (ResourcePack resourcePack : Main.This().getServer().getResourcePackManager().getResourceStack()) {
                                                try {
                                                    add(new String(Base64.getDecoder().decode(resourcePack.getPackName().substring(6)), "gbk"));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }, 0))
                                    .OnResponded((response2, player2) -> {
                                        stopMusic(player2);
                                        playlist.put(player2, ((FormResponseCustom) response2).getDropdownResponse(0).getElementContent());
                                        player2.sendMessage("正在为您播放 >>>> " + ((FormResponseCustom) response2).getDropdownResponse(0).getElementContent());
                                        PlaySoundPacket pk = new PlaySoundPacket();
                                        pk.x = (int) player2.x;
                                        pk.y = (int) player2.y;
                                        pk.z = (int) player2.z;
                                        pk.name = "music." + Base64.getEncoder().encodeToString(((FormResponseCustom) response2).getDropdownResponse(0).getElementContent().getBytes());
                                        pk.volume = 400f;
                                        pk.pitch = 1;
                                        player2.dataPacket(pk);
                                    })
                                    .Send(event.getPlayer());
                        }
                    })
                    .Send(event.getPlayer());
        }
    }

    public static void stopMusic(Player player, boolean stopall) {
        if (player != null && playlist.containsKey(player)) {
            StopSoundPacket pk = new StopSoundPacket();
            pk.name = "music." + Base64.getEncoder().encodeToString(playlist.get(player).getBytes());
            pk.stopAll = stopall;
            player.dataPacket(pk);
            player.sendMessage("已停止 <<< " + playlist.get(player));
            playlist.remove(player);
        }
    }

    public static void stopMusic(Player player) {
        stopMusic(player, false);
    }
}
