package me.spruce.creeperclient.module.modules.hud;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.util.font.FontUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Watermark extends Module {

    public String text = "Creeper Client";

    public Watermark() {
        super("Watermark", "Dislpays the clients watermark.", Keyboard.KEY_NONE, Category.HUD);
        toggled = true;
    }

    @Override
    public void renderText() {
        FontUtil.normal.drawString(text, 6, 6, new Color(140, 34, 239, 255).getRGB());
    }
}
