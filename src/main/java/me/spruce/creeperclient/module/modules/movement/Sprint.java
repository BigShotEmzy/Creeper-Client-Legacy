package me.spruce.creeperclient.module.modules.movement;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.n.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class Sprint extends Module {

    public Setting<String> mode = register("Mode", Arrays.asList("Legit", "Rage"), "Legit");

    public Sprint() {
        super("Sprint", "Makes the player sprint automatically.", Keyboard.KEY_J, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.setSprinting(false);
        KeyBinding.setKeyBindState(29, false);
    }

    @Override
    public void update() {
        switch (mode.getValue()) {
            case "Legit": KeyBinding.setKeyBindState(29, true); break;
            case "Rage": Minecraft.getMinecraft().player.setSprinting(true); break;
        }
    }
}
