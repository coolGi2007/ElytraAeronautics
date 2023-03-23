package com.github.Soulphur0.config.singletons;

import com.github.Soulphur0.config.clothConfig.FlightConfigScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FlightConfig {

    // = Elytra flight attributes

    // ; Flight speed settings.
    private boolean altitudeDeterminesSpeed = true;
    private double minSpeed = 30.35;
    private double maxSpeed = 257.22;
    private double minHeight = 250.0;
    private double maxHeight = 1000.0;

    // ; Flight alignment settings.
    private boolean sneakingRealignsPitch = true;
    private float realignAngle = 0.0F;
    private float realignRate = 0.1F;

    private static FlightConfig instance;

    public static FlightConfig getOrCreateInstance() {
        if (instance == null){
            instance = new FlightConfig();
        }
        return instance;
    }

    // $ ClothConfig config updater.
    // € Updates the config instance with the config screen values, which are automatically saved on disk.
    public static void updateConfig(FlightConfigScreen config){
        getOrCreateInstance();

        instance.setAltitudeDeterminesSpeed(config.altitudeDeterminesSpeed);
        instance.setMinSpeed(config.minSpeed);
        instance.setMaxSpeed(config.maxSpeed);
        instance.setMinHeight(config.minHeight);
        instance.setMaxHeight(config.maxHeight);
        instance.setSneakingRealignsPitch(config.sneakingRealignsPitch);
        instance.setRealignAngle(config.realignAngle);
        instance.setRealignRate(config.realignRate);
    }

    // $ Non-ClothConfig config updater
    // € Updates are done via setters in the command methods, where the writeToDisk method is called right after.

    // ? Only called once in mod initialization.
    public static void readFromDisk(){
        // - Extract json as string
        StringBuilder json = new StringBuilder();

        try{
            // Create dir if it doesn't exist
            File directory = new File("config/ElytraAeronautics");
            if (!directory.exists())
                directory.mkdir();

            File file = new File("config/ElytraAeronautics/flight_settings.json");

            // If file doesn't exist yet, create instance with default values and write it to disk.
            if (file.createNewFile()) {
                getOrCreateInstance();
                writeToDisk();
            } else {
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    json.append(reader.nextLine());
                }
                reader.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // - Convert extracted string into the config instance.
        Gson gson = new Gson();
        instance = gson.fromJson(String.valueOf(json), FlightConfig.class);
    }

    // ? Called every time the config is updated via command.
    public static void writeToDisk(){
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(instance);

        try {
            FileWriter writer = new FileWriter("config/ElytraAeronautics/flight_settings.json");
            writer.write(json);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // $ GETTERS & SETTERS
    public boolean isAltitudeDeterminesSpeed() {
        return altitudeDeterminesSpeed;
    }

    public void setAltitudeDeterminesSpeed(boolean altitudeDeterminesSpeed) {
        this.altitudeDeterminesSpeed = altitudeDeterminesSpeed;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public boolean isSneakingRealignsPitch() {
        return sneakingRealignsPitch;
    }

    public void setSneakingRealignsPitch(boolean sneakingRealignsPitch) {
        this.sneakingRealignsPitch = sneakingRealignsPitch;
    }

    public float getRealignAngle() {
        return realignAngle;
    }

    public void setRealignAngle(float realignAngle) {
        this.realignAngle = realignAngle;
    }

    public float getRealignRate() {
        return realignRate;
    }

    public void setRealignRate(float realignRate) {
        this.realignRate = realignRate;
    }

    public enum Options {
        altitudeDeterminesSpeed,
        minSpeed,
        maxSpeed,
        minHeight,
        maxHeight,
        sneakingRealignsPitch,
        realignAngle,
        realignRate
    }
}