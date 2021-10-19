package me.spruce.creeperclient.module.modules.hud;

import me.spruce.creeperclient.gui.click.ClickGui;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI(){
        super("ClickGUI", "Displays the clients click gui.", Keyboard.KEY_RSHIFT, Category.HUD);
    }

    @Override
    public void onEnable(){
        Minecraft.getMinecraft().displayGuiScreen(new ClickGui());
        toggle();
    }
}
