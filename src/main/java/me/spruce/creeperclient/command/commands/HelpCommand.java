package me.spruce.creeperclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.Command;
import net.minecraftforge.client.event.ClientChatEvent;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void onCall(ClientChatEvent event) {
        sendMessage(ChatFormatting.DARK_GREEN + "--------------------------------------");
        sendMessage(ChatFormatting.DARK_GREEN + "List of all commands available:");
        sendMessage(ChatFormatting.DARK_GREEN + ".help - Show this list.");
        sendMessage(ChatFormatting.DARK_GREEN + ".watermark <text> - Change watermark text.");
        sendMessage(ChatFormatting.DARK_GREEN + ".prefix <text> - Change commands' prefix.");
        sendMessage(ChatFormatting.DARK_GREEN + "--------------------------------------");
    }
}
