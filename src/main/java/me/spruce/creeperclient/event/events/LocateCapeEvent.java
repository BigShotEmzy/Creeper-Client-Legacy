package me.spruce.creeperclient.event.events;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class LocateCapeEvent extends Event {
    ResourceLocation resourceLocation;
    String uuid;

    public LocateCapeEvent(String uuid) {
        this.uuid = uuid;
        this.resourceLocation = null;
    }

    public String getUuid() {
        return uuid;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}
