package me.spruce.creeperclient.event;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class Event extends Cancellable {

    private Mode mode;
    private float partialTicks;

    public Event(){
        this.partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }

    public Event(Mode m){
        this.mode = m;
        this.partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }

    public enum Mode{
        PRE,
        POST,
        PERI;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
