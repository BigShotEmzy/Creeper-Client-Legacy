package me.spruce.creeperclient.command.commands;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.event.events.ChatEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.Command;
import net.minecraftforge.client.event.ClientChatEvent;

public class BaritoneCommand extends Command {
    public BaritoneCommand() {
        super("b");
    }

    @Override
    public void onCall(ClientChatEvent event) {
        String[] message = event.getMessage().split(" ");
        if (message.length >= 1) {
            exec(event.getMessage());
        } else {
            sendMessage(ChatFormatting.DARK_RED + "Invalid input!");
            new HelpCommand().onCall(event);
        }
    }

    private void exec(String args) {
        Settings.Setting<Boolean> chatControl = BaritoneAPI.getSettings().chatControl;
        Boolean prevValue = chatControl.value;
        chatControl.value = true;

        ChatEvent chatEvent = new ChatEvent(args.replace(".b ", ""));
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onSendChatMessage(chatEvent);
        chatControl.value = prevValue;
    }
}
