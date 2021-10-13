package me.spruce.creeperclient.module.modules.render;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.BooleanSetting;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Module {

    private static Minecraft mc = Minecraft.getMinecraft();

    public BooleanSetting chests = new BooleanSetting("Chests", true);
    public BooleanSetting shulkers = new BooleanSetting("Shulkers", true);

    public ChestESP(){
        super("ChestESP", "Highlights all chests in render distance.", Keyboard.KEY_NONE, Category.RENDER);
        addSettings(chests, shulkers);
    }

    @Override
    public void render(){
        Minecraft.getMinecraft().world.loadedTileEntityList.forEach(tile -> {

            if(chests.enabled) {
                if (tile instanceof TileEntityChest || tile instanceof TileEntityEnderChest) {
                    AxisAlignedBB box = new AxisAlignedBB(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), tile.getPos().getX() + 1, tile.getPos().getY() + 1, tile.getPos().getZ() + 1).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
                    GlStateManager.pushMatrix();
                    GlStateManager.disableDepth();
                    GlStateManager.disableLighting();
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GlStateManager.disableCull();
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                    GlStateManager.enableAlpha();
                    GlStateManager.enableBlend();
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);

                    if (tile instanceof TileEntityChest) {
                        if (((TileEntityChest) tile).getChestType() == BlockChest.Type.BASIC)
                            RenderGlobal.drawSelectionBoundingBox(box, 255, 51, 51, 50);
                        else
                            RenderGlobal.drawSelectionBoundingBox(box, 230, 242, 61, 50);

                        GlStateManager.enableDepth();
                        GlStateManager.enableLighting();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_LINE_SMOOTH);
                        GlStateManager.enableCull();
                        GlStateManager.popMatrix();
                    }
                }
            }

            if(shulkers.enabled) {
                if (tile instanceof TileEntityShulkerBox) {
                    AxisAlignedBB box = new AxisAlignedBB(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), tile.getPos().getX() + 1, tile.getPos().getY() + 1, tile.getPos().getZ() + 1).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
                    GlStateManager.pushMatrix();
                    GlStateManager.disableDepth();
                    GlStateManager.disableLighting();
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GlStateManager.disableCull();
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                    GlStateManager.enableAlpha();
                    GlStateManager.enableBlend();
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);

                    RenderGlobal.drawSelectionBoundingBox(box, 255, 51, 0, 50);
                    GlStateManager.enableDepth();
                    GlStateManager.enableLighting();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                    GlStateManager.enableCull();
                    GlStateManager.popMatrix();
                }
            }
        });
    }
}
