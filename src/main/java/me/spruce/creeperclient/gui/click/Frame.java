package me.spruce.creeperclient.gui.click;

import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Frame {

    public String name;
    public int x;
    public int y;
    public int width;
    public int height;
    public int barheight;
    public int dragx;
    public int dragy;
    public boolean hovered;
    public boolean open;
    public boolean dragging;
    public int offset;

    public ArrayList<Button> buttons;

    private static FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    private static Minecraft mc = Minecraft.getMinecraft();

    public Frame(String name, int x, int y, int width, int barheight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.height = 200;
        this.width = width;
        this.barheight = barheight;
        buttons = new ArrayList<Button>();
        this.open = true;
    }

    public void update(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX - dragx;
            y = mouseY - dragy;
        }
        for (Button b : buttons) {
            b.update(mouseX, mouseY);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + barheight, new Color(140, 34, 239, 255).getRGB());
        FontUtil.normal.drawString(name, x + 1, y + 4.5f, 0);

        offset = barheight;

        for (Button b : buttons) {
            b.drawScreen(mouseX, mouseY, partialTicks, offset);
            offset += barheight;
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        for (Button b : buttons) {
            b.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            if (bounding(mouseX, mouseY)) {
                dragging = true;
                this.dragx = mouseX - x;
                this.dragy = mouseY - y;
            }
        }

        for (Button b : buttons) {
            b.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        for (Button b : buttons) {
            b.mouseReleased(mouseX, mouseY, state);
        }
    }

    public boolean bounding(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y
                && mouseY <= this.y + this.barheight;
    }
}
