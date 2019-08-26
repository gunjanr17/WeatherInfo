package com.gramani;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("App");
        FileHandler fileHandler = new FileHandler("city-weather.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        URL url = new URL("https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22");
        int count = 0;
        try (InputStream is = url.openStream();
             JsonReader rdr = Json.createReader(is)) {
            JsonObject obj = rdr.readObject();
            if (obj.containsKey("list")) {
                JsonArray results = obj.getJsonArray("list");
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    if (result.containsKey("name")) {
                        String cityName = result.getString("name");
                        if (cityName.startsWith("T")) {
                            count++;
                        }
                    }

                }
            }
        }
        logger.info("Count of cities staring with T: " + count);
    }
}
