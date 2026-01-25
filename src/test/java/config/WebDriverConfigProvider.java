package config;

import org.aeonbits.owner.ConfigFactory;

import java.util.Map;

public class WebDriverConfigProvider {

    public static WebDriverConfig get() {
        String env = System.getProperty("env", "local");
        return ConfigFactory.create(WebDriverConfig.class, System.getProperties(), Map.of("env", env));
    }
}

