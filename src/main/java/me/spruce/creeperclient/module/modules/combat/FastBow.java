package me.spruce.creeperclient.module.modules.combat;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.NumberSetting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class FastBow extends Module {

    public NumberSetting drawLength = new NumberSetting("Draw Length", 3, 3, 21, 1);

    public FastBow(){
        super("FastBow", "Shoots a bow faster than vanilla.", Keyboard.KEY_NONE, Category.COMBAT);
        addSettings(drawLength);
    }

    @Override
    public void update(){
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= drawLength.getValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
        }
    }
}
