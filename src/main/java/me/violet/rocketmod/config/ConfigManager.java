package me.violet.rocketmod.config;

import me.violet.rocketmod.Rocketmod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class ConfigManager {
    private static final Path CONFIG_FOLDER = FabricLoader.getInstance().getGameDir().resolve("rocketmod");
    private static final File CONFIG = Path.of(CONFIG_FOLDER.toString() + "\\delay.txt").toFile();

    public static void load() {
        if (!CONFIG_FOLDER.toFile().exists()) CONFIG_FOLDER.toFile().mkdirs();
        try {
            if (!CONFIG.exists()) CONFIG.createNewFile();
            Scanner fileReader = new Scanner(CONFIG);
            double delay = 30;
            if(fileReader.hasNextLine()) delay = Double.parseDouble(fileReader.nextLine());
            fileReader.close();

            Rocketmod.INSTANCE.updateDelay(delay);
            Rocketmod.LOGGER.info("Loaded the config!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        if (!CONFIG_FOLDER.toFile().exists()) CONFIG_FOLDER.toFile().mkdirs();
        try {
            if (!CONFIG.exists()) CONFIG.createNewFile();
            FileWriter fileWriter = new FileWriter(CONFIG);
            fileWriter.write(String.valueOf(Rocketmod.INSTANCE.getDelay()));
            fileWriter.close();
            Rocketmod.LOGGER.info("Saved the config!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
