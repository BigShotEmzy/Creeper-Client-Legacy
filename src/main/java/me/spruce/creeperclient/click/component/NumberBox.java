package me.spruce.creeperclient.click.component;

import java.io.IOException;

import me.spruce.creeperclient.click.Button;
import me.spruce.creeperclient.setting.NumberSetting;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

public class NumberBox {
	private int x;
	private int y;
	private NumberSetting option;
	private Button b;
	
	public NumberBox(Button parent, NumberSetting s, int x, int y) {
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
		//plus button
		Gui.drawRect(x + 84, y + 1, x + 94, y + 12, 0xff000000);
		FontUtil.normal.drawString("+", x + 85, y + 2, -1);
		//minus button
		Gui.drawRect(x + 73, y + 1, x + 83, y + 12, 0xff000000);
		FontUtil.normal.drawString("-", x + 74, y + 2, -1);

		FontUtil.normal.drawString(option.name, x + 1, y + 1, 0);
		FontUtil.normal.drawString("" + option.getValue(), x + 64 - FontUtil.normal.getStringWidth("" + option.getValue()), y + 1, 0);
	}
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(boundingPlus(mouseX, mouseY)) {
			if(option.value <= option.maximum) {
				option.setValue(option.value + option.increment);
			}
		}
		if(boundingMinus(mouseX, mouseY)) {
			if(option.value >= option.minimum) {
				option.setValue(option.value - option.increment);
			}
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		
	}
	
	public boolean boundingPlus(int mouseX, int mouseY) {
		if(mouseX >= x + 84 && mouseY >= y + 1 && mouseX <= x + 94 && mouseY <= y + 12) {
			return true;
		}else {
			return false;
		}	
	}
	
	public boolean boundingMinus(int mouseX, int mouseY) {
		if(mouseX >= x + 74 && mouseY >= y + 1 && mouseX <= x + 84 && mouseY <= y + 12) {
			return true;
		}else {
			return false;
		}	
	}
}
