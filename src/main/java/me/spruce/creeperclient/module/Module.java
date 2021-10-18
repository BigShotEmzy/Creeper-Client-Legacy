package me.spruce.creeperclient.module;

import me.spruce.creeperclient.setting.KeybindSetting;
import me.spruce.creeperclient.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Module {

    public String name, description;
    public int key;
    public Category category;

    public boolean toggled;

    public KeybindSetting keyCode = new KeybindSetting(0);
    public List<Setting> settings = new ArrayList<Setting>();

    public static Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category c) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = c;
        this.keyCode.code = key;
        addSettings(keyCode);
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    public void onEnable() {
        //Client.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        //Client.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void update() {}

    public void render() {}

    public void renderText() {}

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public static ArrayList<Module> getModulesByCategory(Category cat) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            if (m.getCategory() == cat) {
                mods.add(m);
            }
        }
        return mods;
    }
}
