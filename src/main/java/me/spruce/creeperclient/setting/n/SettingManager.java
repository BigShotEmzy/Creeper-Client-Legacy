package me.spruce.creeperclient.setting.n;

import me.spruce.creeperclient.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingManager {
    private final List<Setting<?>> settings;

    public SettingManager() {
        this.settings = new ArrayList<>();
    }

    public List<Setting<?>> getSettings() {
        return this.settings;
    }

    public void add(Setting<?> setting) {
        this.settings.add(setting);
    }

    public Setting<?> getSetting(String name, Module module) {
        return this.settings.stream().filter(s -> s.getModule().equals(module) && s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Setting<?>> getSettings(Module module) {
        return this.settings.stream().filter(s -> s.getModule().equals(module)).collect(Collectors.toList());
    }

    public Setting<?> getSetting(String name) {
        return settings.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Setting getGroup(Module module, Setting setting) {
        for (Setting s : Module.settingManager.getSettings(module)) {
            if (s.isGroup()) {
                for (Object s1 : s.getSettings()) {
                    if (s1 == setting) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    public boolean isInGroup(Module module, Setting<?> s) {
        for (Setting<?> setting : Module.settingManager.getSettings(module)) {
            if (setting.isGroup()) {
                for (Object setting1 : setting.getSettings()) {
                    if (setting1 == s) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Setting<?> getToggleSettingOfGroup(Setting<?> setting) {
        return null;
    }
}