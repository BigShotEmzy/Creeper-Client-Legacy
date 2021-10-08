package me.spruce.creeperclient.module.modules.world;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class NoWeather extends Module {

    public NoWeather(){
        super("NoWeather", "Removes rain and thunder.", Keyboard.KEY_NONE, Category.WORLD);
    }

    @Override
    public void update(){
        Minecraft.getMinecraft().world.setRainStrength(0);
        Minecraft.getMinecraft().world.setThunderStrength(0);
    }
}
