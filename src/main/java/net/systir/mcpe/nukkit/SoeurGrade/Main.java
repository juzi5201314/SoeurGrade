package net.systir.mcpe.nukkit.SoeurGrade;

import cn.nukkit.plugin.PluginBase;

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
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new net.systir.mcpe.nukkit.SoeurGrade.gui.EventListener(), this);
    }
}
