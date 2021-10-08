package me.spruce.creeperclient.click;

import java.io.IOException;
import java.util.ArrayList;

import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Button {

    public Frame parent;
    public Module module;
    public int offset;
    private boolean settingsOpen = false;

    private static FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    private static Minecraft mc = Minecraft.getMinecraft();


    public Button(Frame parent, Module module) {
        this.parent = parent;
        this.module = module;
    }

    public void update(int mouseX, int mouseY) {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;

        int rectY1Offset = parent.y + offset;
        int rectY2Offset = parent.y + offset + parent.barheight;
        int fontYOffset = parent.y + offset + 6;

        Gui.drawRect(parent.x, rectY1Offset, parent.x + parent.width, rectY2Offset, module.toggled ? 0x90b0e5ff : 0x80010101);
        FontUtil.normal.drawStringWithShadow(module.getName(), parent.x + 2, fontYOffset, -1);

    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            if (bounding(mouseX, mouseY)) {
                module.toggle();
            }
        }
        // settings
        if (mouseButton == 1) {
            if (bounding(mouseX, mouseY)) {
                settingsOpen = !settingsOpen;
            }
        }

    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public boolean bounding(int mouseX, int mouseY) {
        if (mouseX >= this.parent.x && mouseX <= this.parent.x + this.parent.width && mouseY >= this.parent.y + offset
                && mouseY <= this.parent.y + offset + this.parent.barheight) {
            return true;
        } else {
            return false;
        }
    }
}
