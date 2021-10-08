package me.spruce.creeperclient.module.modules.hud;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Watermark extends Module {

    public Watermark(){
        super("Watermark", "Dislpays the clients watermark.", Keyboard.KEY_NONE, Category.HUD);
        toggled = true;
    }

    @Override
    public void renderText(){
        FontUtil.normal.drawString("Creeper Client", 6, 6, -1);
    }
}
