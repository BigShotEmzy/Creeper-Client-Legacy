package me.spruce.creeperclient;
/**/

import me.spruce.creeperclient.command.CommandManager;
import me.spruce.creeperclient.config.Config;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.font.FontUtil;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Mod(modid = "creeper", name = "CreeperClient", version = Client.version)
public class Client {

    public static final String version = "0.1-beta5";

    public static final ModuleManager moduleManager = new ModuleManager();
    public static final CommandManager commandManager = new CommandManager();
    public static Config config;

    @Mod.Instance
    public static Client instance = new Client();

    public static final EventManager EVENT_BUS = new EventManager();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        MinecraftForge.EVENT_BUS.register(commandManager);
        config = new Config(event.getModConfigurationDirectory());
        config.load();
        FontUtil.bootstrap();
        firstSetup();
    }

    private int ticks = 0;
    public BufferedReader l_Reader = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        ticks++;
        if (ticks < 600) return;
        ticks = 0;

        new Thread(() -> {
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
        }).start();
    }

    private void firstSetup() throws IOException {
        URL l_URL;
        URLConnection l_Connection;

        l_URL = new URL("https://raw.githubusercontent.com/DaCreeperGuy/Creeper-Client/Main/data.txt");
        l_Connection = l_URL.openConnection();
        l_Connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

        l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
    }
}
