package me.spruce.creeperclient.setting.n;

import me.spruce.creeperclient.module.Module;

import java.util.List;

public class Setting<T> {

	private final String name;
	private final Module module;

	private T value, max, min;
	private double inc;
	private List<String> modes;

	private Setting<?> toggle;
	private List<Setting<?>> settings;
	private boolean group, canBeToggled;

	public Setting(String name, Module module, T value) {
		this.name = name;
		this.module = module;
		this.value = value;
	}

	public Setting(String name, Module module, boolean canBeToggled, Setting<?> toggle, List<Setting<?>> settings) {
		this.name = name;
		this.module = module;
		this.canBeToggled = canBeToggled;
		this.settings = settings;
		this.toggle = toggle;
	}

	public Setting(String name, Module module, T value, T min, T max, double inc) {
		this.name = name;
		this.module = module;
		this.value = value;
		this.min = min;
		this.max = max;
		this.inc = inc;
	}

	public Setting(String name, Module module, List<String> modes, T value) {
		this.name = name;
		this.module = module;
		this.value = value;
		this.modes = modes;
	}

	public String getType() {
		return this.value.getClass().getSimpleName();
	}

	public String getName() {
		return name;
	}

	public Module getModule() {
		return module;
	}

	public List<String> getModes() {
		return modes;
	}

	public List<Setting<?>> getSettingsInGroup() {
		return this.settings;
	}

	public double getInc() {
		return inc;
	}

	public T getMax() {
		return max;
	}

	public T getMin() {
		return min;
	}

	public T getValue() {
		return value;
	}

	public boolean isCanBeToggled() {
		return canBeToggled;
	}

	public Setting<?> getToggleSetting() {
		return toggle;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isGroup() {
		return group;
	}

	public List<Setting<?>> getSettings() {
		return settings;
	}

	public void asGroup() {
		this.group = true;
	}

	int index = 0;
	public void cycle() {
		index = modes.indexOf(value);
		if(index < modes.size() - 1) {
			index++;
		}else {
			index = 0;
		}
		value = (T) modes.get(index);
	}
}