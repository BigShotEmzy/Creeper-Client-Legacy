package com.viaversion.viafabric.platform;

import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class VRViaAPI extends ViaAPIBase {
   public int getPlayerVersion(Object x0) {
      Object var10000 = null;
      return super.getPlayerVersion((UUID)x0);
   }

   public void sendRawPacket(Object x0, ByteBuf x1) {
      Object var10000 = null;
      super.sendRawPacket((UUID)x0, x1);
   }
}
