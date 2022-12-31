package com.pm.utils;

import static org.apache.commons.lang3.SystemUtils.USER_DIR;

import com.pm.mobile.configuration.Capabilities;
import com.pm.mobile.configuration.TestData;
import org.aeonbits.owner.ConfigCache;

import java.io.File;

public final class FileManager {
    private static final String USER_DIR_PROPERTY = USER_DIR;

    private FileManager() {}

    public static TestData getData() {
        return ConfigCache.getOrCreate(TestData.class, System.getProperties());
    }

    public static Capabilities getCapability() {
        return ConfigCache.getOrCreate(Capabilities.class, System.getProperties());
    }

    public static String getAppLocation() {
        return getPath(getCapability().app()).replace("/", File.separator);
    }

    private static String getPath(String path) {
        return USER_DIR_PROPERTY + path;
    }
}
