package me.spruce.creeperclient.module.modules.hud;

import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class Arraylist extends Module {

    public Arraylist(){
        super("Arraylist", "Displays the clients Arraylist.", Keyboard.KEY_NONE, Category.HUD);
        toggled = true;
    }

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module o1, Module o2) {
            return Integer.compare((int) FontUtil.normal.getStringWidth(o2.name), (int) FontUtil.normal.getStringWidth(o1.name));
        }
    }

    @Override
    public void renderText(){
        int offset = 0;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        ModuleManager.modules.sort(new ModuleComparator());
        for(Module m : Client.moduleManager.getModules()){
            if(m.toggled) {
                FontUtil.normal.drawString(m.name, sr.getScaledWidth() - FontUtil.normal.getStringWidth(m.name) - 6, 6 + offset * fr.FONT_HEIGHT, -1);
                offset++;
            }
        }
    }
}
