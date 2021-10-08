package me.spruce.creeperclient.click;

import java.io.IOException;
import java.util.ArrayList;

import me.spruce.creeperclient.click.component.CheckBox;
import me.spruce.creeperclient.click.component.ModeBox;
import me.spruce.creeperclient.click.component.NumberBox;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.BooleanSetting;
import me.spruce.creeperclient.setting.ModeSetting;
import me.spruce.creeperclient.setting.NumberSetting;
import me.spruce.creeperclient.setting.Setting;
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
    private ArrayList<CheckBox> checkBoxSettings = new ArrayList<>();
    private ArrayList<ModeBox> modeBoxSettings = new ArrayList<>();
    private ArrayList<NumberBox> numberBoxSettings = new ArrayList<>();

    public Button(Frame parent, Module module) {
        this.parent = parent;
        this.module = module;

        for (Setting s : module.settings) {
            if (s instanceof BooleanSetting) {
                checkBoxSettings.add(new CheckBox(this, (BooleanSetting) s, parent.x, parent.y + offset + 12));
            }
            if(s instanceof ModeSetting) {
                modeBoxSettings.add(new ModeBox(this, (ModeSetting) s, parent.x, parent.y + offset + 12));
            }
            if(s instanceof NumberSetting) {
                numberBoxSettings.add(new NumberBox(this, (NumberSetting) s, parent.x, parent.y + offset + 12));
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
        int fontYOffset = parent.y + offset + 6;

        Gui.drawRect(parent.x, rectY1Offset, parent.x + parent.width, rectY2Offset, module.toggled ? 0x90b0e5ff : 0x80010101);
        FontUtil.normal.drawStringWithShadow(module.getName(), parent.x + 2, fontYOffset, module.toggled ? 1 : -1);

        if(settingsOpen) {
            Gui.drawRect(parent.x, rectY1Offset + parent.barheight, parent.x + parent.width, rectY2Offset + settingOffset, 0x9068ada5);
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
        if (mouseX >= this.parent.x && mouseX <= this.parent.x + this.parent.width && mouseY >= this.parent.y + offset
                && mouseY <= this.parent.y + offset + this.parent.barheight) {
            return true;
        } else {
            return false;
        }
    }
}
