package me.spruce.creeperclient.mixin.mixins;

import com.viaversion.viafabric.handler.CommonTransformer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Redirect(method = "setCompressionThreshold", at = @At(
            value = "INVOKE",
            remap = false,
            target = "Lio/netty/channel/ChannelPipeline;addBefore(Ljava/lang/String;Ljava/lang/String;Lio/netty/channel/ChannelHandler;)Lio/netty/channel/ChannelPipeline;"
    ))
    private ChannelPipeline decodeEncodePlacement(ChannelPipeline instance, String base, String newHandler, ChannelHandler handler) {
        // Fixes the handler order
        if (!new File(Minecraft.getMinecraft().gameDir, "novia").exists()) {
            switch (base) {
                case "decoder": {
                    if (instance.get(CommonTransformer.HANDLER_DECODER_NAME) != null)
                        base = CommonTransformer.HANDLER_DECODER_NAME;
                    break;
                }
                case "encoder": {
                    if (instance.get(CommonTransformer.HANDLER_ENCODER_NAME) != null)
                        base = CommonTransformer.HANDLER_ENCODER_NAME;
                    break;
                }
            }
        }
        return instance.addBefore(base, newHandler, handler);
    }
}