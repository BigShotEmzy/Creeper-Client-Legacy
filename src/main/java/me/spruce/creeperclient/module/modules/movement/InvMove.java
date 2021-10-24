package me.spruce.creeperclient.module.modules.movement;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.MovementUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    public Setting<Boolean> sneak = register("Sneak", false);

    public InvMove() {
        super("Inventory Move", "Allows you to move when GUI is open", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent event) {
        if (!(event.getMovementInput() instanceof MovementInputFromOptions) || isValidGui(mc.currentScreen)) return;

        event.getMovementInput().moveStrafe = 0.0f;
        event.getMovementInput().moveForward = 0.0f;

        try {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
                ++event.getMovementInput().moveForward;
                event.getMovementInput().forwardKeyDown = true;
            } else {
                event.getMovementInput().forwardKeyDown = false;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                --event.getMovementInput().moveForward;
                event.getMovementInput().backKeyDown = true;
            } else {
                event.getMovementInput().backKeyDown = false;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
                ++event.getMovementInput().moveStrafe;
                event.getMovementInput().leftKeyDown = true;
            } else {
                event.getMovementInput().leftKeyDown = false;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
                --event.getMovementInput().moveStrafe;
                event.getMovementInput().rightKeyDown = true;
            } else {
                event.getMovementInput().rightKeyDown = false;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                event.getMovementInput().jump = true;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && sneak.getValue()) {
                event.getMovementInput().sneak = true;
            }

            if ((Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()) && MovementUtils.canSprint()) || ModuleManager.getSprint().isToggled()) {
                mc.player.setSprinting(true);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidGui(GuiScreen screen) {
        return screen == null || screen instanceof GuiChat || screen instanceof GuiEditSign || screen instanceof GuiRepair;
    }
}
