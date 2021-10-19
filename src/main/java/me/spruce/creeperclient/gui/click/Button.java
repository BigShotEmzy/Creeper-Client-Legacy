package me.spruce.creeperclient.gui.click;

import me.spruce.creeperclient.gui.click.component.CheckBox;
import me.spruce.creeperclient.gui.click.component.ModeBox;
import me.spruce.creeperclient.gui.click.component.NumberBox;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Button {

    public Frame parent;
    public Module module;
    public int offset;
    private boolean settingsOpen = false;

    private static final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList<CheckBox> checkBoxSettings = new ArrayList<>();
    private final ArrayList<ModeBox> modeBoxSettings = new ArrayList<>();
    private final ArrayList<NumberBox> numberBoxSettings = new ArrayList<>();

    public Button(Frame parent, Module module) {
        this.parent = parent;
        this.module = module;

        for (Setting<?> s : Module.settingManager.getSettings(module)) {
            switch (s.getType()) {
                case "Boolean": {checkBoxSettings.add(new CheckBox(this, s, parent.x, parent.y + offset + 12)); break;}
                case "String" : {modeBoxSettings.add(new ModeBox(this, s, parent.x, parent.y + offset + 12)); break;}
                case "Double" : {numberBoxSettings.add(new NumberBox(this, s, parent.x, parent.y + offset + 12)); break;}
            }
        }
    }

    public void update(int mouseX, int mouseY) {
        if(settingsOpen) {
            int settingsOffset = 0;
            for(CheckBox cbx : checkBoxSettings) {
                cbx.update(mouseX, mouseY, parent.x + 2, parent.y + offset + 20 + (settingsOffset * 16));
                settingsOffset++;
            }
            for(ModeBox mdbx : modeBoxSettings) {
                mdbx.update(mouseX, mouseY, parent.x + 2, parent.y + offset + 20  + (settingsOffset * 16));
                settingsOffset++;
            }
            for(NumberBox nmbx : numberBoxSettings) {
                nmbx.update(mouseX, mouseY, parent.x + 2, parent.y + offset + 20  + (settingsOffset * 16));
                settingsOffset++;
            }
        }

    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        int settingListSize = checkBoxSettings.size() + modeBoxSettings.size() + numberBoxSettings.size();
        this.offset = offset;

        int settingOffset = 0;
        if(this.settingsOpen) {
            settingOffset += settingListSize * 20;
            parent.offset += settingOffset;
        }
        int rectY1Offset = parent.y + offset;
        int rectY2Offset = parent.y + offset + parent.barheight;
        int fontYOffset = parent.y + offset + 3;

        int color = module.toggled ? new Color(167, 73, 255, 200).getRGB() : new Color(75, 67, 87, 200).getRGB();
        Gui.drawRect(parent.x, rectY1Offset, parent.x + parent.width, rectY2Offset, color);
        FontUtil.normal.drawString(module.getName(), parent.x + 2, fontYOffset, -1);

        if(settingsOpen) {
            Gui.drawRect(parent.x, rectY1Offset + parent.barheight, parent.x + parent.width, rectY2Offset + settingOffset, color);
            for (CheckBox cbx : checkBoxSettings) {
                cbx.drawScreen(mouseX, mouseY, partialTicks, 0);
            }
            for(ModeBox mdbx : modeBoxSettings) {
                mdbx.drawScreen(mouseX, mouseY, partialTicks, 0);
            }
            for(NumberBox nmbx : numberBoxSettings) {
                nmbx.drawScreen(mouseX, mouseY, partialTicks, 0);
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (settingsOpen) {
            for (CheckBox cbx : checkBoxSettings) {
                cbx.keyTyped(typedChar, keyCode);
            }
            for(ModeBox mdbx : modeBoxSettings) {
                mdbx.keyTyped(typedChar, keyCode);
            }
            for(NumberBox nmbx : numberBoxSettings) {
                nmbx.keyTyped(typedChar, keyCode);
            }
        }
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
        if(settingsOpen) {
            for (CheckBox cbx : checkBoxSettings) {
                cbx.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for(ModeBox mdbx : modeBoxSettings) {
                mdbx.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for(NumberBox nmbx : numberBoxSettings) {
                nmbx.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(settingsOpen) {
            for (CheckBox cbx : checkBoxSettings) {
                cbx.mouseReleased(mouseX, mouseY, state);
            }
            for(ModeBox mdbx : modeBoxSettings) {
                mdbx.mouseReleased(mouseX, mouseY, state);
            }
            for(NumberBox nmbx : numberBoxSettings) {
                nmbx.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public boolean bounding(int mouseX, int mouseY) {
        return mouseX >= this.parent.x && mouseX <= this.parent.x + this.parent.width && mouseY >= this.parent.y + offset && mouseY <= this.parent.y + offset + this.parent.barheight;
    }
}
