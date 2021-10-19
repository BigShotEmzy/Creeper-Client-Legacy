package me.spruce.creeperclient.mixin.mixins;

import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.gui.alt.GuiAltManager;
import me.spruce.creeperclient.util.font.FontUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(CallbackInfo ci) {
        FontUtil.normal.drawStringWithShadow("Creeper Client " + Client.version, 20, 6, new Color(140, 34, 239, 255).getRGB());
        //mc.getTextureManager().bindTexture(new ResourceLocation("creeper/creeper_client.png"));
        mc.getTextureManager().bindTexture(new ResourceLocation("creeper/logo.png"));
        drawModalRectWithCustomSizedTexture(2, 2, 0, 0, 16, 16, 16, 16);
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(69420, 2, 20, (int) (FontUtil.normal.getStringWidth("Creeper Client " + Client.version) + 20), 20, "Alt Manager"));
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    public void actionPerformed(GuiButton button, CallbackInfo ci) {
        if(button.id == 69420) {
            this.mc.displayGuiScreen(new GuiAltManager(this));
        }
    }
}
