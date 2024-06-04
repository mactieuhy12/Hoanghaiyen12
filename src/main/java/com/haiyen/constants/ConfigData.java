package com.haiyen.constants;

import com.haiyen.helpers.PropertiesHelper;

public class ConfigData {
    public static boolean HIGHLIGHT_ELEMENT = Boolean.parseBoolean(PropertiesHelper.getValue("HIGHLIGHT_ELEMENT"));
    public static double HIGHLIGHT_TIMEOUT = Double.parseDouble(PropertiesHelper.getValue("HIGHLIGHT_TIMEOUT"));
    public static String AUTHOR = PropertiesHelper.getValue("AUTHOR");
    public static String BROWSER = PropertiesHelper.getValue("BROWSER");
}
