package com.viaversion.viafabric.loader;

import com.viaversion.viafabric.ViaFabric;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;

public class VRProviderLoader$1 extends BaseVersionProvider {
   public VRProviderLoader this$0;

   public int getClosestServerProtocol(UserConnection connection) throws Exception {
      Object var10000 = null;
      return connection.isClientSide() ? ViaFabric.getInstance().getVersion() : super.getClosestServerProtocol(connection);
   }

   public VRProviderLoader$1(VRProviderLoader this$0) {
      this.this$0 = this$0;
   }
}
