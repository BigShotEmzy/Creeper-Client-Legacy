package me.spruce.creeperclient;
/**/

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.CommandManager;
import me.spruce.creeperclient.config.Config;
import me.spruce.creeperclient.mixin.mixins.IMixinRightClickDelayTimer;
import me.spruce.creeperclient.mixin.mixins.IMixinSession;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.font.FontUtil;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Mod(modid = "creeper", name = "CreeperClient", version = Client.version)
public class Client {

    public static final String version = "0.1-beta6";

    public static final ModuleManager moduleManager = new ModuleManager();
    public static final CommandManager commandManager = new CommandManager();
    public static Config config;
    public static final IMixinSession IMC = (IMixinSession) Minecraft.getMinecraft();
    public static final IMixinRightClickDelayTimer rightClickDelayTimer = (IMixinRightClickDelayTimer) Minecraft.getMinecraft();
    public static String status = ChatFormatting.YELLOW + "Idle";
    public static Map<String, Session> savedAlts = new HashMap<>();

    @Mod.Instance
    public static Client instance = new Client();

    public static final EventManager EVENT_BUS = new EventManager();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        MinecraftForge.EVENT_BUS.register(commandManager);
        startThread();
        config = new Config(event.getModConfigurationDirectory());
        config.load();
        FontUtil.bootstrap();
    }

    private long time = System.currentTimeMillis() + 69420L;
    public BufferedReader l_Reader;

    public void startThread() {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (System.currentTimeMillis() - time < 6000) return;
                time = System.currentTimeMillis();
                try {
                    URL l_URL;
                    URLConnection l_Connection;

                    l_URL = new URL("https://raw.githubusercontent.com/DaCreeperGuy/Creeper-Client/Main/data.txt");
                    l_Connection = l_URL.openConnection();
                    l_Connection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

                    l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
