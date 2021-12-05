package com.viaversion.viafabric;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viafabric.loader.VRBackwardsLoader;
import com.viaversion.viafabric.loader.VRProviderLoader;
import com.viaversion.viafabric.platform.VRInjector;
import com.viaversion.viafabric.platform.VRPlatform;
import com.viaversion.viafabric.util.JLoggerToLog4j;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class ViaFabric {
   public static int SHARED_VERSION = 340;
   public static ViaFabric instance = new ViaFabric();
   public Logger jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaFabric"));
   public CompletableFuture initFuture = new CompletableFuture();
   public ExecutorService asyncExecutor;
   public EventLoop eventLoop;
   public File file;
   public int version;
   public String lastServer;

   public int getVersion() {
      Object var10000 = null;
      return this.version;
   }

   public String getLastServer() {
      Object var10000 = null;
      return this.lastServer;
   }

   public static ViaFabric getInstance() {
      Object var10000 = null;
      return instance;
   }

   public void setVersion(int version) {
      Object var10000 = null;
      this.version = version;
   }

   public CompletableFuture getInitFuture() {
      Object var10000 = null;
      return this.initFuture;
   }

   public Logger getjLogger() {
      Object var10000 = null;
      return this.jLogger;
   }

   public void start() {
      Object var10000 = null;
      ThreadFactory factory = (new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("ViaFabric-%d").build();
      this.asyncExecutor = Executors.newFixedThreadPool(8, factory);
      this.eventLoop = (new LocalEventLoopGroup(1, factory)).next();
      CompletableFuture var10001 = this.initFuture;
      this.eventLoop.submit(var10001::join);
      this.setVersion(340);
      this.file = new File("ViaFabric");
      if (this.file.mkdir()) {
         this.getjLogger().info("Creating ViaFabric Folder");
      }

      Via.init(ViaManagerImpl.builder().injector(new VRInjector()).loader(new VRProviderLoader()).platform(new VRPlatform(this.file)).build());
      MappingDataLoader.enableMappingsCache();
      ((ViaManagerImpl)Via.getManager()).init();
      new VRBackwardsLoader(this.file);
      this.initFuture.complete(null);
   }

   public void setFile(File file) {
      Object var10000 = null;
      this.file = file;
   }

   public File getFile() {
      Object var10000 = null;
      return this.file;
   }

   public EventLoop getEventLoop() {
      Object var10000 = null;
      return this.eventLoop;
   }

   public ExecutorService getAsyncExecutor() {
      Object var10000 = null;
      return this.asyncExecutor;
   }

   public void setLastServer(String lastServer) {
      Object var10000 = null;
      this.lastServer = lastServer;
   }
}
