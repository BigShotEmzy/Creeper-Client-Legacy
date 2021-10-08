package me.spruce.creeperclient.click;

import java.io.IOException;
import java.util.ArrayList;

import me.spruce.creeperclient.click.Button;
import me.spruce.creeperclient.click.Frame;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGui extends GuiScreen{

    public ArrayList<Frame> frames;

    public ClickGui() {
        frames = new ArrayList<>();
        int offset = 0;
        for(Category c : Category.values()) {
            Frame frame = new Frame(c.name(), 10 + offset, 20, 100, 16);
            for(Module m : Module.getModulesByCategory(c)) {
                Button button = new Button(frame, m);
                frame.buttons.add(button);
            }
            frames.add(frame);
            offset+=110;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(Frame fr : frames) {
            fr.drawScreen(mouseX, mouseY, partialTicks);
            fr.update(mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for(Frame fr : frames) {
            fr.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(Frame fr : frames) {
            fr.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for(Frame fr : frames) {
            fr.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;

    }
}
