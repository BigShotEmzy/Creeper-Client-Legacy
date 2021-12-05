package me.spruce.creeperclient.mixin.mixins;

import com.viaversion.viafabric.ViaFabric;
import com.viaversion.viafabric.handler.VRDecodeHandler;
import com.viaversion.viafabric.handler.VREncodeHandler;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.network.NetworkManager$5")
public abstract class MixinNetworkManagerChInit {

    @Inject(method = "initChannel", at = @At(value = "TAIL"), remap = false)
    private void onInitChannel(Channel channel, CallbackInfo ci) {
        if (channel instanceof SocketChannel && ViaFabric.getInstance().getVersion() != 340) {
            UserConnectionImpl user = new UserConnectionImpl(channel, true);
            new ProtocolPipelineImpl(user);
            channel.pipeline().addBefore("encoder", "via-encoder", new VREncodeHandler(user)).addBefore("decoder", "via-decoder", new VRDecodeHandler(user));
        }
    }
}
