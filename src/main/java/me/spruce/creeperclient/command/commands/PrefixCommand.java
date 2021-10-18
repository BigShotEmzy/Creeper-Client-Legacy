package me.spruce.creeperclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.command.Command;
import net.minecraftforge.client.event.ClientChatEvent;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix");
    }

    @Override
    public void onCall(ClientChatEvent event) {
        String[] message = event.getMessage().split(" ");
        if (message.length >= 2) {
            if (message[1].equals("reset")) {
                Client.commandManager.prefix = ".";
                sendMessage(ChatFormatting.DARK_GREEN + "Successfully reset prefix!");
            } else {
                Client.commandManager.prefix = message[1];
                sendMessage(ChatFormatting.DARK_GREEN + "Successfully set prefix to \"" + message[1] + "\"!");
            }
        } else {
            sendMessage(ChatFormatting.DARK_RED + "Invalid input!");
        }
    }
}
