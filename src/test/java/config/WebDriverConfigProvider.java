package config;

import org.aeonbits.owner.ConfigFactory;

public class WebDriverConfigProvider {

    private WebDriverConfigProvider() {}

    public static WebDriverConfig get() {
        System.setProperty("env", System.getProperty("env", "local"));
        return ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }
}

