package com.smart.spark.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fc.w on 2017/3/29.
 */
public class ConfigProperties {

    private static Properties properties = new Properties();

    static {
        try {
            String path = ConfigProperties.class.getResource("/").getPath()+"config.properties";
            InputStream input = new FileInputStream(path);
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

}
