package com.jspbbs.core.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

    private static Properties properties = new Properties();

    static {
        PropUtil.use("application.conf");
    }

    public static void use(String fileName){
        InputStream inputStream = null;
        inputStream = PropUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != inputStream)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String get(String key){
        return get(key, "");
    }

    public static String get(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

    @Test
    public void test(){
        PropUtil.use("application.conf");
        assert "123".equals(PropUtil.get("jdbc.use", "123"));
        assert "root".equals(PropUtil.get("jdbc.user"));
    }
}
