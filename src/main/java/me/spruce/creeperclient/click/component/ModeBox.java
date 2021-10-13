package me.spruce.creeperclient.click.component;

import java.awt.*;
import java.io.IOException;

import me.spruce.creeperclient.click.Button;
import me.spruce.creeperclient.setting.ModeSetting;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class ModeBox {
	
	private int x;
	private int y;
	private ModeSetting option;
	private Button b;
	
	public ModeBox(Button parent, ModeSetting s, int x, int y) {
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
		Gui.drawRect(x, y, x + 96, y + 15, -1);
		FontUtil.normal.drawString(option.name + " : " + option.getMode(), x + 1, y + 1, 0);
	}
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(bounding(mouseX, mouseY)) {
			if(mouseButton == 0) {
				option.cycle();
			}
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		
	}
	
	public boolean bounding(int mouseX, int mouseY) {
		if(mouseX >= x && mouseY >= y && mouseX <= x + 96 && mouseY <= y + 15) {
			return true;
		}else {
			return false;
		}	
	}
}
