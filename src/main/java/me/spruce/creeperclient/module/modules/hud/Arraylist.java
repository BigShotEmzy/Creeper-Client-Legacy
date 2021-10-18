package me.spruce.creeperclient.module.modules.hud;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.RainbowUtils;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class Arraylist extends Module {

    public Setting<Boolean> rainbow = register("Rainbow", true);

    public Arraylist() {
        super("Arraylist", "Displays the clients Arraylist.", Keyboard.KEY_NONE, Category.HUD);
        toggled = true;
    }

    private final RainbowUtils rainbowUtils = new RainbowUtils(9);

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module o1, Module o2) {
            return Integer.compare((int) FontUtil.normal.getStringWidth(o2.name), (int) FontUtil.normal.getStringWidth(o1.name));
        }
    }

    @Override
    public void renderText() {
        int offset = 0;
        rainbowUtils.OnRender();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        ModuleManager.modules.sort(new ModuleComparator());
        int i = 0;
        for (Module m : ModuleManager.getModules()) {
            i += 10;
            if (i >= 355)
                i = 0;
            if (m.isToggled()) {
                FontUtil.normal.drawString(m.name, sr.getScaledWidth() - FontUtil.normal.getStringWidth(m.name) - 6, 6 + offset * fr.FONT_HEIGHT, rainbow.getValue() ? rainbowUtils.GetRainbowColorAt(i) : -1);
                offset++;
            }
        }
    }
}
