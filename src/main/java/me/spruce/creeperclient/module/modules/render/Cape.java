package me.spruce.creeperclient.module.modules.render;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import org.lwjgl.input.Keyboard;

public class Cape extends Module {
    public Cape() {
        super("Cape", "Show exclusive cape", Keyboard.KEY_NONE, Category.RENDER);
    }
}
