package net.systir.mcpe.nukkit.SoeurGrade;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import net.systir.mcpe.nukkit.SoeurGrade.music.ResourcePackMaker;

import java.io.File;

public class Main extends PluginBase {

    private static Main me;

    public static Main This() {
        return me;
    }

    @Override
    public void onLoad() {
        me = this;
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        new File(getDataFolder(), "/music/build").mkdirs();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new net.systir.mcpe.nukkit.SoeurGrade.gui.EventListener(), this);
        getServer().getPluginManager().registerEvents(new net.systir.mcpe.nukkit.SoeurGrade.music.EventListener(), this);

        for (File file : new File(getDataFolder(), "/music/").listFiles()) {
            if (file.isFile() && file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("ogg")) {
                new ResourcePackMaker(file.getName()).make();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals("play"))
            return false;
        Player player = (Player) sender;

        getServer().getScheduler().scheduleAsyncTask(new AsyncTask() {
            @Override
            public void onRun() {
                PlaySoundPacket pk = new PlaySoundPacket();
                pk.x = (int) player.x;
                pk.y = (int) player.y;
                pk.z = (int) player.z;
                pk.name = "music.test";
                pk.volume = 400f;
                pk.pitch = 1;
                player.dataPacket(pk);

            }
        });
        return true;
    }
}
