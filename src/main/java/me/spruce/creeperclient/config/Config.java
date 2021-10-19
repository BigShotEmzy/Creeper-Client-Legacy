package me.spruce.creeperclient.config;

import com.google.gson.*;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.spruce.creeperclient.Client;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.setting.n.Setting;
import net.minecraft.util.Session;

import java.io.*;
import java.net.Proxy;
import java.nio.file.Files;
import java.util.Map;

@SuppressWarnings("all")
public class Config {
    File file;

    public Config(File folder) {
        file = new File(folder + "/creeper/");
    }

    public void save() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File output;

            for (Module m : ModuleManager.getModules()) {
                if (!(output = new File(file + "/" + m.getName() + ".json")).exists()) {
                    file.mkdirs();
                    output.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(output.toPath())));

                String json = gson.toJson(settingWriter(m));
                writer.write(json);
                writer.close();
            }

            savePrefix();
            saveAlts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JsonObject settingWriter(Module module) {
        JsonObject object = new JsonObject();
        if (!Module.settingManager.getSettings(module).isEmpty()) {
            for (Setting<?> setting : Module.settingManager.getSettings(module)) {
                if (!setting.isGroup()) {
                    switch (setting.getType()) {
                        case "Double": {
                            object.add(setting.getName(), new JsonPrimitive(((Setting<Number>) setting).getValue()));
                            break;
                        }
                        case "Boolean": {
                            object.add(setting.getName(), new JsonPrimitive(((Setting<Boolean>) setting).getValue()));
                            break;
                        }
                        case "String": {
                            object.add(setting.getName(), new JsonPrimitive(((Setting<String>) setting).getValue()));
                            break;
                        }
                    }
                }
            }
        }

        if (module.getCategory().equals(Category.HUD)) {
            //object.add("x", new JsonPrimitive());
            //object.add("y", new JsonPrimitive());
        }

        if (module.getName().equals("Watermark")) {
            object.add("text", new JsonPrimitive(ModuleManager.getWatermark().text));
        }

        object.add("enabled", new JsonPrimitive(module.isToggled()));
        object.add("bind", new JsonPrimitive(module.getKey()));
        return object;
    }

    private void savePrefix() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File output;
        if (!(output = new File(file + "/" + "prefix" + ".json")).exists()) {
            file.mkdirs();
            output.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(output.toPath())));

        JsonObject object = new JsonObject();
        object.add("prefix", new JsonPrimitive(Client.commandManager.prefix));
        String json = gson.toJson(object);
        writer.write(json);
        writer.close();
    }

    private void saveAlts() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File output;
        if (!(output = new File(file + "/" + "alts" + ".json")).exists()) {
            file.mkdirs();
            output.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(output.toPath())));

        JsonObject object = new JsonObject();
        Client.savedAlts.forEach((s, session) -> {
            String[] ss = s.split(":");
            object.add(ss[0], new JsonPrimitive(ss[1]));
        });
        String json = gson.toJson(object);
        writer.write(json);
        writer.close();
    }

    public void load() {
        try {
            loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() throws IOException {
        for (Module m : ModuleManager.getModules()) {
            File settings = new File(file + "/" + m.getName() + ".json");
            InputStream stream = Files.newInputStream(settings.toPath());
            loadSettingsFromFile(new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject(), m);
            stream.close();
        }

        loadPrefix();
        loadAlts();
    }

    private void loadSettingsFromFile(JsonObject data, Module module) {
        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            String settingName = entry.getKey();
            JsonElement value = entry.getValue();

            switch (settingName) {
                case "enabled":
                    module.setToggled(value.getAsBoolean());
                    break;
                case "bind":
                    module.setKey(value.getAsInt());
                    break;
                case "text":
                    ModuleManager.getWatermark().text = value.getAsString();
                    break;
            }

            if (module.getCategory().equals(Category.HUD)) {
            }

            Module.settingManager.getSettings(module).forEach(setting -> {
                if (settingName.equals(setting.getName())) {
                    valueLoader(setting, value);
                }
            });
        }
    }

    private void loadPrefix() throws IOException {
        File settings = new File(file + "/" + "prefix" + ".json");
        InputStream stream = Files.newInputStream(settings.toPath());
        for (Map.Entry<String, JsonElement> entry : new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject().entrySet()) {
            if(entry.getKey().equals("prefix")) {
                Client.commandManager.prefix = entry.getValue().getAsString();
            }
        }
        stream.close();
    }

    private void loadAlts() throws IOException {
        File settings = new File(file + "/" + "alts" + ".json");
        InputStream stream = Files.newInputStream(settings.toPath());
        for (Map.Entry<String, JsonElement> entry : new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject().entrySet()) {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            try{
                YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
                auth.setUsername(entry.getKey());
                auth.setPassword(entry.getValue().getAsString());
                auth.logIn();
                Session session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                Client.savedAlts.put(entry.getKey() + ":" + entry.getValue().getAsString(), session);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }
        stream.close();
    }

    private void valueLoader(Setting<?> setting, JsonElement element) {
        switch (setting.getType()) {
            case "Boolean": {
                ((Setting<Boolean>) setting).setValue(element.getAsBoolean());
                break;
            }
            case "Double": {
                ((Setting<Number>) setting).setValue(element.getAsDouble());
                break;
            }
            case "String": {
                ((Setting<String>) setting).setValue(element.getAsString());
                break;
            }
        }
    }
}
