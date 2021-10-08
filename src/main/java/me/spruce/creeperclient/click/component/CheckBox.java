package me.spruce.creeperclient.click.component;

import java.io.IOException;

import me.spruce.creeperclient.click.Button;
import me.spruce.creeperclient.setting.BooleanSetting;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

public class CheckBox {

	private int x;
	private int y;
	private BooleanSetting option;
	private Button b;
	
	public CheckBox(Button button, BooleanSetting s, int x, int y) {
		this.b = button;
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
		Gui.drawRect(x + 84, y + 1, x + 94, y + 12, !option.enabled ? 0xff000000 : 0x9000ffff);
		FontUtil.normal.drawString(option.name, x + 1, y + 1, 0);
	}
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(bounding(mouseX, mouseY)) {
			if(mouseButton == 0) {
				option.setEnabled(!option.enabled);
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
