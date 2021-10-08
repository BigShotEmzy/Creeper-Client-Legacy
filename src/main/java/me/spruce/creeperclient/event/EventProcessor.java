package me.spruce.creeperclient.event;

import me.spruce.creeperclient.Client;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventProcessor {

    public EventProcessor(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event){
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event){
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event){
        Client.EVENT_BUS.post(event);
    }
}
