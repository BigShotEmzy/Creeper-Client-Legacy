package com.viaversion.viafabric.platform;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VRViaConfig extends AbstractViaConfig {
   public static List UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");

   public String getBlockConnectionMethod() {
      Object var10000 = null;
      return "packet";
   }

   public boolean isAntiXRay() {
      Object var10000 = null;
      return false;
   }

   public boolean is1_14HitboxFix() {
      Object var10000 = null;
      return false;
   }

   public void handleConfig(Map var1) {
      Object var10000 = null;
   }

   public boolean isNMSPlayerTicking() {
      Object var10000 = null;
      return false;
   }

   public boolean is1_12QuickMoveActionFix() {
      Object var10000 = null;
      return false;
   }

   public VRViaConfig(File configFile) {
      super(configFile);
      this.reloadConfig();
   }

   public List getUnsupportedOptions() {
      Object var10000 = null;
      return UNSUPPORTED;
   }

   public boolean is1_9HitboxFix() {
      Object var10000 = null;
      return false;
   }

   public URL getDefaultConfigURL() {
      Object var10000 = null;
      return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
   }
}
