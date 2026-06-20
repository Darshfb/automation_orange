package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
  private static Properties properties;

  static {
    try {
      FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
      properties = new Properties();
      properties.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
    }
  }

  public static String getProperty(String key) {
    String sysProp = System.getProperty(key);
    if (sysProp != null) {
      return sysProp;
    }
    return properties.getProperty(key);
  }

  public static String getProperty(String key, String defaultValue) {
    String sysProp = System.getProperty(key);
    if (sysProp != null) {
      return sysProp;
    }
    return properties.getProperty(key, defaultValue);
  }

  public static int getIntProperty(String key) {
    return Integer.parseInt(getProperty(key).trim());
  }
}
