package me.spruce.creeperclient.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.command.Command;
import me.spruce.creeperclient.gui.click.ClickGui;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.setting.n.Setting;
import me.spruce.creeperclient.util.PlayerUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class AutoTotem extends Module {

    public Setting<Number> health = register("Health", 16.0, 0.0, 20.0, 0.5);
    public Setting<String> Mode = register("Mode", Arrays.asList("Totem", "Gap", "Crystal", "Pearl", "Chorus", "Strength"), "Totem");
    public Setting<String> FallbackMode = register("Fallback Mode", Arrays.asList("Totem", "Gap", "Crystal", "Pearl", "Chorus", "Strength"), "Crystal");
    public Setting<Number> FallDistance = register("FallDistance", 14.0, 0.0, 100.0, 10.0);
    public Setting<Boolean> TotemOnElytra = register("TotemOnElytra", true);
    public Setting<Boolean> OffhandGapOnSword = register("SwordGap", false);
    public Setting<Boolean> OffhandStrNoStrSword = register("StrGap", false);
    public Setting<Boolean> HotbarFirst = register("HotbarFirst", false);

    public enum AutoTotemMode
    {
        Totem,
        Gap,
        Crystal,
        Pearl,
        Chorus,
        Strength,
    }

    public AutoTotem() {
        super("AutoTotem", "Automatically places a totem of undying in your offhand", Keyboard.KEY_NONE, Category.COMBAT);
    }

    private OffHand OffhandMod = null;

    private void SwitchOffHandIfNeed(AutoTotemMode p_Val)
    {
        Item l_Item = GetItemFromModeVal(p_Val);

        if (mc.player.getHeldItemOffhand().getItem() != l_Item)
        {
            int l_Slot = HotbarFirst.getValue() ? PlayerUtils.GetRecursiveItemSlot(l_Item) : PlayerUtils.GetItemSlot(l_Item);

            Item l_Fallback = GetItemFromModeVal(AutoTotemMode.valueOf(FallbackMode.getValue()));

            String l_Display = GetItemNameFromModeVal(p_Val);

            if (l_Slot == -1 && l_Item != l_Fallback && mc.player.getHeldItemOffhand().getItem() != l_Fallback)
            {
                l_Slot = PlayerUtils.GetRecursiveItemSlot(l_Fallback);
                l_Display = GetItemNameFromModeVal(AutoTotemMode.valueOf(FallbackMode.getValue()));

                /// still -1...
                if (l_Slot == -1 && l_Fallback != Items.TOTEM_OF_UNDYING)
                {
                    l_Fallback = Items.TOTEM_OF_UNDYING;

                    if (l_Item != l_Fallback && mc.player.getHeldItemOffhand().getItem() != l_Fallback)
                    {
                        l_Slot = PlayerUtils.GetRecursiveItemSlot(l_Fallback);
                        l_Display = "Emergency Totem";
                    }
                }
            }

            if (l_Slot != -1)
            {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP,
                        mc.player);

                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.updateController();

                Command.sendMessage(ChatFormatting.YELLOW + "[AutoTotem] " + ChatFormatting.LIGHT_PURPLE + "Offhand now has a " + l_Display);
            }
        }
    }

    @SubscribeEvent
    public void OnPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.currentScreen != null && (!(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof ClickGui)) || OffhandMod.isToggled())
            return;

        if (!mc.player.getHeldItemMainhand().isEmpty())
        {
            if (health.getValue().doubleValue() <= PlayerUtils.GetHealthWithAbsorption() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && OffhandStrNoStrSword.getValue() && !mc.player.isPotionActive(MobEffects.STRENGTH))
            {
                SwitchOffHandIfNeed(AutoTotemMode.Strength);
                return;
            }

            /// Sword override
            if (health.getValue().doubleValue() <= PlayerUtils.GetHealthWithAbsorption() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && OffhandGapOnSword.getValue())
            {
                SwitchOffHandIfNeed(AutoTotemMode.Gap);
                return;
            }
        }

        /// First check health, most important as we don't want to die for no reason.
        if (health.getValue().doubleValue() > PlayerUtils.GetHealthWithAbsorption() || AutoTotemMode.valueOf(Mode.getValue()) == AutoTotemMode.Totem || (TotemOnElytra.getValue() && mc.player.isElytraFlying()) || (mc.player.fallDistance >= FallDistance.getValue().doubleValue() && !mc.player.isElytraFlying()))
        {
            SwitchOffHandIfNeed(AutoTotemMode.Totem);
            return;
        }

        /// If we meet the required health
        SwitchOffHandIfNeed(AutoTotemMode.valueOf(Mode.getValue()));
    }

    @Override
    public void onEnable()
    {
        super.onEnable();

        OffhandMod = ModuleManager.getOffHand();
    }

    public Item GetItemFromModeVal(AutoTotemMode p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return Items.END_CRYSTAL;
            case Gap:
                return Items.GOLDEN_APPLE;
            case Pearl:
                return Items.ENDER_PEARL;
            case Chorus:
                return Items.CHORUS_FRUIT;
            case Strength:
                return Items.POTIONITEM;
            default:
                break;
        }

        return Items.TOTEM_OF_UNDYING;
    }

    private String GetItemNameFromModeVal(AutoTotemMode p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return "End Crystal";
            case Gap:
                return "Gap";
            case Pearl:
                return "Pearl";
            case Chorus:
                return "Chorus";
            case Strength:
                return "Strength";
            default:
                break;
        }

        return "Totem";
    }
}
