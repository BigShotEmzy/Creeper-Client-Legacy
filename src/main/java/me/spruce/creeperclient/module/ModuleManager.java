package me.spruce.creeperclient.module;

import me.spruce.creeperclient.module.modules.combat.FastBow;
import me.spruce.creeperclient.module.modules.exploits.PearlTracker;
import me.spruce.creeperclient.module.modules.hud.Arraylist;
import me.spruce.creeperclient.module.modules.hud.ClickGUI;
import me.spruce.creeperclient.module.modules.hud.Watermark;
import me.spruce.creeperclient.module.modules.misc.RestartNotifier2b2t;
import me.spruce.creeperclient.module.modules.movement.Sprint;
import me.spruce.creeperclient.module.modules.movement.Step;
import me.spruce.creeperclient.module.modules.render.Cape;
import me.spruce.creeperclient.module.modules.render.ChestESP;
import me.spruce.creeperclient.module.modules.render.Fullbright;
import me.spruce.creeperclient.module.modules.world.NoWeather;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public static CopyOnWriteArrayList<Module> modules;
    private static final Watermark watermark = new Watermark();
    private static final Cape cape = new Cape();

    public ModuleManager() {
        modules = new CopyOnWriteArrayList<>();

        // COMBAT
        modules.add(new FastBow());

        // MOVEMENT
        modules.add(new Sprint());
        modules.add(new Step());

        // RENDER
        modules.add(new Fullbright());
        modules.add(new ChestESP());
        modules.add(cape);

        // WORLD
        modules.add(new NoWeather());

        // HUD
        modules.add(watermark);
        modules.add(new Arraylist());
        modules.add(new ClickGUI());

        // EXPLOITS
        modules.add(new PearlTracker());

        // MISC
        modules.add(new RestartNotifier2b2t());
    }

    public static CopyOnWriteArrayList<Module> getModules() {
        return modules;
    }

    public static Watermark getWatermark() {
        return watermark;
    }

    public static Cape getCape() {
        return cape;
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module m : modules) {
                if (m.getKey() == Keyboard.getEventKey()) {
                    m.toggle();
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world != null) {
            for (Module m : modules) {
                if (m.toggled) {
                    m.update();
                }
            }
        }
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().world != null) {
            for (Module m : modules) {
                if (m.toggled) {
                    m.render();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        for (Module m : modules) {
            if (m.toggled) {
                m.renderText();
            }
        }
    }
}
