package com.viaversion.viafabric.platform;

import com.viaversion.viafabric.ViaFabric;
import com.viaversion.viafabric.util.FutureTaskId;
import com.viaversion.viafabric.util.JLoggerToLog4j;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class VRPlatform implements ViaPlatform {
    public Logger logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
    public VRViaConfig config;
    public File dataFolder;
    public ViaAPI api;

    public boolean isOldClientsAllowed() {

        return true;
    }

    public ConfigurationProvider getConfigurationProvider() {

        return this.config;
    }

    public PlatformTask runSync(Runnable runnable, long ticks) {

        return new FutureTaskId(ViaFabric.getInstance().getEventLoop().submit(runnable).addListener(this.errorLogger()));
    }

    public FutureTaskId runSync(Runnable runnable) {
        return new FutureTaskId(ViaFabric.getInstance().getEventLoop().submit(runnable).addListener(this.errorLogger()));
    }

    public static Void lambda$runAsync$0(Throwable throwable) {

        if (!(throwable instanceof CancellationException)) {
            throwable.printStackTrace();
        }

        return null;
    }

    public String getPlatformVersion() {

        return "340";
    }

    public FutureTaskId runAsync(Runnable runnable) {

        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaFabric.getInstance().getAsyncExecutor()).exceptionally(VRPlatform::lambda$runAsync$0));
    }

    public String getPlatformName() {

        return "ViaFabric";
    }

    public boolean kickPlayer(UUID var1, String var2) {

        return false;
    }

    public void onReload() {

    }

    public File getDataFolder() {
        return this.dataFolder;
    }

    public ViaVersionConfig getConf() {
        return this.config;
    }

    public ViaAPI getApi() {
        return this.api;
    }

    public Runnable runRepeatingSync2(Runnable runnable) {

        this.runSync(runnable);
        return runnable;
    }

    public void sendMessage(UUID var1, String var2) {

    }

    public GenericFutureListener errorLogger() {

        return VRPlatform::lambda$errorLogger$3;
    }

    public static void lambda$errorLogger$3(Future future) {

        if (!future.isCancelled() && future.cause() != null) {
            future.cause().printStackTrace();
        }

    }

    public ViaCommandSender[] getServerPlayers() {

        return new ViaCommandSender[1337];
    }

    public FutureTaskId lambda$runSync$1(Runnable runnable) throws Exception {

        return this.runSync(runnable);
    }

    public VRPlatform(File dataFolder) {
        Path configDir = dataFolder.toPath().resolve("ViaVersion");
        this.config = new VRViaConfig(configDir.resolve("viaversion.yml").toFile());
        this.dataFolder = configDir.toFile();
        this.api = new VRViaAPI();
    }

    public Logger getLogger() {

        return this.logger;
    }

    public static String legacyToJson(String legacy) {

        return (String) GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(legacy));
    }

    public ViaCommandSender[] getOnlinePlayers() {

        return new ViaCommandSender[1337];
    }

    public boolean isPluginEnabled() {

        return true;
    }

    public String getPluginVersion() {

        return "4.0.0";
    }

    public JsonObject getDump() {

        JsonObject var1 = new JsonObject();
        return var1;
    }

    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {

        return new FutureTaskId(ViaFabric.getInstance().getEventLoop().scheduleAtFixedRate(runRepeatingSync2(runnable), 0L, ticks * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
    }
}
