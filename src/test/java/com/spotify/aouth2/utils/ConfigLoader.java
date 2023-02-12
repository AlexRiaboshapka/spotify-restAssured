package com.spotify.aouth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private static ConfigLoader configLoader;
    private final Properties propertyUtils;

    private ConfigLoader() {
        propertyUtils = PropertyUtils.propertiesReader("src/test/resources/config/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getGrantType() {
        String prop = propertyUtils.getProperty("grant_type");
        if (prop == null) {
            throw new RuntimeException("Property grant_type is not set");
        }
        return prop;
    }

    public String getClientId() {
        String prop = propertyUtils.getProperty("client_id");
        if (prop == null) {
            throw new RuntimeException("Property client_id is not set");
        }
        return prop;
    }

    public String getClientSecret() {
        String prop = propertyUtils.getProperty("client_secret");
        if (prop == null) {
            throw new RuntimeException("Property client_secret is not set");
        }
        return prop;
    }

    public String getUserId() {
        String prop = propertyUtils.getProperty("user_id");
        if (prop == null) {
            throw new RuntimeException("Property user_id is not set");
        }
        return prop;
    }

    public String getRefreshToken() {
        String prop = propertyUtils.getProperty("refresh_token");
        if (prop == null) {
            throw new RuntimeException("Property refresh_token is not set");
        }
        return prop;
    }
}
