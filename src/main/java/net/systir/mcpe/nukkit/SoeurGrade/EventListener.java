package net.systir.mcpe.nukkit.SoeurGrade;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemMap;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.PluginTask;
import net.systir.mcpe.nukkit.SoeurGrade.gui.ModalWindow;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.scale.AWTUtil;

import java.awt.image.BufferedImage;
import java.io.File;

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
                            onRun(i);
                        });
                        //.Send(event.getPlayer());

                ItemMap map = (ItemMap) Item.get(ItemID.MAP);
                event.getPlayer().getInventory().setItemInHand(map);
                try {
                    File mp4 = new File(Main.This().getDataFolder().getAbsolutePath() + "/menu.mp4");
                    //FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(mp4));
                    Main.This().getServer().getScheduler().scheduleAsyncTask(new AsyncTask() {
                        @Override
                        public void onRun() {
                            try {
                                FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(mp4));
                                BufferedImage image;
                                while ((image = AWTUtil.toBufferedImage(grab.getNativeFrame())) != null) {
                                    Thread.sleep(34);
                                    map.setImage(image);
                                    map.sendImage(event.getPlayer());
                                }
                                this.cleanObject();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }

                //map.sendImage(event.getPlayer());
            }
        }, 20);
    }

}
