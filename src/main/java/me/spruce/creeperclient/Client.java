package me.spruce.creeperclient;
/**/
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.font.FontUtil;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "creeper", name = "CreeperClient", version = "0.1")
public class Client {

    public static String version = "0.1";

    public static ModuleManager moduleManager = new ModuleManager();

    @Mod.Instance
    public static Client instance = new Client();

    public static final EventManager EVENT_BUS = new EventManager();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        FontUtil.bootstrap();
    }
}
