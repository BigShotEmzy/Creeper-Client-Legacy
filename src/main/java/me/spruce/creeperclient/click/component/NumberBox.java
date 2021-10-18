package me.spruce.creeperclient.click.component;

import me.spruce.creeperclient.click.Button;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class NumberBox {
    private int x;
    private int y;
    private Setting<Number> option;
    private Button b;

    public NumberBox(Button parent, Setting s, int x, int y) {
        this.b = parent;
        this.x = x;
        this.y = y;
        this.option = s;
    }

    public void update(int mouseX, int mouseY, int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        Gui.drawRect(x, y, x + 96, y + 12, new Color(217, 200, 250, 255).getRGB());
        //plus button
        Gui.drawRect(x + 84, y + 1, x + 93, y + 10, new Color(50, 50, 50, 255).getRGB());
        FontUtil.normal.drawString("+", x + 85, y + 2, -1);
        //minus button
        Gui.drawRect(x + 73, y + 1, x + 82, y + 10, new Color(50, 50, 50, 255).getRGB());
        FontUtil.normal.drawString("-", x + 74, y + 2, -1);

        FontUtil.normal.drawString(option.getName() + " : " + option.getValue().doubleValue(), x + 1, y + 2.75f, 0);
        //FontUtil.normal.drawString("" + option.getValue(), x + 64 - FontUtil.normal.getStringWidth("" + option.getValue()), y + 1, 0x000000);
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (boundingPlus(mouseX, mouseY)) {
            if (option.getValue().doubleValue() <= option.getMax().doubleValue()) {
                option.setValue(option.getValue().doubleValue() + option.getInc());
            }
        }
        if (boundingMinus(mouseX, mouseY)) {
            if (option.getValue().doubleValue() >= option.getMin().doubleValue()) {
                option.setValue(option.getValue().doubleValue() - option.getInc());
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public boolean boundingPlus(int mouseX, int mouseY) {
        if (mouseX >= x + 84 && mouseY >= y + 1 && mouseX <= x + 94 && mouseY <= y + 12) {
            return true;
        } else {
            return false;
        }
    }

    public boolean boundingMinus(int mouseX, int mouseY) {
        if (mouseX >= x + 74 && mouseY >= y + 1 && mouseX <= x + 84 && mouseY <= y + 12) {
            return true;
        } else {
            return false;
        }
    }
}
