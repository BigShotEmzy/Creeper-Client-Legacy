package com.viaversion.viafabric.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.concurrent.Future;

public class FutureTaskId implements PlatformTask {
   public Future object;

   public void cancel() {
      Object var10000 = null;
      this.object.cancel(false);
   }

   public Future getObject() {
      Object var10000 = null;
      return this.object;
   }

   public FutureTaskId(Future object) {
      this.object = object;
   }
}
