package com.viaversion.viafabric.platform;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class VRInjector implements ViaInjector {
   public String getDecoderName() {
      Object var10000 = null;
      return "via-decoder";
   }

   public JsonObject getDump() {
      Object var10000 = null;
      JsonObject var1 = new JsonObject();
      return var1;
   }

   public void inject() {
      Object var10000 = null;
   }

   public int getServerProtocolVersion() {
      Object var10000 = null;
      return 340;
   }

   public String getEncoderName() {
      Object var10000 = null;
      return "via-encoder";
   }

   public void uninject() {
      Object var10000 = null;
   }
}
