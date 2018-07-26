package net.systir.mcpe.nukkit.SoeurGrade.gui;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Window {
    BiConsumer<FormResponse, Player> GetRespondedFunc();
    Consumer<Player> GetCloseFunc();
    Window Send(Player player);
    Window OnResponded(BiConsumer<FormResponse, Player> func);
    Window OnClose(Consumer<Player> func);
    Window SetTitle(String title);
}
