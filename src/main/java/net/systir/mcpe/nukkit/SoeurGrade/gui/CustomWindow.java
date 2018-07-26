package net.systir.mcpe.nukkit.SoeurGrade.gui;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindowCustom;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomWindow extends FormWindowCustom implements Window {

    protected BiConsumer<FormResponse, Player> RespondedFunc = (response, player) -> {};
    protected Consumer<Player> CloseFunc = (player) -> {};

    public CustomWindow() {
        super("");
    }

    public void Send(Player player) {
        player.showFormWindow(this);
    }

    public CustomWindow OnResponded(BiConsumer<FormResponse, Player> func) {
        RespondedFunc = func;
        return this;
    }

    public CustomWindow OnClose(Consumer<Player> func) {
        CloseFunc = func;
        return this;
    }

    public BiConsumer<FormResponse, Player> GetRespondedFunc() {
        return RespondedFunc;
    }


    public Consumer<Player> GetCloseFunc() {
        return CloseFunc;
    }

    public CustomWindow SetTitle(String title) {
        super.setTitle(title);
        return this;
    }

}
