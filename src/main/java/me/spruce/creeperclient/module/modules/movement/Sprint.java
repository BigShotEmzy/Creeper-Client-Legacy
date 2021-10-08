package me.spruce.creeperclient.module.modules.movement;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.util.MovementUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint(){
        super("Sprint", "Makes the player sprint automatically.", Keyboard.KEY_J, Category.MOVEMENT);
    }

    @Override
    public void onDisable(){
        Minecraft.getMinecraft().player.setSprinting(false);
    }

    @Override
    public void update(){
        if(MovementUtils.canSprint()){
            Minecraft.getMinecraft().player.setSprinting(true);
        }
    }
}
