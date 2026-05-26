package edu.usal.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppProperties {

    private static final String FILE_NAME = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (inputStream == null) {
                throw new IllegalStateException("No se encontro el archivo " + FILE_NAME);
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron cargar las properties de la aplicacion", e);
        }
    }

    private AppProperties() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
