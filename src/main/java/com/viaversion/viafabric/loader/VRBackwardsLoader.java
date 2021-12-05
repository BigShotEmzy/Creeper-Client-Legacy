package com.viaversion.viafabric.loader;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viafabric.ViaFabric;
import java.io.File;
import java.util.logging.Logger;

public class VRBackwardsLoader implements ViaBackwardsPlatform {
   public File file;

   public File getDataFolder() {
      Object var10000 = null;
      return new File(this.file, "config.yml");
   }

   public boolean isOutdated() {
      Object var10000 = null;
      return false;
   }

   public Logger getLogger() {
      Object var10000 = null;
      return ViaFabric.getInstance().getjLogger();
   }

   public VRBackwardsLoader(File file) {
      this.init(this.file = new File(file, "ViaBackwards"));
   }

   public void disable() {
      Object var10000 = null;
   }
}
