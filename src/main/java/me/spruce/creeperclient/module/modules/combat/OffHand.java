package me.spruce.creeperclient.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.Command;
import me.spruce.creeperclient.gui.click.ClickGui;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.PlayerUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class OffHand extends Module {

    public Setting<String> mode = register("Mode", Arrays.asList("Gap", "Crystal", "Bow"), "Gap");
    public Setting<Number> toggleHealth = register("ToggleHealth", 10.0, 0.0, 20.0, 0.5);
    public Setting<Boolean> hotbarFirst  = register("HotbarFirst", false);

    public enum Modes
    {
        Gap,
        Crystal,
        Bow,
    }

    public OffHand() {
        super("OffHand", "Pauses AutoTotem and places something else in your offhand.", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @SubscribeEvent
    public void OnPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.currentScreen != null && (!(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof ClickGui)))
            return;

        if (PlayerUtils.GetHealthWithAbsorption() < toggleHealth.getValue().doubleValue())
        {
            Command.sendMessage("You are below the ToggleHealth requirement, toggling..");
            toggle();
            return;
        }

        SwitchOffHandIfNeed(Modes.valueOf(mode.getValue()));
    }

    private void SwitchOffHandIfNeed(Modes p_Val)
    {
        Item l_Item = GetItemFromModeVal(p_Val);

        if (mc.player.getHeldItemOffhand().getItem() != l_Item)
        {
            int l_Slot = hotbarFirst.getValue() ? PlayerUtils.GetRecursiveItemSlot(l_Item) : PlayerUtils.GetItemSlot(l_Item);

            if (l_Slot != -1)
            {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP,
                        mc.player);

                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.updateController();

                Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Offhand now has a " + GetItemNameFromModeVal(Modes.valueOf(mode.getValue())));
            }
        }
    }

    public Item GetItemFromModeVal(Modes p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return Items.END_CRYSTAL;
            case Gap:
                return Items.GOLDEN_APPLE;
            case Bow:
                return Items.BOW;
            default:
                break;
        }

        return Items.TOTEM_OF_UNDYING;
    }

    private String GetItemNameFromModeVal(Modes p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return "End Crystal";
            case Gap:
                return "Gap";
            case Bow:
                return "Bow";
            default:
                break;
        }

        return "Totem";
    }
}
