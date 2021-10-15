package me.spruce.creeperclient.mixin.mixins;

import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.util.RenderUtils;
import net.minecraft.client.Minecraft;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Mixin(LayerCape.class)
public abstract class MixinCapeLayer {
    @Shadow
    @Final
    private RenderPlayer playerRenderer;

    @Inject(method = "doRenderLayer*", at = @At("TAIL"), cancellable = true)
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) throws IOException {
        Module module1 = ModuleManager.modules.stream().filter(module -> module.name.equals("Cape")).findFirst().orElse(null);

        assert module1 != null;
        if (module1.isToggled()) {
            String uuid = Minecraft.getMinecraft().getSession().getProfile().getId().toString();
            //uuid = "f11aefb8-6e8e-4411-905a-1171f1ae9900";

            URL l_URL = null;
            URLConnection l_Connection = null;
            BufferedReader l_Reader = null;

            l_URL = new URL("https://hastebin.com/raw/sipupireke");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));

            String user = l_Reader.lines().filter(s -> {
                String[] ss = s.split(":");
                if (ss[0].equals(uuid)) return true;
                return false;
            }).findFirst().orElse("NOTHING");

            if (user.equals("NOTHING")) return;
            String cape = user.split(":")[1];
            //System.out.println("CAPE");
            if (RenderUtils.renderCape(playerRenderer, player, new ResourceLocation("creeper/capes/" + cape.toLowerCase() + ".png"), partialTicks))
                ci.cancel();
        }
    }
}
