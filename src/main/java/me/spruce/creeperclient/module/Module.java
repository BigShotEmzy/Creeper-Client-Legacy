package me.spruce.creeperclient.module;

import me.spruce.creeperclient.setting.KeybindSetting;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.setting.n.SettingManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {

    public String name, description;
    public int key;
    public Category category;

    public boolean toggled;

    public KeybindSetting keyCode = new KeybindSetting(0);

    public static Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category c) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = c;
        this.keyCode.code = key;
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
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
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

    public static SettingManager settingManager = new SettingManager();
    //	Integer
    protected Setting<Number> register(String name, double value, double min, double max, double inc) {
        Setting<Number> s = new Setting<>(name, this, value, min, max, inc);
        settingManager.add(s);
        return s;
    }

    //	Boolean
    protected Setting<Boolean> register(String name, boolean value) {
        Setting<Boolean> s = new Setting<>(name, this, value);
        settingManager.add(s);
        return s;
    }

    //	String
    protected Setting<String> register(String name, List<String> modes, String value) {
        Setting<String> s = new Setting<>(name, this, modes, value);
        settingManager.add(s);
        return s;
    }

    //  Group
    protected Setting<Boolean> register(String name, boolean canBeToggled, Setting<?> toToggle, Setting<?>... settings) {
        Setting<Boolean> s = new Setting<>(name, this, canBeToggled, toToggle, Arrays.asList(settings));
        s.asGroup();
        settingManager.add(s);
        return s;
    }
}
