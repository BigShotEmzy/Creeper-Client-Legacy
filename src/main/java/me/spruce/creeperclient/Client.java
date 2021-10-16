package me.spruce.creeperclient;
/**/

import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.font.FontUtil;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
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

    public static final String version = "0.1";

    public static ModuleManager moduleManager = new ModuleManager();

    @Mod.Instance
    public static Client instance = new Client();

    public static final EventManager EVENT_BUS = new EventManager();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        MinecraftForge.EVENT_BUS.register(this);
        FontUtil.bootstrap();
        firstSetup();
    }

    public String user;
    private int ticks = 0;
    private int step = 0;
    URL l_URL = null;
    URLConnection l_Connection = null;
    BufferedReader l_Reader = null;
    String uuid = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws IOException {
        ticks++;
        if (ticks < 600) return;
        ticks = 0;

        new Thread(() -> {
            try {
                uuid = Minecraft.getMinecraft().getSession().getProfile().getId().toString();
                //uuid = "f11aefb8-6e8e-4411-905a-1171f1ae992c";

                l_URL = new URL("https://raw.githubusercontent.com/DaCreeperGuy/Creeper-Client/Main/data.txt");
                l_Connection = l_URL.openConnection();
                l_Connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

                l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));

                user = l_Reader.lines().filter(s -> {
                    String[] ss = s.split(":");
                    if (ss[0].equals(uuid)) return true;
                    return false;
                }).findFirst().orElse("NOTHING");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void firstSetup() throws IOException {
        uuid = Minecraft.getMinecraft().getSession().getProfile().getId().toString();
        //uuid = "f11aefb8-6e8e-4411-905a-1171f1ae992c";

        l_URL = new URL("https://raw.githubusercontent.com/DaCreeperGuy/Creeper-Client/Main/data.txt");
        l_Connection = l_URL.openConnection();
        step++;
        l_Connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

        l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));

        user = l_Reader.lines().filter(s -> {
            String[] ss = s.split(":");
            if (ss[0].equals(uuid)) return true;
            return false;
        }).findFirst().orElse("NOTHING");
    }
}
