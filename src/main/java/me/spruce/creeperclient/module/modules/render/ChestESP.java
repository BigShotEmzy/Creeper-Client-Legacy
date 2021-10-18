package me.spruce.creeperclient.module.modules.render;

import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.setting.n.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ChestESP extends Module {

    private static Minecraft mc = Minecraft.getMinecraft();

    public Setting<Boolean> chests = register("Chests", true);
    public Setting<Boolean> enderChests = register("Ender Chests", true);
    public Setting<Boolean> shulkers = register("Shulkers", true);

    public ChestESP() {
        super("StorageESP", "Highlights all chests and shulkers in render distance.", Keyboard.KEY_NONE, Category.RENDER);
    }

    public final List<StorageBlockPos> Storages = new ArrayList<>();
    private final ICamera camera = new Frustum();

    @Override
    public void update() {
        Storages.clear();

        mc.world.loadedTileEntityList.forEach(p_Tile ->
        {
            if (p_Tile instanceof TileEntityEnderChest && enderChests.getValue())
                Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Ender));
            else if (p_Tile instanceof TileEntityChest && chests.getValue())
                Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Chest));
            else if (p_Tile instanceof TileEntityShulkerBox && shulkers.getValue())
                Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Shulker));
        });
    }

    @Override
    public void render() {
        if (mc.getRenderManager() == null || mc.getRenderManager().options == null) return;

        new ArrayList<>(Storages).forEach(p_Pos ->
        {
            final AxisAlignedBB bb = new AxisAlignedBB(p_Pos.getX() - mc.getRenderManager().viewerPosX, p_Pos.getY() - mc.getRenderManager().viewerPosY,
                    p_Pos.getZ() - mc.getRenderManager().viewerPosZ, p_Pos.getX() + 1 - mc.getRenderManager().viewerPosX, p_Pos.getY() + 1 - mc.getRenderManager().viewerPosY,
                    p_Pos.getZ() + 1 - mc.getRenderManager().viewerPosZ);

            camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

            if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ,
                    bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                switch (p_Pos.GetType()) {
                    case Chest:
                        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.94f, 1.0f, 0f, 0.6f);
                        break;
                    case Ender:
                        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.65f, 0f, 0.93f, 0.6f);
                        break;
                    case Shulker:
                        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 1.0f, 0.0f, 0.59f, 0.6f);
                        break;
                    default:
                        break;
                }
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
            }
        });
    }

    enum StorageType {
        Chest,
        Shulker,
        Ender,
    }

    static class StorageBlockPos extends BlockPos {
        public StorageType Type;

        public StorageBlockPos(int x, int y, int z, StorageType p_Type) {
            super(x, y, z);

            Type = p_Type;
        }

        public StorageType GetType() {
            return Type;
        }
    }
}
