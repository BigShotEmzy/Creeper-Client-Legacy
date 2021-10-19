package me.spruce.creeperclient.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMixinSession {
    @Accessor("session")
    void setSession(Session session);
}
