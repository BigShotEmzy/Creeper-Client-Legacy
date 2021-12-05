package com.viaversion.viafabric.loader;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

public class VRProviderLoader implements ViaPlatformLoader {
   public void unload() {
      Object var10000 = null;
   }

   public void load() {
      Object var10000 = null;
      Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
      Via.getManager().getProviders().use(VersionProvider.class, new VRProviderLoader$1(this));
   }
}
