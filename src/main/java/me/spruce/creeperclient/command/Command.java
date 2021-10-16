package me.spruce.creeperclient.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;

import static me.spruce.creeperclient.module.Module.mc;

public class Command {

    public String name;

    public Command(String name) {
        this.name = name;
    }

    public void onCall(ClientChatEvent event) {
    }

    public static void sendMessage(String string) {
        if (mc.ingameGUI != null || mc.player == null)
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(ChatFormatting.DARK_PURPLE + "[CreeperClient] " + string));
    }
}
