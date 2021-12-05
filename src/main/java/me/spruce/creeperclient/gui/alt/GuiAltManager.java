package me.spruce.creeperclient.gui.alt;

import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.gui.alt.comp.PasswordField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiAltManager extends GuiScreen {
    private final GuiScreen parentScreen;
    private AltLoginThread thread;

    private GuiTextField username;
    private PasswordField password;


    public GuiAltManager(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 10, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 40, "Alts List"));
        this.buttonList.add(new GuiButton(1, 5, this.height - 25, "Back"));
        this.username = new GuiTextField(this.height / 4, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRenderer, this.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRenderer, "Alt Login", this.width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRenderer, Client.status, this.width / 2, 31, -1);

        if (this.username.getText().isEmpty())
            this.drawString(this.mc.fontRenderer, "Username / E-Mail", this.width / 2 - 96, 66, -7829368);
        if (this.password.getText().isEmpty())
            this.drawString(this.mc.fontRenderer, "Password", this.width / 2 - 96, 106, -7829368);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                this.thread.start();
                break;
            case 1:
                this.mc.displayGuiScreen(this.parentScreen);
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAltList(this));
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

        if (typedChar == '\t') {
            if (this.username.isFocused() || this.password.isFocused()) {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            } else this.username.setFocused(true);
        }

        if (typedChar == '\r') this.actionPerformed(this.buttonList.get(0));

        this.username.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
