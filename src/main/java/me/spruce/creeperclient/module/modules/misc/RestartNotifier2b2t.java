package me.spruce.creeperclient.module.modules.misc;

import me.spruce.creeperclient.command.Command;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class RestartNotifier2b2t extends Module {
    public RestartNotifier2b2t() {
        super("2b2t Restart Notifier", "Notifies you when 2b2t restarts", Keyboard.KEY_NONE, Category.MISC);
    }

    Thread t = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                URLConnection connection = new URL("http://crystalpvp.ru/restarts/fetch").openConnection();
                connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
                InputStream inputStream = connection.getInputStream();
                String message = parseValue(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).readLine());
                if (!message.equalsIgnoreCase("None")) Command.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private String parseValue(String value) {
        String message;
        if (value.equalsIgnoreCase("m")) message = "2b2t restarting in " + StringUtils.chop(value) + " minutes!";
        else if (value.equalsIgnoreCase("now")) message = "2b2t restarting!";
        else if (value.equalsIgnoreCase("s")) message = "2b2t restarting in 15 seconds!";
        else message = "None";
        return message;
    }

    @Override
    public void onEnable() {
        t.start();
    }

    @Override
    public void onDisable() {
        t.interrupt();
    }
}
