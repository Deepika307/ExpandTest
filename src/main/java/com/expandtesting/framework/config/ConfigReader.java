package com.expandtesting.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to locate " + CONFIG_FILE + " on the classpath.");
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read " + CONFIG_FILE, exception);
        }
    }

    public static String getProperty(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing configuration value for key: " + key);
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static Duration getTimeout() {
        return Duration.ofSeconds(Long.parseLong(getProperty("timeout.seconds")));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public static String getLoginUsername() {
        return getProperty("login.username");
    }

    public static String getLoginPassword() {
        return getProperty("login.password");
    }

    public static String getRegisterDataPath() {
        return getProperty("register.data.path");
    }

    public static String getRegisterDataSheet() {
        return getProperty("register.data.sheet");
    }
}
