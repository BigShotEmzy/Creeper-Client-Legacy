package me.spruce.creeperclient.module.modules.render;

import me.spruce.creeperclient.event.events.LocateCapeEvent;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Cape extends Module {
    public BufferedReader reader;

    public Cape() {
        super("Cape", "Show exclusive cape", Keyboard.KEY_NONE, Category.RENDER);
    }

    @SubscribeEvent
    public void onLocateCape(LocateCapeEvent event) {
        if (reader == null) {
            try {
                URL l_URL = new URL("https://raw.githubusercontent.com/DaCreeperGuy/Creeper-Client/Main/data.txt");
                URLConnection l_Connection = l_URL.openConnection();
                l_Connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

                reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String uuid = "f1fd5105-1a24-48ab-9243-b98c86d0c435";
        String user =
                reader.lines().filter(s -> {
                    String[] ss = s.split(":");
                    return ss[0].equals(uuid);
                }).findFirst().orElse("NOTHING");
        if (user.equals("NOTHING")) return;
        String capeName = user.split(":")[1];
        ResourceLocation location = new ResourceLocation("creeper/capes/" + capeName.toLowerCase() + ".png");
        ITextureObject image = Minecraft.getMinecraft().getTextureManager().getTexture(new ResourceLocation("/creeper/capes/" + capeName.toLowerCase() + ".png"));
        Minecraft.getMinecraft().getTextureManager().loadTexture(location, image);
        event.setResourceLocation(location);
        event.setCanceled(true);
    }
}
