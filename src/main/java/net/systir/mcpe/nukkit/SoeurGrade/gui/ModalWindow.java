package net.systir.mcpe.nukkit.SoeurGrade.gui;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindowModal;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModalWindow extends FormWindowModal implements Window {

    private BiConsumer<FormResponse, Player> RespondedFunc = (response, player) -> {};
    private Consumer<Player> CloseFunc = (player) -> {};
    private Consumer<Player> TrueButtonFunc = (player) -> {};
    private Consumer<Player> FalseButtonFunc = (player) -> {};

    public ModalWindow() {
        super("", "", "", "");
        OnResponded((response, player) -> {
            if (((FormResponseModal) response).getClickedButtonText().equals(getButton1()))
                TrueButtonFunc.accept(player);
            else
                FalseButtonFunc.accept(player);
        });
    }

    public Window Send(Player player) {
        player.showFormWindow(this);
        return this;
    }

    public ModalWindow OnResponded(BiConsumer<FormResponse, Player> func) {
        RespondedFunc = func;
        return this;
    }

    public ModalWindow OnClose(Consumer<Player> func) {
        CloseFunc = func;
        return this;
    }

    public ModalWindow OnTrue(Consumer<Player> func) {
        TrueButtonFunc = func;
        return this;
    }

    public ModalWindow OnFalse(Consumer<Player> func) {
        FalseButtonFunc = func;
        return this;
    }

    public BiConsumer<FormResponse, Player> GetRespondedFunc() {
        return RespondedFunc;
    }


    public Consumer<Player> GetCloseFunc() {
        return CloseFunc;
    }

    public ModalWindow SetTitle(String title) {
        super.setTitle(title);
        return this;
    }

    public ModalWindow SetContent(String title) {
        super.setContent(title);
        return this;
    }

    public ModalWindow SetTrueButtonText(String title) {
        super.setButton1(title);
        return this;
    }

    public ModalWindow SetFalseButtonText(String title) {
        super.setButton2(title);
        return this;
    }

}
