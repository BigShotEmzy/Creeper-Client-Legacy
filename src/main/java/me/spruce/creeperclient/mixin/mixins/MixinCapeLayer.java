package me.spruce.creeperclient.mixin.mixins;

import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.RenderUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCape.class)
public abstract class MixinCapeLayer {
    @Shadow
    @Final
    private RenderPlayer playerRenderer;

    @Inject(method = "doRenderLayer*", at = @At("TAIL"), cancellable = true)
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
        Module module1 = ModuleManager.modules.stream().filter(module -> module.name.equals("Cape")).findFirst().orElse(null);
        String uuid = player.getGameProfile().getId().toString();

        assert module1 != null;
        if (module1.isToggled()) {
            String user =
            Client.instance.l_Reader.lines().filter(s -> {
                String[] ss = s.split(":");
                return ss[0].equals(uuid);
            }).findFirst().orElse("NOTHING");
            if (user.equals("NOTHING")) return;
            String cape = user.split(":")[1];
            if (RenderUtils.renderCape(playerRenderer, player, new ResourceLocation("creeper/capes/" + cape.toLowerCase() + ".png"), partialTicks))
                ci.cancel();
        }
    }
}
