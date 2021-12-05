package me.spruce.creeperclient.gui.alt;

import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.gui.alt.comp.AltButton;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiAltList extends GuiScreen {
    private final GuiScreen parentScreen;

    private GuiTextField search;
    private ArrayList<AltButton> altButtons;
    private AltButton selected;

    public GuiAltList(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, 5, this.height - 25, "Back"));
        this.buttonList.add(new GuiButton(1, 250, this.height - 25, "Remove Selected"));
        this.search = new GuiTextField(99, this.mc.fontRenderer, 5, 5, 200, 20);

        int x = this.width / 2 - 150;
        int y = 50;
        final int[] i = {0};
        altButtons = new ArrayList<>();
        Client.savedAlts.forEach((s, session) -> {
            String[] ss = s.split(":");
            altButtons.add(new AltButton(x, y + i[0], ss[0], ss[1], session, this));
            i[0] += FontUtil.normal20.getHeight() * 2 + 20;
        });

        this.search.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.search.drawTextBox();
        FontUtil.normal.drawString(Client.status, 5, 35, new Color(232, 186, 0, 255).getRGB());

        altButtons.forEach(altButton -> {
            if (search.getText().isEmpty()) altButton.drawButton();
            else if (altButton.getSession().getUsername().contains(search.getText())) altButton.drawButton();
        });

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(this.parentScreen);
                break;
            case 1:
                if(selected != null) Client.savedAlts.remove(selected.getUsername() + ":" + selected.getPassword());
                this.initGui();
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        this.search.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.search.mouseClicked(mouseX, mouseY, mouseButton);
        altButtons.forEach(altButton -> altButton.actionPerformed(mouseX, mouseY, mouseButton));
    }

    @Override
    public void updateScreen() {
        this.search.updateCursorCounter();
    }

    public void unselect(AltButton button) {
        altButtons.forEach(altButton -> {
            if(altButton != button) altButton.selected = false;
            else this.selected = button;
        });
    }
}
