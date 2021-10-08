package me.spruce.creeperclient.setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
	
	public int index;
	public List<String> modes;
	public String selected;
	
	public ModeSetting(String name, String defaultMode, String... options) {
		this.name = name;
		this.modes = Arrays.asList(options);
		index = this.modes.indexOf(defaultMode);
		this.selected = modes.get(index);
	}
	
	public String getMode() {
		return modes.get(index);
	}
	
	public void setSelected(String selected) {
        this.selected = selected;
        index = modes.indexOf(selected);
    }
	
	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}
	
	public void cycle() {
		if(index < modes.size() - 1) {
			index++;
		}else {
			index = 0;
		}
	}

}
