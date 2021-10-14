package me.spruce.creeperclient.module.modules.movement;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.ModeSetting;
import me.spruce.creeperclient.util.MovementUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Sprint extends Module {

    public ModeSetting mode = new ModeSetting("Mode", "Legit", "Legit", "Rage");

    public Sprint() {
        super("Sprint", "Makes the player sprint automatically.", Keyboard.KEY_J, Category.MOVEMENT);
        addSettings(mode);
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.setSprinting(false);
        KeyBinding.setKeyBindState(29, false);
    }

    @Override
    public void update() {
        switch (mode.getMode()) {
            case "Legit": KeyBinding.setKeyBindState(29, true); break;
            case "Rage": Minecraft.getMinecraft().player.setSprinting(true); break;
        }
    }
}
