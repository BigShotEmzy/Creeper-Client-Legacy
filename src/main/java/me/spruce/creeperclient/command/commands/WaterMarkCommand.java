package me.spruce.creeperclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.Command;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.module.modules.hud.Watermark;
import net.minecraftforge.client.event.ClientChatEvent;

public class WaterMarkCommand extends Command {
    public WaterMarkCommand() {
        super("watermark");
    }

    @Override
    public void onCall(ClientChatEvent event) {
        String[] message = event.getMessage().split(" ");
        if (message.length >= 2) {
            Watermark waterMark = ModuleManager.getWatermark();
            if (message[1].equals("reset")) {
                waterMark.text = "Creeper Client";
                sendMessage(ChatFormatting.DARK_GREEN + "Successfully reset watermark text!");
            } else {
                waterMark.text = message[1];
                sendMessage(ChatFormatting.DARK_GREEN + "Successfully set watermark text to \"" + message[1] + "\"!");
            }
        } else {
            sendMessage(ChatFormatting.DARK_RED + "Invalid input!");
        }
    }
}
