package me.spruce.creeperclient.module.modules.render;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    private float oldGamma = 1.0F;

    public Fullbright(){
        super("FullBright", "You can see everything.", Keyboard.KEY_NONE, Category.RENDER);
    }

    @Override
    public void onEnable(){
        oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 6.0f;
    }

    @Override
    public void onDisable(){
        Minecraft.getMinecraft().gameSettings.gammaSetting = oldGamma;
    }
}
