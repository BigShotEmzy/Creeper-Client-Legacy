package me.spruce.creeperclient.gui.alt.comp;

import me.spruce.creeperclient.gui.alt.AltLoginThread;
import me.spruce.creeperclient.gui.alt.GuiAltList;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Session;

import java.awt.*;

public class AltButton {
    private final int x, y;
    private final String username, password;
    private final Session session;
    public boolean selected = false;
    private final GuiAltList list;

    public AltButton(int x, int y, String username, String password, Session session, GuiAltList list) {
        this.x = x;
        this.y = y;
        this.username = username;
        this.password = password;
        this.session = session;
        this.list = list;
    }

    public void drawButton() {
        int color;
        if (Minecraft.getMinecraft().getSession().getUsername().equals(session.getUsername())) color = new Color(108, 13, 196, 152).getRGB();
        else if (selected) color = new Color(0, 255, 0, 152).getRGB();
        else color = new Color(73, 73, 73, 152).getRGB();
        Gui.drawRect(x, y, x + 300, y + FontUtil.normal20.getHeight() * 2 + 15, color);
        FontUtil.normal20.drawString(session.getUsername(), x + 5, y + 5, -1);
        FontUtil.normal20.drawString(username, x + 5, y + 10 + FontUtil.normal20.getHeight(), -1);
    }

    public void actionPerformed(int mouseX, int mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY) && mouseButton == 0) {
            if (selected) new AltLoginThread(username, password).start();
            else {
                selected = true;
                list.unselect(this);
            }
        }
    }

    private boolean bounding(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x + 300 && mouseY <= y + FontUtil.normal20.getHeight() * 2 + 15;
    }

    public Session getSession() {
        return session;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
