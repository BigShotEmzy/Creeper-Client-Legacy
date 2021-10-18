package me.spruce.creeperclient.config;

import com.google.gson.*;
import me.spruce.creeperclient.module.Category;
import me.spruce.creeperclient.module.Module;
import me.spruce.creeperclient.module.ModuleManager;
import me.spruce.creeperclient.setting.n.Setting;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

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

        object.add("enabled", new JsonPrimitive(module.isToggled()));
        object.add("bind", new JsonPrimitive(module.getKey()));
        return object;
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
        }
    }

    private void loadSettingsFromFile(JsonObject data, Module module) {
        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            String settingName = entry.getKey();
            JsonElement value = entry.getValue();

            if (settingName.equals("enabled")) {
                module.setToggled(value.getAsBoolean());
            }

            if (settingName.equals("bind")) {
                module.setKey(value.getAsInt());
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

    private void valueLoader(Setting<?> setting, JsonElement element) {
        switch (setting.getType()) {
            case "Boolean": {
                ((Setting<Boolean>) setting).setValue(element.getAsBoolean());
            }
            case "Double": {
                ((Setting<Number>) setting).setValue(element.getAsDouble());
            }
            case "String": {
                ((Setting<String>) setting).setValue(element.getAsString());
            }
        }
    }
}
