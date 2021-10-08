package me.spruce.creeperclient.module;

import me.spruce.creeperclient.Client;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class Module {

    public String name, description;
    public int key;
    public Category category;

    public boolean toggled;

    public Module(String name, String description, int key, Category c){
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = c;
    }

    public void onEnable(){
        Client.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable(){
        Client.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void update(){

    }

    public void render(){

    }

    public void renderText(){

    }

    public void toggle(){
        toggled = !toggled;
        if(toggled){
            onEnable();
        }else{
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public static ArrayList<Module> getModulesByCategory(Category cat) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for(Module m : Client.moduleManager.getModules()) {
            if(m.getCategory() == cat) {
                mods.add(m);
            }
        }
        return mods;
    }
}
