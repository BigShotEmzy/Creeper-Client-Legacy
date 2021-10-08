package me.spruce.creeperclient.util;

import net.minecraft.client.Minecraft;

public class MovementUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean canSprint() {
        if (mc.player.moveForward != 0 && !mc.gameSettings.keyBindBack.isKeyDown() && mc.player.getFoodStats().getFoodLevel() > 6 && !mc.player.collidedHorizontally) {
            return !mc.player.isSprinting();
        }
        return false;
    }
}
