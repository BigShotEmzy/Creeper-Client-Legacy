package me.spruce.creeperclient.mixin.mixins;

import com.viaversion.viafabric.ViaFabric;
import me.spruce.creeperclient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {
    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void shutdownMinecraftApplet(CallbackInfo ci) {
        Client.config.save();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void injectConstructor(GameConfiguration gameConfiguration, CallbackInfo ci) {
        try {
            ViaFabric.getInstance().start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
