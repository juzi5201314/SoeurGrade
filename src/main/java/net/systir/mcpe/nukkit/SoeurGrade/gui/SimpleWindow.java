package net.systir.mcpe.nukkit.SoeurGrade.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindowSimple;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleWindow extends FormWindowSimple implements Window {

    protected BiConsumer<FormResponse, Player> RespondedFunc = (response, player) -> {};
    protected Consumer<Player> CloseFunc = (player) -> {};

    public SimpleWindow() {
        super("", "");
    }

    public Window Send(Player player) {
        player.showFormWindow(this);
        return this;
    }

    public SimpleWindow OnResponded(BiConsumer<FormResponse, Player> func) {
        RespondedFunc = func;
        return this;
    }

    public SimpleWindow OnClose(Consumer<Player> func) {
        CloseFunc = func;
        return this;
    }

    public BiConsumer<FormResponse, Player> GetRespondedFunc() {
        return RespondedFunc;
    }


    public Consumer<Player> GetCloseFunc() {
        return CloseFunc;
    }

    public SimpleWindow SetTitle(String title) {
        super.setTitle(title);
        return this;
    }

    public SimpleWindow SetContent(String content) {
        super.setContent(content);
        return this;
    }

    public SimpleWindow AddButton(ElementButton button) {
        super.addButton(button);
        return this;
    }

}
